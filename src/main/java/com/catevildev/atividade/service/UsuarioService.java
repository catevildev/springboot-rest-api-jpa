package com.catevildev.atividade.service;

import com.catevildev.atividade.entity.Usuario;
import com.catevildev.atividade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // salvar usuário
    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + usuario.getEmail());
        }
        return usuarioRepository.save(usuario);
    }
    
    // busca todos os usuarios
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
    
    // busca usuario por id
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    // busca usuario por email
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    // busca usuarios ativos
    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuariosAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }
    
    // busca usuarios por nome
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    // busca usuarios por termo (nome ou email)
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorTermo(String termo) {
        return usuarioRepository.buscarPorNomeOuEmail(termo);
    }
    
    // atualiza usuario
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        // verifica se o email nao esta sendo usado por outro usuario
        if (!usuario.getEmail().equals(usuarioAtualizado.getEmail()) && 
            usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + usuarioAtualizado.getEmail());
        }
        
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setTelefone(usuarioAtualizado.getTelefone());
        usuario.setAtivo(usuarioAtualizado.getAtivo());
        
        return usuarioRepository.save(usuario);
    }
    
    // desativa usuario
    public void desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
    
    // ativa usuario
    public void ativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }
    
    // deleta usuario
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    // conta usuarios ativos
    @Transactional(readOnly = true)
    public long contarUsuariosAtivos() {
        return usuarioRepository.countByAtivoTrue();
    }
}
