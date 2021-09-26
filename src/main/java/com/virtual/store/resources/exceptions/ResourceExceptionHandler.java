package com.virtual.store.resources.exceptions;

import com.virtual.store.services.excpetions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    /** INDICANDO QUE É UM TRATATOR DE EXCECAO ESPECÍFICO DESSA CLASSE**/
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException excecao, HttpServletRequest request){
        StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), excecao.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
