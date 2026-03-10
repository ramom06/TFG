package org.example.proyectocampeonato.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CombateDTO {

    private Long idCompetidorRojo;
    private Long idCampeonato;
    private Long idCategoria;
    private Integer numeroTatami;

    private Long idCompetidorAzul;

    private String nombreCompetidorRojo;

    private String nombreCompetidorAzul;

    private String ronda;
    private Integer puntuacionRojo;
    private Integer puntuacionAzul;
    private String senshu;
    private String estado;

    private LocalTime horaProgramada;
    private LocalDateTime horaInicioReal;
    private Integer duracionSegundos;
    private String observaciones;
    private Integer numeroCombate;
}
