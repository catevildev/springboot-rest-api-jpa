package com.catevildev.atividade.repository;

import com.catevildev.atividade.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // busca usuario por email
    Optional<Usuario> findByEmail(String email);
    
    // busca usuarios ativos
    List<Usuario> findByAtivoTrue();
    
    // busca usuarios por nome (case insensitive)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    
    // busca usuarios por telefone
    List<Usuario> findByTelefone(String telefone);
    
    // query customizada para buscar usuários por parte do nome ou email
    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Usuario> buscarPorNomeOuEmail(@Param("termo") String termo);
    
    // verifica se email ja existe
    boolean existsByEmail(String email);
    
    // conta usuarios ativos
    long countByAtivoTrue();
}
