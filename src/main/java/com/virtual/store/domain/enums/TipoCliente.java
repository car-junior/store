package com.virtual.store.domain.enums;

public enum TipoCliente {
    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private int codigo;
    private String descricao;

    TipoCliente(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    /** Recebe um inteiro e retorna um objeto(TipoCliente) respectivo ao codigo informado **/
    public static TipoCliente toEnum(Integer codigo){
        if ( codigo == null){
            return null;
        }

        for (TipoCliente elemento : TipoCliente.values()){
            if (codigo.equals(elemento.getCodigo())){
                return elemento;
            }
        }

        throw new IllegalArgumentException("Código: " + codigo + "inválido" );
    }
}
