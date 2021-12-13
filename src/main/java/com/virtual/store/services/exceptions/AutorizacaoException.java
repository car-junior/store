package com.virtual.store.services.exceptions;

public class AutorizacaoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public AutorizacaoException(String msgErro){
        super(msgErro);
    }

    public AutorizacaoException(String msgErro, Throwable causaErro){
        super(msgErro, causaErro);
    }

}
