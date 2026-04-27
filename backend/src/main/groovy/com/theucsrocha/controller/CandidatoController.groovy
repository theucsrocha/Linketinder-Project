package com.theucsrocha.controller

import com.theucsrocha.model.entities.Candidato
import com.theucsrocha.model.service.CandidatoService

class CandidatoController {

    private final CandidatoService candidatoService

    CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService
    }

    void adicionarCandidato(Candidato candidato, List<String> competencias) {
        candidatoService.adicionarCandidato(candidato, competencias)
    }

    void listarCandidatos() {
        candidatoService.listarCandidatos().forEach { println(it) }
    }

    List<Candidato> getAllCandidatos(){
        return candidatoService.listarCandidatos()
    }

    Candidato getCandidatoByCPF(String cpf) {
        candidatoService.getCandidatoByCPF(cpf)
    }
}
