package com.catevildev.atividade.service;

import com.catevildev.atividade.entity.Produto;
import com.catevildev.atividade.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    // Salvar produto
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    // Buscar todos os produtos
    @Transactional(readOnly = true)
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }
    
    // Buscar produto por ID
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }
    
    // Buscar produtos ativos
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosAtivos() {
        return produtoRepository.findByAtivoTrue();
    }
    
    // Buscar produtos por categoria
    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }
    
    // Buscar produtos por nome
    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    // Buscar produtos por faixa de preço
    @Transactional(readOnly = true)
    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax);
    }
    
    // Buscar produtos com estoque baixo
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosComEstoqueBaixo(Integer quantidadeLimite) {
        return produtoRepository.findProdutosComEstoqueBaixo(quantidadeLimite);
    }
    
    // Buscar produtos por termo
    @Transactional(readOnly = true)
    public List<Produto> buscarPorTermo(String termo) {
        return produtoRepository.buscarPorTermo(termo);
    }
    
    // Atualizar produto
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
        produto.setCategoria(produtoAtualizado.getCategoria());
        produto.setAtivo(produtoAtualizado.getAtivo());
        
        return produtoRepository.save(produto);
    }
    
    // Atualizar estoque
    public Produto atualizarEstoque(Long id, Integer novaQuantidade) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }
        
        produto.setQuantidadeEstoque(novaQuantidade);
        return produtoRepository.save(produto);
    }
    
    // Desativar produto
    public void desativar(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
    
    // Ativar produto
    public void ativar(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        
        produto.setAtivo(true);
        produtoRepository.save(produto);
    }
    
    // Deletar produto
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
    
    // Contar produtos por categoria
    @Transactional(readOnly = true)
    public long contarPorCategoria(String categoria) {
        return produtoRepository.countByCategoriaAndAtivoTrue(categoria);
    }
    
    // Buscar produto mais caro
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutoMaisCaro() {
        return produtoRepository.findProdutoMaisCaro();
    }
}
