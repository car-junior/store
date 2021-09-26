package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.repositories.CategoriaRepository;
import com.virtual.store.services.excpetions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria buscar(Integer id){
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Categoria.class.getName()));
    }
}
