package org.example.proyectocampeonato;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.service.CampeonatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampeonatoTest {

    @Mock
    private CampeonatoRepository campeonatoRepository;

    @InjectMocks
    private CampeonatoService campeonatoService;

    private Campeonato campeonato;

    @BeforeEach
    void setUp() {
        campeonato = Campeonato.builder()
                .nombre("Campeonato de España Absoluto 2026")
                .ubicacion("Madrid, España")
                .estado("futuro")
                .nivel("Nacional")
                .build();
        campeonato.setId_campeonato(1L);
    }

    @Test
    void getAll_ListaDeCampeonatos() {
        when(campeonatoRepository.findAll()).thenReturn(List.of(campeonato));

        List<Campeonato> resultado = campeonatoService.getAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Campeonato de España Absoluto 2026");
        verify(campeonatoRepository, times(1)).findAll();
    }

    @Test
    void one_idExistente_devuelveCampeonato() {
        when(campeonatoRepository.findById(1L)).thenReturn(Optional.of(campeonato));

        Campeonato resultado = campeonatoService.one(1L);

        assertThat(resultado.getNombre()).isEqualTo("Campeonato de España Absoluto 2026");
        assertThat(resultado.getUbicacion()).isEqualTo("Madrid, España");
    }

    @Test
    void one_idInexistente_lanzaExcepcion() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> campeonatoService.one(99L))
                .isInstanceOf(CampeonatoNotFoundException.class);
    }

    @Test
    void save_campeonatoValido_guardaYDevuelve() {
        when(campeonatoRepository.save(campeonato)).thenReturn(campeonato);

        Campeonato resultado = campeonatoService.save(campeonato);

        assertThat(resultado.getNombre()).isEqualTo("Campeonato de España Absoluto 2026");
        verify(campeonatoRepository, times(1)).save(campeonato);
    }

    @Test
    void replace_idInexistente_lanzaExcepcion() {
        when(campeonatoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> campeonatoService.replace(99L, campeonato))
                .isInstanceOf(CampeonatoNotFoundException.class);
    }

    @Test
    void delete_eliminaCorrectamente() {
        when(campeonatoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(campeonatoRepository).deleteById(1L);

        assertThatCode(() -> campeonatoService.delete(1L))
                .doesNotThrowAnyException();

        verify(campeonatoRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_idInexistente_lanzaExcepcion() {
        when(campeonatoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> campeonatoService.delete(99L))
                .isInstanceOf(CampeonatoNotFoundException.class);

        verify(campeonatoRepository, never()).deleteById(any());
    }
}