package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*; // Importante para las anotaciones de BD
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@JsonIgnoreProperties({"campeonatoCategorias"})
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCampeonato")
    private Long idCampeonato;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String nivel;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String urlPortada;

    @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Campeonato_Categoria> campeonatoCategorias = new HashSet<>();
}