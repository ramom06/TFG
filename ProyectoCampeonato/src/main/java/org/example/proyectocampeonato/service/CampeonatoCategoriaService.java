package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.modelo.Campeonato_Categoria;
import org.example.proyectocampeonato.modelo.Campeonato_Categoria_Id;
import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.repository.Campeonato_CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CampeonatoCategoriaService {

    private final Campeonato_CategoriaRepository repository;

    public CampeonatoCategoriaService(Campeonato_CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Campeonato_Categoria> getAll() {
        return this.repository.findAll();
    }

    public Campeonato_Categoria save(Campeonato_Categoria item) {
        return this.repository.save(item);
    }

    @Transactional
    public void delete(Long idCampeonato, Long idCategoria) {
        // Asumiendo que tu repositorio tiene un método delete personalizado o compuesto
        this.repository.deleteById(new Campeonato_Categoria_Id(idCampeonato, idCategoria));
    }

    public List<Categoria> getCategoriasPorCampeonato(Long id) {
        return repository.findCategoriasByCampeonatoId(id);
    }
}