package com.virtual.store.resources;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Pedido;
import com.virtual.store.domain.Produto;
import com.virtual.store.domain.dto.CategoriaDTO;
import com.virtual.store.domain.dto.ProdutoDTO;
import com.virtual.store.repositories.ProdutoRepository;
import com.virtual.store.resources.utils.URL;
import com.virtual.store.services.PedidoService;
import com.virtual.store.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll(){
        List<Produto> produtos = produtoRepository.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> find(@PathVariable(value = "id") Integer id){
        Produto produto = produtoService.findId(id);
        return ResponseEntity.ok().body(produto);
    }

    /** BUSCA PAGINADA DE PRODUTOS **/
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao){

        String produtoDecode = URL.decodeParam(nome);
        List<Integer> idsCategorias = URL.decodeIdsList(categorias);
        Page<Produto> produtosPorCategoriaPage = produtoService.search(nome, idsCategorias, pagina, linhasPorPagina, ordenarPor, direcao);

        /** Page faz parte do java8+ então só se usa o .map() para fazer conversao para o tipo DTO**/
        Page<ProdutoDTO> produtosDTOPage = produtosPorCategoriaPage.map(elemento -> new ProdutoDTO(elemento));

        return ResponseEntity.ok().body(produtosDTOPage);
    }


}
