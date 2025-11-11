package com.gsuatavosdaniel.encurtador_url.Exception;

import com.gsuatavosdaniel.encurtador_url.links.LinkNotFoundException;
import com.gsuatavosdaniel.encurtador_url.links.UrlJaExisteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandle {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        log.warn("Validation falid {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
            "Erro de validação",
                "Erro de validação no campo de inserir Url",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UrlJaExisteException.class)
    public ResponseEntity<ErrorResponse> handleUrlJaExisteException(UrlJaExisteException ex) {

        log.warn("Link com essa URL já existe {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "Url já existente",
                "Url inserida já foi encurtada",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLinkNotFound(LinkNotFoundException ex) {

        log.warn("Link não encontrado {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "Link não Encontrado",
                "O link buscado não foi encontrado",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }
}
