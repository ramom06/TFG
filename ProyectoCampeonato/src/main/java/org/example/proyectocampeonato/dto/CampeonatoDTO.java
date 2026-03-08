package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampeonatoDTO {

    @JsonProperty("id_campeonato")
    private Long id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String ubicacion;
    private String estado;
    private String nivel;
    private String descripcion;
    private String urlPortada;
}
