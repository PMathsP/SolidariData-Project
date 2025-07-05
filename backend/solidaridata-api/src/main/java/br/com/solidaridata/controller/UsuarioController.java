package br.com.solidaridata.controller;

import br.com.solidaridata.model.Usuario;
import br.com.solidaridata.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://127.0.0.1:3000"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para listar todos os usuários
    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Endpoint para buscar um usuário por ID
    // GET /api/usuarios/1
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para cadastrar um novo usuário
    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario novoUsuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    // Endpoint para atualizar um usuário existente
    // PUT /api/usuarios/1
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioDetalhes) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDetalhes);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(usuarioAtualizado); // Retorna 200 OK com o usuário atualizado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    // Endpoint para deletar um usuário
    // DELETE /api/usuarios/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        if (usuarioService.deletarUsuario(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso, sem corpo)
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }
}