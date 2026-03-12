package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CombateService {

    private final CombateRepository combateRepository;
    private final CompetidorRepository competidorRepository;
    private final Campeonato_CategoriaRepository campeonatoCategoriaRepository;

    public CombateService(CombateRepository combateRepository,
                          CompetidorRepository competidorRepository,
                          Campeonato_CategoriaRepository campeonatoCategoriaRepository) {
        this.combateRepository = combateRepository;
        this.competidorRepository = competidorRepository;
        this.campeonatoCategoriaRepository = campeonatoCategoriaRepository;
    }

    // ── Record usado como body de entrada para POST/PUT ───────────────────────
    public record CombateRequest(
            Long idCampeonato,
            Long idCategoria,
            Integer numeroTatami,
            Integer numeroCombate,
            Long idCompetidorRojo,
            Long idCompetidorAzul,
            String ronda,
            Integer puntuacionRojo,
            Integer puntuacionAzul,
            String senshu,
            String estado,
            LocalTime horaProgramada,
            LocalDateTime horaInicioReal,
            Integer duracionSegundos,
            String observaciones
    ) {}

    // ─────────────────────────────────────────────────────────────────────────

    public List<Combate> getAll() {
        return combateRepository.findAll();
    }

    public List<Combate> getByCampeonatoCategoria(Long idCampeonato, Long idCategoria) {
        return combateRepository.findByIdIdCampeonatoAndIdIdCategoria(idCampeonato, idCategoria);
    }

    public List<Combate> getByCompetidor(Long idCompetidor) {
        return combateRepository.findByCompetidor(idCompetidor);
    }

    public Combate one(Combate_Id id) {
        return combateRepository.findById(id)
                .orElseThrow(() -> new CampeonatoNotFoundException(id.getIdCampeonato()));
    }

    @Transactional
    public Combate save(CombateRequest req) {
        Combate entidad = buildCombate(req);
        return combateRepository.save(entidad);
    }

    @Transactional
    public Combate replace(Combate_Id id, CombateRequest req) {
        return combateRepository.findById(id)
                .map(existing -> {
                    Combate entidad = buildCombate(req);
                    entidad.setId(id);
                    return combateRepository.save(entidad);
                })
                .orElseThrow(() -> new CampeonatoNotFoundException(id.getIdCampeonato()));
    }

    @Transactional
    public void delete(Combate_Id id) {
        if (!combateRepository.existsById(id))
            throw new CampeonatoNotFoundException(id.getIdCampeonato());
        combateRepository.deleteById(id);
    }

    // ── helper ───────────────────────────────────────────────────────────────

    private Combate buildCombate(CombateRequest req) {
        Competidor rojo = competidorRepository.findById(req.idCompetidorRojo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Competidor rojo con id " + req.idCompetidorRojo() + " no encontrado"));

        Campeonato_Categoria_Id ccId = new Campeonato_Categoria_Id(req.idCampeonato(), req.idCategoria());
        Campeonato_Categoria campeonatoCategoria = campeonatoCategoriaRepository.findById(ccId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe esa combinación de campeonato y categoría"));

        Combate_Id id = new Combate_Id(
                req.idCampeonato(),
                req.idCategoria(),
                req.numeroTatami(),
                req.numeroCombate()
        );

        Combate.CombateBuilder builder = Combate.builder()
                .id(id)
                .ronda(req.ronda())
                .estado(req.estado())
                .senshu(req.senshu())
                .puntuacionRojo(req.puntuacionRojo() != null ? req.puntuacionRojo() : 0)
                .puntuacionAzul(req.puntuacionAzul() != null ? req.puntuacionAzul() : 0)
                .horaProgramada(req.horaProgramada())
                .horaInicioReal(req.horaInicioReal())
                .duracionSegundos(req.duracionSegundos())
                .observaciones(req.observaciones())
                .competidorRojo(rojo)
                .campeonatoCategoria(campeonatoCategoria);

        if (req.idCompetidorAzul() != null) {
            Competidor azul = competidorRepository.findById(req.idCompetidorAzul())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Competidor azul con id " + req.idCompetidorAzul() + " no encontrado"));
            builder.competidorAzul(azul);
        }

        return builder.build();
    }
}