package com.fiap.challenge.targetcustomer.service;

import com.fiap.challenge.targetcustomer.model.EmailEmpresa;
import com.fiap.challenge.targetcustomer.model.dto.EmailEmpresaDTO;
import com.fiap.challenge.targetcustomer.model.dto.EmailEmpresaUpdateDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.EmailEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailEmpresaService {

    private final EmailEmpresaRepository emailEmpresaRepository;
    private final CadastroRepository cadastroRepository;

    public Page<EmailEmpresa> index(Pageable pageable) {
        return emailEmpresaRepository.findAll(pageable);
    }

    public EmailEmpresa create(EmailEmpresaDTO emailEmpresaRequest) {
        var emailEmpresa = EmailEmpresaDTO.toEmailEmpresa(emailEmpresaRequest);
        var cadastro = cadastroRepository.findById(emailEmpresaRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );

        emailEmpresa.setCadastro(cadastro);
        return emailEmpresaRepository.save(emailEmpresa);
    }

    public Optional<EmailEmpresa> get(Long id) {
        return emailEmpresaRepository.findById(id);
    }

    public void destroy(Long id) {
        verificarSeExisteEmailEmpresa(id);
        emailEmpresaRepository.deleteById(id);
    }

    public EmailEmpresa update(Long id, EmailEmpresaUpdateDTO emailEmpresaRequest) {
        var emailEmpresaToUpdate = verificarSeExisteEmailEmpresa(id);
        emailEmpresaToUpdate.setEmail(emailEmpresaRequest.email());
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
