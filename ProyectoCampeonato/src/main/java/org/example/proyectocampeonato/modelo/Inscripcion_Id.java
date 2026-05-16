package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscripcion_Id implements Serializable {

    @Column(name = "id_campeonato")
    private Long idCampeonato;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "id_competidor")
    private Long idCompetidor;

}
