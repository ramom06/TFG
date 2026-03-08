package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
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

    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;
}