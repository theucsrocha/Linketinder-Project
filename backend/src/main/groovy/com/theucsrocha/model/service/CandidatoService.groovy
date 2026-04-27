package com.theucsrocha.model.service

import com.theucsrocha.model.entities.Candidato
import com.theucsrocha.repository.CandidatoRepository
import com.theucsrocha.validator.CompetenciaValidator

class CandidatoService {

    private final CandidatoRepository candidatoRepository
    private final CompetenciaValidator competenciaValidator

    CandidatoService(CandidatoRepository candidatoRepository, CompetenciaValidator competenciaValidator) {
        this.candidatoRepository = candidatoRepository
        this.competenciaValidator = competenciaValidator
    }

    void adicionarCandidato(Candidato candidato, List<String> competencias) {
        competenciaValidator.validarCompetenciasExistentes(competencias)
        candidatoRepository.inserir(candidato)
        candidatoRepository.adicionarCompetenciasNoCandidato(candidato.cpf, competencias)
    }

    List<Candidato> listarCandidatos() {
        candidatoRepository.findAll().collect { candidato ->
            candidato.competencias = candidatoRepository.getCompetenciasDoCandidatoPorCpf(candidato.cpf)
            candidato
        }
    }

    List<Candidato> getAllCandidatos() {
        candidatoRepository.findAll()
    }

    Candidato getCandidatoByCPF(String cpf) {
        candidatoRepository.findByCPF(cpf)
    }
}
