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

    List<Combate> findByIdIdCampeonatoAndIdIdCategoria(Long idCampeonato, Long idCategoria);

    List<Combate> findByIdIdCampeonatoAndIdNumeroTatami(Long idCampeonato, Integer numeroTatami);

    // id_usuario es el PK heredado de Usuario en Competidor
    @Query("SELECT c FROM Combate c WHERE c.competidorRojo.idUsuario = :id OR c.competidorAzul.idUsuario = :id")
    List<Combate> findByCompetidor(@Param("id") Long idCompetidor);
}
