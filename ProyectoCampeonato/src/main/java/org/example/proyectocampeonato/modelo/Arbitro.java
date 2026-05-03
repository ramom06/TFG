package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "arbitro")
@DiscriminatorValue("ARBITRO")
public class Arbitro extends Usuario {

    @Column(nullable = false, unique = true)
    private String licencia;

    @Column(name = "categoria_arbitral", nullable = false)
    private String categoriaArbitral;
}