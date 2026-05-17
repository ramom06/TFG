package org.example.proyectocampeonato.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false, updatable = false)
    private Date fechaRegistro;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false)
    private char genero;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = new Date();
    }

    public enum Rol {
        ADMIN,
        COMPETIDOR
    }
}