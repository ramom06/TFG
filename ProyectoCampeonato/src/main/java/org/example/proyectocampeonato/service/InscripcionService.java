package org.example.proyectocampeonato.service;

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

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final CategoriaRepository categoriaRepository;
    private final CompetidorRepository competidorRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              CampeonatoRepository campeonatoRepository,
                              CategoriaRepository categoriaRepository,
                              CompetidorRepository competidorRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.campeonatoRepository = campeonatoRepository;
        this.categoriaRepository = categoriaRepository;
        this.competidorRepository = competidorRepository;
    }

    public List<Inscripcion> getByCompetidor(Long id_competidor) {return inscripcionRepository.findByCompetidor(id_competidor);}

    public List<Inscripcion> getByCampeonatoAndCategoria(Long id_campeonato, Long id_categoria) {
        return inscripcionRepository.findByCampeonatoAndCategoria(id_campeonato, id_categoria);
    }

    @Transactional
    public Inscripcion save(Long id_campeonato, Long id_categoria, Long id_competidor) {
        Campeonato campeonato = campeonatoRepository.findById(id_campeonato)
                .orElseThrow(() -> new CampeonatoNotFoundException(id_campeonato));

        Categoria categoria = categoriaRepository.findById(id_categoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría con id " + id_categoria + " no encontrada"));

        Competidor competidor = competidorRepository.findById(id_competidor)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Competidor con id " + id_competidor + " no encontrado"));

        Inscripcion_Id id = new Inscripcion_Id(id_campeonato, id_categoria, id_competidor);

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

        return inscripcionRepository.save(inscripcion);
    }

    @Transactional
    public void delete(Long id_campeonato, Long id_categoria, Long id_competidor) {
        Inscripcion_Id id = new Inscripcion_Id(id_campeonato, id_categoria, id_competidor);
        if (!inscripcionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(id);
    }
}