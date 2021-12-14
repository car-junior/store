package com.virtual.store.services;

import com.virtual.store.domain.Cliente;
import com.virtual.store.repositories.ClienteRepository;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AutorizacaoService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder encodeSenha;

    @Autowired
    private ServicoEmail servicoEmail;

    private Random rand = new Random();

    public void enviarNovaSenhaCliente(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado!");
        }

        String novaSenha = novaSenha();
        cliente.setSenha(encodeSenha.encode(novaSenha));

        clienteRepository.save(cliente);
        servicoEmail.enviarNovaSenhaClienteEmail(cliente, novaSenha);
    }

    private String novaSenha() {
        char[] charSenha = new char[10];

        for (int i = 0; i < charSenha.length; i++) {
            charSenha[i] = randomChar();
        }

        return new String(charSenha);
    }

    private char randomChar() {
        int opcao = rand.nextInt(3);
        if (opcao == 0) { // gera digito
            return (char) (rand.nextInt(10) + 48);
        }
        else if (opcao == 1) {  // gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        }
        else { // gera leta minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
