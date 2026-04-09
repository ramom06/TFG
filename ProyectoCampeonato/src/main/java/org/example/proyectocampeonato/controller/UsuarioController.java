package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "https://www.campeonatolive.online")

public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> all() {
        log.info("Obteniendo todos los usuarios");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> one(@PathVariable Long id) {
        log.info("Obteniendo usuario con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> byUsername(@PathVariable String username) {
        log.info("Obteniendo usuario por username: {}", username);
        return ResponseEntity.ok(service.findByNombre(username));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> byRol(@PathVariable Usuario.Rol rol) {
        log.info("Obteniendo usuarios con rol: {}", rol);
        return ResponseEntity.ok(service.findByRol(rol));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        log.info("Creando usuario: {}", usuario.getNombre());
        return new ResponseEntity<>(service.save(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> replace(@PathVariable Long id, @RequestBody Usuario usuario) {
        log.info("Actualizando usuario con id: {}", id);
        return ResponseEntity.ok(service.replace(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando usuario con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}