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

    @Column(name = "idCampeonato")
    private Long idCampeonato;

    @Column(name = "idCategoria")
    private Long idCategoria;

    private Integer numeroCombate;
}
