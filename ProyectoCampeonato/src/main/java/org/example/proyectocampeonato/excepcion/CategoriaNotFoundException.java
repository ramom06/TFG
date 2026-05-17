package org.example.proyectocampeonato.excepcion;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("No se ha encontrado Categoria con el id " + id);
    }
}
