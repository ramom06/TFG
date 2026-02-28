package org.example.proyectocampeonato.repository;

import org.example.proyectocampeonato.modelo.Campeonato_Categoria;
import org.example.proyectocampeonato.modelo.Campeonato_Categoria_Id;
import org.example.proyectocampeonato.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Campeonato_CategoriaRepository extends JpaRepository<Campeonato_Categoria, Campeonato_Categoria_Id> {
    // En CategoriaRepository.java
    @Query("SELECT cc.categoria FROM Campeonato_Categoria cc WHERE cc.campeonato.id_campeonato = :id")
    List<Categoria> findCategoriasByCampeonatoId(@Param("id") Long id);
}
