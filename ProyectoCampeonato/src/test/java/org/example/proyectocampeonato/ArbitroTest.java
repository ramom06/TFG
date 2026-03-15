package org.example.proyectocampeonato;

import org.example.proyectocampeonato.modelo.Arbitro;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.ArbitroRepository;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.example.proyectocampeonato.service.ArbitroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArbitroTest {

    @Mock private ArbitroRepository arbitroRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ArbitroService arbitroService;

    private Arbitro arbitro;

    @BeforeEach
    void setUp() {
        arbitro = Arbitro.builder()
                .nombre("Luis")
                .apellidos("Gómez Juez")
                .dni("12345678Z")
                .email("luis.gomez@arbitros.es")
                .password("password")
                .rol(Usuario.Rol.ARBITRO)
                .genero('M')
                .licencia("LIC-2026-001")
                .categoriaArbitral("Nacional")
                .build();
        arbitro.setIdUsuario(1L);
    }

    // ── getAll ────────────────────────────────────────────────────────────────

    @Test
    void getAll_devuelveListaDeArbitros() {
        when(arbitroRepository.findAll()).thenReturn(List.of(arbitro));

        List<Arbitro> resultado = arbitroService.getAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getLicencia()).isEqualTo("LIC-2026-001");
    }

    // ── one ───────────────────────────────────────────────────────────────────

    @Test
    void one_idExistente_devuelveArbitro() {
        when(arbitroRepository.findById(1L)).thenReturn(Optional.of(arbitro));

        Arbitro resultado = arbitroService.one(1L);

        assertThat(resultado.getNombre()).isEqualTo("Luis");
        assertThat(resultado.getCategoriaArbitral()).isEqualTo("Nacional");
    }

    @Test
    void one_idInexistente_lanzaExcepcion() {
        when(arbitroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> arbitroService.one(99L))
                .isInstanceOf(ResponseStatusException.class);
    }

    // ── findByLicencia ────────────────────────────────────────────────────────

    @Test
    void findByLicencia_existente_devuelveArbitro() {
        when(arbitroRepository.findByLicencia("LIC-2026-001")).thenReturn(Optional.of(arbitro));

        Arbitro resultado = arbitroService.findByLicencia("LIC-2026-001");

        assertThat(resultado.getNombre()).isEqualTo("Luis");
    }

    @Test
    void findByLicencia_inexistente_lanzaExcepcion() {
        when(arbitroRepository.findByLicencia("LIC-FAKE")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> arbitroService.findByLicencia("LIC-FAKE"))
                .isInstanceOf(ResponseStatusException.class);
    }

    // ── findByCategoria ───────────────────────────────────────────────────────

    @Test
    void findByCategoria_devuelveArbitrosDeEsaCategoria() {
        when(arbitroRepository.findByCategoriaArbitral("Nacional")).thenReturn(List.of(arbitro));

        List<Arbitro> resultado = arbitroService.findByCategoria("Nacional");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getCategoriaArbitral()).isEqualTo("Nacional");
    }

    // ── save ──────────────────────────────────────────────────────────────────

    @Test
    void save_arbitroValido_asignaRolYCodificaPassword() {
        when(usuarioRepository.existsByNombre("Luis")).thenReturn(false);
        when(usuarioRepository.existsByEmail("luis.gomez@arbitros.es")).thenReturn(false);
        when(arbitroRepository.existsByLicencia("LIC-2026-001")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("$2a$10$hash");
        when(arbitroRepository.save(any())).thenReturn(arbitro);

        Arbitro resultado = arbitroService.save(arbitro);

        // El rol debe haberse forzado a ARBITRO
        verify(passwordEncoder, times(1)).encode("password");
        assertThat(resultado.getNombre()).isEqualTo("Luis");
    }

    @Test
    void save_licenciaDuplicada_lanzaConflict() {
        when(usuarioRepository.existsByNombre("Luis")).thenReturn(false);
        when(usuarioRepository.existsByEmail("luis.gomez@arbitros.es")).thenReturn(false);
        when(arbitroRepository.existsByLicencia("LIC-2026-001")).thenReturn(true);

        assertThatThrownBy(() -> arbitroService.save(arbitro))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("licencia");
    }

    // ── delete ────────────────────────────────────────────────────────────────

    @Test
    void delete_idExistente_eliminaCorrectamente() {
        when(arbitroRepository.existsById(1L)).thenReturn(true);
        doNothing().when(arbitroRepository).deleteById(1L);

        assertThatCode(() -> arbitroService.delete(1L)).doesNotThrowAnyException();
        verify(arbitroRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_idInexistente_lanzaExcepcion() {
        when(arbitroRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> arbitroService.delete(99L))
                .isInstanceOf(ResponseStatusException.class);
        verify(arbitroRepository, never()).deleteById(any());
    }
}