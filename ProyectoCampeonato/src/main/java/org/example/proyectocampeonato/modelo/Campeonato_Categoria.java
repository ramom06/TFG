package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class Campeonato_Categoria {
    //Esta clase es para la relacion entre campeonato y categoria

    private Campeonato_Categoria_Id id_campeonato_categoria;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

}
