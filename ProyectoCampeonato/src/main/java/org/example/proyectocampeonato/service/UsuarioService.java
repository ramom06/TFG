package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.mapperDTO.DtoMapper;
import org.example.proyectocampeonato.dto.UsuarioDTO;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final DtoMapper mapper;

    public UsuarioService(UsuarioRepository usuarioRepository, DtoMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public List<UsuarioDTO> getAll() {
        return usuarioRepository.findAll().stream()
                .map(mapper::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO one(Long id) {
        return mapper.toUsuarioDTO(
                usuarioRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado"))
        );
    }

    public UsuarioDTO findByNombre(String nombre) {
        return mapper.toUsuarioDTO(
                usuarioRepository.findByNombre(nombre)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Usuario '" + nombre + "' no encontrado"))
        );
    }

    public List<UsuarioDTO> findByRol(Usuario.Rol rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(mapper::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO save(UsuarioDTO dto) {
        validarNombreUnico(dto.getNombre());
        validarEmailUnico(dto.getEmail());
        Usuario entidad = mapper.toUsuarioEntity(dto);
        return mapper.toUsuarioDTO(usuarioRepository.save(entidad));
    }

    @Transactional
    public UsuarioDTO replace(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id)
                .map(existing -> {
                    if (!existing.getNombre().equals(dto.getNombre())) {
                        validarNombreUnico(dto.getNombre());
                    }
                    if (!existing.getEmail().equals(dto.getEmail())) {
                        validarEmailUnico(dto.getEmail());
                    }
                    Usuario entidad = mapper.toUsuarioEntity(dto);
                    entidad.setIdUsuario(id);
                    entidad.setFechaRegistro(existing.getFechaRegistro());
                    return mapper.toUsuarioDTO(usuarioRepository.save(entidad));
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private void validarNombreUnico(String nombre) {
        if (usuarioRepository.existsByNombre(nombre)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El nombre '" + nombre + "' ya está en uso");
        }
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El email '" + email + "' ya está registrado");
        }
    }
}
