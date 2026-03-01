package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combate_Id implements Serializable {

    private Integer idCompetidorRojo;

    private Integer idCompetidorAzul;

    private Integer numeroTatami;
}