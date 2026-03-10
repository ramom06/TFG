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
    @AttributeOverrides({
            @AttributeOverride(name = "idCampeonato",   column = @Column(name = "id_campeonato")),
            @AttributeOverride(name = "idCategoria",    column = @Column(name = "id_categoria")),
            @AttributeOverride(name = "numeroTatami",   column = @Column(name = "numero_tatami")),
            @AttributeOverride(name = "numeroCombate",  column = @Column(name = "numero_combate"))
    })
    private Combate_Id id;

    @Column(nullable = false)
    private String ronda;

    @Column(name = "puntuacion_rojo")
    private Integer puntuacionRojo = 0;

    @Column(name = "puntuacion_azul")
    private Integer puntuacionAzul = 0;

    private String senshu;
    private String estado;

    @Column(name = "hora_programada", nullable = false)
    private java.time.LocalTime horaProgramada;

    @Column(name = "hora_inicio_real", nullable = false)
    private java.time.LocalDateTime horaInicioReal;

    @Column(name = "duracion_segundos", nullable = false)
    private Integer duracionSegundos;

    private String observaciones;

    // Competidor rojo (siempre hay al menos uno) — ya NO forma parte de la PK
    @ManyToOne
    @JoinColumn(name = "id_competidor_rojo", nullable = false)
    private Competidor competidorRojo;

    // Competidor azul (puede ser null si hay número impar de competidores → bye)
    @ManyToOne
    @JoinColumn(name = "id_competidor_azul", nullable = true)
    private Competidor competidorAzul;

    // Relación con Campeonato_Categoria
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_campeonato", referencedColumnName = "id_campeonato", insertable = false, updatable = false),
            @JoinColumn(name = "id_categoria",  referencedColumnName = "id_categoria",  insertable = false, updatable = false)
    })
    private Campeonato_Categoria campeonatoCategoria;
}