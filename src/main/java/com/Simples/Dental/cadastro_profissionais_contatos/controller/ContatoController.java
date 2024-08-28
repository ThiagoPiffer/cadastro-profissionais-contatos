package com.Simples.Dental.cadastro_profissionais_contatos.controller;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.service.iservice.IContatoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private IContatoService contatoService;

    @Operation(summary = "Listar contatos")
    @GetMapping
    public ResponseEntity<List<Object[]>> listarContatos(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {

        List<Object[]> contatos = contatoService.listarContatos(q, fields).orElseGet(List::of);
        return ResponseEntity.ok(contatos);
    }

    @Operation(summary = "Buscar contato por id")
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        return contatoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo contato")
    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Contato contato) {
        Contato novoContato = contatoService.criar(contato);
        return ResponseEntity.ok("Sucesso: contato com id " + novoContato.getId() + " cadastrado");
    }

    @Operation(summary = "Atualiza um contato existente")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Contato contato) {
        contatoService.atualizar(id, contato);
        return ResponseEntity.ok("Sucesso: contato alterado");
    }

    @Operation(summary = "Exclui um contato")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        contatoService.deletar(id);
        return ResponseEntity.ok("Sucesso: contato excluído");
    }
}
