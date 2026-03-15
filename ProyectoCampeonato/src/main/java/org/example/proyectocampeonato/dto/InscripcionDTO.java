package org.example.proyectocampeonato.dto;


public record InscripcionDTO(
        Long idCampeonato,
        Long idCategoria,
        Long idCompetidor,
        String nombreCampeonato,
        String nombreCategoria,
        String nombreCompetidor,
        String clubCompetidor
) {}