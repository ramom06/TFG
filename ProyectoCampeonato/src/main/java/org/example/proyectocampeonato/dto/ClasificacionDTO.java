package org.example.proyectocampeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClasificacionDTO {

    private Integer idClasificacion;
    private String nombreCategoria;
    private String nombreCompetidor;
    private Integer puntos;
}