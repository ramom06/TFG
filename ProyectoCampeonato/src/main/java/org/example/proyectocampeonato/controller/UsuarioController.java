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
@CrossOrigin(origins = "*")

public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService service, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.one(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> byUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.findByNombre(username));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> byRol(@PathVariable Usuario.Rol rol) {
        return ResponseEntity.ok(service.findByRol(rol));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(service.save(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> replace(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.replace(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Login para administradores.
     * Devuelve id, nombre, email y rol.
     * Devuelve 401 si las credenciales no son válidas y 403 si el usuario no tiene rol ADMIN.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> body) {
        String dni      = body.get("dni");
        String password = body.get("password");

        return usuarioRepository.findByDni(dni)
                //Valida contraseña
                .filter(usuario -> passwordEncoder.matches(password, usuario.getPassword()))
                .filter(usuario -> usuario.getRol() == Usuario.Rol.ADMIN)
                //Creamos respuesta exitosa
                .map(usuario -> ResponseEntity.ok((Object) Map.of(
                        "id",     usuario.getIdUsuario(),
                        "nombre", usuario.getNombre(),
                        "email",  usuario.getEmail(),
                        "rol",    usuario.getRol().name()
                )))
                //Si no existe o no es admin
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Credenciales incorrectas o acceso denegado")));
    }

    //Login competidores
    @PostMapping("/login-competidor")
    public ResponseEntity<?> loginCompetidor(@RequestBody Map<String, String> body) {
        String dni      = body.get("dni");
        String password = body.get("password");

        return usuarioRepository.findByDni(dni)
                //Valida contraseña
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .filter(u -> u.getRol() == Usuario.Rol.COMPETIDOR)
                //Creamos respuesta exitosa
                .map(usuario -> ResponseEntity.ok((Object) Map.of(
                        "id",              usuario.getIdUsuario(),
                        "id_usuario",      usuario.getIdUsuario(),
                        "nombre",          usuario.getNombre(),
                        "apellidos",       usuario.getApellidos() != null ? usuario.getApellidos() : "",
                        "email",           usuario.getEmail(),
                        "rol",             usuario.getRol().name(),
                        "genero",          String.valueOf(usuario.getGenero()),
                        "fechaNacimiento", usuario.getFechaNacimiento() != null
                                ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(usuario.getFechaNacimiento())
                                : ""
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Credenciales incorrectas o acceso no autorizado")));
    }
}