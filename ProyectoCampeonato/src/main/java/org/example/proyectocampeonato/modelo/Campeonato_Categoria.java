package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Campeonato_Categoria {

    @EmbeddedId
    private Campeonato_Categoria_Id idCampeonatoCategoria;

    @ManyToOne
    @MapsId("idCampeonato")
    @JoinColumn(name = "idCampeonato")
    private Campeonato campeonato;

    @ManyToOne
    @MapsId("idCategoria")
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
}
