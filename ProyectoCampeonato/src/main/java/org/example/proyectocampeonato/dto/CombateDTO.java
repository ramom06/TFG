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

    // ── Clave compuesta (Combate_Id) ─────────────────────────────────────────
    private Long idCompetidorRojo;
    private Long idCampeonato;
    private Long idCategoria;
    private Integer numeroTatami;

    // ── Competidor azul (opcional) ───────────────────────────────────────────
    private Long idCompetidorAzul;

    // ── Datos informativos de los competidores (solo lectura) ────────────────
    private String nombreCompetidorRojo;
    private String nombreCompetidorAzul;

    // ── Datos del combate ────────────────────────────────────────────────────
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