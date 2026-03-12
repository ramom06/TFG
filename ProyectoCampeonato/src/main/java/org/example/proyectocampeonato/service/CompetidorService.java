package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException;
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
        return competidorRepository.findAll();
    }

    public Competidor one(Long id) {
        return competidorRepository.findById(id)
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public Competidor save(Competidor competidor) {
        return competidorRepository.save(competidor);
    }

    @Transactional
    public Competidor replace(Long id, Competidor competidor) {
        return competidorRepository.findById(id)
                .map(existing -> {
                    competidor.setIdUsuario(id);
                    return competidorRepository.save(competidor);
                })
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!competidorRepository.existsById(id))
            throw new CompetidorNotFoundException(id);
        competidorRepository.deleteById(id);
    }
}