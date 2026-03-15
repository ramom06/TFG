package org.example.proyectocampeonato;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
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
    @Mock private CampeonatoRepository campeonatoRepository;
    @Mock private CategoriaRepository categoriaRepository;
    @Mock private CompetidorRepository competidorRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    private Campeonato campeonato;
    private Categoria categoria;
    private Competidor competidor;
    private Inscripcion inscripcion;

    @BeforeEach
    void setUp() {
        campeonato = Campeonato.builder()
                .nombre("Campeonato de España Absoluto 2026")
                .ubicacion("Madrid")
                .estado("futuro")
                .nivel("Nacional")
                .build();
        campeonato.setId_campeonato(1L);

        categoria = Categoria.builder()
                .nombre("Senior Masculino <75kg")
                .modalidad("kumite")
                .genero("M")
                .build();
        categoria.setId_categoria(2L);

        competidor = Competidor.builder()
                .nombre("Juan")
                .apellidos("Pérez")
                .rol(Usuario.Rol.COMPETIDOR)
                .build();
        competidor.setIdUsuario(3L);

        Inscripcion_Id inscId = new Inscripcion_Id(1L, 2L, 3L);
        inscripcion = Inscripcion.builder()
                .id_inscripcion(inscId)
                .campeonato(campeonato)
                .categoria(categoria)
                .competidor(competidor)
                .build();
    }

    // ── getByCampeonatoAndCategoria ───────────────────────────────────────────

    @Test
    void getByCampeonatoAndCategoria_devuelveInscripciones() {
        when(inscripcionRepository.findByCampeonatoAndCategoria(1, 2L))
                .thenReturn(List.of(inscripcion));

        List<Inscripcion> resultado = inscripcionService.getByCampeonatoAndCategoria(1L, 2L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCompetidor().getNombre()).isEqualTo("Juan");
    }

    @Test
    void getByCampeonatoAndCategoria_sinInscritos_listaVacia() {
        when(inscripcionRepository.findByCampeonatoAndCategoria(99, 99L))
                .thenReturn(List.of());

        assertThat(inscripcionService.getByCampeonatoAndCategoria(99L, 99L)).isEmpty();
    }

    // ── save ──────────────────────────────────────────────────────────────────

    @Test
    void save_inscripcionValida_guardaYDevuelve() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoria));
        when(competidorRepository.findById(3L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any())).thenReturn(false);
        when(inscripcionRepository.save(any())).thenReturn(inscripcion);

        Inscripcion resultado = inscripcionService.save(1L, 2L, 3L);

        assertThat(resultado.getCompetidor().getNombre()).isEqualTo("Juan");
        verify(inscripcionRepository, times(1)).save(any());
    }

    @Test
    void save_campeonatoInexistente_lanzaExcepcion() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscripcionService.save(99L, 2L, 3L))
                .isInstanceOf(CampeonatoNotFoundException.class);
    }

    @Test
    void save_categoriaInexistente_lanzaExcepcion() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscripcionService.save(1L, 99L, 3L))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void save_competidorYaInscrito_lanzaConflict() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoria));
        when(competidorRepository.findById(3L)).thenReturn(Optional.of(competidor));
        when(inscripcionRepository.existsById(any())).thenReturn(true); // ya existe

        assertThatThrownBy(() -> inscripcionService.save(1L, 2L, 3L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ya está inscrito");
    }

    // ── delete ────────────────────────────────────────────────────────────────

    @Test
    void delete_inscripcionExistente_eliminaCorrectamente() {
        when(inscripcionRepository.existsById(any())).thenReturn(true);
        doNothing().when(inscripcionRepository).deleteById(any());

        assertThatCode(() -> inscripcionService.delete(1L, 2L, 3L)).doesNotThrowAnyException();
        verify(inscripcionRepository, times(1)).deleteById(any());
    }

    @Test
    void delete_inscripcionInexistente_lanzaExcepcion() {
        when(inscripcionRepository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> inscripcionService.delete(1L, 2L, 3L))
                .isInstanceOf(ResponseStatusException.class);
        verify(inscripcionRepository, never()).deleteById(any());
    }
}