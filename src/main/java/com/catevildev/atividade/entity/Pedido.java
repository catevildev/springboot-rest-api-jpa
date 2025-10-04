package com.catevildev.atividade.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "numero_pedido", unique = true, length = 50)
    private String numeroPedido;
    
    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status = StatusPedido.PENDENTE;
    
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;
    
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    
    // construtores
    public Pedido() {
        this.dataPedido = LocalDateTime.now();
        this.numeroPedido = gerarNumeroPedido();
    }
    
    public Pedido(Usuario usuario, BigDecimal valorTotal, String observacoes) {
        this();
        this.usuario = usuario;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
    }
    
    private String gerarNumeroPedido() {
        return "PED" + System.currentTimeMillis();
    }
    
    // get e set
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getNumeroPedido() {
        return numeroPedido;
    }
    
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }
    
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    // enum para status do pedido
    public enum StatusPedido {
        PENDENTE,
        PROCESSANDO,
        ENVIADO,
        ENTREGUE,
        CANCELADO
    }
}
