package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
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

    /*public List<Categoria> getByGrupo(String grupo) {
        return categoriaRepository.findByGrupo(grupo);
    }

    public List<Categoria> getByModalidad(String modalidad) {
        return categoriaRepository.findByModalidad(modalidad);
    }*/

    public Categoria one(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria replace(Long id, Categoria categoria) {
        return categoriaRepository.findById(id)
                .map(c -> {
                    categoria.setId_categoria(id);
                    return categoriaRepository.save(categoria);
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