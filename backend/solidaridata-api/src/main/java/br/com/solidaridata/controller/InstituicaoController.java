package br.com.solidaridata.controller;

import br.com.solidaridata.model.Instituicao;
import br.com.solidaridata.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://127.0.0.1:3000"})
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    @GetMapping
    public ResponseEntity<List<Instituicao>> getAllInstituicoes() {
        return ResponseEntity.ok(instituicaoService.listarTodasInstituicoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> getInstituicaoById(@PathVariable Integer id) {
        return instituicaoService.buscarInstituicaoPorId(id)
                .map(ResponseEntity::ok)
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
}