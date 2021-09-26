package com.virtual.store.services.excpetions;

public class ObjectNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String msgErro){
        super(msgErro);
    }

    public ObjectNotFoundException(String msgErro, Throwable causaErro){
        super(msgErro, causaErro);
    }

}
