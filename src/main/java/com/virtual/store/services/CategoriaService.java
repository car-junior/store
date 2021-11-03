package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.dto.CategoriaDTO;
import com.virtual.store.repositories.CategoriaRepository;
import com.virtual.store.services.exceptions.DataIntegrityException;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria insert(Categoria categoria){
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria){
        Categoria categoriaObj = findId(categoria.getId());
        updateDados(categoriaObj, categoria);
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

    public Page<Categoria> findPage(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
        /** pageRequest é um objeto que prepara a consulta para retornar a pagina de dados **/
        PageRequest pageRequest = PageRequest.of(pagina,linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);

        /** retornando objeto de acordo com o findPage **/
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria converterDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateDados(Categoria categoria, Categoria categoriaDTO){
        categoria.setNome(categoriaDTO.getNome());
    }
}
