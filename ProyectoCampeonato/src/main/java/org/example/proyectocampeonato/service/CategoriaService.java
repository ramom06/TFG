package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CategoriaNotFoundException;
import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Categoria one(Long id) {return categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));}

    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria replace(Long id, Categoria categoria) {
        if (!categoriaRepository.existsById(id)) throw new CategoriaNotFoundException(id);
        categoria.setIdCategoria(id);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) throw new CategoriaNotFoundException(id);
        categoriaRepository.deleteById(id);
    }
}
