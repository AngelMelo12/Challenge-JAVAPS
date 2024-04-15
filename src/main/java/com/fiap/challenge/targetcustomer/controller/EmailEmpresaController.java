package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.Cadastro;
import com.fiap.challenge.targetcustomer.model.EmailEmpresa;
import com.fiap.challenge.targetcustomer.model.EmailEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.EmailEmpresaDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.EmailEmpresaRepository;
import com.fiap.challenge.targetcustomer.repository.EmailEmpresaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("email")
@Slf4j
public class EmailEmpresaController {

    @Autowired
    private EmailEmpresaRepository emailEmpresaRepository;

    @Autowired
    private CadastroRepository cadastroRepository;


    @GetMapping
    public List<EmailEmpresa> index() {
        return emailEmpresaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EmailEmpresa create(@RequestBody @Valid EmailEmpresaDTO emailEmpresaRequest) {
        log.info("Cadastrando empresa: {}", emailEmpresaRequest);
        var emailEmpresa = EmailEmpresaDTO.toEmailEmpresa(emailEmpresaRequest);
        var cadastro = cadastroRepository.findById(emailEmpresaRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );

        emailEmpresa.setCadastro(cadastro);
        return emailEmpresaRepository.save(emailEmpresa);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmailEmpresa> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return emailEmpresaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando e-mail id: {}", id);
        verificarSeExisteEmailEmpresa(id);
        emailEmpresaRepository.deleteById(id);
    }


    @PutMapping("{id}")
    public EmailEmpresa update(@PathVariable Long id, @RequestBody EmailEmpresaDTO emailEmpresaRequest){
        log.info("Atualizando e-mail de empresa id {} para {}", id, emailEmpresaRequest);

        var emailEmpresaToUpdate = verificarSeExisteEmailEmpresa(id);
        emailEmpresaToUpdate.setEmail(emailEmpresaRequest.getEmail());

        return emailEmpresaRepository.save(emailEmpresaToUpdate);
    }


    private EmailEmpresa verificarSeExisteEmailEmpresa(Long id) {
        return emailEmpresaRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "E-mail de empresa não encontrado" )
                );
    }
}
