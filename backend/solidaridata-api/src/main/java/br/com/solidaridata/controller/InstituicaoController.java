package br.com.solidaridata.controller;

import br.com.solidaridata.model.Instituicao;
import br.com.solidaridata.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    @GetMapping
    public ResponseEntity<List<Instituicao>> getAllInstituicoes() {
        return ResponseEntity.ok(instituicaoService.listarTodasInstituicoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> getInstituicaoById(@PathVariable Integer id) {
        Optional<Instituicao> instituicao = instituicaoService.buscarInstituicaoPorId(id);
        return instituicao.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instituicao> createInstituicao(@RequestBody Instituicao novaInstituicao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoService.salvarInstituicao(novaInstituicao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instituicao> updateInstituicao(@PathVariable Integer id, @RequestBody Instituicao instituicaoDetalhes) {
        Instituicao instituicaoAtualizada = instituicaoService.atualizarInstituicao(id, instituicaoDetalhes);
        if (instituicaoAtualizada != null) {
            return ResponseEntity.ok(instituicaoAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable Integer id) {
        if (instituicaoService.deletarInstituicao(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // NOVO ENDPOINT: Associa um serviço a uma instituição
    // Ex: POST /api/instituicoes/1/servicos/2
    @PostMapping("/{idInstituicao}/servicos/{idServico}")
    public ResponseEntity<Void> adicionarServicoAInstituicao(
            @PathVariable Integer idInstituicao,
            @PathVariable Integer idServico) {
        instituicaoService.adicionarServico(idInstituicao, idServico);
        // Retorna 201 Created para indicar que a associação foi criada com sucesso
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // NOVO ENDPOINT: Desassocia um serviço de uma instituição
    // Ex: DELETE /api/instituicoes/1/servicos/2
    @DeleteMapping("/{idInstituicao}/servicos/{idServico}")
    public ResponseEntity<Void> removerServicoDaInstituicao(
            @PathVariable Integer idInstituicao,
            @PathVariable Integer idServico) {
        instituicaoService.removerServico(idInstituicao, idServico);
        // Retorna 204 No Content para indicar que a associação foi removida
        return ResponseEntity.noContent().build();

    }
}