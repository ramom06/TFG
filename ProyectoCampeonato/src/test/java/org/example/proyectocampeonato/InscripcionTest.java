package org.example.proyectocampeonato;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.excepcion.CategoriaNotFoundException;
import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException;
import org.example.proyectocampeonato.excepcion.InscripcionNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.example.proyectocampeonato.repository.CompetidorRepository;
import org.example.proyectocampeonato.repository.InscripcionRepository;
import org.example.proyectocampeonato.service.InscripcionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscripcionTest {

    @Mock private InscripcionRepository inscripcionRepository;
    @Mock private CampeonatoRepository  campeonatoRepository;
    @Mock private CategoriaRepository   categoriaRepository;
    @Mock private CompetidorRepository  competidorRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    private Campeonato campeonato;
    private Categoria  categoriaKumite;
    private Categoria  categoriaKata;
    private Competidor competidor;

    @BeforeEach
    void setUp() {
        campeonato = Campeonato.builder()
                .nombre("Campeonato test")
                .ubicacion("Madrid")
                .estado("futuro")
                .nivel("Nacional")
                .build();
        campeonato.setIdCampeonato(1L);

        categoriaKumite = Categoria.builder()
                .nombre("Senior Masculino <75kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(75.0)
                .build();
        categoriaKumite.setIdCategoria(10L);

        categoriaKata = Categoria.builder()
                .nombre("Senior Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .build();
        categoriaKata.setIdCategoria(11L);

        competidor = Competidor.builder()
                .club("Mi Club")
                .federacionAutonomica("Andalucía")
                .build();
        competidor.setIdUsuario(100L);
        competidor.setNombre("Pedro");
        competidor.setApellidos("Sánchez");
    }

    // ── getters ──────────────────────────────────────────────────────────────

    @Test
    void getByCompetidor_devuelveSusInscripciones() {
        when(inscripcionRepository.findByCompetidor(100L)).thenReturn(List.of());
        assertThat(inscripcionService.getByCompetidor(100L)).isEmpty();
        verify(inscripcionRepository, times(1)).findByCompetidor(100L);
    }

    @Test
    void getByCampeonatoAndCategoria_devuelveInscritos() {
        when(inscripcionRepository.findByCampeonatoAndCategoria(1L, 10L)).thenReturn(List.of());
        assertThat(inscripcionService.getByCampeonatoAndCategoria(1L, 10L)).isEmpty();
    }

    // ── save (camino feliz) ──────────────────────────────────────────────────

    @Test
    void save_inscripcionValida_persisteYDevuelve() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoriaKumite));
        when(competidorRepository.findById(100L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(false);
        when(inscripcionRepository.findByCompetidor(100L)).thenReturn(List.of());
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(inv -> inv.getArgument(0));

        Inscripcion resultado = inscripcionService.save(1L, 10L, 100L);

        assertThat(resultado.getCategoria()).isEqualTo(categoriaKumite);
        assertThat(resultado.getCompetidor()).isEqualTo(competidor);
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    // ── save (validaciones de negocio) ───────────────────────────────────────

    @Test
    void save_campeonatoInexistente_lanzaCampeonatoNotFound() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscripcionService.save(99L, 10L, 100L))
                .isInstanceOf(CampeonatoNotFoundException.class);
    }

    @Test
    void save_campeonatoConInscripcionesCerradas_lanza409() {
        campeonato.setEstado("inscripciones_cerradas");
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));

        assertThatThrownBy(() -> inscripcionService.save(1L, 10L, 100L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("409")
                .hasMessageContaining("cerradas");
    }

    @Test
    void save_campeonatoPasado_lanza409() {
        campeonato.setEstado("pasado");
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));

        assertThatThrownBy(() -> inscripcionService.save(1L, 10L, 100L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("409");
    }

    @Test
    void save_categoriaInexistente_lanzaCategoriaNotFound() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscripcionService.save(1L, 99L, 100L))
                .isInstanceOf(CategoriaNotFoundException.class);
    }

    @Test
    void save_competidorInexistente_lanzaCompetidorNotFound() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoriaKumite));
        when(competidorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscripcionService.save(1L, 10L, 999L))
                .isInstanceOf(CompetidorNotFoundException.class);
    }

    @Test
    void save_inscripcionDuplicada_lanza409() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoriaKumite));
        when(competidorRepository.findById(100L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(true);

        assertThatThrownBy(() -> inscripcionService.save(1L, 10L, 100L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("409")
                .hasMessageContaining("ya está inscrito");
    }

    @Test
    void save_modalidadYaInscrita_lanza409() {
        // El competidor ya tiene OTRA inscripción en kumite en el mismo campeonato
        Inscripcion existenteKumite = Inscripcion.builder()
                .idInscripcion(new Inscripcion_Id(1L, 77L, 100L))
                .campeonato(campeonato)
                .categoria(Categoria.builder().modalidad("kumite").build())
                .competidor(competidor)
                .build();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoriaKumite));
        when(competidorRepository.findById(100L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(false);
        when(inscripcionRepository.findByCompetidor(100L)).thenReturn(List.of(existenteKumite));

        assertThatThrownBy(() -> inscripcionService.save(1L, 10L, 100L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("409")
                .hasMessageContaining("kumite");

        verify(inscripcionRepository, never()).save(any());
    }

    @Test
    void save_competidorConKumiteEnOtroCampeonato_permiteInscripcionEnEsteCampeonato() {
        // Inscripción kumite previa en OTRO campeonato (idCampeonato=2) → no debe bloquear
        Campeonato otro = Campeonato.builder().nombre("Otro").estado("futuro").ubicacion("X").nivel("N").build();
        otro.setIdCampeonato(2L);

        Inscripcion enOtroCamp = Inscripcion.builder()
                .idInscripcion(new Inscripcion_Id(2L, 77L, 100L))
                .campeonato(otro)
                .categoria(Categoria.builder().modalidad("kumite").build())
                .competidor(competidor)
                .build();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoriaKumite));
        when(competidorRepository.findById(100L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(false);
        when(inscripcionRepository.findByCompetidor(100L)).thenReturn(List.of(enOtroCamp));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(inv -> inv.getArgument(0));

        Inscripcion resultado = inscripcionService.save(1L, 10L, 100L);

        assertThat(resultado).isNotNull();
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    @Test
    void save_competidorConKataPrevia_permiteInscripcionEnKumite() {
        // Inscripción kata previa en mismo campeonato → puede apuntarse a kumite (modalidad distinta)
        Inscripcion existenteKata = Inscripcion.builder()
                .idInscripcion(new Inscripcion_Id(1L, 11L, 100L))
                .campeonato(campeonato)
                .categoria(categoriaKata)
                .competidor(competidor)
                .build();

        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(10L)).thenReturn(Optional.of(categoriaKumite));
        when(competidorRepository.findById(100L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(false);
        when(inscripcionRepository.findByCompetidor(100L)).thenReturn(List.of(existenteKata));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(inv -> inv.getArgument(0));

        Inscripcion resultado = inscripcionService.save(1L, 10L, 100L);

        assertThat(resultado.getCategoria().getModalidad()).isEqualTo("kumite");
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    void delete_inscripcionExistente_eliminaCorrectamente() {
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(true);

        assertThatCode(() -> inscripcionService.delete(1L, 10L, 100L))
                .doesNotThrowAnyException();

        verify(inscripcionRepository, times(1)).deleteById(any(Inscripcion_Id.class));
    }

    @Test
    void delete_inscripcionInexistente_lanzaInscripcionNotFound() {
        when(inscripcionRepository.existsById(any(Inscripcion_Id.class))).thenReturn(false);

        assertThatThrownBy(() -> inscripcionService.delete(1L, 10L, 100L))
                .isInstanceOf(InscripcionNotFoundException.class);

        verify(inscripcionRepository, never()).deleteById(any(Inscripcion_Id.class));
    }
}
