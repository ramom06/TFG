package org.example.proyectocampeonato;

import org.example.proyectocampeonato.excepcion.CombateNotFoundException;
import org.example.proyectocampeonato.modelo.Combate;
import org.example.proyectocampeonato.modelo.Combate_Id;
import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.repository.CombateRepository;
import org.example.proyectocampeonato.service.CombateService;
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
class CombateTest {

    @Mock
    private CombateRepository combateRepository;

    @InjectMocks
    private CombateService combateService;

    private Combate combate;
    private Combate_Id id;
    private Competidor rojo;
    private Competidor azul;

    @BeforeEach
    void setUp() {
        rojo = Competidor.builder().club("Club Rojo").federacionAutonomica("Andalucía").build();
        rojo.setIdUsuario(10L);
        rojo.setNombre("Ana");
        rojo.setApellidos("García");

        azul = Competidor.builder().club("Club Azul").federacionAutonomica("Madrid").build();
        azul.setIdUsuario(20L);
        azul.setNombre("Luis");
        azul.setApellidos("Pérez");

        id = new Combate_Id(1L, 1L, 1);
        combate = Combate.builder()
                .idCombate(id)
                .ronda("cuartos")
                .competidorRojo(rojo)
                .competidorAzul(azul)
                .estado("pendiente")
                .puntuacionRojo(0)
                .puntuacionAzul(0)
                .build();
    }

    @Test
    void getAll_devuelveListaDeCombates() {
        when(combateRepository.findAll()).thenReturn(List.of(combate));

        List<Combate> resultado = combateService.getAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getRonda()).isEqualTo("cuartos");
        verify(combateRepository, times(1)).findAll();
    }

    @Test
    void getAll_listaVacia() {
        when(combateRepository.findAll()).thenReturn(List.of());

        assertThat(combateService.getAll()).isEmpty();
    }

    @Test
    void getByCampeonatoCategoria_devuelveLosCombatesDeEsaCategoria() {
        when(combateRepository.findByIdIdCampeonatoAndIdIdCategoria(1L, 1L))
                .thenReturn(List.of(combate));

        List<Combate> resultado = combateService.getByCampeonatoCategoria(1L, 1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCompetidorRojo()).isEqualTo(rojo);
    }

    @Test
    void getByCompetidor_devuelveCombatesDondeAparece() {
        when(combateRepository.findByCompetidor(10L)).thenReturn(List.of(combate));

        List<Combate> resultado = combateService.getByCompetidor(10L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCompetidorRojo().getIdUsuario()).isEqualTo(10L);
    }

    @Test
    void one_idExistente_devuelveCombate() {
        when(combateRepository.findById(id)).thenReturn(Optional.of(combate));

        Combate resultado = combateService.one(id);

        assertThat(resultado.getRonda()).isEqualTo("cuartos");
        assertThat(resultado.getIdCombate()).isEqualTo(id);
    }

    @Test
    void one_idInexistente_lanzaCombateNotFound() {
        Combate_Id inexistente = new Combate_Id(99L, 99L, 99);
        when(combateRepository.findById(inexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> combateService.one(inexistente))
                .isInstanceOf(CombateNotFoundException.class);
    }

    @Test
    void save_combateValido_guardaYDevuelve() {
        when(combateRepository.save(combate)).thenReturn(combate);

        Combate resultado = combateService.save(combate);

        assertThat(resultado.getRonda()).isEqualTo("cuartos");
        verify(combateRepository, times(1)).save(combate);
    }

    @Test
    void save_sinId_lanzaBadRequest() {
        Combate sinId = Combate.builder()
                .ronda("final")
                .competidorRojo(rojo)
                .estado("pendiente")
                .puntuacionRojo(0)
                .puntuacionAzul(0)
                .build();

        assertThatThrownBy(() -> combateService.save(sinId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("400");

        verify(combateRepository, never()).save(any());
    }

    @Test
    void replace_idExistente_actualiza() {
        Combate actualizado = Combate.builder()
                .ronda("final")
                .competidorRojo(rojo)
                .competidorAzul(azul)
                .estado("finalizado")
                .puntuacionRojo(7)
                .puntuacionAzul(3)
                .build();

        when(combateRepository.existsById(id)).thenReturn(true);
        when(combateRepository.save(actualizado)).thenReturn(actualizado);

        Combate resultado = combateService.replace(id, actualizado);

        assertThat(resultado.getEstado()).isEqualTo("finalizado");
        assertThat(resultado.getPuntuacionRojo()).isEqualTo(7);
        assertThat(resultado.getIdCombate()).isEqualTo(id);
    }

    @Test
    void replace_idInexistente_lanzaCombateNotFound() {
        Combate_Id inexistente = new Combate_Id(99L, 99L, 99);
        when(combateRepository.existsById(inexistente)).thenReturn(false);

        assertThatThrownBy(() -> combateService.replace(inexistente, combate))
                .isInstanceOf(CombateNotFoundException.class);

        verify(combateRepository, never()).save(any());
    }

    @Test
    void delete_idExistente_eliminaCorrectamente() {
        when(combateRepository.existsById(id)).thenReturn(true);

        assertThatCode(() -> combateService.delete(id)).doesNotThrowAnyException();
        verify(combateRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_idInexistente_lanzaCombateNotFound() {
        Combate_Id inexistente = new Combate_Id(99L, 99L, 99);
        when(combateRepository.existsById(inexistente)).thenReturn(false);

        assertThatThrownBy(() -> combateService.delete(inexistente))
                .isInstanceOf(CombateNotFoundException.class);

        verify(combateRepository, never()).deleteById(any());
    }
}
