package org.example.proyectocampeonato.excepcion;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("No se ha encontrado Usuario con el id " + id);
    }
}

