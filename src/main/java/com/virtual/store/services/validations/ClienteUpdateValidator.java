package com.virtual.store.services.validations;

import com.virtual.store.domain.Cliente;
import com.virtual.store.domain.dto.ClienteCreateDTO;
import com.virtual.store.domain.dto.ClienteUpdateDTO;
import com.virtual.store.domain.enums.TipoCliente;
import com.virtual.store.repositories.ClienteRepository;
import com.virtual.store.resources.exceptions.FieldMessage;
import com.virtual.store.services.validations.utils.CNPJCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/** ConstraintValidator<NomeDaAnotacao, TipoDaClasseQueAceitaAnotacao> **/
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteUpdateDTO> {

    @Autowired
    HttpServletRequest request;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate clienteUpdate) {
    }

    @Override
    public boolean isValid(ClienteUpdateDTO clienteUpdateDTO,
                           ConstraintValidatorContext context) {

        /** criado lista vazia de erros **/
        List<FieldMessage> listaErros = new ArrayList<>();

        /** criado map para pegar id do objeto cliente na URL de atualização **/
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        /** procurando no map o valor de id passado chave id **/
        Integer idCliente = Integer.parseInt(map.get("id"));

        /** retorna cliente com email buscado **/
        Cliente aux = clienteRepository.findByEmail(clienteUpdateDTO.getEmail());

        /** aqui verifica se o cliente retornado é igual ao cliente para atualizar
         * é verificado por meio do id vinculado ao cliente para att, caso seja diferente
         * não é permitido atualizar pois já existe outro cliente com esse email
         **/
        if (aux != null && !aux.equals(idCliente)){
            listaErros.add(new FieldMessage("email", "Email já cadastrado"));
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
