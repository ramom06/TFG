package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "categoria", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"modalidad", "genero", "peso_minimo", "peso_maximo", "edad_maxima"})
})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String modalidad;

    @Column(nullable = false)
    private String genero;

    @Column(name = "peso_minimo")
    @Min(value = 0, message = "{categoria.peso.minimo}")
    private Double pesoMinimo;

    @Min(value = 0, message = "{categoria.peso.minimo}")
    @Column(name = "peso_maximo")
    private Double pesoMaximo;

    @Column(name = "edad_maxima", nullable = false)
    @Min(value = 0, message = "{categoria.edadMaxima.minimo}")
    @Max(value = 100, message = "{categoria.edadMaxima.maximo}")
    private int edadMaxima;

    @Column(name = "edad_minima", nullable = false)
    @Min(value = 0, message = "{categoria.edadMinima.minimo}")
    @Max(value = 40, message = "{categoria.edadMinima.maximo}")
    private int edadMinima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campeonato")
    @JsonIgnore
    private Campeonato campeonato;

    @ManyToMany(mappedBy = "categorias", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Competidor> competidores = new HashSet<>();

}
