package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.Cadastro;
import com.fiap.challenge.targetcustomer.model.dto.CadastroDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
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
@RequestMapping("cadastro")
@Slf4j
public class CadastroController {

    @Autowired
    private CadastroRepository cadastroRepository;

    @GetMapping
    public List<Cadastro> index() {
        return cadastroRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cadastro create(@RequestBody @Valid CadastroDTO cadastroRequest) {
        log.info("Cadastrando empresa: {}", cadastroRequest);
        return cadastroRepository.save(CadastroDTO.toCadastro(cadastroRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<Cadastro> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return cadastroRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando cadastro {}", id);
        verificarSeExisteCadastro(id);
        cadastroRepository.deleteById(id);
    }


    @PutMapping("{id}")
    public Cadastro update(@PathVariable Long id, @RequestBody CadastroDTO cadastroRequest){
        log.info("Atualizando cadastro id {} para {}", id, cadastroRequest);

        var cadastroToUpdate = verificarSeExisteCadastro(id);
        cadastroToUpdate.setCnpj(cadastroRequest.getCnpj());
        cadastroToUpdate.setSenha(cadastroRequest.getSenha());
        cadastroToUpdate.setRazaoSocial(cadastroRequest.getRazaoSocial());

        return cadastroRepository.save(cadastroToUpdate);

    }


    private Cadastro verificarSeExisteCadastro(Long id) {
        return cadastroRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );
    }
}
