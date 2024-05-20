package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.TelefoneEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.TelefoneEmpresaDTO;
import com.fiap.challenge.targetcustomer.model.dto.TelefoneEmpresaUpdateDTO;
import com.fiap.challenge.targetcustomer.service.TelefoneEmpresaService;
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
@RequestMapping("telefone")
@CacheConfig(cacheNames = "telefones")
@Slf4j
public class TelefoneEmpresaController {

    @Autowired
    private TelefoneEmpresaService telefoneEmpresaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os telefones", description = "Endpoint retorna de forma paginada todos os telefones, por padrão cada pagina contém 10 telefones, porém estes dados são parametrizáveis.")
    public ResponseEntity<Page<TelefoneEmpresa>> index(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(telefoneEmpresaService.index(pageable));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cria um novo telefone no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para realizar um novo telefone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "201", description = "Telefone criado com sucesso!")
    })
    public ResponseEntity<TelefoneEmpresa> create(@RequestBody @Valid TelefoneEmpresaDTO telefoneEmpresaRequest) {
        log.info("Cadastrando telefone: {}", telefoneEmpresaRequest);
        var createdTelefone = telefoneEmpresaService.create(telefoneEmpresaRequest);
        return ResponseEntity
                .created(URIBuilder.createFromId(createdTelefone.getId()))
                .build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Exibe os detalhes do telefone de id equivalente", description = "Endpoint retorna dados de um telefone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado"),
            @ApiResponse(responseCode = "200", description = "Telefone detalhado com sucesso!")
    })
    public ResponseEntity<TelefoneEmpresa> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);
        return telefoneEmpresaService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                .build());
    }


    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta um telefone do sistema", description = "Endpoint recebe no path o id do telefone a ser deletado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado"),
            @ApiResponse(responseCode = "204", description = "Telefone removido com sucesso!")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando endereco {}", id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza um telefone no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para atualizar um telefone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso!")
    })
    public ResponseEntity<TelefoneEmpresa> update(@PathVariable Long id, @RequestBody TelefoneEmpresaUpdateDTO telefoneEmpresaRequest){
        log.info("Atualizando telefone de empresa id {} para {}", id, telefoneEmpresaRequest);
        return ResponseEntity.ok(telefoneEmpresaService.update(id, telefoneEmpresaRequest));
    }
}
