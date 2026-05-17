package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Campeonato_Categoria_Id implements Serializable {

    @Column(name = "idCampeonato")
    private Long idCampeonato;

    @Column(name = "idCategoria")
    private Long idCategoria;

}
