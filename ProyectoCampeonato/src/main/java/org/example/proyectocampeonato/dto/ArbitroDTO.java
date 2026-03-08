package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArbitroDTO {

    private Long id_usuario;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private boolean activo;
    private LocalDateTime fechaRegistro;

    // Campos propios de Arbitro
    private String nombre;
    private String apellidos;
    private String licencia;
    private String categoriaArbitral;
    private Date fechaNacimiento;
}