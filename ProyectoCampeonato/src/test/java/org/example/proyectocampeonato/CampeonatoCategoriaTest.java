package org.example.proyectocampeonato;

import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.example.proyectocampeonato.repository.Campeonato_CategoriaRepository;
import org.example.proyectocampeonato.service.CampeonatoCategoriaService;
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
class CampeonatoCategoriaTest {

    @Mock private Campeonato_CategoriaRepository repository;
    @Mock private CampeonatoRepository campeonatoRepository;
    @Mock private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CampeonatoCategoriaService service;

    private Campeonato campeonato;
    private Categoria categoria;
    private Campeonato_Categoria cc;

    @BeforeEach
    void setUp() {
        campeonato = Campeonato.builder()
                .nombre("Campeonato España 2026")
                .ubicacion("Madrid")
                .estado("futuro")
                .nivel("Nacional")
                .build();
        campeonato.setId_campeonato(1L);

        categoria = Categoria.builder()
                .nombre("Senior Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .build();
        categoria.setId_categoria(2L);

        Campeonato_Categoria_Id ccId = new Campeonato_Categoria_Id(1L, 2L);
        cc = new Campeonato_Categoria();
        cc.setId(ccId);
        cc.setCampeonato(campeonato);
        cc.setCategoria(categoria);
    }

    // ── getCategoriasPorCampeonato ────────────────────────────────────────────

    @Test
    void getCategoriasPorCampeonato_devuelveCategorias() {
        when(repository.findCategoriasByCampeonatoId(1L)).thenReturn(List.of(categoria));

        List<Categoria> resultado = service.getCategoriasPorCampeonato(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Senior Masculino Kata");
    }

    @Test
    void getCategoriasPorCampeonato_sinCategorias_listaVacia() {
        when(repository.findCategoriasByCampeonatoId(99L)).thenReturn(List.of());

        assertThat(service.getCategoriasPorCampeonato(99L)).isEmpty();
    }

    @Test
    void asignarCategoria_valida_creaRelacion() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoria));
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(cc);

        Campeonato_Categoria resultado = service.asignarCategoria(1L, 2L);

        assertThat(resultado.getCampeonato().getNombre()).isEqualTo("Campeonato España 2026");
        assertThat(resultado.getCategoria().getNombre()).isEqualTo("Senior Masculino Kata");
        verify(repository, times(1)).save(any());
    }

    @Test
    void asignarCategoria_campeonatoInexistente_lanzaExcepcion() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.asignarCategoria(99L, 2L))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void asignarCategoria_yaAsignada_lanzaConflict() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoria));
        when(repository.existsById(any())).thenReturn(true); // ya existe

        assertThatThrownBy(() -> service.asignarCategoria(1L, 2L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ya está asignada");
    }

    // ── eliminarCategoria ─────────────────────────────────────────────────────

    @Test
    void eliminarCategoria_existente_eliminaCorrectamente() {
        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(any());

        assertThatCode(() -> service.eliminarCategoria(1L, 2L)).doesNotThrowAnyException();
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void eliminarCategoria_inexistente_lanzaExcepcion() {
        when(repository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> service.eliminarCategoria(1L, 99L))
                .isInstanceOf(ResponseStatusException.class);
        verify(repository, never()).deleteById(any());
    }
}