package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combate_Id implements Serializable {

    @Column(name = "id_campeonato")
    private Long idCampeonato;

    @Column(name = "id_categoria")
    private Long idCategoria;

    private Integer numeroCombate;
}
