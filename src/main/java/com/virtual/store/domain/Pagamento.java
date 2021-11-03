package com.virtual.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.virtual.store.domain.enums.EstadoPagamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
/** com o @Inheritance crio um tabela pra cada entidade filha de Pagamento **/
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    @JsonIgnore
    private Pedido pedido;
    private Integer estadoPagamento;
    public Pagamento(){}

    public Pagamento(Integer id, Pedido pedido, EstadoPagamento estadoPagamento) {
        this.id = id;
        this.pedido = pedido;
        this.estadoPagamento = (estadoPagamento == null) ? null : estadoPagamento.getCodigo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /** retorna enum com estado do pagamento **/
    public EstadoPagamento getEstadoPagamento() {
        return EstadoPagamento.toEnum(estadoPagamento);
    }

    public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
        this.estadoPagamento = estadoPagamento.getCodigo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return id.equals(pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
