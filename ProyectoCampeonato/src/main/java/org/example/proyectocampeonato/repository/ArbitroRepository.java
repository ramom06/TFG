package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArbitroRepository extends JpaRepository<Arbitro,Integer> {
    //Busca por dni
}
