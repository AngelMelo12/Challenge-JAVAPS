package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.EnderecoEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.EnderecoEmpresaDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.EnderecoEmpresaRepository;
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
@RequestMapping("endereco")
@Slf4j
public class EnderecoEmpresaController {

    @Autowired
    private EnderecoEmpresaRepository enderecoEmpresaRepository;

    @Autowired
    private CadastroRepository cadastroRepository;

    @GetMapping
    public List<EnderecoEmpresa> index() {
        return enderecoEmpresaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EnderecoEmpresa create(@RequestBody @Valid EnderecoEmpresaDTO enderecoEmpresaRequest) {
        log.info("Cadastrando empresa: {}", enderecoEmpresaRequest);

        var enderecoEmpresa = EnderecoEmpresaDTO.toEnderecoEmpresa(enderecoEmpresaRequest);
        var cadastro = cadastroRepository.findById(enderecoEmpresaRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );

        enderecoEmpresa.setCadastro(cadastro);
        return enderecoEmpresaRepository.save(enderecoEmpresa);
    }

    @GetMapping("{id}")
    public ResponseEntity<EnderecoEmpresa> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return enderecoEmpresaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando endereco {}", id);
        verificarSeExisteEnderecoEmpresa(id);
        enderecoEmpresaRepository.deleteById(id);
    }


    @PutMapping("{id}")
    public EnderecoEmpresa update(@PathVariable Long id, @RequestBody EnderecoEmpresaDTO emailEmpresaRequest){
        log.info("Atualizando endereço de empresa id {} para {}", id, emailEmpresaRequest);

        var emailEmpresaToUpdate = verificarSeExisteEnderecoEmpresa(id);
        emailEmpresaToUpdate.setLogradouro(emailEmpresaRequest.getLogradouro());
        emailEmpresaToUpdate.setCep(emailEmpresaRequest.getCep());
        emailEmpresaToUpdate.setDescricaoPontoDeReferencia(emailEmpresaRequest.getDescricaoPontoDeReferencia());

        return enderecoEmpresaRepository.save(emailEmpresaToUpdate);
    }


    private EnderecoEmpresa verificarSeExisteEnderecoEmpresa(Long id) {
        return enderecoEmpresaRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço de empresa não encontrado" )
                );
    }
}
