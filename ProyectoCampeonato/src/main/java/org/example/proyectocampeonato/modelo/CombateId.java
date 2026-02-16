package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombateId implements Serializable {

    private Integer idCompetidorRojo;

    private Integer idCompetidorAzul;

    private Integer numeroTatami;
}