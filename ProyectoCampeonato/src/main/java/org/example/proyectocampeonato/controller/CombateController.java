package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Combate;
import org.example.proyectocampeonato.modelo.Combate_Id;
import org.example.proyectocampeonato.service.CombateService;
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

    @GetMapping
    public ResponseEntity<List<Combate>> all() {return ResponseEntity.ok(service.getAll());}

    @GetMapping("/campeonato/{idCampeonato}/categoria/{idCategoria}")
    public ResponseEntity<List<Combate>> getByCampeonatoCategoria(@PathVariable Long idCampeonato, @PathVariable Long idCategoria) {
        return ResponseEntity.ok(service.getByCampeonatoCategoria(idCampeonato, idCategoria));
    }

    @GetMapping("/competidor/{idCompetidor}")
    public ResponseEntity<List<Combate>> getByCompetidor(@PathVariable Long idCompetidor) {return ResponseEntity.ok(service.getByCompetidor(idCompetidor));}

    @GetMapping("/{idCampeonato}/{idCategoria}/{numeroCombate}")
    public ResponseEntity<Combate> one(@PathVariable Long idCampeonato, @PathVariable Long idCategoria, @PathVariable Integer numeroCombate) {
        Combate_Id id = new Combate_Id(idCampeonato, idCategoria, numeroCombate);
        return ResponseEntity.ok(service.one(id));
    }

    @PostMapping
    public ResponseEntity<Combate> save(@RequestBody Combate combate) {return new ResponseEntity<>(service.save(combate), HttpStatus.CREATED);}

    @PutMapping("/{idCampeonato}/{idCategoria}/{numeroCombate}")
    public ResponseEntity<Combate> replace(@PathVariable Long idCampeonato, @PathVariable Long idCategoria, @PathVariable Integer numeroCombate, @RequestBody Combate combate) {
        Combate_Id id = new Combate_Id(idCampeonato, idCategoria, numeroCombate);
        return ResponseEntity.ok(service.replace(id, combate));
    }

    @DeleteMapping("/{idCampeonato}/{idCategoria}/{numeroCombate}")
    public ResponseEntity<Void> delete(@PathVariable Long idCampeonato, @PathVariable Long idCategoria, @PathVariable Integer numeroCombate) {
        Combate_Id id = new Combate_Id(idCampeonato, idCategoria, numeroCombate);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}