package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "combate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combate {

    @EmbeddedId
    private Combate_Id id;

    @Column(nullable = false)
    private String ronda;

    @Column(name = "puntuacion_rojo")
    private Integer puntuacionRojo = 0;

    @Column(name = "puntuacion_azul")
    private Integer puntuacionAzul = 0;

    private String estado;

    // Competidor rojo (siempre hay al menos uno) — ya NO forma parte de la PK
    @ManyToOne
    @JoinColumn(name = "id_competidor_rojo", nullable = true)
    private Competidor competidorRojo;

    // Competidor azul (puede ser null si hay número impar de competidores → bye)
    @ManyToOne
    @JoinColumn(name = "id_competidor_azul", nullable = true)
    private Competidor competidorAzul;

    // Relación con Campeonato_Categoria
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_campeonato", insertable = false, updatable = false),
            @JoinColumn(name = "id_categoria",  insertable = false, updatable = false)
    })
    private Campeonato_Categoria campeonatoCategoria;
}