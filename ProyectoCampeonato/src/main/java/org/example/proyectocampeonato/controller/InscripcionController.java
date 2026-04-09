package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.InscripcionDTO;
import org.example.proyectocampeonato.modelo.Inscripcion;
import org.example.proyectocampeonato.service.InscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    // GET /api/inscripciones/competidor/{id}
    @GetMapping("/competidor/{id}")
    public ResponseEntity<List<InscripcionDTO>> getByCompetidor(@PathVariable Long id) {
        log.info("Obteniendo inscripciones del competidor con id: {}", id);
        return ResponseEntity.ok(service.getByCompetidor(id).stream()
                .map(this::toDTO).toList());
    }

    // GET /api/inscripciones/campeonato/{idCampeonato}/categoria/{idCategoria}
    @GetMapping("/campeonato/{idCampeonato}/categoria/{idCategoria}")
    public ResponseEntity<List<InscripcionDTO>> getByCampeonatoAndCategoria(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria) {
        log.info("Obteniendo inscripciones del campeonato {} en categoría {}", idCampeonato, idCategoria);
        return ResponseEntity.ok(
                service.getByCampeonatoAndCategoria(idCampeonato, idCategoria).stream()
                        .map(this::toDTO).toList()
        );
    }

    // POST /api/inscripciones/{idCampeonato}/{idCategoria}/{idCompetidor}
    @PostMapping("/{idCampeonato}/{idCategoria}/{idCompetidor}")
    public ResponseEntity<InscripcionDTO> save(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Long idCompetidor) {
        log.info("Inscribiendo competidor {} en campeonato {} categoría {}",
                idCompetidor, idCampeonato, idCategoria);
        return new ResponseEntity<>(toDTO(service.save(idCampeonato, idCategoria, idCompetidor)), HttpStatus.CREATED);
    }

    // DELETE /api/inscripciones/{idCampeonato}/{idCategoria}/{idCompetidor}
    @DeleteMapping("/{idCampeonato}/{idCategoria}/{idCompetidor}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Long idCompetidor) {
        log.info("Eliminando inscripción del competidor {} en campeonato {} categoría {}",
                idCompetidor, idCampeonato, idCategoria);
        service.delete(idCampeonato, idCategoria, idCompetidor);
        return ResponseEntity.noContent().build();
    }

    // ── Mapper entidad → DTO ─────────────────────────────────
    private InscripcionDTO toDTO(Inscripcion i) {
        return new InscripcionDTO(
                i.getCampeonato().getId_campeonato(),
                i.getCategoria().getId_categoria(),
                i.getCompetidor().getIdUsuario(),
                i.getCampeonato().getNombre(),
                i.getCategoria().getNombre(),
                i.getCompetidor().getNombre() + " " + i.getCompetidor().getApellidos(),
                i.getCompetidor().getClub()
        );
    }
}