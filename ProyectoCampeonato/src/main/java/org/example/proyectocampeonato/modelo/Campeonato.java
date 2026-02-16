package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*; // Importante para las anotaciones de BD
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
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
    private String nivel; //Regional, Nacional

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "campeonato")
    private Set<Categoria> categorias = new HashSet<>();

    public void addCategoria(Categoria categoria) {
        categorias.add(categoria);
        categoria.setCampeonato(this);
    }

    public void removeCategoria(Categoria categoria) {
        categorias.remove(categoria);
        categoria.setCampeonato(null);
    }
}