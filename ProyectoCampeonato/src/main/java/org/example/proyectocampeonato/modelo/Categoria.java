package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "categoria")
public class Categoria {

    //Esto quiere decir que tiene una clave compuesta
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "modalidad", column = @Column(name = "modalidad")),
            @AttributeOverride(name = "genero", column = @Column(name = "genero")),
            @AttributeOverride(name = "pesoMaximo", column = @Column(name = "peso_maximo")),
            @AttributeOverride(name = "pesoMinimo", column = @Column(name = "peso_minimo")),
            @AttributeOverride(name = "edadMaxima", column = @Column(name = "edad_maxima"))
    })
    private CategoriaId id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "edad_minima", nullable = false, length = 100)
    private int edadMinima;

    @ManyToMany(mappedBy = "categorias")
    private Set<Campeonato> campeonatos;

    @ManyToMany(mappedBy = "categorias", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Competidor> competidores = new HashSet<>();

}
