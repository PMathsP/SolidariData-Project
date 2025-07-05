package br.com.solidaridata.repository;

import br.com.solidaridata.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // O Spring Data JPA cria a query automaticamente baseada no nome do método!
    // Ele entende que "findByCpfUsuario" significa "SELECT * FROM usuario WHERE cpf_usuario = ?"
    UserDetails findByCpfUsuario(String cpf);
}