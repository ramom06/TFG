package org.example.proyectocampeonato.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO{

    private Long idCampeonato;
    private Long idCategoria;
    private Long idCompetidor;
    private String nombreCampeonato;
    private String nombreCategoria;
    private String nombreCompetidor;
    private String clubCompetidor;
}
