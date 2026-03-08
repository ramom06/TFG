package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.dto.UsuarioDTO;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> all() {
        log.info("Obteniendo todos los usuarios");
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> one(@PathVariable Long id) {
        log.info("Obteniendo usuario con id: {}", id);
        return ResponseEntity.ok(service.one(id));
    }

    // GET /api/usuarios/username/{username}
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDTO> byUsername(@PathVariable String username) {
        log.info("Obteniendo usuario por username: {}", username);
        return ResponseEntity.ok(service.findByUsername(username));
    }

    // GET /api/usuarios/rol/{rol}  →  ej: /api/usuarios/rol/ADMIN
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioDTO>> byRol(@PathVariable Usuario.Rol rol) {
        log.info("Obteniendo usuarios con rol: {}", rol);
        return ResponseEntity.ok(service.findByRol(rol));
    }

    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody UsuarioDTO dto) {
        log.info("Creando usuario: {}", dto.getUsername());
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> replace(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        log.info("Actualizando usuario con id: {}", id);
        return ResponseEntity.ok(service.replace(id, dto));
    }

    // PATCH /api/usuarios/{id}/toggle-activo
    @PatchMapping("/{id}/toggle-activo")
    public ResponseEntity<UsuarioDTO> toggleActivo(@PathVariable Long id) {
        log.info("Cambiando estado activo del usuario con id: {}", id);
        return ResponseEntity.ok(service.toggleActivo(id));
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando usuario con id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}