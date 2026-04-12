package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.example.proyectocampeonato.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "https://www.campeonatolive.online")

public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService service,
                             UsuarioRepository usuarioRepository,
                             PasswordEncoder passwordEncoder) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
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

    /**
     * Login para competidores.
     * Devuelve id, nombre, apellidos, email, rol y genero.
     * El campo 'id' (no 'idUsuario') es el que usa el frontend para identificar al competidor.
     */
    @PostMapping("/login-competidor")
    public ResponseEntity<?> loginCompetidor(@RequestBody Map<String, String> body) {
        String dni      = body.get("dni");
        String password = body.get("password");

        var usuarioOpt = usuarioRepository.findByDni(dni);
        if (usuarioOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));

        if (usuario.getRol() != Usuario.Rol.COMPETIDOR)
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Esta cuenta no es de competidor"));

        // Devolvemos 'id' (no 'idUsuario') para que coincida con el frontend
        return ResponseEntity.ok(Map.of(
                "id",             usuario.getIdUsuario(),
                "nombre",         usuario.getNombre(),
                "apellidos",      usuario.getApellidos() != null ? usuario.getApellidos() : "",
                "email",          usuario.getEmail(),
                "rol",            usuario.getRol().name(),
                "genero",         String.valueOf(usuario.getGenero()),
                "fechaNacimiento", usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento().toString() : ""
        ));
    }
}