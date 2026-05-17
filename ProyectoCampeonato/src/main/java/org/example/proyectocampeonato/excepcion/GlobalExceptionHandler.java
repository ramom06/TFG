package org.example.proyectocampeonato.excepcion;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampeonatoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCampeonatoNotFound(CampeonatoNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CompetidorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCompetidorNotFound(CompetidorNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoriaNotFound(CategoriaNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CombateNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCombateNotFound(CombateNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InscripcionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleInscripcionNotFound(InscripcionNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CampeonatoCategoriaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCampeonatoCategoriaNotFound(CampeonatoCategoriaNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return error(status, ex.getReason());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String msg = "Ya existe un registro con esos datos (DNI o email duplicado)";
        return error(HttpStatus.CONFLICT, msg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor: " + ex.getMessage());
    }

    //Construye el JSON
    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status",    status.value(),
                "error",     status.getReasonPhrase(),
                "message",   message != null ? message : "Sin detalle"
        ));
    }
}