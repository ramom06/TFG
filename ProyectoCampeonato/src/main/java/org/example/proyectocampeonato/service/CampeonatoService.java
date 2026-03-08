package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.dto.CampeonatoDTO;
import org.example.proyectocampeonato.mapperDTO.DtoMapper;
import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final DtoMapper mapper;

    public CampeonatoService(CampeonatoRepository campeonatoRepository, DtoMapper mapper) {
        this.campeonatoRepository = campeonatoRepository;
        this.mapper = mapper;
    }

    public List<CampeonatoDTO> getAll() {
        return campeonatoRepository.findAll().stream()
                .map(mapper::toCampeonatoDTO)
                .collect(Collectors.toList());
    }

    public CampeonatoDTO one(Long id) {
        return mapper.toCampeonatoDTO(
                campeonatoRepository.findById(id)
                        .orElseThrow(() -> new CampeonatoNotFoundException(id))
        );
    }

    @Transactional
    public CampeonatoDTO save(CampeonatoDTO dto) {
        Campeonato entidad = mapper.toCampeonatoEntity(dto);
        return mapper.toCampeonatoDTO(campeonatoRepository.save(entidad));
    }

    @Transactional
    public CampeonatoDTO replace(Long id, CampeonatoDTO dto) {
        return campeonatoRepository.findById(id)
                .map(existing -> {
                    Campeonato entidad = mapper.toCampeonatoEntity(dto);
                    entidad.setId_campeonato(id);
                    return mapper.toCampeonatoDTO(campeonatoRepository.save(entidad));
                })
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!campeonatoRepository.existsById(id)) {
            throw new CampeonatoNotFoundException(id);
        }
        campeonatoRepository.deleteById(id);
    }
}