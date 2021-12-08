package com.virtual.store.domain.enums;

public enum EstadoPagamento {
    PENDENTE(1,  "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int codigo;
    private String descricao;

    EstadoPagamento(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo){
        if (codigo == null){
            return null;
        }

        for (EstadoPagamento elemento : EstadoPagamento.values()){
            if (codigo.equals(elemento.getCodigo())){
                return elemento;
            }
        }

        throw new IllegalArgumentException("Código: " + codigo + "inválido" );
    }

}
