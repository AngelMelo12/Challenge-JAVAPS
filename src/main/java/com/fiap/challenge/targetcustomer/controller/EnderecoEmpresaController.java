package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.EnderecoEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.EnderecoEmpresaDTO;
import com.fiap.challenge.targetcustomer.model.dto.EnderecoEmpresaUpdateDTO;
import com.fiap.challenge.targetcustomer.service.EnderecoEmpresaService;
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
@RequestMapping("endereco")
@CacheConfig(cacheNames = "enderecosEmpresas")
@Slf4j
public class EnderecoEmpresaController {

    @Autowired
    private EnderecoEmpresaService enderecoEmpresaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os endereços", description = "Endpoint retorna de forma paginada todos os endereços, por padrão cada pagina contém 10 endereços, porém estes dados são parametrizáveis.")
    public ResponseEntity<Page<EnderecoEmpresa>> index(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(enderecoEmpresaService.index(pageable));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cria um novo endereço no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para realizar um novo endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso!")
    })
    public ResponseEntity<EnderecoEmpresa> create(@RequestBody @Valid EnderecoEmpresaDTO enderecoEmpresaRequest) {
        log.info("Cadastrando empresa: {}", enderecoEmpresaRequest);

        var enderecoEmpresa = enderecoEmpresaService.create(enderecoEmpresaRequest);
        return ResponseEntity
                .created(URIBuilder.createFromId(enderecoEmpresa.getId()))
                .build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Exibe os detalhes do endereço de id equivalente", description = "Endpoint retorna dados de um endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "200", description = "Endereço detalhado com sucesso!")
    })
    public ResponseEntity<EnderecoEmpresa> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return enderecoEmpresaService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                .build());
    }


    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta um endereço do sistema", description = "Endpoint recebe no path o id do endereço a ser deletado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso!")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando endereco {}", id);
        enderecoEmpresaService.destroy(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza um endereço no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para atualizar um endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso!")
    })
    public ResponseEntity<EnderecoEmpresa> update(@PathVariable Long id, @RequestBody EnderecoEmpresaUpdateDTO emailEmpresaRequest){
        log.info("Atualizando endereço de empresa id {} para {}", id, emailEmpresaRequest);
        return ResponseEntity.ok(enderecoEmpresaService.update(id, emailEmpresaRequest));
    }
}
