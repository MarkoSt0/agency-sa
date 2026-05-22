/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.izdavanjestanovaback.exception;

import javax.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import rs.ac.bg.fon.izdavanjestanovaback.com.ApiResponseBuilder;

/**
 *
 * @author Marko
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseBuilder.kreirajOdgovor("Sistem ne može da obriše entitet jer učestvuje u izdavanju!", HttpStatus.CONFLICT));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseBuilder.kreirajOdgovor(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                ));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleMissingBody(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseBuilder.kreirajOdgovor(
                        "Telo zahteva je obavezno.",
                        HttpStatus.BAD_REQUEST
                ));
    }
}
