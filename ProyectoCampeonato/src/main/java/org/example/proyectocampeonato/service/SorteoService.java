package org.example.proyectocampeonato.service;

import lombok.RequiredArgsConstructor;
import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.repository.CombateRepository;
import org.example.proyectocampeonato.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SorteoService {

    public static final String ESTADO_INSCRIPCIONES_OK = "inscripciones_cerradas";
    public static final String ESTADO_FINALIZADO       = "pasado";

    public static final String COMBATE_PENDIENTE  = "pendiente";
    public static final String COMBATE_FINALIZADO = "finalizado";

    private final CampeonatoRepository  campeonatoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final CombateRepository     combateRepository;

    private final Random random = new Random();

    // ── Sortear solo la primera ronda ────────────────────────────────────────

    /**
     * Sortea la primera ronda de todas las categorías del campeonato.
     * Idempotente: si una categoría ya tiene combates, no se vuelve a sortear.
     * Cambia el estado del campeonato a "inscripciones_cerradas".
     */
    @Transactional
    public Campeonato sortearPrimeraRonda(Long idCampeonato) {
        Campeonato c = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new CampeonatoNotFoundException(idCampeonato));

        for (Campeonato_Categoria cc : c.getCampeonatoCategorias()) {
            sortearPrimeraRondaCategoria(idCampeonato, cc.getCategoria().getIdCategoria());
        }

        // No degradamos el estado si ya estaba en "pasado"
        if (!ESTADO_FINALIZADO.equals(c.getEstado())) {
            c.setEstado(ESTADO_INSCRIPCIONES_OK);
        }
        return campeonatoRepository.save(c);
    }

    // ── Sorteo completo: primera ronda + desarrollo hasta el ganador ─────────

    @Transactional
    public Campeonato sortearCompleto(Long idCampeonato) {
        Campeonato c = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new CampeonatoNotFoundException(idCampeonato));

        for (Campeonato_Categoria cc : c.getCampeonatoCategorias()) {
            Long idCategoria = cc.getCategoria().getIdCategoria();
            sortearPrimeraRondaCategoria(idCampeonato, idCategoria);
            // Flush para que desarrollarSorteoCategoria vea los combates recién creados
            combateRepository.flush();
            desarrollarSorteoCategoria(idCampeonato, idCategoria);
        }

        c.setEstado(ESTADO_FINALIZADO);
        return campeonatoRepository.save(c);
    }

    // ── Implementación interna ───────────────────────────────────────────────

    private void sortearPrimeraRondaCategoria(Long idCampeonato, Long idCategoria) {
        // si ya hay combates, no se vuelve a sortear
        List<Combate> existentes = combateRepository.findByIdIdCampeonatoAndIdIdCategoria(idCampeonato, idCategoria);
        if (!existentes.isEmpty()) return;

        List<Competidor> competidores = inscripcionRepository
                .findByCampeonatoAndCategoria(idCampeonato, idCategoria).stream()
                .map(Inscripcion::getCompetidor)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));

        if (competidores.isEmpty()) return;

        // 1 competidor: ganador directo
        if (competidores.size() == 1) {
            combateRepository.save(Combate.builder()
                    .idCombate(new Combate_Id(idCampeonato, idCategoria, 1))
                    .ronda(nombreRonda(2))
                    .competidorRojo(competidores.get(0))
                    .competidorAzul(null)
                    .estado(COMBATE_FINALIZADO)
                    .puntuacionRojo(0)
                    .puntuacionAzul(0)
                    .build());
            return;
        }

        // >= 2 competidores: armar cuadro de sorteo con byes
        int tamanoSorteo = nextPowerOf2(competidores.size());
        int numByes      = tamanoSorteo - competidores.size();

        List<Competidor> participantesOrdenados = new ArrayList<>(competidores);
        for (int i = 0; i < numByes; i++) participantesOrdenados.add(null);
        Collections.shuffle(participantesOrdenados, random);

        String ronda = nombreRonda(tamanoSorteo);
        int numero   = 1;
        for (int i = 0; i < participantesOrdenados.size(); i += 2) {
            Competidor rojo = participantesOrdenados.get(i);
            Competidor azul = participantesOrdenados.get(i + 1);

            // Si el rojo es null, intercambiamos: el bye se representa con azul=null
            if (rojo == null) { rojo = azul; azul = null; }

            String estado = (azul == null) ? COMBATE_FINALIZADO : COMBATE_PENDIENTE;

            combateRepository.save(Combate.builder()
                    .idCombate(new Combate_Id(idCampeonato, idCategoria, numero++))
                    .ronda(ronda)
                    .competidorRojo(rojo)
                    .competidorAzul(azul)
                    .estado(estado)
                    .puntuacionRojo(0)
                    .puntuacionAzul(0)
                    .build());
        }
    }

    private void desarrollarSorteoCategoria(Long idCampeonato, Long idCategoria) {
        List<Combate> todos = combateRepository
                .findByIdIdCampeonatoAndIdIdCategoria(idCampeonato, idCategoria);
        if (todos.isEmpty()) return; // categoría sin inscritos, nada que hacer

        int maxNumero = todos.stream()
                .mapToInt(c -> c.getIdCombate().getNumeroCombate())
                .max().orElse(0);

        Map<String, List<Combate>> porRonda = new HashMap<>();
        for (Combate cm : todos) {
            porRonda.computeIfAbsent(cm.getRonda(), k -> new ArrayList<>()).add(cm);
        }

        List<Combate> rondaActual = rondaMasReciente(porRonda);
        rondaActual.sort(Comparator.comparingInt(c -> c.getIdCombate().getNumeroCombate()));

        int siguienteNumero = maxNumero + 1;

        while (rondaActual.size() >= 2) {
            // Resolver pendientes de la ronda actual
            for (Combate cm : rondaActual) {
                if (COMBATE_PENDIENTE.equals(cm.getEstado())) {
                    resolverAleatorio(cm);
                    combateRepository.save(cm);
                }
            }

            // Construir la siguiente ronda con los ganadores
            List<Competidor> ganadores = new ArrayList<>(rondaActual.size());
            for (Combate cm : rondaActual) ganadores.add(ganadorDe(cm));

            int tamanoSiguienteRonda = ganadores.size();
            String rondaSiguiente    = nombreRonda(tamanoSiguienteRonda);

            List<Combate> nuevaRonda = new ArrayList<>();
            for (int i = 0; i < ganadores.size(); i += 2) {
                Competidor rojo = ganadores.get(i);
                Competidor azul = ganadores.get(i + 1);

                Combate nuevo = Combate.builder()
                        .idCombate(new Combate_Id(idCampeonato, idCategoria, siguienteNumero++))
                        .ronda(rondaSiguiente)
                        .competidorRojo(rojo)
                        .competidorAzul(azul)
                        .estado(COMBATE_PENDIENTE)
                        .puntuacionRojo(0)
                        .puntuacionAzul(0)
                        .build();
                nuevaRonda.add(combateRepository.save(nuevo));
            }
            rondaActual = nuevaRonda;
        }

        // El último combate (final) puede haber quedado pendiente
        if (rondaActual.size() == 1) {
            Combate finalC = rondaActual.get(0);
            if (COMBATE_PENDIENTE.equals(finalC.getEstado())) {
                resolverAleatorio(finalC);
                combateRepository.save(finalC);
            }
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private List<Combate> rondaMasReciente(Map<String, List<Combate>> porRonda) {
        return porRonda.values().stream()
                .max(Comparator.comparingInt(list -> list.stream()
                        .mapToInt(c -> c.getIdCombate().getNumeroCombate())
                        .max().orElse(0)))
                .orElseThrow();
    }

    private void resolverAleatorio(Combate c) {
        // Bye: ya tiene ganador (el rojo), solo se confirma estado
        if (c.getCompetidorAzul() == null || c.getCompetidorRojo() == null) {
            c.setEstado(COMBATE_FINALIZADO);
            return;
        }
        boolean rojoGana    = random.nextBoolean();
        int puntosGanador   = 3 + random.nextInt(8);            // 3..10
        int puntosPerdedor  = random.nextInt(puntosGanador);    // 0..ganador-1
        if (rojoGana) {
            c.setPuntuacionRojo(puntosGanador);
            c.setPuntuacionAzul(puntosPerdedor);
        } else {
            c.setPuntuacionAzul(puntosGanador);
            c.setPuntuacionRojo(puntosPerdedor);
        }
        c.setEstado(COMBATE_FINALIZADO);
    }

    private Competidor ganadorDe(Combate c) {
        if (c.getCompetidorAzul() == null) return c.getCompetidorRojo();
        if (c.getCompetidorRojo() == null) return c.getCompetidorAzul();
        return (c.getPuntuacionRojo() >= c.getPuntuacionAzul())
                ? c.getCompetidorRojo()
                : c.getCompetidorAzul();
    }

    private int nextPowerOf2(int n) {
        int p = 1;
        while (p < n) p *= 2;
        return p;
    }

    private String nombreRonda(int tamano) {
        return switch (tamano) {
            case 2  -> "final";
            case 4  -> "semifinal";
            case 8  -> "cuartos";
            case 16 -> "octavos";
            case 32 -> "dieciseisavos";
            default -> "ronda_" + tamano;
        };
    }
}
