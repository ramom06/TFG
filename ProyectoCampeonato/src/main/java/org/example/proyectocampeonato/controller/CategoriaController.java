package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Categoria;
import org.example.proyectocampeonato.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> all() {
        log.info("Accediendo a todas las categorías");
        return ResponseEntity.ok(service.getAll());
    }
/*
    // GET /api/categorias?grupo=Juvenil
    @GetMapping(params = "grupo")
    public ResponseEntity<List<Categoria>> allByGrupo(@RequestParam String grupo) {
        log.info("Accediendo a categorías del grupo: {}", grupo);
        return ResponseEntity.ok(service.getByGrupo(grupo));
    }

    // GET /api/categorias?modalidad=kumite
    @GetMapping(params = "modalidad")
    public ResponseEntity<List<Categoria>> allByModalidad(@RequestParam String modalidad) {
        log.info("Accediendo a categorías de modalidad: {}", modalidad);
        return ResponseEntity.ok(service.getByModalidad(modalidad));
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> one(@PathVariable Long id) {
        log.info("Accediendo a categoría con ID: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
        log.info("Creando nueva categoría: {}", categoria.getNombre());
        return new ResponseEntity<>(service.save(categoria), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> replace(@PathVariable Long id, @RequestBody Categoria categoria) {
        log.info("Actualizando categoría con ID: {}", id);
        return ResponseEntity.ok(service.replace(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando categoría con ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}