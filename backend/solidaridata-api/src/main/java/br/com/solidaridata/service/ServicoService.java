package br.com.solidaridata.service;

import br.com.solidaridata.model.Servico;
import br.com.solidaridata.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> listarTodosServicos() {
        return servicoRepository.findAll();
    }

    public Optional<Servico> buscarServicoPorId(Integer id) {
        return servicoRepository.findById(id);
    }

    public Servico salvarServico(Servico servico) {
        return servicoRepository.save(servico);
    }

    public Servico atualizarServico(Integer id, Servico servicoDetalhes) {
        Optional<Servico> optionalServico = servicoRepository.findById(id);
        if (optionalServico.isPresent()) {
            Servico servicoExistente = optionalServico.get();
            servicoExistente.setDescricaoServico(servicoDetalhes.getDescricaoServico());
            return servicoRepository.save(servicoExistente);
        }
        return null;
    }

    public boolean deletarServico(Integer id) {
        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}