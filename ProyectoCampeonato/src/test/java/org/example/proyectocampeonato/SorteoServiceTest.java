package org.example.proyectocampeonato;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.repository.CombateRepository;
import org.example.proyectocampeonato.repository.InscripcionRepository;
import org.example.proyectocampeonato.service.SorteoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SorteoServiceTest {

    @Mock private CampeonatoRepository  campeonatoRepository;
    @Mock private InscripcionRepository inscripcionRepository;
    @Mock private CombateRepository     combateRepository;

    @InjectMocks
    private SorteoService sorteoService;

    private Campeonato campeonato;
    private Categoria  categoria;
    private Campeonato_Categoria cc;

    @BeforeEach
    void setUp() {
        campeonato = Campeonato.builder()
                .nombre("Campeonato test")
                .ubicacion("Madrid")
                .estado("futuro")
                .nivel("Nacional")
                .build();
        campeonato.setIdCampeonato(1L);

        categoria = Categoria.builder()
                .nombre("Senior Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .build();
        categoria.setIdCategoria(10L);

        cc = new Campeonato_Categoria();
        cc.setIdCampeonatoCategoria(new Campeonato_Categoria_Id(1L, 10L));
        cc.setCampeonato(campeonato);
        cc.setCategoria(categoria);

        // @Builder de Lombok ignora la inicialización inline del Set, hay que asignarlo aquí
        campeonato.setCampeonatoCategorias(new HashSet<>());
        campeonato.getCampeonatoCategorias().add(cc);
    }

    private Competidor competidor(long id, String nombre) {
        Competidor c = Competidor.builder().club("Club").federacionAutonomica("FED").build();
        c.setIdUsuario(id);
        c.setNombre(nombre);
        c.setApellidos("Apellido");
        return c;
    }

    private Inscripcion inscripcion(Competidor c) {
        return Inscripcion.builder()
                .idInscripcion(new Inscripcion_Id(1L, 10L, c.getIdUsuario()))
                .campeonato(campeonato)
                .categoria(categoria)
                .competidor(c)
                .build();
    }

    @Test
    void sortearPrimeraRonda_campeonatoInexistente_lanzaCampeonatoNotFound() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sorteoService.sortearPrimeraRonda(99L))
                .isInstanceOf(CampeonatoNotFoundException.class);
    }

    @Test
    void sortearPrimeraRonda_marcaEstadoComoInscripcionesCerradas() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L)).thenReturn(List.of());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        Campeonato resultado = sorteoService.sortearPrimeraRonda(1L);

        assertThat(resultado.getEstado()).isEqualTo("inscripciones_cerradas");
    }

    @Test
    void sortearPrimeraRonda_noDegradaEstadoSiYaEstabaPasado() {
        campeonato.setEstado("pasado");
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L)).thenReturn(List.of());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        Campeonato resultado = sorteoService.sortearPrimeraRonda(1L);

        assertThat(resultado.getEstado()).isEqualTo("pasado");
    }


    @Test
    void sortearPrimeraRonda_categoriaSinInscritos_noCreaCombates() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L)).thenReturn(List.of());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        sorteoService.sortearPrimeraRonda(1L);

        verify(combateRepository, never()).save(any(Combate.class));
    }

    @Test
    void sortearPrimeraRonda_unSoloInscrito_creaUnCombateFinalizadoComoBye() {
        Competidor c1 = competidor(1L, "Solo");

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L)).thenReturn(List.of(inscripcion(c1)));
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        sorteoService.sortearPrimeraRonda(1L);

        ArgumentCaptor<Combate> captor = ArgumentCaptor.forClass(Combate.class);
        verify(combateRepository, times(1)).save(captor.capture());

        Combate creado = captor.getValue();
        assertThat(creado.getCompetidorRojo()).isEqualTo(c1);
        assertThat(creado.getCompetidorAzul()).isNull();
        assertThat(creado.getEstado()).isEqualTo("finalizado");
        assertThat(creado.getRonda()).isEqualTo("final");
    }

    @Test
    void sortearPrimeraRonda_cuatroInscritos_creaDosCombatesEnSemifinal() {
        List<Competidor> comps = IntStream.rangeClosed(1, 4)
                .mapToObj(i -> competidor(i, "C" + i)).toList();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L))
                .thenReturn(comps.stream().map(this::inscripcion).toList());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        sorteoService.sortearPrimeraRonda(1L);

        ArgumentCaptor<Combate> captor = ArgumentCaptor.forClass(Combate.class);
        verify(combateRepository, times(2)).save(captor.capture());

        List<Combate> creados = captor.getAllValues();
        assertThat(creados).allSatisfy(c -> {
            assertThat(c.getRonda()).isEqualTo("semifinal");
            assertThat(c.getCompetidorRojo()).isNotNull();
            assertThat(c.getCompetidorAzul()).isNotNull();
            assertThat(c.getEstado()).isEqualTo("pendiente");
        });
    }

    @Test
    void sortearPrimeraRonda_seisInscritos_creaCuatroCombatesEnCuartosConDosByes() {
        // Bracket = 8, 6 inscritos → 2 byes (combates con azul=null, ya finalizados)
        List<Competidor> comps = IntStream.rangeClosed(1, 6)
                .mapToObj(i -> competidor(i, "C" + i)).toList();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L))
                .thenReturn(comps.stream().map(this::inscripcion).toList());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        sorteoService.sortearPrimeraRonda(1L);

        ArgumentCaptor<Combate> captor = ArgumentCaptor.forClass(Combate.class);
        verify(combateRepository, times(4)).save(captor.capture());

        List<Combate> creados = captor.getAllValues();
        long byes      = creados.stream().filter(c -> c.getCompetidorAzul() == null).count();
        long regulares = creados.stream().filter(c -> c.getCompetidorAzul() != null).count();

        assertThat(byes).isEqualTo(2);
        assertThat(regulares).isEqualTo(2);
        assertThat(creados).allSatisfy(c -> assertThat(c.getRonda()).isEqualTo("cuartos"));
        // Los byes tienen rojo no nulo y se persisten como finalizados
        creados.stream().filter(c -> c.getCompetidorAzul() == null).forEach(c -> {
            assertThat(c.getCompetidorRojo()).isNotNull();
            assertThat(c.getEstado()).isEqualTo("finalizado");
        });
    }

    @Test
    void sortearPrimeraRonda_ochoInscritos_creaCuatroCombatesEnCuartos() {
        List<Competidor> comps = IntStream.rangeClosed(1, 8)
                .mapToObj(i -> competidor(i, "C" + i)).toList();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L))
                .thenReturn(comps.stream().map(this::inscripcion).toList());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        sorteoService.sortearPrimeraRonda(1L);

        ArgumentCaptor<Combate> captor = ArgumentCaptor.forClass(Combate.class);
        verify(combateRepository, times(4)).save(captor.capture());

        assertThat(captor.getAllValues()).allSatisfy(c -> {
            assertThat(c.getRonda()).isEqualTo("cuartos");
            assertThat(c.getCompetidorRojo()).isNotNull();
            assertThat(c.getCompetidorAzul()).isNotNull();
            assertThat(c.getEstado()).isEqualTo("pendiente");
        });
        // Los 8 competidores deben aparecer exactamente una vez (en rojo o azul)
        Set<Long> ids = new HashSet<>();
        captor.getAllValues().forEach(c -> {
            ids.add(c.getCompetidorRojo().getIdUsuario());
            ids.add(c.getCompetidorAzul().getIdUsuario());
        });
        assertThat(ids).hasSize(8);
    }

    @Test
    void sortearPrimeraRonda_idempotente_categoriaConCombatesExistentesNoSeReSortea() {
        // Si findByIdIdCampeonatoAndIdIdCategoria devuelve algo, no se debe re-sortear
        Combate existente = Combate.builder()
                .idCombate(new Combate_Id(1L, 10L, 1))
                .ronda("cuartos")
                .estado("pendiente")
                .puntuacionRojo(0).puntuacionAzul(0)
                .build();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L))
                .thenReturn(List.of(existente));
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        sorteoService.sortearPrimeraRonda(1L);

        // No se debe consultar inscripciones (return temprano por idempotencia)
        verify(inscripcionRepository, never()).findByCampeonatoAndCategoria(anyLong(), anyLong());
        // No se debe persistir ningún combate nuevo
        verify(combateRepository, never()).save(any(Combate.class));
    }

    @Test
    void sortearCompleto_marcaEstadoComoPasado() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 10L)).thenReturn(List.of());
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L)).thenReturn(List.of());
        when(campeonatoRepository.save(any(Campeonato.class))).thenAnswer(inv -> inv.getArgument(0));

        Campeonato resultado = sorteoService.sortearCompleto(1L);

        assertThat(resultado.getEstado()).isEqualTo("pasado");
    }

    @Test
    void sortearCompleto_campeonatoInexistente_lanzaCampeonatoNotFound() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sorteoService.sortearCompleto(99L))
                .isInstanceOf(CampeonatoNotFoundException.class);
    }
}
