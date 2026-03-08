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
public class Campeonato_CategoriaDTO {

    private Long idCampeonato;
    private Long idCategoria;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nombreCampeonato;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nombreCategoria;

    private Date fechaInicioCampeonato;
    private Date fechaFinCampeonato;
    private String modalidad;
    private String genero;
}
