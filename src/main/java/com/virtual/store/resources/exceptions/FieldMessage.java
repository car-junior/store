package com.virtual.store.resources.exceptions;

import java.io.Serializable;


/** classe que captura o erros da validocoes dos formularios ao criar e atualizar **/
public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomeCampo;
    private String mensagem;

    public FieldMessage(){}

    public FieldMessage(String nomeCampo, String mensagem) {
        this.nomeCampo = nomeCampo;
        this.mensagem = mensagem;
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
