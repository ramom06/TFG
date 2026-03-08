package org.example.proyectocampeonato.mapperDTO;

import org.example.proyectocampeonato.dto.*;
import org.example.proyectocampeonato.modelo.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoMapper {

    // ── USUARIO ──────────────────────────────────────────────────────────────

    public UsuarioDTO toUsuarioDTO(Usuario u) {
        return UsuarioDTO.builder()
                .id_usuario(u.getId_usuario())
                .username(u.getNombre())
                .email(u.getEmail())
                // password nunca se mapea hacia el DTO de salida
                .rol(u.getRol())
                .fechaRegistro(u.getFechaRegistro())
                .build();
    }

    public Usuario toUsuarioEntity(UsuarioDTO dto) {
        return Usuario.builder()
                .nombre(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .build();
    }

    // ── ARBITRO ──────────────────────────────────────────────────────────────

    public ArbitroDTO toArbitroDTO(Arbitro a) {
        return ArbitroDTO.builder()
                .id_usuario(a.getId_usuario())
                .email(a.getEmail())
                .fechaRegistro(a.getFechaRegistro())
                .nombre(a.getNombre())
                .apellidos(a.getApellidos())
                .licencia(a.getLicencia())
                .categoriaArbitral(a.getCategoriaArbitral())
                .fechaNacimiento(a.getFechaNacimiento())
                .build();
    }

    public Arbitro toArbitroEntity(ArbitroDTO dto) {
        return Arbitro.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .apellidos(dto.getApellidos())
                .licencia(dto.getLicencia())
                .categoriaArbitral(dto.getCategoriaArbitral())
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();
    }

    // ── CAMPEONATO ───────────────────────────────────────────────────────────

    public CampeonatoDTO toCampeonatoDTO(Campeonato c) {
        return CampeonatoDTO.builder()
                .id_campeonato(c.getId_campeonato())
                .nombre(c.getNombre())
                .fechaInicio(c.getFechaInicio())
                .fechaFin(c.getFechaFin())
                .ubicacion(c.getUbicacion())
                .estado(c.getEstado())
                .nivel(c.getNivel())
                .descripcion(c.getDescripcion())
                .urlPortada(c.getUrlPortada())
                .categorias(
                    c.getCampeonatoCategorias().stream()
                        .map(cc -> cc.getCategoria().getNombre())
                        .collect(Collectors.toList())
                )
                .build();
    }

    public Campeonato toCampeonatoEntity(CampeonatoDTO dto) {
        return Campeonato.builder()
                .nombre(dto.getNombre())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .ubicacion(dto.getUbicacion())
                .estado(dto.getEstado())
                .nivel(dto.getNivel())
                .descripcion(dto.getDescripcion())
                .urlPortada(dto.getUrlPortada())
                .build();
    }

    // ── CATEGORIA ────────────────────────────────────────────────────────────

    public CategoriaDTO toCategoriaDTO(Categoria c) {
        return CategoriaDTO.builder()
                .id_categoria(c.getId_categoria())
                .nombre(c.getNombre())
                .modalidad(c.getModalidad())
                .genero(c.getGenero())
                .pesoMinimo(c.getPesoMinimo())
                .pesoMaximo(c.getPesoMaximo())
                .edadMinima(c.getEdadMinima())
                .edadMaxima(c.getEdadMaxima())
                .build();
    }

    public Categoria toCategoriaEntity(CategoriaDTO dto) {
        return Categoria.builder()
                .nombre(dto.getNombre())
                .modalidad(dto.getModalidad())
                .genero(dto.getGenero())
                .pesoMinimo(dto.getPesoMinimo())
                .pesoMaximo(dto.getPesoMaximo())
                .edadMinima(dto.getEdadMinima())
                .edadMaxima(dto.getEdadMaxima())
                .build();
    }

    // ── COMPETIDOR ───────────────────────────────────────────────────────────

    public CompetidorDTO toCompetidorDTO(Competidor c) {
        return CompetidorDTO.builder()
                .id_competidor(c.getId_competidor())
                .nombre(c.getNombre())
                .apellidos(c.getApellidos())
                .dni(c.getDni())
                .fechaNacimiento(c.getFechaNacimiento())
                .genero(c.getGenero())
                .club(c.getClub())
                .federacionAutonomica(c.getFederacionAutonomica())
                .build();
    }

    public Competidor toCompetidorEntity(CompetidorDTO dto) {
        return Competidor.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .dni(dto.getDni())
                .fechaNacimiento(dto.getFechaNacimiento())
                .genero(dto.getGenero())
                .club(dto.getClub())
                .federacionAutonomica(dto.getFederacionAutonomica())
                .build();
    }

    // ── INSCRIPCION ──────────────────────────────────────────────────────────

    public InscripcionDTO toInscripcionDTO(Inscripcion i) {
        return InscripcionDTO.builder()
                .idCampeonato(i.getCampeonato().getId_campeonato())
                .idCategoria(i.getCategoria().getId_categoria())
                .idCompetidor(i.getCompetidor().getId_competidor())
                .nombreCampeonato(i.getCampeonato().getNombre())
                .nombreCategoria(i.getCategoria().getNombre())
                .nombreCompetidor(i.getCompetidor().getNombre() + " " + i.getCompetidor().getApellidos())
                .clubCompetidor(i.getCompetidor().getClub())
                .build();
    }

    // ── COMBATE ──────────────────────────────────────────────────────────────

    public CombateDTO toCombateDTO(Combate c) {
        CombateDTO.CombateDTOBuilder builder = CombateDTO.builder()
                .idCompetidorRojo(c.getCompetidorRojo().getId_competidor())
                .idCampeonato(c.getId().getId_campeonato())
                .idCategoria(c.getId().getId_categoria())
                .numeroTatami(c.getId().getNumeroTatami())
                .ronda(c.getRonda())
                .estado(c.getEstado())
                .senshu(c.getSenshu())
                .puntuacionRojo(c.getPuntuacionRojo())
                .puntuacionAzul(c.getPuntuacionAzul())
                .horaProgramada(c.getHoraProgramada())
                .horaInicioReal(c.getHoraInicioReal())
                .duracionSegundos(c.getDuracionSegundos())
                .observaciones(c.getObservaciones())
                .nombreCampeonato(c.getCampeonatoCategoria().getCampeonato().getNombre())
                .nombreCategoria(c.getCampeonatoCategoria().getCategoria().getNombre())
                .nombreCompletoRojo(c.getCompetidorRojo().getNombre() + " " + c.getCompetidorRojo().getApellidos());

        // Competidor azul es nullable (bye)
        if (c.getCompetidorAzul() != null) {
            builder.idCompetidorAzul(c.getCompetidorAzul().getId_competidor())
                   .nombreCompletoAzul(c.getCompetidorAzul().getNombre() + " " + c.getCompetidorAzul().getApellidos());
        }

        return builder.build();
    }

    // ── CAMPEONATO_CATEGORIA ─────────────────────────────────────────────────

    public Campeonato_CategoriaDTO toCampeonatoCategoriaDTO(Campeonato_Categoria cc) {
        return Campeonato_CategoriaDTO.builder()
                .idCampeonato(cc.getCampeonato().getId_campeonato())
                .idCategoria(cc.getCategoria().getId_categoria())
                .nombreCampeonato(cc.getCampeonato().getNombre())
                .nombreCategoria(cc.getCategoria().getNombre())
                .modalidad(cc.getCategoria().getModalidad())
                .genero(cc.getCategoria().getGenero())
                .build();
    }

    // ── CLASIFICACION ────────────────────────────────────────────────────────

    public ClasificacionDTO toClasificacionDTO(Clasificacion cl) {
        return ClasificacionDTO.builder()
                .idClasificacion(cl.getIdClasificacion())
                .nombreCategoria(cl.getNombreCategoria())
                .nombreCompetidor(cl.getNombreCompetidor())
                .puntos(cl.getPuntos())
                .build();
    }
}