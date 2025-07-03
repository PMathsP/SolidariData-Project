package br.com.solidaridata.service;

import br.com.solidaridata.model.Usuario;
import br.com.solidaridata.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // NOVO MÉTODO: Atualiza um usuário existente
    public Usuario atualizarUsuario(Integer id, Usuario usuarioDetalhes) {
        // Busca o usuário no banco para garantir que ele existe
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();
            // Atualiza os campos do usuário existente com os novos detalhes
            usuarioExistente.setNomeUsuario(usuarioDetalhes.getNomeUsuario());
            usuarioExistente.setSenhaHash(usuarioDetalhes.getSenhaHash());
            // (aqui você atualizaria outros campos que podem ser modificados)

            // Salva o usuário atualizado de volta no banco
            return usuarioRepository.save(usuarioExistente);
        } else {
            // Retorna nulo se o usuário não for encontrado
            return null;
        }
    }

    // NOVO MÉTODO: Deleta um usuário
    public boolean deletarUsuario(Integer id) {
        // Verifica se o usuário existe antes de tentar deletar
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true; // Sucesso
        } else {
            return false; // Usuário não encontrado
        }
    }
}