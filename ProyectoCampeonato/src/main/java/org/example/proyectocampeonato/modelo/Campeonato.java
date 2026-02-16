package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*; // Importante para las anotaciones de BD
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "campeonato")
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_campeonato;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String nivel; //Regional, Nacional, Internacional

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany
    @JoinTable(
            name = "campeonato_categoria",
            joinColumns = @JoinColumn(name = "id_campeonato"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Categoria> categorias;
}