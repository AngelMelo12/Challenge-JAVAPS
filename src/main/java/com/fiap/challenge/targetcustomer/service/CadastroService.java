package com.fiap.challenge.targetcustomer.service;

import com.fiap.challenge.targetcustomer.model.Cadastro;
import com.fiap.challenge.targetcustomer.model.dto.CadastroDTO;
import com.fiap.challenge.targetcustomer.model.dto.CadastroUpdateDTO;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroService {

    private final CadastroRepository cadastroRepository;

    public Page<Cadastro> index(Pageable pageable) {
        return cadastroRepository.findAll(pageable);
    }

    public Cadastro create(CadastroDTO cadastroRequest) {
        return cadastroRepository.save(CadastroDTO.toCadastro(cadastroRequest));
    }

    public Optional<Cadastro> get(Long id) {
        return cadastroRepository.findById(id);
    }

    public void destroy(Long id) {
        verificarSeExisteCadastro(id);
        cadastroRepository.deleteById(id);
    }

    public Cadastro update(Long id, CadastroUpdateDTO cadastroRequest){
        var cadastroToUpdate = verificarSeExisteCadastro(id);
        cadastroToUpdate.setCnpj(cadastroRequest.cnpj());
        cadastroToUpdate.setSenha(cadastroRequest.senha());
        cadastroToUpdate.setRazaoSocial(cadastroRequest.razaoSocial());

        return cadastroRepository.save(cadastroToUpdate);
    }

    private Cadastro verificarSeExisteCadastro(Long id) {
        return cadastroRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );
    }
}
