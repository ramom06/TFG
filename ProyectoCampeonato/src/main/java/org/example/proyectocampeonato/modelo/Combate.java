package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combate {

    @EmbeddedId
    private Combate_Id idCombate;

    @Column(nullable = false)
    private String ronda;

    private Integer puntuacionRojo = 0;

    private Integer puntuacionAzul = 0;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "idCompetidor_rojo", nullable = true)
    private Competidor competidorRojo;

    @ManyToOne
    @JoinColumn(name = "idCompetidor_azul", nullable = true)
    private Competidor competidorAzul;

    // Relación con Campeonato_Categoria
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "idCampeonato", insertable = false, updatable = false),
            @JoinColumn(name = "idCategoria",  insertable = false, updatable = false)
    })
    private Campeonato_Categoria campeonatoCategoria;
}