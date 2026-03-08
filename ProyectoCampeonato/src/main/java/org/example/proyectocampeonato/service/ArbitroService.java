package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.dto.ArbitroDTO;
import org.example.proyectocampeonato.mapperDTO.DtoMapper;
import org.example.proyectocampeonato.modelo.Arbitro;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.ArbitroRepository;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArbitroService {

    private final ArbitroRepository arbitroRepository;
    private final UsuarioRepository usuarioRepository;
    private final DtoMapper mapper;

    public ArbitroService(ArbitroRepository arbitroRepository,
                          UsuarioRepository usuarioRepository,
                          DtoMapper mapper) {
        this.arbitroRepository = arbitroRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public List<ArbitroDTO> getAll() {
        return arbitroRepository.findAll().stream()
                .map(mapper::toArbitroDTO)
                .collect(Collectors.toList());
    }

    public ArbitroDTO one(Long id) {
        return mapper.toArbitroDTO(
                arbitroRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Árbitro con id " + id + " no encontrado"))
        );
    }

    public ArbitroDTO findByLicencia(String licencia) {
        return mapper.toArbitroDTO(
                arbitroRepository.findByLicencia(licencia)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Árbitro con licencia " + licencia + " no encontrado"))
        );
    }

    public List<ArbitroDTO> findByCategoria(String categoriaArbitral) {
        return arbitroRepository.findByCategoriaArbitral(categoriaArbitral).stream()
                .map(mapper::toArbitroDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArbitroDTO save(ArbitroDTO dto) {
        validarNombreUnico(dto.getNombre());
        validarEmailUnico(dto.getEmail());
        validarLicenciaUnica(dto.getLicencia());

        Arbitro entidad = mapper.toArbitroEntity(dto);
        entidad.setRol(Usuario.Rol.ARBITRO);
        return mapper.toArbitroDTO(arbitroRepository.save(entidad));
    }

    @Transactional
    public ArbitroDTO replace(Long id, ArbitroDTO dto) {
        return arbitroRepository.findById(id)
                .map(existing -> {
                    if (!existing.getNombre().equals(dto.getNombre())) {
                        validarNombreUnico(dto.getNombre());
                    }
                    if (!existing.getEmail().equals(dto.getEmail())) {
                        validarEmailUnico(dto.getEmail());
                    }
                    if (!existing.getLicencia().equals(dto.getLicencia())) {
                        validarLicenciaUnica(dto.getLicencia());
                    }
                    Arbitro entidad = mapper.toArbitroEntity(dto);
                    entidad.setIdUsuario(id);
                    entidad.setRol(Usuario.Rol.ARBITRO);
                    entidad.setFechaRegistro(existing.getFechaRegistro());
                    return mapper.toArbitroDTO(arbitroRepository.save(entidad));
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Árbitro con id " + id + " no encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!arbitroRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Árbitro con id " + id + " no encontrado");
        }
        arbitroRepository.deleteById(id);
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

    private void validarLicenciaUnica(String licencia) {
        if (arbitroRepository.existsByLicencia(licencia)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un árbitro con la licencia " + licencia);
        }
    }
}
