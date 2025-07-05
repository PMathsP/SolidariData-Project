package br.com.solidaridata.controller;

import br.com.solidaridata.model.Atendimento;
import br.com.solidaridata.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://127.0.0.1:3000"})
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping
    public ResponseEntity<List<Atendimento>> getAllAtendimentos() {
        return ResponseEntity.ok(atendimentoService.listarTodosAtendimentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atendimento> getAtendimentoById(@PathVariable Integer id) {
        return atendimentoService.buscarAtendimentoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Atendimento> createAtendimento(@RequestBody Atendimento novoAtendimento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoService.salvarAtendimento(novoAtendimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atendimento> updateAtendimento(@PathVariable Integer id, @RequestBody Atendimento atendimentoDetalhes) {
        Atendimento atendimentoAtualizado = atendimentoService.atualizarAtendimento(id, atendimentoDetalhes);
        if (atendimentoAtualizado != null) {
            return ResponseEntity.ok(atendimentoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtendimento(@PathVariable Integer id) {
        if (atendimentoService.deletarAtendimento(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}