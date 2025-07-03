package br.com.solidaridata.service;

import br.com.solidaridata.model.Beneficiario;
import br.com.solidaridata.repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    public List<Beneficiario> listarTodosBeneficiarios() {
        return beneficiarioRepository.findAll();
    }

    public Optional<Beneficiario> buscarBeneficiarioPorId(Integer id) {
        return beneficiarioRepository.findById(id);
    }

    public Beneficiario salvarBeneficiario(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }

    public Beneficiario atualizarBeneficiario(Integer id, Beneficiario beneficiarioDetalhes) {
        Optional<Beneficiario> optionalBeneficiario = beneficiarioRepository.findById(id);
        if (optionalBeneficiario.isPresent()) {
            Beneficiario beneficiarioExistente = optionalBeneficiario.get();
            beneficiarioExistente.setNome(beneficiarioDetalhes.getNome());
            beneficiarioExistente.setDataNascimento(beneficiarioDetalhes.getDataNascimento());
            beneficiarioExistente.setGrauParentesco(beneficiarioDetalhes.getGrauParentesco());
            // Adicionar outros campos para atualizar
            return beneficiarioRepository.save(beneficiarioExistente);
        }
        return null;
    }

    public boolean deletarBeneficiario(Integer id) {
        if (beneficiarioRepository.existsById(id)) {
            beneficiarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}