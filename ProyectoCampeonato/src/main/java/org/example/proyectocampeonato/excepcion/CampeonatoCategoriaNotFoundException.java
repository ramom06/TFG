package org.example.proyectocampeonato.excepcion;

import org.example.proyectocampeonato.modelo.Campeonato_Categoria_Id;

public class CampeonatoCategoriaNotFoundException extends RuntimeException {
    public CampeonatoCategoriaNotFoundException(Campeonato_Categoria_Id id) {
        super("No se ha encontrado la asignacion Campeonato_Categoria con el id " + id);
    }
}
