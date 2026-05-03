package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.InscripcionDTO;
import org.example.proyectocampeonato.mapper.InscripcionMapper;
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
    private final InscripcionDTO inscripcionDTO;
    private final InscripcionMapper mapper;

    public InscripcionController(InscripcionService service, InscripcionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
        this.inscripcionDTO = new InscripcionDTO();
    }

    @GetMapping("/competidor/{id}")
    public ResponseEntity<List<InscripcionDTO>> getByCompetidor(@PathVariable Long id) {
        log.info("Obteniendo inscripciones del competidor con id: {}", id);
        return ResponseEntity.ok(mapper.toDTOList(service.getByCompetidor(id)));
    }

    @GetMapping("/campeonato/{idCampeonato}/categoria/{idCategoria}")
    public ResponseEntity<List<InscripcionDTO>> getByCampeonatoAndCategoria(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria) {
        log.info("Obteniendo inscripciones del campeonato {} en categoría {}", idCampeonato, idCategoria);
        return ResponseEntity.ok(mapper.toDTOList(service.getByCampeonatoAndCategoria(idCampeonato, idCategoria)));
    }

    @PostMapping("/{idCampeonato}/{idCategoria}/{idCompetidor}")
    public ResponseEntity<InscripcionDTO> save(
            @PathVariable Long idCampeonato,
            @PathVariable Long idCategoria,
            @PathVariable Long idCompetidor) {
        log.info("Inscribiendo competidor {} en campeonato {} categoría {}",
                idCompetidor, idCampeonato, idCategoria);
        //HTTPS.CREATE --> Es la respuesta a devolver para comunicar que todo a ido bien
        return new ResponseEntity<>(mapper.toDTO(service.save(idCampeonato,idCategoria,idCompetidor)),HttpStatus.CREATED);
    }

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
}