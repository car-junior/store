package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Cliente;
import com.virtual.store.repositories.ClienteRepository;
import com.virtual.store.services.excpetions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarPorId(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Cliente.class.getName()));
    }
}
