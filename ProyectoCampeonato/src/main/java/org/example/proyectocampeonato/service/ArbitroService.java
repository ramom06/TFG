package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.Arbitro;
import org.example.proyectocampeonato.modelo.Campeonato;
import org.example.proyectocampeonato.repository.ArbitroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArbitroService {

    private ArbitroRepository arbitroRepository;

    public ArbitroService(ArbitroRepository arbitroRepository) {this.arbitroRepository = arbitroRepository;}

    public List<Arbitro> getAll() {
        return this.arbitroRepository.findAll();
    }

    public Arbitro save(Arbitro arbitro) {
        return this.arbitroRepository.save(arbitro);
    }

    public Arbitro one(Long id) {
        return this.arbitroRepository.findById(id)
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    @Transactional
    public Arbitro replace(Long id, Arbitro campeonato) {
        return this.arbitroRepository.findById(id)
                .map(p -> {

                    campeonato.setId_arbitro(id);
                    return this.arbitroRepository.save(campeonato);
                })
                .orElseThrow(() -> new CampeonatoNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!this.arbitroRepository.existsById(id)) {
            throw new CampeonatoNotFoundException(id);
        }
        this.arbitroRepository.deleteById(id);
    }
}
