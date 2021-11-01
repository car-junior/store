package com.virtual.store.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> listaMensagens = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return listaMensagens;
    }

    public void adicionarError(String nomeCampo, String mensagem) {
        listaMensagens.add(new FieldMessage(nomeCampo, mensagem));
    }
}
