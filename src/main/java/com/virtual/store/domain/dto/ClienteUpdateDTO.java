package com.virtual.store.domain.dto;

import com.virtual.store.domain.Cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ClienteUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size(min = 3, max = 120, message = "Nome deve ser maior que 3 e menor que 120 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    public ClienteUpdateDTO(){}

    public ClienteUpdateDTO(Cliente cliente){
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
