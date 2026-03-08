package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.dto.DtoMapper;
import org.example.proyectocampeonato.dto.InscripcionDTO;
import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.example.proyectocampeonato.repository.CompetidorRepository;
import org.example.proyectocampeonato.repository.InscripcionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final CategoriaRepository categoriaRepository;
    private final CompetidorRepository competidorRepository;
    private final DtoMapper mapper;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              CampeonatoRepository campeonatoRepository,
                              CategoriaRepository categoriaRepository,
                              CompetidorRepository competidorRepository,
                              DtoMapper mapper) {
        this.inscripcionRepository = inscripcionRepository;
        this.campeonatoRepository = campeonatoRepository;
        this.categoriaRepository = categoriaRepository;
        this.competidorRepository = competidorRepository;
        this.mapper = mapper;
    }

    public List<InscripcionDTO> getByCompetidor(Long idCompetidor) {
        return inscripcionRepository.findByCompetidor(idCompetidor).stream()
                .map(mapper::toInscripcionDTO)
                .collect(Collectors.toList());
    }

    public List<InscripcionDTO> getByCampeonatoAndCategoria(Long idCampeonato, Long idCategoria) {
        return inscripcionRepository.findByCampeonatoAndCategoria(idCampeonato, idCategoria).stream()
                .map(mapper::toInscripcionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public InscripcionDTO save(InscripcionDTO dto) {
        Campeonato campeonato = campeonatoRepository.findById(dto.getIdCampeonato())
                .orElseThrow(() -> new CampeonatoNotFoundException(dto.getIdCampeonato()));

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría con id " + dto.getIdCategoria() + " no encontrada"));

        Competidor competidor = competidorRepository.findById(dto.getIdCompetidor())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Competidor con id " + dto.getIdCompetidor() + " no encontrado"));

        Inscripcion_Id id = new Inscripcion_Id(
                dto.getIdCampeonato(),
                dto.getIdCategoria(),
                dto.getIdCompetidor()
        );

        if (inscripcionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El competidor ya está inscrito en esa categoría del campeonato");
        }

        Inscripcion inscripcion = Inscripcion.builder()
                .id_inscripcion(id)
                .campeonato(campeonato)
                .categoria(categoria)
                .competidor(competidor)
                .build();

        return mapper.toInscripcionDTO(inscripcionRepository.save(inscripcion));
    }

    @Transactional
    public void delete(Long idCampeonato, Long idCategoria, Long idCompetidor) {
        Inscripcion_Id id = new Inscripcion_Id(idCampeonato, idCategoria, idCompetidor);
        if (!inscripcionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(id);
    }
}