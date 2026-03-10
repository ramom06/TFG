package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combate_Id implements Serializable {

    private Long idCampeonato;
    private Long idCategoria;
    private Integer numeroTatami;
    private Integer numeroCombate;
}