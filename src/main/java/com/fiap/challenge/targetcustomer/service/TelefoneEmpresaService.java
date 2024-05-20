package com.fiap.challenge.targetcustomer.service;

import com.fiap.challenge.targetcustomer.model.TelefoneEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.TelefoneEmpresaDTO;
import com.fiap.challenge.targetcustomer.model.dto.TelefoneEmpresaUpdateDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.TelefoneEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelefoneEmpresaService {

    private final TelefoneEmpresaRepository telefoneEmpresaRepository;
    private final CadastroRepository cadastroRepository;

    public Page<TelefoneEmpresa> index(Pageable pageable) {
        return telefoneEmpresaRepository.findAll(pageable);
    }


    public TelefoneEmpresa create(TelefoneEmpresaDTO enderecoEmpresaRequest) {

        var telefoneEmpresa = TelefoneEmpresaDTO.toTelefoneEmpresa(enderecoEmpresaRequest);
        var cadastro = cadastroRepository.findById(enderecoEmpresaRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );

        telefoneEmpresa.setCadastro(cadastro);
        return telefoneEmpresaRepository.save(telefoneEmpresa);
    }

    public Optional<TelefoneEmpresa> get(Long id) {
         return telefoneEmpresaRepository.findById(id);
    }

    public void destroy(Long id) {
        verificarSeExisteTelefoneEmpresa(id);
        telefoneEmpresaRepository.deleteById(id);
    }

    public TelefoneEmpresa update(Long id, TelefoneEmpresaUpdateDTO telefoneEmpresaRequest) {
        var telefoneEmpresaToUpdate = verificarSeExisteTelefoneEmpresa(id);
        telefoneEmpresaToUpdate.setDdi(telefoneEmpresaRequest.ddi());
        telefoneEmpresaToUpdate.setDdd(telefoneEmpresaRequest.ddd());
        telefoneEmpresaToUpdate.setTelefone(telefoneEmpresaRequest.telefone());
        telefoneEmpresaToUpdate.setTipoTelefone(telefoneEmpresaRequest.tipoTelefone());

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
