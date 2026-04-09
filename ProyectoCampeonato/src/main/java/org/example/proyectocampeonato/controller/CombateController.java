package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Combate;
import org.example.proyectocampeonato.modelo.Combate_Id;
import org.example.proyectocampeonato.service.CombateService;
import org.example.proyectocampeonato.service.CombateService.CombateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/combates")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class CombateController {

    private final CombateService service;

    public CombateController(CombateService service) {
        this.service = service;
    }

    // GET /api/combates
    @GetMapping
    public ResponseEntity<List<Combate>> all() {
        log.info("Obteniendo todos los combates");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/combates/campeonato/{idCampeonato}/categoria/{idCategoria}
    @GetMapping("/campeonato/{idCampeonato}/categoria/{idCategoria}")
    public ResponseEntity<List<Combate>> getByCampeonatoCategoria(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria) {
        log.info("Obteniendo combates del campeonato {} en categoría {}", idCampeonato, idCategoria);
        return ResponseEntity.ok(service.getByCampeonatoCategoria(idCampeonato, idCategoria));
    }

    // GET /api/combates/competidor/{idCompetidor}
    @GetMapping("/competidor/{idCompetidor}")
    public ResponseEntity<List<Combate>> getByCompetidor(@PathVariable Long idCompetidor) {
        log.info("Obteniendo combates del competidor con id: {}", idCompetidor);
        return ResponseEntity.ok(service.getByCompetidor(idCompetidor));
    }

    // POST /api/combates
    @PostMapping
    public ResponseEntity<Combate> save(@RequestBody CombateRequest req) {
        log.info("Creando combate tatami {} nº{}", req.numeroTatami(), req.numeroCombate());
        return new ResponseEntity<>(service.save(req), HttpStatus.CREATED);
    }

    // PUT /api/combates/{idCampeonato}/{idCategoria}/{numeroTatami}/{numeroCombate}
    @PutMapping("/{idCampeonato}/{idCategoria}/{numeroTatami}/{numeroCombate}")
    public ResponseEntity<Combate> replace(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Integer numeroTatami,
            @PathVariable Integer numeroCombate,
            @RequestBody CombateRequest req) {
        Combate_Id id = new Combate_Id(idCampeonato, idCategoria, numeroTatami, numeroCombate);
        log.info("Actualizando combate: {}", id);
        return ResponseEntity.ok(service.replace(id, req));
    }

    // DELETE /api/combates/{idCampeonato}/{idCategoria}/{numeroTatami}/{numeroCombate}
    @DeleteMapping("/{idCampeonato}/{idCategoria}/{numeroTatami}/{numeroCombate}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Integer numeroTatami,
            @PathVariable Integer numeroCombate) {
        Combate_Id id = new Combate_Id(idCampeonato, idCategoria, numeroTatami, numeroCombate);
        log.info("Eliminando combate: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}