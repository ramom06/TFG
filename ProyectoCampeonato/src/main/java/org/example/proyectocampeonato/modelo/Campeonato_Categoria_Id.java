package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campeonato_Categoria_Id implements Serializable {

    private Long id_campeonato;
    private Long id_categoria;

}
