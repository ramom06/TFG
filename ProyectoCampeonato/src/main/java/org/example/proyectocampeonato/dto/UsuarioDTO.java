package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectocampeonato.modelo.Usuario;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class    UsuarioDTO {

    private String username;
    private String email;

    @JsonIgnore  // nunca se serializa en la respuesta
    private String password;

    private Usuario.Rol rol;
    private boolean activo;
    private LocalDateTime fechaRegistro;
}