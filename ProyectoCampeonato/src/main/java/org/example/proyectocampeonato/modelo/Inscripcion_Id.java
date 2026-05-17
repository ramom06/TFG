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

    @Column(name = "idCampeonato")
    private Long idCampeonato;

    @Column(name = "idCategoria")
    private Long idCategoria;

    @Column(name = "idCompetidor")
    private Long idCompetidor;

}
