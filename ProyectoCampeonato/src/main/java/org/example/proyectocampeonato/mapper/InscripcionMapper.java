package org.example.proyectocampeonato.mapper;

import org.example.proyectocampeonato.dto.InscripcionDTO;
import org.example.proyectocampeonato.modelo.Inscripcion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InscripcionMapper {

    public InscripcionDTO toDTO(Inscripcion inscripcion) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setId_campeonato(inscripcion.getCampeonato().getId_campeonato());
        dto.setId_categoria(inscripcion.getCategoria().getId_categoria());
        dto.setId_competidor(inscripcion.getCompetidor().getIdUsuario());
        dto.setNombreCampeonato(inscripcion.getCampeonato().getNombre());
        dto.setNombreCategoria(inscripcion.getCategoria().getNombre());
        dto.setNombreCompetidor(
                inscripcion.getCompetidor().getNombre() + " " + inscripcion.getCompetidor().getApellidos()
        );
        dto.setClubCompetidor(inscripcion.getCompetidor().getClub());
        return dto;
    }

    public List<InscripcionDTO> toDTOList(List<Inscripcion> inscripciones) {
        return inscripciones.stream()
                .map(this::toDTO)
                .toList();
    }
}
