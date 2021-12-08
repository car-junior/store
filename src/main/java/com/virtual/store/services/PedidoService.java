package com.virtual.store.services;

import com.virtual.store.domain.ItemPedido;
import com.virtual.store.domain.PagamentoComBoleto;
import com.virtual.store.domain.Pedido;
import com.virtual.store.domain.enums.EstadoPagamento;
import com.virtual.store.repositories.ItemPedidoRepository;
import com.virtual.store.repositories.PagamentoRepository;
import com.virtual.store.repositories.PedidoRepository;
import com.virtual.store.repositories.ProdutoRepository;
import com.virtual.store.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    /** é neceesário fazer a instanciacao dessa variavel seja com MockEmailService ou **/
    /** ele é feita na classe de configuração referente a cada profile **/
    @Autowired
    private ServicoEmail servicoEmail;

    public Pedido findId(Integer id){
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class.getName()));
    }

    public Pedido insertPedido(Pedido pedido){
        /** GARANTINDO QUE ESTEJA SENDO INSERINDO UM NOVO PEDIDO **/
        pedido.setId(null);
        pedido.setInstante(new Date());

        pedido.setCliente(clienteService.findId(pedido.getCliente().getId()));

        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        /** FAZENDO O PAGAMENTO SABER QUE É SEU PEDIDO INSERCAO DE MAO DUPLA **/
        pedido.getPagamento().setPedido(pedido);

        /** VERIFICANDO QUAL O TIPO DE PAGAMENTO BOLETO OU COM CARTAO **/
        if (pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
            /** USANDO CLASSE QUE SIMULAR DATA DE VENCIMENTO DO BOLETO **/
            boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        /** PERCORRENDO A LISTA DE ITENS PEDIDOS NO JSON PASSADO E ATRIBUINDO O PRECO AO ITEM PEDIDO **/
        for (ItemPedido itemPedido : pedido.getItens()){
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoRepository.findById(itemPedido.getProduto().getId()).get());
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            /** ASSOCIANDO ITEM DE PEDIDO AO PEDIDO **/
            itemPedido.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        servicoEmail.enviarConfirmacaoPedidoEmailHtml(pedido);
        return pedido;
    }
}
