package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Inscripcion;
import org.example.proyectocampeonato.modelo.Inscripcion_Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Inscripcion_Id> {

    // Obtenemos las inscripciones de un competidor
    @Query("SELECT i FROM Inscripcion i WHERE i.competidor.id_usuario = :id_competidor")
    List<Inscripcion> findByCompetidor(@Param("id_competidor") Long id_competidor);

    @Query("SELECT i FROM Inscripcion i WHERE i.campeonato.id_campeonato = :id_campeonato AND i.categoria.id_categoria = :id_categoria")
    List<Inscripcion> findByCampeonatoAndCategoria(@Param("id_campeonato") Long id_campeonato, @Param("id_categoria") Long id_categoria);
}
