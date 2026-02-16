package org.example.proyectocampeonato.excepcion;

public class CampeonatoNotFoundException extends RuntimeException {
    public CampeonatoNotFoundException(Integer id) {
        super("No se ha encontrado Campeonato con el id " + id);
    }
}

