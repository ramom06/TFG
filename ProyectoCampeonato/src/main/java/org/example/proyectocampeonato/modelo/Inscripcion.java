package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* Esta clase representa la relacion N:M entre competidor y campeonato_Categoria
* Será usada cuando un competidor quiera apuntarse a una o varias categorias en un campeonato
* */
@Entity
@Table(name = "inscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscripcion {

    @EmbeddedId
    private Inscripcion_Id id_inscripcion;

    @ManyToOne
    @MapsId("id_campeonato")
    @JoinColumn(name = "id_campeonato")
    private Campeonato campeonato;

    @ManyToOne
    @MapsId("id_categoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @MapsId("id_competidor")
    @JoinColumn(name = "id_competidor")
    private Competidor competidor;

}
