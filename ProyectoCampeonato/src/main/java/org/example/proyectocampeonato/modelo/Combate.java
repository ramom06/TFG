package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "combate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combate {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "idCompetidorRojo", column = @Column(name = "competidorRojo")),
            @AttributeOverride(name = "idCompetidorAzul", column = @Column(name = "competidorAzul")),
            @AttributeOverride(name = "numeroTatami", column = @Column(name = "numeroTatami"))
    })
    private CombateId id;

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

    @ManyToOne
    @MapsId("idCompetidorRojo") // Nombre exacto del campo dentro de CombateId
    @JoinColumn(name = "id_competidor_rojo")
    private Competidor competidorRojo;

    @ManyToOne
    @MapsId("idCompetidorAzul") // Nombre exacto del campo dentro de CombateId
    @JoinColumn(name = "id_competidor_azul")
    private Competidor competidorAzul;
}