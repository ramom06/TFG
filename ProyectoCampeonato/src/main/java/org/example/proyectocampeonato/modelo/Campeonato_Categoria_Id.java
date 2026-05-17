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

    private Long idCampeonato;

    private Long idCategoria;

}
