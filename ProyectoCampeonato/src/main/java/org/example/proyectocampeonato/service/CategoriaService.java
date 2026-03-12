package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    public Categoria one(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría con id " + id + " no encontrada"));
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria replace(Long id, Categoria categoria) {
        return categoriaRepository.findById(id)
                .map(existing -> {
                    categoria.setId_categoria(id);
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría con id " + id + " no encontrada"));
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría con id " + id + " no encontrada");
        categoriaRepository.deleteById(id);
    }
}