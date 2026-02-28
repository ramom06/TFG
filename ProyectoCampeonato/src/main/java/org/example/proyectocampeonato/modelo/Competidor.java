package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "competidor")
public class Competidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_competidor;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private char genero;

    @Column(nullable = false)
    private String club;

    @Column(name = "federacion_autonomica", nullable = false)
    private String federacionAutonomica;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "categoria_competidor",
            joinColumns = @JoinColumn(name = "id_competidor")
    )
    @JsonIgnore
    private Set<Categoria> categorias = new HashSet<>();
}