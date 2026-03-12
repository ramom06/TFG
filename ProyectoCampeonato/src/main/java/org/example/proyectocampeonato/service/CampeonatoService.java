package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    public List<Campeonato> getAll() {
        return campeonatoRepository.findAll();
    }

    public Campeonato one(Long id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    @Transactional
    public Campeonato save(Campeonato campeonato) {
        return campeonatoRepository.save(campeonato);
    }

    @Transactional
    public Campeonato replace(Long id, Campeonato campeonato) {
        return campeonatoRepository.findById(id)
                .map(existing -> {
                    campeonato.setId_campeonato(id);
                    return campeonatoRepository.save(campeonato);
                })
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!campeonatoRepository.existsById(id))
            throw new CampeonatoNotFoundException(id);
        campeonatoRepository.deleteById(id);
    }
}