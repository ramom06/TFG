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

    /** Errores de negocio ya tipados */
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

    /** ResponseStatusException (lanzadas con HttpStatus.CONFLICT, NOT_FOUND, etc.) */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {
        return error(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason());
    }

    /**
     * Constraint de BD violada (DNI/email duplicado que se nos escapó antes de validar).
     * Devuelve 409 en vez de 500.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String msg = "Ya existe un registro con esos datos (DNI o email duplicado)";

        // Intentar ser más específicos mirando el mensaje de la causa
        String cause = ex.getMostSpecificCause().getMessage().toLowerCase();
        if (cause.contains("dni")) {
            msg = "Ya existe un usuario con ese DNI";
        } else if (cause.contains("email")) {
            msg = "Ya existe un usuario con ese email";
        }

        return error(HttpStatus.CONFLICT, msg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor: " + ex.getMessage());
    }

    // ── helper ────────────────────────────────────────────────────────────────
    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status",    status.value(),
                "error",     status.getReasonPhrase(),
                "message",   message != null ? message : "Sin detalle"
        ));
    }
}