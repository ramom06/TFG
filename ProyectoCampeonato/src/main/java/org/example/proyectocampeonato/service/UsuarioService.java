package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario one(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado"));
    }

    public Usuario findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario '" + nombre + "' no encontrado"));
    }

    public List<Usuario> findByRol(Usuario.Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        validarNombreUnico(usuario.getNombre());
        validarEmailUnico(usuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario replace(Long id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(existing -> {
                    if (!existing.getNombre().equals(usuario.getNombre()))
                        validarNombreUnico(usuario.getNombre());
                    if (!existing.getEmail().equals(usuario.getEmail()))
                        validarEmailUnico(usuario.getEmail());
                    usuario.setIdUsuario(id);
                    usuario.setFechaRegistro(existing.getFechaRegistro());
                    if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
                        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                    } else {
                        usuario.setPassword(existing.getPassword());
                    }
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado");
        usuarioRepository.deleteById(id);
    }

    private void validarNombreUnico(String nombre) {
        if (usuarioRepository.existsByNombre(nombre))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre '" + nombre + "' ya está en uso");
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email '" + email + "' ya está registrado");
    }
}