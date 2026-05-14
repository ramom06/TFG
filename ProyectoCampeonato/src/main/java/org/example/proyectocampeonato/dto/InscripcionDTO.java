package org.example.proyectocampeonato.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO{

    private Long id_campeonato;

    private Long id_categoria;

    private Long id_competidor;

    private String nombreCampeonato;

    private String nombreCategoria;

    private String nombreCompetidor;

    private String clubCompetidor;

}
