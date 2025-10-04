package com.catevildev.atividade.repository;

import com.catevildev.atividade.entity.Pedido;
import com.catevildev.atividade.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // busca pedidos por usuario
    List<Pedido> findByUsuario(Usuario usuario);
    
    // busca pedidos por status
    List<Pedido> findByStatus(Pedido.StatusPedido status);
    
    // busca pedidos por numero
    Optional<Pedido> findByNumeroPedido(String numeroPedido);
    
    // busca pedidos por usuario e status
    List<Pedido> findByUsuarioAndStatus(Usuario usuario, Pedido.StatusPedido status);
    
    // busca pedidos por faixa de valor
    List<Pedido> findByValorTotalBetween(BigDecimal valorMin, BigDecimal valorMax);
    
    // busca pedidos por periodo
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    // query customizada para buscar pedidos por usuario com valor total
    @Query("SELECT p FROM Pedido p WHERE p.usuario = :usuario AND p.valorTotal >= :valorMinimo")
    List<Pedido> findPedidosUsuarioComValorMinimo(@Param("usuario") Usuario usuario, 
                                                  @Param("valorMinimo") BigDecimal valorMinimo);
    
    // busca pedidos recentes (ultimos 30 dias)
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido >= :dataLimite ORDER BY p.dataPedido DESC")
    List<Pedido> findPedidosRecentes(@Param("dataLimite") LocalDateTime dataLimite);
    
    // calcula valor total de pedidos por usuario
    @Query("SELECT SUM(p.valorTotal) FROM Pedido p WHERE p.usuario = :usuario AND p.status != 'CANCELADO'")
    BigDecimal calcularValorTotalPorUsuario(@Param("usuario") Usuario usuario);
    
    // conta pedidos por status
    long countByStatus(Pedido.StatusPedido status);
    
    // busca pedidos pendentes ha mais de X dias
    @Query("SELECT p FROM Pedido p WHERE p.status = 'PENDENTE' AND p.dataPedido < :dataLimite")
    List<Pedido> findPedidosPendentesAntigos(@Param("dataLimite") LocalDateTime dataLimite);
}
