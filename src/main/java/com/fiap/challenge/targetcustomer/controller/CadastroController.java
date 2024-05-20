package com.fiap.challenge.targetcustomer.controller;

import com.fiap.challenge.targetcustomer.model.Cadastro;
import com.fiap.challenge.targetcustomer.model.dto.CadastroDTO;
import com.fiap.challenge.targetcustomer.model.dto.CadastroUpdateDTO;
import com.fiap.challenge.targetcustomer.service.CadastroService;
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
@RequestMapping("cadastro")
@CacheConfig(cacheNames = "cadastros")
@Tag(name = "cadastros", description = "Endpoint relacionado aos dados de usuários cadastrados")
@Slf4j
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os cadastros", description = "Endpoint retorna de forma paginada todos os cadastros, por padrão cada pagina contém 10 cadastros, porém estes dados são parametrizáveis.")
    public ResponseEntity<Page<Cadastro>> index(@ParameterObject @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(cadastroService.index(pageable));
    }

    @PostMapping
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cria um novo cadastro no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para realizar um novo cadastro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "201", description = "Cadastro criado com sucesso!")
    })
    public ResponseEntity<Cadastro> create(@RequestBody @Valid CadastroDTO cadastroRequest) {
        log.info("Cadastrando empresa: {}", cadastroRequest);
        var createdCadastro = cadastroService.create(cadastroRequest);
        return ResponseEntity
                .created(URIBuilder.createFromId(createdCadastro.getId()))
                .build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Exibe os detalhes do cadastro de id equivalente", description = "Endpoint retorna dados de um cadastro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
            @ApiResponse(responseCode = "200", description = "Cadastro detalhado com sucesso!")
    })
    public ResponseEntity<Cadastro> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);
        return cadastroService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                .build());
    }

    @DeleteMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta um cadastro do sistema", description = "Endpoint recebe no path o id do cadastro a ser deletado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
            @ApiResponse(responseCode = "204", description = "Cadastro removido com sucesso!")
    })
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("Apagando cadastro por id: {}", id);
        cadastroService.destroy(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Transactional
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza um cadastro no sistema", description = "Endpoint recebe no corpo da requisição os dados necessários para atualizar um cadastro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "200", description = "Cadastro atualizado com sucesso!")
    })
    public ResponseEntity<Cadastro> update(@PathVariable Long id, @RequestBody CadastroUpdateDTO cadastroRequest){
        log.info("Atualizando cadastro id {} para {}", id, cadastroRequest);
        return ResponseEntity.ok(cadastroService.update(id, cadastroRequest));
    }
}
