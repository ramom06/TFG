package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.dto.CategoriaDTO;
import org.example.proyectocampeonato.dto.DtoMapper;
import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final DtoMapper mapper;

    public CategoriaService(CategoriaRepository categoriaRepository, DtoMapper mapper) {
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }

    public List<CategoriaDTO> getAll() {
        return categoriaRepository.findAll().stream()
                .map(mapper::toCategoriaDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO one(Long id) {
        return mapper.toCategoriaDTO(
                categoriaRepository.findById(id)
                        .orElseThrow(() -> new CampeonatoNotFoundException(id))
        );
    }

    @Transactional
    public CategoriaDTO save(CategoriaDTO dto) {
        Categoria entidad = mapper.toCategoriaEntity(dto);
        return mapper.toCategoriaDTO(categoriaRepository.save(entidad));
    }

    @Transactional
    public CategoriaDTO replace(Long id, CategoriaDTO dto) {
        return categoriaRepository.findById(id)
                .map(existing -> {
                    Categoria entidad = mapper.toCategoriaEntity(dto);
                    entidad.setId_categoria(id);
                    return mapper.toCategoriaDTO(categoriaRepository.save(entidad));
                })
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CampeonatoNotFoundException(id);
        }
        categoriaRepository.deleteById(id);
    }
}