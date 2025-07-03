package br.com.solidaridata.controller;

import br.com.solidaridata.model.Servico;
import br.com.solidaridata.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin("*")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos() {
        return ResponseEntity.ok(servicoService.listarTodosServicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Integer id) {
        Optional<Servico> servico = servicoService.buscarServicoPorId(id);
        return servico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servico> createServico(@RequestBody Servico novoServico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.salvarServico(novoServico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> updateServico(@PathVariable Integer id, @RequestBody Servico servicoDetalhes) {
        Servico servicoAtualizado = servicoService.atualizarServico(id, servicoDetalhes);
        if (servicoAtualizado != null) {
            return ResponseEntity.ok(servicoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Integer id) {
        if (servicoService.deletarServico(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}