package com.catevildev.atividade.controller;

import com.catevildev.atividade.entity.Pedido;
import com.catevildev.atividade.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    
    @Autowired // autowired para injetar o servico de pedido
    private PedidoService pedidoService;
    
    // cria novo pedido
    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {
        try {
            Pedido pedidoSalvo = pedidoService.salvar(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao criar pedido: " + e.getMessage());
        }
    }
    
    // busca todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> buscarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.buscarTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    // busca pedido por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // atualiza pedido
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizar(id, pedido);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar pedido: " + e.getMessage());
        }
    }
    
    // apaga pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletar(id);
            return ResponseEntity.ok().body("Pedido deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao deletar pedido: " + e.getMessage());
        }
    }
    
    // busca pedidos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> buscarPedidosPorUsuario(@PathVariable Long usuarioId) {
        List<Pedido> pedidos = pedidoService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(pedidos);
    }
    
    // busca pedidos por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> buscarPedidosPorStatus(@PathVariable Pedido.StatusPedido status) {
        List<Pedido> pedidos = pedidoService.buscarPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }
    
    // atualiza status do pedido
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatusPedido(@PathVariable Long id, @RequestParam Pedido.StatusPedido status) {
        try {
            Pedido pedido = pedidoService.atualizarStatus(id, status);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar status: " + e.getMessage());
        }
    }
    
    // cancela pedido
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.cancelar(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao cancelar pedido: " + e.getMessage());
        }
    }
    
    // busca pedidos por periodo
    @GetMapping("/periodo")
    public ResponseEntity<List<Pedido>> buscarPedidosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Pedido> pedidos = pedidoService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(pedidos);
    }
    
    // calcula valor total por usuario
    @GetMapping("/usuario/{usuarioId}/valor-total")
    public ResponseEntity<BigDecimal> calcularValorTotalPorUsuario(@PathVariable Long usuarioId) {
        BigDecimal valorTotal = pedidoService.calcularValorTotalPorUsuario(usuarioId);
        return ResponseEntity.ok(valorTotal);
    }
}
