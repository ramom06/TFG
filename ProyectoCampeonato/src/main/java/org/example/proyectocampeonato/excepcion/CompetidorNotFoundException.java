package org.example.proyectocampeonato.excepcion;

public class CompetidorNotFoundException extends RuntimeException {
    public CompetidorNotFoundException(Long id) {
        super("No se ha encontrado Competidor con el id " + id);
    }
}

