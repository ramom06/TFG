package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.example.proyectocampeonato.repository.Campeonato_CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CampeonatoCategoriaService {

    private final Campeonato_CategoriaRepository repository;
    private final CampeonatoRepository campeonatoRepository;
    private final CategoriaRepository categoriaRepository;

    public CampeonatoCategoriaService(Campeonato_CategoriaRepository repository,
                                      CampeonatoRepository campeonatoRepository,
                                      CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.campeonatoRepository = campeonatoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Campeonato_Categoria> getAll() {
        return repository.findAll();
    }

    public List<Categoria> getCategoriasPorCampeonato(Long idCampeonato) {
        return repository.findCategoriasByCampeonatoId(idCampeonato);
    }

    @Transactional
    public Campeonato_Categoria asignarCategoria(Long idCampeonato, Long idCategoria) {
        Campeonato campeonato = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Campeonato con id " + idCampeonato + " no encontrado"));

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría con id " + idCategoria + " no encontrada"));

        Campeonato_Categoria_Id ccId = new Campeonato_Categoria_Id(idCampeonato, idCategoria);
        if (repository.existsById(ccId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La categoría ya está asignada a ese campeonato");
        }

        Campeonato_Categoria cc = new Campeonato_Categoria();
        cc.setId(ccId);
        cc.setCampeonato(campeonato);
        cc.setCategoria(categoria);

        return repository.save(cc);
    }

    @Transactional
    public void eliminarCategoria(Long idCampeonato, Long idCategoria) {
        Campeonato_Categoria_Id ccId = new Campeonato_Categoria_Id(idCampeonato, idCategoria);
        if (!repository.existsById(ccId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No existe la categoría " + idCategoria + " en el campeonato " + idCampeonato);
        }
        repository.deleteById(ccId);
    }
}