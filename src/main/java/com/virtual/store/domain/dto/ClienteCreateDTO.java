package com.virtual.store.domain.dto;

import com.virtual.store.services.validations.ClienteCreate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ClienteCreate
public class ClienteCreateDTO {

    @NotEmpty(message = "Nome Obrigatório")
    @Size(min = 3, max = 120, message = "Tamanho deve ser maior que 3 caracteres")
    private String nome;

    @NotEmpty(message = "Email Obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "CPF/CNPJ Obrigatório")
    private String cpfOuCnpj;

    private Integer tipoCliente;

    @NotEmpty(message = "Logradouro Obrigatório")
    private String logradouro;

    @NotEmpty(message = "Número Obrigatório")
    private String numero;

    @NotEmpty(message = "Complemento Obrigatório")
    private String complemento;

    @NotEmpty(message = "Bairro Obrigatório")
    private String bairro;

    @NotEmpty(message = "CEP Obrigatório")
    private String cep;

    @NotEmpty(message = "Telefone Obrigatório")
    private String telefone1;

    private String telefone2;
    private String telefone3;

    @NotNull(message = "Cidade Obrigatória")
    private Integer cidadeId;

    public ClienteCreateDTO(){}

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

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Integer getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Integer tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }
}
