package com.virtual.store.services.validations;

import com.virtual.store.domain.dto.ClienteCreateDTO;
import com.virtual.store.domain.enums.TipoCliente;
import com.virtual.store.repositories.ClienteRepository;
import com.virtual.store.resources.exceptions.FieldMessage;
import com.virtual.store.services.validations.utils.CNPJCPF;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/** ConstraintValidator<NomeDaAnotacao, TipoDaClasseQueAceitaAnotacao> **/
public class ClienteCreateValidator implements ConstraintValidator<ClienteCreate, ClienteCreateDTO> {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteCreate clienteCreate) {
    }

    @Override
    public boolean isValid(ClienteCreateDTO clienteCreateDTO,
                           ConstraintValidatorContext context) {

        clienteCreateDTO.setCpfOuCnpj(clienteCreateDTO.getCpfOuCnpj().replaceAll("\\D", ""));

        /** criado lista vazia de erros **/
        List<FieldMessage> listaErros = new ArrayList<>();

        if (clienteCreateDTO.getTipoCliente() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Tipo Cliente não pode ser vazio")
                    .addPropertyNode("tipoCliente").addConstraintViolation();

            return false;
        }

        /** verificando se já existe email cadastrado **/
        if (clienteRepository.findByEmail(clienteCreateDTO.getEmail()) != null){
            listaErros.add(new FieldMessage("email", "Email já cadastrado"));
        }

        /** verificando se já existe CPF OU CNPJ cadastrado **/

        if (clienteRepository.findByCpfOuCnpj(clienteCreateDTO.getCpfOuCnpj()) != null){
            if (clienteCreateDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo())){
                listaErros.add(new FieldMessage("cpfOuCnpj", "CPF já cadastrado"));
            } else {
                listaErros.add(new FieldMessage("cpfOuCnpj", "CNPJ já cadastrado"));
            }
        }

        /** verificando se clente é do tipo pessoa fisica e caso cpf nao seja válido acrescenta 1 erro **/
        if (clienteCreateDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !CNPJCPF.isValidCPF(clienteCreateDTO.getCpfOuCnpj())){
            listaErros.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));

        }

        /** verificando se clente é do tipo pessoa juridica e caso cnpj nao seja válido acrescenta 1 erro **/
        if (clienteCreateDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !CNPJCPF.isValidCNPJ(clienteCreateDTO.getCpfOuCnpj())){
            listaErros.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        /** adicionando erros na lista de erros capturada pelo controler **/
        for (FieldMessage e : listaErros) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMensagem())
                    .addPropertyNode(e.getNomeCampo()).addConstraintViolation();
        }

        return listaErros.isEmpty();
    }
}
