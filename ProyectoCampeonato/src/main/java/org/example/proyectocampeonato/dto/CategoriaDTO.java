package org.example.proyectocampeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {

    private Long id_categoria;
    private String nombre;
    private String modalidad;
    private String genero;
    private Double pesoMinimo;
    private Double pesoMaximo;
    private int edadMinima;
    private int edadMaxima;
}