package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectocampeonato.modelo.Usuario;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private Usuario.Rol rol;
    private Date fechaRegistro;
}
