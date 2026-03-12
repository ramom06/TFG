package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.modelo.Arbitro;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.ArbitroRepository;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArbitroService {

    private final ArbitroRepository arbitroRepository;
    private final UsuarioRepository usuarioRepository;

    public ArbitroService(ArbitroRepository arbitroRepository, UsuarioRepository usuarioRepository) {
        this.arbitroRepository = arbitroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Arbitro> getAll() {
        return arbitroRepository.findAll();
    }

    public Arbitro one(Long id) {
        return arbitroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Árbitro con id " + id + " no encontrado"));
    }

    public Arbitro findByLicencia(String licencia) {
        return arbitroRepository.findByLicencia(licencia)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Árbitro con licencia " + licencia + " no encontrado"));
    }

    public List<Arbitro> findByCategoria(String categoriaArbitral) {
        return arbitroRepository.findByCategoriaArbitral(categoriaArbitral);
    }

    @Transactional
    public Arbitro save(Arbitro arbitro) {
        validarNombreUnico(arbitro.getNombre());
        validarEmailUnico(arbitro.getEmail());
        validarLicenciaUnica(arbitro.getLicencia());
        arbitro.setRol(Usuario.Rol.ARBITRO);
        return arbitroRepository.save(arbitro);
    }

    @Transactional
    public Arbitro replace(Long id, Arbitro arbitro) {
        return arbitroRepository.findById(id)
                .map(existing -> {
                    if (!existing.getNombre().equals(arbitro.getNombre()))
                        validarNombreUnico(arbitro.getNombre());
                    if (!existing.getEmail().equals(arbitro.getEmail()))
                        validarEmailUnico(arbitro.getEmail());
                    if (!existing.getLicencia().equals(arbitro.getLicencia()))
                        validarLicenciaUnica(arbitro.getLicencia());
                    arbitro.setIdUsuario(id);
                    arbitro.setRol(Usuario.Rol.ARBITRO);
                    arbitro.setFechaRegistro(existing.getFechaRegistro());
                    return arbitroRepository.save(arbitro);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Árbitro con id " + id + " no encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!arbitroRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Árbitro con id " + id + " no encontrado");
        arbitroRepository.deleteById(id);
    }

    private void validarNombreUnico(String nombre) {
        if (usuarioRepository.existsByNombre(nombre))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre '" + nombre + "' ya está en uso");
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email '" + email + "' ya está registrado");
    }

    private void validarLicenciaUnica(String licencia) {
        if (arbitroRepository.existsByLicencia(licencia))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un árbitro con la licencia " + licencia);
    }
}