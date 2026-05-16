package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Inscripcion;
import org.example.proyectocampeonato.modelo.Inscripcion_Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Inscripcion_Id> {

    @Query("SELECT i FROM Inscripcion i WHERE i.competidor.idUsuario = :idCompetidor")
    List<Inscripcion> findByCompetidor(@Param("idCompetidor") Long idCompetidor);

    @Query("SELECT i FROM Inscripcion i WHERE i.campeonato.idCampeonato = :idCampeonato AND i.categoria.idCategoria = :idCategoria")
    List<Inscripcion> findByCampeonatoAndCategoria(@Param("idCampeonato") Long idCampeonato, @Param("idCategoria") Long idCategoria);
}
