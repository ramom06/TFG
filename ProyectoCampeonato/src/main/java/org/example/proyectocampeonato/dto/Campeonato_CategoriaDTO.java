package org.example.proyectocampeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampeonatoCategoriaDTO {

    // IDs para crear la relación (Request)
    private Long idCampeonato;
    private Long idCategoria;

    // Nombres para mostrar en la vista (Response)
    private String nombreCampeonato;
    private String nombreCategoria;
    private String modalidad;
    private String genero;
}