package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "campeonato_categoria")
public class Campeonato_Categoria {

    @EmbeddedId
    private Campeonato_Categoria_Id idCampeonatoCategoria;

    @ManyToOne
    @MapsId("idCampeonato")
    @JoinColumn(name = "id_campeonato")
    private Campeonato campeonato;

    @ManyToOne
    @MapsId("idCategoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
