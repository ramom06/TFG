package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.CombateDTO;
import org.example.proyectocampeonato.modelo.Combate_Id;
import org.example.proyectocampeonato.service.CombateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/combates")
public class CombateController {

    private final CombateService service;

    public CombateController(CombateService service) {
        this.service = service;
    }

    // GET /api/combates
    @GetMapping
    public ResponseEntity<List<CombateDTO>> all() {
        log.info("Obteniendo todos los combates");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/combates/campeonato/{idCampeonato}/categoria/{idCategoria}
    @GetMapping("/campeonato/{idCampeonato}/categoria/{idCategoria}")
    public ResponseEntity<List<CombateDTO>> getByCampeonatoCategoria(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria) {
        log.info("Obteniendo combates del campeonato {} en categoría {}", idCampeonato, idCategoria);
        return ResponseEntity.ok(service.getByCampeonatoCategoria(idCampeonato, idCategoria));
    }

    // GET /api/combates/competidor/{idCompetidor}
    @GetMapping("/competidor/{idCompetidor}")
    public ResponseEntity<List<CombateDTO>> getByCompetidor(@PathVariable Long idCompetidor) {
        log.info("Obteniendo combates del competidor con id: {}", idCompetidor);
        return ResponseEntity.ok(service.getByCompetidor(idCompetidor));
    }

    // POST /api/combates
    @PostMapping
    public ResponseEntity<CombateDTO> save(@RequestBody CombateDTO dto) {
        log.info("Creando combate en tatami {}", dto.getNumeroTatami());
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    // PUT /api/combates/{idCompetidorRojo}/{idCampeonato}/{idCategoria}/{numeroTatami}
    @PutMapping("/{idCompetidorRojo}/{idCampeonato}/{idCategoria}/{numeroTatami}")
    public ResponseEntity<CombateDTO> replace(
            @PathVariable Long idCompetidorRojo,
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Integer numeroTatami,
            @RequestBody CombateDTO dto) {
        Combate_Id id = new Combate_Id(idCompetidorRojo, idCampeonato, idCategoria, numeroTatami);
        log.info("Actualizando combate: {}", id);
        return ResponseEntity.ok(service.replace(id, dto));
    }

    // DELETE /api/combates/{idCompetidorRojo}/{idCampeonato}/{idCategoria}/{numeroTatami}
    @DeleteMapping("/{idCompetidorRojo}/{idCampeonato}/{idCategoria}/{numeroTatami}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idCompetidorRojo,
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Integer numeroTatami) {
        Combate_Id id = new Combate_Id(idCompetidorRojo, idCampeonato, idCategoria, numeroTatami);
        log.info("Eliminando combate: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}