package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.InscripcionDTO;
import org.example.proyectocampeonato.mapper.InscripcionMapper;
import org.example.proyectocampeonato.service.InscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;
    private final InscripcionMapper mapper;

    public InscripcionController(InscripcionService service, InscripcionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/competidor/{id_competidor}")
    public ResponseEntity<List<InscripcionDTO>> getByCompetidor(@PathVariable Long id_competidor) {return ResponseEntity.ok(mapper.toDTOList(service.getByCompetidor(id_competidor)));}

    @GetMapping("/campeonato/{id_campeonato}/categoria/{id_categoria}")
    public ResponseEntity<List<InscripcionDTO>> getByCampeonatoAndCategoria(
            @PathVariable Long id_campeonato,
            @PathVariable Long id_categoria) {
        log.info("Obteniendo inscripciones del campeonato {} en categoría {}", id_campeonato, id_categoria);
        return ResponseEntity.ok(mapper.toDTOList(service.getByCampeonatoAndCategoria(id_campeonato, id_categoria)));
    }

    // POST /api/inscripciones/{id_campeonato}/{id_categoria}/{id_competidor}
    @PostMapping("/{id_campeonato}/{id_categoria}/{id_competidor}")
    public ResponseEntity<InscripcionDTO> save(
            @PathVariable Long id_campeonato,
            @PathVariable Long id_categoria,
            @PathVariable Long id_competidor) {
        log.info("Inscribiendo competidor {} en campeonato {} categoría {}",
                id_competidor, id_campeonato, id_categoria);
        return new ResponseEntity<>(
                mapper.toDTO(service.save(id_campeonato, id_categoria, id_competidor)),
                HttpStatus.CREATED
        );
    }

    // DELETE /api/inscripciones/{id_campeonato}/{id_categoria}/{id_competidor}
    @DeleteMapping("/{id_campeonato}/{id_categoria}/{id_competidor}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id_campeonato,
            @PathVariable Long id_categoria,
            @PathVariable Long id_competidor) {
        service.delete(id_campeonato, id_categoria, id_competidor);
        return ResponseEntity.noContent().build();
    }
}