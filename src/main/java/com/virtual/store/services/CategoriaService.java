package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.repositories.CategoriaRepository;
import com.virtual.store.services.exceptions.DataIntegrityException;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findId(Integer id){
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria){
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria){
        findId(categoria.getId());
        return categoriaRepository.save(categoria);
    }

    public void delete(Integer id){
        findId(id);
        try{
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir categoria com produtos vinculados!");
        }
    }
}
