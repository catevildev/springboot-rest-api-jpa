package com.catevildev.atividade.controller;

import com.catevildev.atividade.entity.Usuario;
import com.catevildev.atividade.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    // cria novo usuário
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao criar usuário: " + e.getMessage());
        }
    }
    
    // busca todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }
    
    // busca usuário por id
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // atualiza usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
    
    // apaga usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok().body("Usuário deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao deletar usuário: " + e.getMessage());
        }
    }
    
    // busca usuários ativos
    @GetMapping("/ativos")
    public ResponseEntity<List<Usuario>> buscarUsuariosAtivos() {
        List<Usuario> usuarios = usuarioService.buscarUsuariosAtivos();
        return ResponseEntity.ok(usuarios);
    }
    
    // busca usuários por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNome(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }
    
    // desativa usuário
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<?> desativarUsuario(@PathVariable Long id) {
        try {
            usuarioService.desativar(id);
            return ResponseEntity.ok().body("Usuário desativado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao desativar usuário: " + e.getMessage());
        }
    }
}
