package org.example.proyectocampeonato.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Campeonato_CategoriaDTO {

    // IDs para crear la relación (Request)
    private Long idCampeonato;
    private Long idCategoria;

    private Date fechaInicioCampeonato;
    private Date fechaFinCampeonato;
    private String modalidad;
    private String genero;
}