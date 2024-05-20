package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.EmailEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.EmailEmpresaDTO;
import com.fiap.challenge.targetcustomer.model.dto.EmailEmpresaUpdateDTO;
import com.fiap.challenge.targetcustomer.service.EmailEmpresaService;
import com.fiap.challenge.targetcustomer.utils.URIBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
@CacheConfig(cacheNames = "emailsEmpresas")
@Slf4j
public class EmailEmpresaController {

    @Autowired
    private EmailEmpresaService emailEmpresaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os e-mails", description = "Endpoint retorna de forma paginada todos os e-mails, por padrão cada pagina contém 10 e-mails, porém estes dados são parametrizáveis.")
    public ResponseEntity<Page<EmailEmpresa>> index(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(emailEmpresaService.index(pageable));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cria um novo e-mail no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para realizar um novo e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "201", description = "E-mail criado com sucesso!")
    })
    public ResponseEntity<EmailEmpresa> create(@RequestBody @Valid EmailEmpresaDTO emailEmpresaRequest) {
        log.info("Cadastrando empresa: {}", emailEmpresaRequest);
        var emailEmpresa = emailEmpresaService.create(emailEmpresaRequest);
        return ResponseEntity
                .created(URIBuilder.createFromId(emailEmpresa.getId()))
                .build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Exibe os detalhes do e-mail de id equivalente", description = "Endpoint retorna dados de um e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "E-mail não encontrado"),
            @ApiResponse(responseCode = "200", description = "E-mail detalhado com sucesso!")
    })
    public ResponseEntity<EmailEmpresa> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return emailEmpresaService
                .get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta um e-mail do sistema", description = "Endpoint recebe no path o id do e-mail a ser deletado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "E-mail não encontrado"),
            @ApiResponse(responseCode = "204", description = "E-mail removido com sucesso!")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando e-mail id: {}", id);
        emailEmpresaService.destroy(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza um e-mail no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para atualizar um e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso!")
    })
    public ResponseEntity<EmailEmpresa> update(@PathVariable Long id, @RequestBody EmailEmpresaUpdateDTO emailEmpresaRequest){
        log.info("Atualizando e-mail de empresa id {} para {}", id, emailEmpresaRequest);
        return ResponseEntity.ok(emailEmpresaService.update(id, emailEmpresaRequest));
    }
}
