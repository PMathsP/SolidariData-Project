package br.com.solidaridata.service;

import br.com.solidaridata.model.Instituicao;
import br.com.solidaridata.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.solidaridata.model.ServicoInstituicao;
import br.com.solidaridata.model.ServicoInstituicaoId;
import br.com.solidaridata.repository.ServicoInstituicaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // NOVA INJEÇÃO DE DEPENDÊNCIA
    @Autowired
    private ServicoInstituicaoRepository servicoInstituicaoRepository;

    // ... (métodos existentes: listar, buscar, salvar, atualizar, deletar)

    // NOVO MÉTODO: Adiciona um serviço a uma instituição
    public void adicionarServico(Integer idInstituicao, Integer idServico) {
        ServicoInstituicaoId servicoInstituicaoId = new ServicoInstituicaoId(idInstituicao, idServico);
        ServicoInstituicao associacao = new ServicoInstituicao(servicoInstituicaoId);
        servicoInstituicaoRepository.save(associacao);
    }

    // NOVO MÉTODO: Remove um serviço de uma instituição
    public void removerServico(Integer idInstituicao, Integer idServico) {
        ServicoInstituicaoId servicoInstituicaoId = new ServicoInstituicaoId(idInstituicao, idServico);
        servicoInstituicaoRepository.deleteById(servicoInstituicaoId);
    }

    public List<Instituicao> listarTodasInstituicoes() {
        return instituicaoRepository.findAll();
    }

    public Optional<Instituicao> buscarInstituicaoPorId(Integer id) {
        return instituicaoRepository.findById(id);
    }

    public Instituicao salvarInstituicao(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public Instituicao atualizarInstituicao(Integer id, Instituicao instituicaoDetalhes) {
        Optional<Instituicao> optionalInstituicao = instituicaoRepository.findById(id);
        if (optionalInstituicao.isPresent()) {
            Instituicao instituicaoExistente = optionalInstituicao.get();
            instituicaoExistente.setNomeInstituicao(instituicaoDetalhes.getNomeInstituicao());
            instituicaoExistente.setRazaoSocial(instituicaoDetalhes.getRazaoSocial());
            // Adicionar outros campos para atualizar conforme necessário
            return instituicaoRepository.save(instituicaoExistente);
        }
        return null;
    }

    public boolean deletarInstituicao(Integer id) {
        if (instituicaoRepository.existsById(id)) {
            instituicaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}