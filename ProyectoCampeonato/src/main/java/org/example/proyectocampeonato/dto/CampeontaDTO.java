package org.example.proyectocampeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampeonatoDTO {

    private Long id_campeonato;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String ubicacion;
    private String estado;
    private String nivel;
    private String descripcion;
    private String urlPortada;

    // Nombres de las categorías asociadas al campeonato
    private List<String> categorias;
}