package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Combate_Id implements Serializable {

    private Long idCompetidorRojo;

    private Long id_campeonato;      
    private Long id_categoria;

    private Integer numeroTatami;
}