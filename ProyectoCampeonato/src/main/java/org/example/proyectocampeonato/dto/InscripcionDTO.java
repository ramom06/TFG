package org.example.proyectocampeonato.dto;

import lombok.*;


@Getter
@Setter
public class InscripcionDTO{

    private Long id_campeonato;

    private Long id_competidor;

    private Long id_categoria;

    private String nombreCampeonato;

    private String nombreCategoria;

    private String nombreCompetidor;

    private String clubCompetidor;

}
