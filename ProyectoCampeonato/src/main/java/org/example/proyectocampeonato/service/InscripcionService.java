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

    public List<Inscripcion> getByCompetidor(Long idCompetidor) {
        return inscripcionRepository.findByCompetidor(idCompetidor);
    }

    public List<Inscripcion> getByCampeonatoAndCategoria(Long idCampeonato, Long idCategoria) {
        return inscripcionRepository.findByCampeonatoAndCategoria(Math.toIntExact(idCampeonato), idCategoria);
    }

    @Transactional
    public Inscripcion save(Long idCampeonato, Long idCategoria, Long idCompetidor) {
        Campeonato campeonato = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new CampeonatoNotFoundException(idCampeonato));

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría con id " + idCategoria + " no encontrada"));

        Competidor competidor = competidorRepository.findById(idCompetidor)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Competidor con id " + idCompetidor + " no encontrado"));

        Inscripcion_Id id = new Inscripcion_Id(idCampeonato, idCategoria, idCompetidor);

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
    public void delete(Long idCampeonato, Long idCategoria, Long idCompetidor) {
        Inscripcion_Id id = new Inscripcion_Id(idCampeonato, idCategoria, idCompetidor);
        if (!inscripcionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(id);
    }
}