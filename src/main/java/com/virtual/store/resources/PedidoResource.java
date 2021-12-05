package com.virtual.store.resources;

import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.Pedido;
import com.virtual.store.domain.dto.ClienteCreateDTO;
import com.virtual.store.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> find(@PathVariable(value = "id") Integer id){
        Pedido pedido = pedidoService.findId(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){

        pedido = pedidoService.insertPedido(pedido);

        /** atribuindo uri de criacao do objeto cliente para retornar **/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }


}
