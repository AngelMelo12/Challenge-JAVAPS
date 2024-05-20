package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.Consulta;
import com.fiap.challenge.targetcustomer.model.dto.ConsultaDTO;
import com.fiap.challenge.targetcustomer.model.dto.ConsultaUpdateDTO;
import com.fiap.challenge.targetcustomer.service.ConsultaService;
import com.fiap.challenge.targetcustomer.utils.URIBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("consulta")
@CacheConfig(cacheNames = "consultas")
@Tag(name = "consultas", description = "Endpoint relacionado aos dados de consultas realizadas")
@Slf4j
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todas as consultas", description = "Endpoint retorna de forma paginada todos as consultas, por padrão cada pagina contém 10 cadastros, porém estes dados são parametrizáveis.")
    public ResponseEntity<Page<Consulta>> index(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(consultaService.index(pageable));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cria uma nova consulta no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para realizar uma nova consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso!")
    })
    public ResponseEntity<Consulta> create(@RequestBody @Valid ConsultaDTO consultaRequest) {
        log.info("Cadastrando consulta: {}", consultaRequest);
        var emailEmpresa = consultaService.create(consultaRequest, new byte[1]);
        return ResponseEntity
                .created(URIBuilder.createFromId(emailEmpresa.getId()))
                .build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Exibe os detalhes de uma consulta de id equivalente", description = "Endpoint retorna dados de uma consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "200", description = "Consulta detalhada com sucesso!")
    })
    public ResponseEntity<Consulta> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);
        return consultaService
                .get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta uma consulta do sistema", description = "Endpoint recebe no path o id da consulta a ser deletada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "204", description = "Consulta removida com sucesso!")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando consulta por id: {}", id);
        consultaService.destroy(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza uma consulta no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para atualizar uma consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso!")
    })
    public ResponseEntity<Consulta> update(@PathVariable Long id, @RequestBody ConsultaUpdateDTO consultaRequest){
        log.info("Atualizando e-mail de empresa id {} para {}", id, consultaRequest);
        return ResponseEntity.ok(consultaService.update(id, consultaRequest));
    }
}
