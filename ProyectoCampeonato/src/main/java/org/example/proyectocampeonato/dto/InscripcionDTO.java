package org.example.proyectocampeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InscripcionDTO {

    // IDs para crear la inscripción (Request)
    private Long idCampeonato;
    private Long idCategoria;
    private Long idCompetidor;

    // Nombres para mostrar en la vista (Response)
    private String nombreCampeonato;
    private String nombreCategoria;
    private String nombreCompetidor;
    private String clubCompetidor;
}