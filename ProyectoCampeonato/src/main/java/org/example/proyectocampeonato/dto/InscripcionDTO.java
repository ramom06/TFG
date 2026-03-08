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

    private Long idCampeonato;
    private Long idCategoria;
    private Long idCompetidor;
    private String nombreCampeonato;
    private String nombreCategoria;
    private String nombreCompetidor;
    private String clubCompetidor;
}