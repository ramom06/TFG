package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectocampeonato.modelo.Usuario;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArbitroDTO {

    private Long id_usuario;
    private String nombre;
    private String apellidos;
    private String email;
    private Usuario.Rol rol;

    @JsonIgnore
    private String password;

    private boolean activo;
    private Date fechaRegistro;

    // Campos propios de Arbitro
    private String licencia;
    private String categoriaArbitral;
    private Date fechaNacimiento;
}