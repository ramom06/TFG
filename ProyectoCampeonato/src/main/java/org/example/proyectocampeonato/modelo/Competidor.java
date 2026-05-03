package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "competidor")
public class Competidor extends Usuario {

    @Column(nullable = false)
    private String club;

    @Column(name = "federacion_autonomica", nullable = false)
    private String federacionAutonomica;

    @OneToMany(mappedBy = "competidor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Inscripcion> inscripciones = new HashSet<>();
}