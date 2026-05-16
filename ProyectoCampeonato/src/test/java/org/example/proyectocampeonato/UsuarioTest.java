package org.example.proyectocampeonato;

import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.example.proyectocampeonato.service.UsuarioService;
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
class UsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("AdminUser");
        usuario.setApellidos("Sistema");
        usuario.setDni("00000000A");
        usuario.setEmail("admin@admin.es");
        usuario.setPassword("password123");
        usuario.setRol(Usuario.Rol.ADMIN);
        usuario.setGenero('M');
    }


    @Test
    void getAll_devuelveListaDeUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.getAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("AdminUser");
    }

    @Test
    void one_idExistente_devuelveUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.one(1L);

        assertThat(resultado.getEmail()).isEqualTo("admin@admin.es");
        assertThat(resultado.getRol()).isEqualTo(Usuario.Rol.ADMIN);
    }

    @Test
    void one_idInexistente_lanzaExcepcion() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.one(99L))
                .isInstanceOf(ResponseStatusException.class);
    }


    @Test
    void findByNombre_nombreExistente_devuelveUsuario() {
        when(usuarioRepository.findByNombre("AdminUser")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findByNombre("AdminUser");

        assertThat(resultado.getEmail()).isEqualTo("admin@admin.es");
    }

    @Test
    void findByNombre_nombreInexistente_lanzaExcepcion() {
        when(usuarioRepository.findByNombre("Fantasma")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.findByNombre("Fantasma"))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void findByRol_devuelveUsuariosConEseRol() {
        when(usuarioRepository.findByRol(Usuario.Rol.ADMIN)).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.findByRol(Usuario.Rol.ADMIN);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getRol()).isEqualTo(Usuario.Rol.ADMIN);
    }

    @Test
    void save_usuarioValido_codificaPasswordYGuarda() {
        when(usuarioRepository.existsByNombre("AdminUser")).thenReturn(false);
        when(usuarioRepository.existsByEmail("admin@admin.es")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$hash");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario resultado = usuarioService.save(usuario);

        verify(passwordEncoder, times(1)).encode("password123");
        assertThat(resultado.getNombre()).isEqualTo("AdminUser");
    }

    @Test
    void save_nombreDuplicado_lanzaConflict() {
        when(usuarioRepository.existsByNombre("AdminUser")).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.save(usuario))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ya está en uso");
    }

    @Test
    void save_emailDuplicado_lanzaConflict() {
        when(usuarioRepository.existsByNombre("AdminUser")).thenReturn(false);
        when(usuarioRepository.existsByEmail("admin@admin.es")).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.save(usuario))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("ya está registrado");
    }


    @Test
    void delete_idExistente_eliminaCorrectamente() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

        assertThatCode(() -> usuarioService.delete(1L)).doesNotThrowAnyException();
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_idInexistente_lanzaExcepcion() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> usuarioService.delete(99L))
                .isInstanceOf(ResponseStatusException.class);
        verify(usuarioRepository, never()).deleteById(any());
    }
}