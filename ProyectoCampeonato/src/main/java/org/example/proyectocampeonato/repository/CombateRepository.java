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

    @Query("SELECT c FROM Combate c WHERE c.idCombate.idCampeonato = :idCampeonato AND c.idCombate.idCategoria = :idCategoria")
    List<Combate> findByIdIdCampeonatoAndIdIdCategoria(@Param("idCampeonato") Long idCampeonato, @Param("idCategoria") Long idCategoria);

    @Query("SELECT c FROM Combate c WHERE c.competidorRojo.idUsuario = :id OR c.competidorAzul.idUsuario = :id")
    List<Combate> findByCompetidor(@Param("id") Long id);
}
