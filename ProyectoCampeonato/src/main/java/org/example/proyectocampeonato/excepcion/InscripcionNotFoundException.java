package org.example.proyectocampeonato.excepcion;

import org.example.proyectocampeonato.modelo.Inscripcion_Id;

public class InscripcionNotFoundException extends RuntimeException {
    public InscripcionNotFoundException(Inscripcion_Id id) {
        super("No se ha encontrado Inscripcion con el id " + id);
    }
}
