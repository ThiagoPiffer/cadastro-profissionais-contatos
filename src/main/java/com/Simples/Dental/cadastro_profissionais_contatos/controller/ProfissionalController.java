package com.Simples.Dental.cadastro_profissionais_contatos.controller;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.service.iservice.IProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private IProfissionalService profissionalService;

    @Operation(summary = "Listar profissionais")
    @GetMapping
    public ResponseEntity<List<Object[]>> listarProfissionais(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {

        Optional<List<Object[]>> profissionais = profissionalService.listarProfissionais(q, fields);

        if (profissionais.isPresent()) {
            return ResponseEntity.ok(profissionais.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar profissionais por id")
    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        return profissionalService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar profissional")
    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Profissional profissional) {
        Profissional novoProfissional = profissionalService.criar(profissional);
        return ResponseEntity.ok("Sucesso: profissional com id " + novoProfissional.getId() + " cadastrado");
    }
    @Operation(summary = "Atualizar profissional por id")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Profissional profissional) {
        profissionalService.atualizar(id, profissional);
        return ResponseEntity.ok("Sucesso: profissional alterado");
    }

    @Operation(summary = "Deletar profissional")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.ok("Sucesso: profissional excluído");
    }
}
