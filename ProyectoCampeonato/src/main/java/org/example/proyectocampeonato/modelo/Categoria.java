package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@JsonIgnoreProperties({"campeonatoCategorias", "inscripciones"})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private Long idCategoria;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String modalidad;

    @Column(nullable = false)
    private String genero;

    @Min(value = 0, message = "{categoria.peso.minimo}")
    private Double pesoMinimo;

    @Min(value = 0, message = "{categoria.peso.minimo}")
    private Double pesoMaximo;

    @Column(nullable = false)
    @Min(value = 0, message = "{categoria.edadMaxima.minimo}")
    @Max(value = 100, message = "{categoria.edadMaxima.maximo}")
    private int edadMaxima;

    @Column(nullable = false)
    @Min(value = 0, message = "{categoria.edadMinima.minimo}")
    @Max(value = 71, message = "{categoria.edadMaxima.maximo}")
    private int edadMinima;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Campeonato_Categoria> campeonatoCategorias = new HashSet<>();

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inscripcion> inscripciones = new HashSet<>();
}
