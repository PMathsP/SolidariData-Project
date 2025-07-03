package br.com.solidaridata.service;

import br.com.solidaridata.model.Atendimento;
import br.com.solidaridata.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public List<Atendimento> listarTodosAtendimentos() {
        return atendimentoRepository.findAll();
    }

    public Optional<Atendimento> buscarAtendimentoPorId(Integer id) {
        return atendimentoRepository.findById(id);
    }

    public Atendimento salvarAtendimento(Atendimento atendimento) {
        return atendimentoRepository.save(atendimento);
    }

    public Atendimento atualizarAtendimento(Integer id, Atendimento atendimentoDetalhes) {
        return atendimentoRepository.findById(id).map(atendimento -> {
            atendimento.setNivelUrgencia(atendimentoDetalhes.getNivelUrgencia());
            // Geralmente não se atualiza a data, instituição ou beneficiário de um atendimento,
            // mas a lógica seria adicionada aqui se necessário.
            return atendimentoRepository.save(atendimento);
        }).orElse(null);
    }

    public boolean deletarAtendimento(Integer id) {
        if (atendimentoRepository.existsById(id)) {
            atendimentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}