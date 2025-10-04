package com.catevildev.atividade.controller;

import com.catevildev.atividade.entity.Produto;
import com.catevildev.atividade.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    // cria novo produto
    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.salvar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao criar produto: " + e.getMessage());
        }
    }
    
    // busca todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> buscarTodosProdutos() {
        List<Produto> produtos = produtoService.buscarTodos();
        return ResponseEntity.ok(produtos);
    }
    
    // busca produto por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // atualiza produto
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    // apaga produto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.ok().body("Produto deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao deletar produto: " + e.getMessage());
        }
    }
    
    // busca produtos ativos
    @GetMapping("/ativos")
    public ResponseEntity<List<Produto>> buscarProdutosAtivos() {
        List<Produto> produtos = produtoService.buscarProdutosAtivos();
        return ResponseEntity.ok(produtos);
    }
    
    // busca produtos por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> buscarProdutosPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }
    
    // busca produtos por faixa de preco
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarProdutosPorPreco(
            @RequestParam BigDecimal precoMin, 
            @RequestParam BigDecimal precoMax) {
        List<Produto> produtos = produtoService.buscarPorFaixaPreco(precoMin, precoMax);
        return ResponseEntity.ok(produtos);
    }
    
    // atualiza estoque
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<?> atualizarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        try {
            Produto produto = produtoService.atualizarEstoque(id, quantidade);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar estoque: " + e.getMessage());
        }
    }
    
    // busca produtos com estoque baixo
    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarProdutosComEstoqueBaixo(@RequestParam(defaultValue = "10") Integer limite) {
        List<Produto> produtos = produtoService.buscarProdutosComEstoqueBaixo(limite);
        return ResponseEntity.ok(produtos);
    }
}
