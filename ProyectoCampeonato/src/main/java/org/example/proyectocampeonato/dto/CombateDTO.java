package org.example.proyectocampeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CombateDTO {

    private Integer numeroTatami;
    private Long idCompetidorAzul;
    private String nombreCompetidorRojo;
    private String nombreCompetidorAzul;
    private String ronda;
    private Integer puntuacionRojo;
    private Integer puntuacionAzul;
    private String senshu;
    private String estado;

    private Date horaProgramada;
    private Date horaInicioReal;
    private Integer duracionSegundos;

    private String observaciones;
}