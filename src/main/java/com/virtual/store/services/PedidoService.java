package com.virtual.store.services;

import com.virtual.store.domain.Pedido;
import com.virtual.store.repositories.PedidoRepository;
import com.virtual.store.services.excpetions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarPorId(Integer id){
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class.getName()));
    }
}
