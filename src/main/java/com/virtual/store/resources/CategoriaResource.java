package com.virtual.store.resources;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.dto.CategoriaDTO;
import com.virtual.store.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable(value = "id") Integer id){
        Categoria categoria = categoriaService.findId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO){
        Categoria categoria = categoriaService.converterDTO(categoriaDTO);
        categoria = categoriaService.insert(categoria);

        /** atribuindo uri de criacao do objeto categoria para retornar **/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable(value = "id")
                                       Integer id){
        Categoria categoria = categoriaService.converterDTO(categoriaDTO);
        categoria.setId(id);
        categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> categoriaList = categoriaService.findAll();

        /** convertendo minha lista de categorias para categoriasDTO usando o stream **/
        List<CategoriaDTO> categoriaDTOList = categoriaList.stream().map(elemento ->
                new CategoriaDTO(elemento)).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoriaDTOList);
    }

    /** definindo variaveis como parametros e opcionais **/
    /** direcao = ASC OU DESC **/
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao){

        Page<Categoria> categoriasPage = categoriaService.findPage(pagina, linhasPorPagina, ordenarPor, direcao);

        /** Page faz parte do java8+ então só se usa o .map() para fazer conversao para o tipo DTO**/
        Page<CategoriaDTO> categoriasDTOPage = categoriasPage.map(elemento -> new CategoriaDTO(elemento));

        return ResponseEntity.ok().body(categoriasDTOPage);
    }
}
