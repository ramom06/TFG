package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clasificacion")
public class Clasificacion {

    @Id
    @Column(name = "id_clasificacion")
    private Integer idClasificacion;

    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;

    @Column(name = "nombre_competidor", nullable = false)
    private String nombreCompetidor;

    @Column(nullable = false)
    private Integer puntos;
}