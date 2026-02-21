package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>{

    //Busca por id

    //Busca por fecha
}
