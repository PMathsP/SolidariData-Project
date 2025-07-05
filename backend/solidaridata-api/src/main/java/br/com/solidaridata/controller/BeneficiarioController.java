package br.com.solidaridata.controller;

import br.com.solidaridata.model.Beneficiario;
import br.com.solidaridata.service.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/beneficiarios")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000", "http://127.0.0.1:3000"})
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService beneficiarioService;

    @GetMapping
    public ResponseEntity<List<Beneficiario>> getAllBeneficiarios() {
        return ResponseEntity.ok(beneficiarioService.listarTodosBeneficiarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficiario> getBeneficiarioById(@PathVariable Integer id) {
        Optional<Beneficiario> beneficiario = beneficiarioService.buscarBeneficiarioPorId(id);
        return beneficiario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Beneficiario> createBeneficiario(@RequestBody Beneficiario novoBeneficiario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(beneficiarioService.salvarBeneficiario(novoBeneficiario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficiario> updateBeneficiario(@PathVariable Integer id, @RequestBody Beneficiario beneficiarioDetalhes) {
        Beneficiario beneficiarioAtualizado = beneficiarioService.atualizarBeneficiario(id, beneficiarioDetalhes);
        if (beneficiarioAtualizado != null) {
            return ResponseEntity.ok(beneficiarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiario(@PathVariable Integer id) {
        if (beneficiarioService.deletarBeneficiario(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}