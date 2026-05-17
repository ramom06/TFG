package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.excepcion.CategoriaNotFoundException;
import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException;
import org.example.proyectocampeonato.excepcion.InscripcionNotFoundException;
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

    public InscripcionService(InscripcionRepository inscripcionRepository, CampeonatoRepository campeonatoRepository, CategoriaRepository categoriaRepository, CompetidorRepository competidorRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.campeonatoRepository = campeonatoRepository;
        this.categoriaRepository = categoriaRepository;
        this.competidorRepository = competidorRepository;
    }

    public List<Inscripcion> getByCompetidor(Long idCompetidor) {return inscripcionRepository.findByCompetidor(idCompetidor);}

    public List<Inscripcion> getByCampeonatoAndCategoria(Long idCampeonato, Long idCategoria) {return inscripcionRepository.findByCampeonatoAndCategoria(idCampeonato, idCategoria);}

    @Transactional
    public Inscripcion save(Long idCampeonato, Long idCategoria, Long idCompetidor) {
        Campeonato campeonato = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new CampeonatoNotFoundException(idCampeonato));

        String estado = campeonato.getEstado();

        if ("inscripciones_cerradas".equals(estado) || "pasado".equals(estado)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Las inscripciones de este campeonato ya están cerradas");
        }

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new CategoriaNotFoundException(idCategoria));

        Competidor competidor = competidorRepository.findById(idCompetidor)
                .orElseThrow(() -> new CompetidorNotFoundException(idCompetidor));

        Inscripcion_Id id = new Inscripcion_Id(idCampeonato, idCategoria, idCompetidor);

        if (inscripcionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El competidor ya está inscrito en esa categoría del campeonato");
        }

        // Un competidor solo puede inscribirse a 1 categoría por modalidad en cada campeonato
        String modalidadNueva = categoria.getModalidad();

        boolean yaInscritoEnModalidad = inscripcionRepository.findByCompetidor(idCompetidor).stream()
                .filter(i -> i.getCampeonato().getIdCampeonato().equals(idCampeonato))
                .anyMatch(i -> modalidadNueva.equalsIgnoreCase(i.getCategoria().getModalidad()));

        if (yaInscritoEnModalidad) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El competidor ya está inscrito en otra categoría de " + modalidadNueva + " en este campeonato");
        }

        Inscripcion inscripcion = Inscripcion.builder()
                .idInscripcion(id)
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
            throw new InscripcionNotFoundException(id);
        }
        inscripcionRepository.deleteById(id);
    }
}
