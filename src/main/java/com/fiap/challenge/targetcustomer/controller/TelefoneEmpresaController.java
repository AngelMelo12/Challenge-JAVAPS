package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.TelefoneEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.TelefoneEmpresaDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.TelefoneEmpresaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("telefone")
@Slf4j
public class TelefoneEmpresaController {

    @Autowired
    private TelefoneEmpresaRepository telefoneEmpresaRepository;

    @Autowired
    private CadastroRepository cadastroRepository;

    @GetMapping
    public List<TelefoneEmpresa> index() {
        return telefoneEmpresaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TelefoneEmpresa create(@RequestBody @Valid TelefoneEmpresaDTO enderecoEmpresaRequest) {
        log.info("Cadastrando empresa: {}", enderecoEmpresaRequest);

        var telefoneEmpresa = TelefoneEmpresaDTO.toTelefoneEmpresa(enderecoEmpresaRequest);
        var cadastro = cadastroRepository.findById(enderecoEmpresaRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );

        telefoneEmpresa.setCadastro(cadastro);
        return telefoneEmpresaRepository.save(telefoneEmpresa);
    }

    @GetMapping("{id}")
    public ResponseEntity<TelefoneEmpresa> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return telefoneEmpresaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando endereco {}", id);
        verificarSeExisteTelefoneEmpresa(id);
        telefoneEmpresaRepository.deleteById(id);
    }


    @PutMapping("{id}")
    public TelefoneEmpresa update(@PathVariable Long id, @RequestBody TelefoneEmpresaDTO telefoneEmpresaRequest){
        log.info("Atualizando telefone de empresa id {} para {}", id, telefoneEmpresaRequest);

        var telefoneEmpresaToUpdate = verificarSeExisteTelefoneEmpresa(id);
        telefoneEmpresaToUpdate.setDdi(telefoneEmpresaRequest.getDdi());
        telefoneEmpresaToUpdate.setDdd(telefoneEmpresaRequest.getDdd());
        telefoneEmpresaToUpdate.setTelefone(telefoneEmpresaToUpdate.getTelefone());
        telefoneEmpresaToUpdate.setTipoTelefone(telefoneEmpresaRequest.getTipoTelefone());

        return telefoneEmpresaRepository.save(telefoneEmpresaToUpdate);
    }


    private TelefoneEmpresa verificarSeExisteTelefoneEmpresa(Long id) {
        return telefoneEmpresaRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefone de empresa não encontrado" )
                );
    }
}
