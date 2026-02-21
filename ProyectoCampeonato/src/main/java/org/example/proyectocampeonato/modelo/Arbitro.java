package org.example.proyectocampeonato.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "arbitro")
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_arbitro;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String licencia;

    @Column(nullable = false)
    private String categoria_Arbitral;

    private boolean activo;

    @Column(nullable = false)
    private Date fecha_nacimiento;
}
