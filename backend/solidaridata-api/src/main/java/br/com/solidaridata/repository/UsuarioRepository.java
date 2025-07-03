package br.com.solidaridata.repository;

import br.com.solidaridata.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // A mágica do Spring Data JPA acontece aqui.
    // Nenhum código é necessário por enquanto.
}