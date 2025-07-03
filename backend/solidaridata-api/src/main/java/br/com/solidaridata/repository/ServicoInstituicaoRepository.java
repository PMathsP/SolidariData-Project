package br.com.solidaridata.repository;

import br.com.solidaridata.model.ServicoInstituicao;
import br.com.solidaridata.model.ServicoInstituicaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoInstituicaoRepository extends JpaRepository<ServicoInstituicao, ServicoInstituicaoId> {
}