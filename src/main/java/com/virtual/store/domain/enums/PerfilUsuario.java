package com.virtual.store.domain.enums;

public enum PerfilUsuario {
    CLIENTE(1, "ROLE_CLIENTE"),
    ADMIN(2, "ROLE_ADMIN");

    private int codigo;
    private String descricao;

    PerfilUsuario(int codigo, String descricao) {
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
    public static PerfilUsuario toEnum(Integer codigo){
        if ( codigo == null){
            return null;
        }

        for (PerfilUsuario elemento : PerfilUsuario.values()){
            if (codigo.equals(elemento.getCodigo())){
                return elemento;
            }
        }

        throw new IllegalArgumentException("Código: " + codigo + "inválido" );
    }
}
