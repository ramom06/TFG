package org.example.proyectocampeonato.mapperDTO;

import org.example.proyectocampeonato.dto.*;
import org.example.proyectocampeonato.modelo.*;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public UsuarioDTO toUsuarioDTO(Usuario u) {
        return UsuarioDTO.builder()
                .id(u.getIdUsuario())
                .nombre(u.getNombre())
                .apellidos(u.getApellidos())
                .email(u.getEmail())
                .rol(u.getRol())
                .fechaRegistro(u.getFechaRegistro())
                .build();
    }

    public Usuario toUsuarioEntity(UsuarioDTO dto) {
        return Usuario.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .build();
    }

    public ArbitroDTO toArbitroDTO(Arbitro a) {
        return ArbitroDTO.builder()
                .id(a.getIdUsuario())
                .nombre(a.getNombre())
                .apellidos(a.getApellidos())
                .email(a.getEmail())
                .rol(a.getRol())
                .fechaRegistro(a.getFechaRegistro())
                .licencia(a.getLicencia())
                .categoriaArbitral(a.getCategoriaArbitral())
                .fechaNacimiento(a.getFechaNacimiento())
                .build();
    }

    public Arbitro toArbitroEntity(ArbitroDTO dto) {
        return Arbitro.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .fechaNacimiento(dto.getFechaNacimiento())
                .licencia(dto.getLicencia())
                .categoriaArbitral(dto.getCategoriaArbitral())
                .build();
    }

    public CampeonatoDTO toCampeonatoDTO(Campeonato c) {
        return CampeonatoDTO.builder()
                .id(c.getId_campeonato())
                .nombre(c.getNombre())
                .fechaInicio(c.getFechaInicio())
                .fechaFin(c.getFechaFin())
                .ubicacion(c.getUbicacion())
                .estado(c.getEstado())
                .nivel(c.getNivel())
                .descripcion(c.getDescripcion())
                .urlPortada(c.getUrlPortada())
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

    public CategoriaDTO toCategoriaDTO(Categoria c) {
        return CategoriaDTO.builder()
                .id(c.getId_categoria())
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

    public CompetidorDTO toCompetidorDTO(Competidor c) {
        java.time.LocalDate fechaNac = null;
        if (c.getFechaNacimiento() != null) {
            fechaNac = c.getFechaNacimiento().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
        }
        return CompetidorDTO.builder()
                .id(c.getIdUsuario())
                .nombre(c.getNombre())
                .apellidos(c.getApellidos())
                .dni(c.getDni())
                .fechaNacimiento(fechaNac)
                .genero(c.getGenero())
                .club(c.getClub())
                .federacionAutonomica(c.getFederacionAutonomica())
                .build();
    }

    public Competidor toCompetidorEntity(CompetidorDTO dto) {
        java.util.Date fechaNac = null;
        if (dto.getFechaNacimiento() != null) {
            fechaNac = java.util.Date.from(
                    dto.getFechaNacimiento()
                            .atStartOfDay(java.time.ZoneId.systemDefault())
                            .toInstant()
            );
        }
        return Competidor.builder()
                .nombre(dto.getNombre())
                .apellidos(dto.getApellidos())
                .dni(dto.getDni())
                .fechaNacimiento(fechaNac)
                .genero(dto.getGenero())
                .club(dto.getClub())
                .federacionAutonomica(dto.getFederacionAutonomica())
                .build();
    }


    public InscripcionDTO toInscripcionDTO(Inscripcion i) {
        return InscripcionDTO.builder()
                .idCampeonato(i.getCampeonato().getId_campeonato())
                .idCategoria(i.getCategoria().getId_categoria())
                .idCompetidor(i.getCompetidor().getIdUsuario())
                .nombreCampeonato(i.getCampeonato().getNombre())
                .nombreCategoria(i.getCategoria().getNombre())
                .nombreCompetidor(i.getCompetidor().getNombre() + " " + i.getCompetidor().getApellidos())
                .clubCompetidor(i.getCompetidor().getClub())
                .build();
    }

    public CombateDTO toCombateDTO(Combate c) {
        CombateDTO.CombateDTOBuilder builder = CombateDTO.builder()
                // Nueva PK — ya no incluye idCompetidorRojo
                .idCampeonato(c.getId().getIdCampeonato())
                .idCategoria(c.getId().getIdCategoria())
                .numeroTatami(c.getId().getNumeroTatami())
                .numeroCombate(c.getId().getNumeroCombate())  // ← nuevo campo
                // Competidores como atributos normales
                .idCompetidorRojo(c.getCompetidorRojo().getIdUsuario())
                .nombreCompetidorRojo(c.getCompetidorRojo().getNombre() + " " + c.getCompetidorRojo().getApellidos())
                .ronda(c.getRonda())
                .puntuacionRojo(c.getPuntuacionRojo())
                .puntuacionAzul(c.getPuntuacionAzul())
                .senshu(c.getSenshu())
                .estado(c.getEstado())
                .duracionSegundos(c.getDuracionSegundos())
                .observaciones(c.getObservaciones())
                .horaProgramada(c.getHoraProgramada())
                .horaInicioReal(c.getHoraInicioReal());

        if (c.getCompetidorAzul() != null) {
            builder.idCompetidorAzul(c.getCompetidorAzul().getIdUsuario())
                    .nombreCompetidorAzul(
                            c.getCompetidorAzul().getNombre() + " " + c.getCompetidorAzul().getApellidos()
                    );
        }

        return builder.build();
    }

    public Campeonato_CategoriaDTO toCampeonatoCategoriaDTO(Campeonato_Categoria cc) {
        return Campeonato_CategoriaDTO.builder()
                .idCampeonato(cc.getCampeonato().getId_campeonato())
                .idCategoria(cc.getCategoria().getId_categoria())
                .nombreCampeonato(cc.getCampeonato().getNombre())
                .nombreCategoria(cc.getCategoria().getNombre())
                .fechaInicioCampeonato(cc.getCampeonato().getFechaInicio())
                .fechaFinCampeonato(cc.getCampeonato().getFechaFin())
                .modalidad(cc.getCategoria().getModalidad())
                .genero(cc.getCategoria().getGenero())
                .build();
    }

    public ClasificacionDTO toClasificacionDTO(Clasificacion cl) {
        return ClasificacionDTO.builder()
                .nombreCategoria(cl.getNombreCategoria())
                .nombreCompetidor(cl.getNombreCompetidor())
                .puntos(cl.getPuntos())
                .build();
    }
}
