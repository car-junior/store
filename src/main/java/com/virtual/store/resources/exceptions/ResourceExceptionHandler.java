package com.virtual.store.resources.exceptions;

import com.virtual.store.services.exceptions.AutorizacaoException;
import com.virtual.store.services.exceptions.DataIntegrityException;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    /** INDICANDO QUE É UM TRATATOR DE EXCECAO ESPECÍFICO DESSA CLASSE**/
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException excecao, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), excecao.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException excecao, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), excecao.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException excecao, HttpServletRequest request){
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erros de Validação", System.currentTimeMillis());

        /** pegando nomes e erros dos respectivos campos **/
        for ( FieldError elemento : excecao.getBindingResult().getFieldErrors()){
            error.adicionarError(elemento.getField(), elemento.getDefaultMessage());
        }

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> AccessDenied(AccessDeniedException excecao, HttpServletRequest r) {
        String path = new UrlPathHelper().getPathWithinApplication(r);
        StandardError acessoNegado = new AcessoNegado(HttpStatus.FORBIDDEN.value(), excecao.getMessage(),  System.currentTimeMillis(), path);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(acessoNegado);
    }

    @ExceptionHandler(AutorizacaoException.class)
    public ResponseEntity<StandardError> autorazicaoException(AutorizacaoException excecao, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.FORBIDDEN.value(), excecao.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }


}
