package com.fiap.challenge.targetcustomer.service;

import com.fiap.challenge.targetcustomer.model.EnderecoEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.EnderecoEmpresaDTO;
import com.fiap.challenge.targetcustomer.model.dto.EnderecoEmpresaUpdateDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.EnderecoEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoEmpresaService {

    private final EnderecoEmpresaRepository enderecoEmpresaRepository;
    private final CadastroRepository cadastroRepository;

    public Page<EnderecoEmpresa> index(Pageable pageable) {
        return enderecoEmpresaRepository.findAll(pageable);
    }

    public EnderecoEmpresa create(EnderecoEmpresaDTO enderecoEmpresaRequest) {
        var enderecoEmpresa = EnderecoEmpresaDTO.toEnderecoEmpresa(enderecoEmpresaRequest);
        var cadastro = cadastroRepository.findById(enderecoEmpresaRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );

        enderecoEmpresa.setCadastro(cadastro);
        return enderecoEmpresaRepository.save(enderecoEmpresa);
    }

    public Optional<EnderecoEmpresa> get(Long id) {
        return enderecoEmpresaRepository.findById(id);
    }

    public void destroy(Long id) {
        verificarSeExisteEnderecoEmpresa(id);
        enderecoEmpresaRepository.deleteById(id);
    }

    public EnderecoEmpresa update(Long id, EnderecoEmpresaUpdateDTO emailEmpresaRequest){
        var enderecoEmpresaToUpdate = verificarSeExisteEnderecoEmpresa(id);
        enderecoEmpresaToUpdate.setLogradouro(emailEmpresaRequest.logradouro());
        enderecoEmpresaToUpdate.setCep(emailEmpresaRequest.cep());
        enderecoEmpresaToUpdate.setDescricaoPontoDeReferencia(emailEmpresaRequest.descricaoPontoDeReferencia());
        return enderecoEmpresaRepository.save(enderecoEmpresaToUpdate);
    }

    private EnderecoEmpresa verificarSeExisteEnderecoEmpresa(Long id) {
        return enderecoEmpresaRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço de empresa não encontrado" )
                );
    }
}
