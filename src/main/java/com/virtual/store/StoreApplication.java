package com.virtual.store;

import com.virtual.store.domain.*;
import com.virtual.store.domain.enums.EstadoPagamento;
import com.virtual.store.domain.enums.TipoCliente;
import com.virtual.store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria.gmail.com", "123456789", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("62992351487", "62992351488"));

        Endereco e1 = new Endereco(null, "Casa", "255", "Quadra 16 Lote 8", "Jardim Ana Lúcia", "74315530", cli1, c1);
        Endereco e2 = new Endereco(null, "Prédio", "450", "Quadra 14 Lote 10", "Jardim Ana Lúcia", "74315530", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
        clienteRepository.save(cli1);
        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null, sdf.parse("03/10/2020 18:51"), e1, cli1);
        Pedido ped2 = new Pedido(null, sdf.parse("04/10/2020 19:00"), e2, cli1);

        Pagamento pagto1 = new PagamentoComCartao(null, ped1, EstadoPagamento.QUITADO, 6);
        /** vinculando pagamento ao pedido1 **/
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("10/10/2021 23:59"), null);
        /** vinculando pagamento ao pedido2 **/
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
    }
}
