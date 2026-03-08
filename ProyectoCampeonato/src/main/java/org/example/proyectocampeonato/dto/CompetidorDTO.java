package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetidorDTO {

    private Long id;

    private String nombre;
    private String apellidos;
    private String dni;
    private LocalDate fechaNacimiento;
    private char genero;
    private String club;
    private String federacionAutonomica;
}
