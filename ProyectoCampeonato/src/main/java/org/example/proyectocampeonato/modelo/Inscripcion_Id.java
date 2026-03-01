package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
//Esta clase es el id de la clase Inscripcion
public class Inscripcion_Id implements Serializable {

    private Long id_campeonato;
    private Long id_categoria;
    private Long id_competidor;

}
