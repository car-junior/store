package com.virtual.store.resources;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Cliente;
import com.virtual.store.services.CategoriaService;
import com.virtual.store.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable(value = "id") Integer id){
        Cliente cliente = clienteService.findId(id);
        return ResponseEntity.ok().body(cliente);
    }

}
