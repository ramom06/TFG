package org.example.proyectocampeonato.excepcion;

import org.example.proyectocampeonato.modelo.Combate_Id;

public class CombateNotFoundException extends RuntimeException {
    public CombateNotFoundException(Combate_Id id) {
        super("No se ha encontrado Combate con el id " + id);
    }
}
