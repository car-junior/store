package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.dto.CategoriaDTO;
import com.virtual.store.domain.dto.ClienteUpdateDTO;
import com.virtual.store.repositories.ClienteRepository;
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
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente findId(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente insert(Cliente cliente){
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente){
        Cliente clienteObj = findId(cliente.getId());
        updateDados(clienteObj, cliente);
        return clienteRepository.save(clienteObj);
    }

    public void delete(Integer id){
        findId(id);
        try{
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir cliente com pedidos vinculados!");
        }
    }

    public Page<Cliente> findPage(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
        /** pageRequest é um objeto que prepara a consulta para retornar a pagina de dados **/
        PageRequest pageRequest = PageRequest.of(pagina,linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);

        /** retornando objeto de acordo com o findPage **/
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente converterDTO(ClienteUpdateDTO clienteUpdateDTO){
        return new Cliente(clienteUpdateDTO.getId(), clienteUpdateDTO.getNome(), clienteUpdateDTO.getEmail(), null, null);
    }

    private void updateDados(Cliente cliente, Cliente clienteUpdateDTO){
        if (clienteUpdateDTO.getNome() != null){
            cliente.setNome(clienteUpdateDTO.getNome());
        }
        if (clienteUpdateDTO.getEmail() != null){
            cliente.setEmail(clienteUpdateDTO.getEmail());
        }
    }
}
