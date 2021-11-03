package com.virtual.store.services;

import com.virtual.store.domain.Categoria;
import com.virtual.store.domain.Cidade;
import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.Endereco;
import com.virtual.store.domain.dto.CategoriaDTO;
import com.virtual.store.domain.dto.ClienteCreateDTO;
import com.virtual.store.domain.dto.ClienteUpdateDTO;
import com.virtual.store.domain.enums.TipoCliente;
import com.virtual.store.repositories.ClienteRepository;
import com.virtual.store.repositories.EnderecoRepository;
import com.virtual.store.services.exceptions.DataIntegrityException;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente findId(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente insert(Cliente cliente){
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
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

    public Cliente converterDTO(ClienteCreateDTO clienteCreateDTO){
        /** criado cliente a partir do clienteCreateDTO **/
        Cliente cliente = new Cliente(null, clienteCreateDTO.getNome(),
                clienteCreateDTO.getEmail(), clienteCreateDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(clienteCreateDTO.getTipoCliente()));

        /** passando o id da cidade e com isso quando salver tem o id respectivo da cidade para cadastrar **/
        Cidade cidade = new Cidade(clienteCreateDTO.getCidadeId(), null, null);

        /** vinculando endereco ao cliente **/
        Endereco endereco = new Endereco(null, clienteCreateDTO.getLogradouro(),
                clienteCreateDTO.getNumero(),
                clienteCreateDTO.getComplemento(), clienteCreateDTO.getBairro(),
                clienteCreateDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);

        /** vinculando telefones ao cliente **/
        cliente.getTelefones().add(clienteCreateDTO.getTelefone1());
        if (clienteCreateDTO.getTelefone2() != null){
            cliente.getTelefones().add(clienteCreateDTO.getTelefone2());
        }
        if (clienteCreateDTO.getTelefone3() != null){
            cliente.getTelefones().add(clienteCreateDTO.getTelefone3());
        }

        return cliente;
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
