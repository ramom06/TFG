package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);

    boolean existsByNombre(String nombre);

    boolean existsByEmail(String email);

    List<Usuario> findByRol(Usuario.Rol rol);
}
