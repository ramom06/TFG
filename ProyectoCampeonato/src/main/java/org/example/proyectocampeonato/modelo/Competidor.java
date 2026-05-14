package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@JsonIgnoreProperties({"inscripciones"})
@Table(name = "competidor")
public class Competidor extends Usuario {

    @Column(nullable = false)
    private String club;

    @Column(name = "federacion_autonomica", nullable = false)
    private String federacionAutonomica;

    @OneToMany(mappedBy = "competidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inscripcion> inscripciones = new HashSet<>();
}