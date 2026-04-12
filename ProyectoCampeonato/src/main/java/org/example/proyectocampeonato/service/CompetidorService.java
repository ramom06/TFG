package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.excepcion.CompetidorNotFoundException;
import org.example.proyectocampeonato.modelo.Competidor;
import org.example.proyectocampeonato.modelo.Usuario;
import org.example.proyectocampeonato.repository.CompetidorRepository;
import org.example.proyectocampeonato.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompetidorService {

    private final CompetidorRepository competidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CompetidorService(CompetidorRepository competidorRepository,
                             UsuarioRepository usuarioRepository,
                             PasswordEncoder passwordEncoder) {
        this.competidorRepository = competidorRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Competidor> getAll() {
        return competidorRepository.findAll();
    }

    public Competidor one(Long id) {
        return competidorRepository.findById(id)
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public Competidor save(Competidor competidor) {
        // Validar unicidad de DNI
        if (usuarioRepository.existsByDni(competidor.getDni())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un usuario con el DNI " + competidor.getDni());
        }
        // Validar unicidad de email
        if (usuarioRepository.existsByEmail(competidor.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El email '" + competidor.getEmail() + "' ya está registrado");
        }

        competidor.setRol(Usuario.Rol.COMPETIDOR);
        competidor.setPassword(passwordEncoder.encode(competidor.getPassword()));
        return competidorRepository.save(competidor);
    }

    @Transactional
    public Competidor replace(Long id, Competidor competidor) {
        return competidorRepository.findById(id)
                .map(existing -> {
                    if (!existing.getDni().equals(competidor.getDni())
                            && usuarioRepository.existsByDni(competidor.getDni())) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, "Ya existe un usuario con el DNI " + competidor.getDni());
                    }
                    // Validar email único si ha cambiado
                    if (!existing.getEmail().equals(competidor.getEmail())
                            && usuarioRepository.existsByEmail(competidor.getEmail())) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT, "El email '" + competidor.getEmail() + "' ya está registrado");
                    }
                    competidor.setIdUsuario(id);
                    competidor.setRol(Usuario.Rol.COMPETIDOR);
                    if (competidor.getPassword() != null && !competidor.getPassword().isBlank()) {
                        competidor.setPassword(passwordEncoder.encode(competidor.getPassword()));
                    } else {
                        competidor.setPassword(existing.getPassword());
                    }
                    return competidorRepository.save(competidor);
                })
                .orElseThrow(() -> new CompetidorNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        if (!competidorRepository.existsById(id))
            throw new CompetidorNotFoundException(id);
        competidorRepository.deleteById(id);
    }
}