package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException; // Asegúrate de crear esta excepción
import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.repository.CompetidorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CompetidorService {

    private final CompetidorRepository competidorRepository;

    public CompetidorService(CompetidorRepository competidorRepository) {
        this.competidorRepository = competidorRepository;
    }

    public List<Competidor> getAll() {
        return this.competidorRepository.findAll();
    }

    public Competidor save(Competidor competidor) {
        return this.competidorRepository.save(competidor);
    }

    public Competidor one(Long id) {
        return this.competidorRepository.findById(id)
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public Competidor replace(Long id, Competidor competidor) {
        return this.competidorRepository.findById(id)
                .map(c -> {
                    competidor.setId_competidor(id);
                    return this.competidorRepository.save(competidor);
                })
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!this.competidorRepository.existsById(id)) {
            throw new CompetidorNotFoundException(id);
        }
        this.competidorRepository.deleteById(id);
    }
}