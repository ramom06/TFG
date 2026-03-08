package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Combate;
import org.example.proyectocampeonato.modelo.Combate_Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombateRepository extends JpaRepository<Combate, Combate_Id> {

    // Combates de una categoría concreta dentro de un campeonato
    List<Combate> findByIdIdCampeonatoAndIdIdCategoria(Long idCampeonato, Long idCategoria);

    // Combates en los que participa un competidor (como rojo o azul)
    @Query("SELECT c FROM Combate c WHERE c.competidorRojo.id_competidor = :id OR c.competidorAzul.id_competidor = :id")
    List<Combate> findByCompetidor(@Param("id") Long idCompetidor);

    // Combates de un tatami concreto dentro de un campeonato
    List<Combate> findByIdIdCampeonatoAndIdNumeroTatami(Long idCampeonato, Integer numeroTatami);
}