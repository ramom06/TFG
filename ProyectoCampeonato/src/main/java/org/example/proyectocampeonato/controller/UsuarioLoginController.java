package org.example.proyectocampeonato.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "https://www.campeonatolive.online")
public class UsuarioLoginController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioLoginController(UsuarioRepository usuarioRepository,
                                  PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String dni      = body.get("dni");
        String password = body.get("password");

        log.info("Intento de login para DNI: {}", dni);

        var usuarioOpt = usuarioRepository.findByDni(dni);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }

        if (usuario.getRol() != Usuario.Rol.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "No tienes permisos de administrador"));
        }

        return ResponseEntity.ok(Map.of(
                "id",     usuario.getIdUsuario(),
                "nombre", usuario.getNombre(),
                "email",  usuario.getEmail(),
                "rol",    usuario.getRol().name()
        ));
    }

    @PostMapping("/login-competidor")
    public ResponseEntity<?> loginCompetidor(@RequestBody Map<String, String> body) {
        String dni      = body.get("dni");
        String password = body.get("password");

        var usuarioOpt = usuarioRepository.findByDni(dni);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }

        if (usuario.getRol() != Usuario.Rol.COMPETIDOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Esta cuenta no es de competidor"));
        }

        return ResponseEntity.ok(Map.of(
                "id",        usuario.getIdUsuario(),
                "nombre",    usuario.getNombre(),
                "apellidos", usuario.getApellidos(),
                "email",     usuario.getEmail(),
                "rol",       usuario.getRol().name()
        ));
    }
}