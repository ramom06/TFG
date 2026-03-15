package org.example.proyectocampeonato;

import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException;
import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.CompetidorRepository;
import org.example.proyectocampeonato.service.CompetidorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompetidorTest {

    @Mock
    private CompetidorRepository competidorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CompetidorService competidorService;

    private Competidor competidor;

    @BeforeEach
    void setUp() {
        competidor = Competidor.builder()
                .nombre("Juan")
                .apellidos("Pérez Cano")
                .dni("12345678A")
                .email("juan.perez@karate.es")
                .password("password")
                .rol(Usuario.Rol.COMPETIDOR)
                .genero('M')
                .club("Dojo Shoto")
                .federacionAutonomica("Comunidad de Madrid")
                .build();
        competidor.setIdUsuario(1L);
    }

    @Test
    void getAll_devuelveListaDeCompetidores() {
        when(competidorRepository.findAll()).thenReturn(List.of(competidor));

        List<Competidor> resultado = competidorService.getAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan");
        verify(competidorRepository, times(1)).findAll();
    }

    @Test
    void getAll_listaVacia() {
        when(competidorRepository.findAll()).thenReturn(List.of());

        assertThat(competidorService.getAll()).isEmpty();
    }


    @Test
    void one_idExistente_devuelveCompetidor() {
        when(competidorRepository.findById(1L)).thenReturn(Optional.of(competidor));

        Competidor resultado = competidorService.one(1L);

        assertThat(resultado.getNombre()).isEqualTo("Juan");
        assertThat(resultado.getClub()).isEqualTo("Dojo Shoto");
    }

    @Test
    void one_idInexistente_lanzaExcepcion() {
        when(competidorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competidorService.one(99L))
                .isInstanceOf(CompetidorNotFoundException.class);
    }


    @Test
    void save_competidorValido_codificaPasswordYGuarda() {
        // El servicio llama a passwordEncoder.encode() antes de guardar
        when(passwordEncoder.encode("password")).thenReturn("$2a$10$hashedPassword");
        when(competidorRepository.save(any(Competidor.class))).thenReturn(competidor);

        Competidor resultado = competidorService.save(competidor);

        // Verifica que se ha llamado al encoder
        verify(passwordEncoder, times(1)).encode(anyString());
        assertThat(resultado.getNombre()).isEqualTo("Juan");
    }

    @Test
    void save_passwordQuedaraCodificada() {
        when(passwordEncoder.encode("password")).thenReturn("$2a$10$hashedPassword");
        when(competidorRepository.save(any(Competidor.class))).thenAnswer(inv -> inv.getArgument(0));

        Competidor resultado = competidorService.save(competidor);

        // La password guardada debe ser el hash, no el texto plano
        assertThat(resultado.getPassword()).isEqualTo("$2a$10$hashedPassword");
    }


    @Test
    void replace_idExistente_actualizaYDevuelve() {
        Competidor actualizado = Competidor.builder()
                .nombre("Juan Actualizado")
                .apellidos("Pérez Cano")
                .club("Nuevo Club")
                .password("nuevaPassword")
                .build();

        when(competidorRepository.findById(1L)).thenReturn(Optional.of(competidor));
        when(passwordEncoder.encode("nuevaPassword")).thenReturn("$2a$10$nuevoHash");
        when(competidorRepository.save(any())).thenReturn(actualizado);

        Competidor resultado = competidorService.replace(1L, actualizado);

        assertThat(resultado.getNombre()).isEqualTo("Juan Actualizado");
        verify(passwordEncoder, times(1)).encode("nuevaPassword");
    }

    @Test
    void replace_sinNuevaPassword_conservaPasswordAnterior() {
        Competidor actualizado = Competidor.builder()
                .nombre("Juan Actualizado")
                .apellidos("Pérez Cano")
                .password(null)  // sin nueva contraseña
                .build();

        when(competidorRepository.findById(1L)).thenReturn(Optional.of(competidor));
        when(competidorRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        competidorService.replace(1L, actualizado);

        // No se debe llamar al encoder si la password es nula
        verify(passwordEncoder, never()).encode(any());
        // La password debe ser la del competidor original
        assertThat(actualizado.getPassword()).isEqualTo(competidor.getPassword());
    }

    @Test
    void replace_idInexistente_lanzaExcepcion() {
        when(competidorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> competidorService.replace(99L, competidor))
                .isInstanceOf(CompetidorNotFoundException.class);
    }

    @Test
    void delete_idExistente_eliminaCorrectamente() {
        when(competidorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(competidorRepository).deleteById(1L);

        assertThatCode(() -> competidorService.delete(1L)).doesNotThrowAnyException();
        verify(competidorRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_idInexistente_lanzaExcepcion() {
        when(competidorRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> competidorService.delete(99L))
                .isInstanceOf(CompetidorNotFoundException.class);
        verify(competidorRepository, never()).deleteById(any());
    }
}