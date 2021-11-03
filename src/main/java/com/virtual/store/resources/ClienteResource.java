package com.virtual.store.resources;

import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.dto.ClienteCreateDTO;
import com.virtual.store.domain.dto.ClienteUpdateDTO;
import com.virtual.store.services.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable(value = "id") Integer id){
        Cliente cliente = clienteService.findId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO){
        Cliente cliente = clienteService.converterDTO(clienteCreateDTO);
        cliente = clienteService.insert(cliente);

        /** atribuindo uri de criacao do objeto cliente para retornar **/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteUpdateDTO clienteUpdateDTO, @PathVariable(value = "id")
            Integer id){
        Cliente cliente = clienteService.converterDTO(clienteUpdateDTO);
        cliente.setId(id);
        clienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteUpdateDTO>> findAll(){
        List<Cliente> clienteList = clienteService.findAll();

        /** convertendo minha lista de clientes para clientesDTO usando o stream **/
        List<ClienteUpdateDTO> clienteDTOList = clienteList.stream().map(elemento ->
                new ClienteUpdateDTO(elemento)).collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteDTOList);
    }

    /** definindo variaveis como parametros e opcionais **/
    /** direcao = ASC OU DESC **/
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteUpdateDTO>> findPage(
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor,
            @RequestParam(value = "direcao", defaultValue = "ASC") String direcao){

        Page<Cliente> clientesPage = clienteService.findPage(pagina, linhasPorPagina, ordenarPor, direcao);

        /** Page faz parte do java8+ então só se usa o .map() para fazer conversao para o tipo DTO**/
        Page<ClienteUpdateDTO> clientesUpdateDTOPage = clientesPage.map(elemento -> new ClienteUpdateDTO(elemento));

        return ResponseEntity.ok().body(clientesUpdateDTOPage);
    }
}
