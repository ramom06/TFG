package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {

    Optional<Arbitro> findByLicencia(String licencia);

    boolean existsByLicencia(String licencia);

    List<Arbitro> findByCategoriaArbitral(String categoriaArbitral);
}