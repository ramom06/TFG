package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "campeonato_categoria")
public class Campeonato_Categoria {

    @EmbeddedId
    private Campeonato_Categoria_Id id;

    @ManyToOne
    @MapsId("id_campeonato")
    @JoinColumn(name = "id_campeonato")
    private Campeonato campeonato;

    @ManyToOne
    @MapsId("id_categoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

}
