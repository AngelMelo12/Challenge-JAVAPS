package com.fiap.challenge.targetcustomer.service;

import com.fiap.challenge.targetcustomer.model.AnaliseConsulta;
import com.fiap.challenge.targetcustomer.model.AnaliseConsultaStatus;
import com.fiap.challenge.targetcustomer.model.Consulta;
import com.fiap.challenge.targetcustomer.model.dto.ConsultaDTO;
import com.fiap.challenge.targetcustomer.model.dto.ConsultaUpdateDTO;
import com.fiap.challenge.targetcustomer.repository.AnaliseConsultaRepository;
import com.fiap.challenge.targetcustomer.repository.CadastroRepository;
import com.fiap.challenge.targetcustomer.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final AnaliseConsultaRepository analiseConsultaRepository;
    private final CadastroRepository cadastroRepository;

    public Page<Consulta> index(Pageable pageable) {
        return consultaRepository.findAll(pageable);
    }

    public Consulta create(ConsultaDTO cadastroRequest, byte[] dataSetFile) {
        var consulta = ConsultaDTO.toConsulta(cadastroRequest, dataSetFile);
        var cadastro = cadastroRepository.findById(cadastroRequest.getIdCadastro())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado" )
                );
        consulta.setCadastro(cadastro);
        consulta.setCsvArquivo(dataSetFile);

        var createdConsulta = consultaRepository.save(consulta);

        var analiseConsulta = new AnaliseConsulta();
        analiseConsulta.setPdf(new byte[1]);
        analiseConsulta.setStatus(AnaliseConsultaStatus.INDISPONIVEL.value);
        analiseConsulta.setConsulta(consulta);
        analiseConsultaRepository.save(analiseConsulta);

        return createdConsulta;
    }

    public Optional<Consulta> get(Long id) {
        return consultaRepository.findById(id);
    }

    public void destroy(Long id) {
        verificarSeExisteConsulta(id);
        consultaRepository.deleteById(id);
    }

    public Consulta update(Long id, ConsultaUpdateDTO consultaRequest){
        var consultaToUpdate = verificarSeExisteConsulta(id);
        consultaToUpdate.setDescricaoConsulta(consultaRequest.descricao());
        return consultaRepository.save(consultaToUpdate);
    }

    private Consulta verificarSeExisteConsulta(Long id) {
        return consultaRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrado" )
                );
    }
}
