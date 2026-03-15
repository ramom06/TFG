package org.example.proyectocampeonato;

import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.example.proyectocampeonato.service.CategoriaService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .nombre("Senior Masculino <75kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(75.0)
                .build();
        categoria.setId_categoria(1L);
    }

    // ── getAll ────────────────────────────────────────────────────────────────

    @Test
    void getAll_devuelveListaDeCategorias() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<Categoria> resultado = categoriaService.getAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Senior Masculino <75kg");
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void getAll_listaVacia() {
        when(categoriaRepository.findAll()).thenReturn(List.of());

        assertThat(categoriaService.getAll()).isEmpty();
    }

    // ── one ───────────────────────────────────────────────────────────────────

    @Test
    void one_idExistente_devuelveCategoria() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.one(1L);

        assertThat(resultado.getNombre()).isEqualTo("Senior Masculino <75kg");
        assertThat(resultado.getModalidad()).isEqualTo("kumite");
        assertThat(resultado.getGenero()).isEqualTo("M");
    }

    @Test
    void one_idInexistente_lanzaExcepcion() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.one(99L))
                .isInstanceOf(ResponseStatusException.class);
    }

    // ── save ──────────────────────────────────────────────────────────────────

    @Test
    void save_categoriaValida_guardaYDevuelve() {
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaService.save(categoria);

        assertThat(resultado.getNombre()).isEqualTo("Senior Masculino <75kg");
        assertThat(resultado.getPesoMaximo()).isEqualTo(75.0);
        verify(categoriaRepository, times(1)).save(categoria);
    }

    // ── replace ───────────────────────────────────────────────────────────────

    @Test
    void replace_idExistente_actualizaYDevuelve() {
        Categoria actualizada = Categoria.builder()
                .nombre("Senior Masculino <84kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(84.0)
                .build();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(actualizada)).thenReturn(actualizada);

        Categoria resultado = categoriaService.replace(1L, actualizada);

        assertThat(resultado.getNombre()).isEqualTo("Senior Masculino <84kg");
        assertThat(resultado.getId_categoria()).isEqualTo(1L);
    }

    @Test
    void replace_idInexistente_lanzaExcepcion() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.replace(99L, categoria))
                .isInstanceOf(ResponseStatusException.class);
    }

    // ── delete ────────────────────────────────────────────────────────────────

    @Test
    void delete_idExistente_eliminaCorrectamente() {
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(1L);

        assertThatCode(() -> categoriaService.delete(1L)).doesNotThrowAnyException();
        verify(categoriaRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_idInexistente_lanzaExcepcion() {
        when(categoriaRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> categoriaService.delete(99L))
                .isInstanceOf(ResponseStatusException.class);
        verify(categoriaRepository, never()).deleteById(any());
    }
}