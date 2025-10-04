package com.catevildev.atividade.repository;

import com.catevildev.atividade.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // busca produtos ativos
    List<Produto> findByAtivoTrue();
    
    // busca produtos por categoria
    List<Produto> findByCategoria(String categoria);
    
    // busca produtos por nome (case insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    
    // busca produtos por faixa de preço
    List<Produto> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);
    
    // busca produtos com estoque baixo
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque <= :quantidadeLimite AND p.ativo = true")
    List<Produto> findProdutosComEstoqueBaixo(@Param("quantidadeLimite") Integer quantidadeLimite);
    
    // query pra buscar produtos por termo (nome ou descricao)
    @Query("SELECT p FROM Produto p WHERE " +
           "(LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(p.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))) AND " +
           "p.ativo = true")
    List<Produto> buscarPorTermo(@Param("termo") String termo);
    
    // busca produtos por categoria e preço máximo
    List<Produto> findByCategoriaAndPrecoLessThanEqualAndAtivoTrue(String categoria, BigDecimal precoMax);
    
    // conta produtos ativos por categoria
    long countByCategoriaAndAtivoTrue(String categoria);
    
    // busca produto mais caro
    @Query("SELECT p FROM Produto p WHERE p.ativo = true ORDER BY p.preco DESC")
    List<Produto> findProdutoMaisCaro();
}
