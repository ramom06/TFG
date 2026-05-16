package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.modelo.Combate;
import org.example.proyectocampeonato.modelo.Combate_Id;
import org.example.proyectocampeonato.repository.CombateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CombateService {

    private final CombateRepository combateRepository;

    public CombateService(CombateRepository combateRepository) {this.combateRepository = combateRepository;}

    public List<Combate> getAll() {
        return combateRepository.findAll();
    }

    public List<Combate> getByCampeonatoCategoria(Long idCampeonato, Long idCategoria) {
        return combateRepository.findByIdIdCampeonatoAndIdIdCategoria(idCampeonato, idCategoria);
    }

    public List<Combate> getByCompetidor(Long idCompetidor) {
        return combateRepository.findByCompetidor(idCompetidor);
    }

    public Combate one(Combate_Id id) {
        return combateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Combate con id " + id + " no encontrado"));
    }

    @Transactional
    public Combate save(Combate combate) {
        if (combate.getIdCombate() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El combate debe tener un id (id_campeonato + id_categoria + numeroCombate)");
        return combateRepository.save(combate);
    }

    @Transactional
    public Combate replace(Combate_Id id, Combate combate) {
        if (!combateRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Combate con id " + id + " no encontrado");
        combate.setIdCombate(id);
        return combateRepository.save(combate);
    }

    @Transactional
    public void delete(Combate_Id id) {
        if (!combateRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Combate con id " + id + " no encontrado");
        combateRepository.deleteById(id);
    }
}
