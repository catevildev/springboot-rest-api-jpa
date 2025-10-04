package com.catevildev.atividade.service;

import com.catevildev.atividade.entity.Pedido;
import com.catevildev.atividade.entity.Usuario;
import com.catevildev.atividade.repository.PedidoRepository;
import com.catevildev.atividade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // salva pedido
    public Pedido salvar(Pedido pedido) {
        // verifica se o usuario existe
        if (pedido.getUsuario() != null && pedido.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + pedido.getUsuario().getId()));
            pedido.setUsuario(usuario);
        }
        
        return pedidoRepository.save(pedido);
    }
    
    // busca todos os pedidos
    @Transactional(readOnly = true)
    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }
    
    // busca pedido por id
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }
    
    // busca pedidos por usuario
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));
        return pedidoRepository.findByUsuario(usuario);
    }
    
    // busca pedidos por status
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorStatus(Pedido.StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }
    
    // busca pedido por numero
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorNumero(String numeroPedido) {
        return pedidoRepository.findByNumeroPedido(numeroPedido);
    }
    
    // busca pedidos por faixa de valor
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorFaixaValor(BigDecimal valorMin, BigDecimal valorMax) {
        return pedidoRepository.findByValorTotalBetween(valorMin, valorMax);
    }
    
    // busca pedidos por periodo
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return pedidoRepository.findByDataPedidoBetween(dataInicio, dataFim);
    }
    
    // busca pedidos recentes
    @Transactional(readOnly = true)
    public List<Pedido> buscarPedidosRecentes(int dias) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(dias);
        return pedidoRepository.findPedidosRecentes(dataLimite);
    }
    
    // atualiza pedido
    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        pedido.setValorTotal(pedidoAtualizado.getValorTotal());
        pedido.setStatus(pedidoAtualizado.getStatus());
        pedido.setObservacoes(pedidoAtualizado.getObservacoes());
        
        return pedidoRepository.save(pedido);
    }
    
    // atualiza status do pedido
    public Pedido atualizarStatus(Long id, Pedido.StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
    
    // cancela pedido
    public Pedido cancelar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        if (pedido.getStatus() == Pedido.StatusPedido.ENTREGUE) {
            throw new RuntimeException("Não é possível cancelar um pedido já entregue");
        }
        
        pedido.setStatus(Pedido.StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }
    
    // deleta pedido
    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado com ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }
    
    // calcula valor total de pedidos por usuario
    @Transactional(readOnly = true)
    public BigDecimal calcularValorTotalPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));
        
        BigDecimal total = pedidoRepository.calcularValorTotalPorUsuario(usuario);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    // conta pedidos por status
    @Transactional(readOnly = true)
    public long contarPorStatus(Pedido.StatusPedido status) {
        return pedidoRepository.countByStatus(status);
    }
    
    // busca pedidos pendentes antigos
    @Transactional(readOnly = true)
    public List<Pedido> buscarPedidosPendentesAntigos(int dias) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(dias);
        return pedidoRepository.findPedidosPendentesAntigos(dataLimite);
    }
}
