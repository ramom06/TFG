package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.dto.CompetidorDTO;
import org.example.proyectocampeonato.mapperDTO.DtoMapper;
import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException;
import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.repository.CompetidorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetidorService {

    private final CompetidorRepository competidorRepository;
    private final DtoMapper mapper;

    public CompetidorService(CompetidorRepository competidorRepository, DtoMapper mapper) {
        this.competidorRepository = competidorRepository;
        this.mapper = mapper;
    }

    public List<CompetidorDTO> getAll() {
        return competidorRepository.findAll().stream()
                .map(mapper::toCompetidorDTO)
                .collect(Collectors.toList());
    }

    public CompetidorDTO one(Long id) {
        return mapper.toCompetidorDTO(
                competidorRepository.findById(id)
                        .orElseThrow(() -> new CompetidorNotFoundException(id))
        );
    }

    @Transactional
    public CompetidorDTO save(CompetidorDTO dto) {
        Competidor entidad = mapper.toCompetidorEntity(dto);
        return mapper.toCompetidorDTO(competidorRepository.save(entidad));
    }

    @Transactional
    public CompetidorDTO replace(Long id, CompetidorDTO dto) {
        return competidorRepository.findById(id)
                .map(existing -> {
                    Competidor entidad = mapper.toCompetidorEntity(dto);
                    entidad.setIdUsuario(id);
                    return mapper.toCompetidorDTO(competidorRepository.save(entidad));
                })
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!competidorRepository.existsById(id)) {
            throw new CompetidorNotFoundException(id);
        }
        competidorRepository.deleteById(id);
    }
}