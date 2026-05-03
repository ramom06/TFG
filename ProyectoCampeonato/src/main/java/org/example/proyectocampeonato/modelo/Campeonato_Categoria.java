package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "campeonato_categoria")
public class Campeonato_Categoria {

    @EmbeddedId
    private Campeonato_Categoria_Id id_Campeonato_Categoria;

    @ManyToOne
    @MapsId("id_campeonato")
    @JoinColumn(name = "id_campeonato")
    private Campeonato campeonato;

    @ManyToOne
    @MapsId("id_categoria")
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
