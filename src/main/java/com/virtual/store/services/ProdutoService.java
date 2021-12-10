package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Produto;
import com.virtual.store.repositories.CategoriaRepository;
import com.virtual.store.repositories.ProdutoRepository;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findId(Integer id){
        Optional<Produto> obj = produtoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
        /** RETORNANDO LISTA DE CATEGORIAS POR MEIO DOS IDS INFORMADOS **/
        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        PageRequest pageRequest = PageRequest.of(pagina,linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);

        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn (nome, categorias, pageRequest);
    }

}
