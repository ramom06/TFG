package org.example.proyectocampeonato.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
//Seriazable se usa para las claves compuestas
public class CategoriaId implements Serializable {


    private String modalidad;

    private String genero;

    private Double pesoMinimo;
    private Double pesoMaximo;


    private int edadMaxima;
}
