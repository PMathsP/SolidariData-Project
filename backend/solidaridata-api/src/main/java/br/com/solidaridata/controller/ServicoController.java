package br.com.solidaridata.controller;

import br.com.solidaridata.model.Servico;
import br.com.solidaridata.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://127.0.0.1:3000"})
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos() {
        return ResponseEntity.ok(servicoService.listarTodosServicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Integer id) {
        return servicoService.buscarServicoPorId(id)
                .map(ResponseEntity::ok)
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