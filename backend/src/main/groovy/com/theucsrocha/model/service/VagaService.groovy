package com.theucsrocha.model.service

import com.theucsrocha.model.entities.Vaga
import com.theucsrocha.repository.VagaRepository
import com.theucsrocha.validator.CompetenciaValidator

class VagaService {

    private final VagaRepository vagaRepository
    private final CompetenciaValidator competenciaValidator

    VagaService(VagaRepository vagaRepository, CompetenciaValidator competenciaValidator) {
        this.vagaRepository = vagaRepository
        this.competenciaValidator = competenciaValidator
    }

    void adicionarVaga(Vaga vaga, List<String> competenciasExigidas) {
        competenciaValidator.validarCompetenciasExistentes(competenciasExigidas)
        vagaRepository.inserir(vaga)
        int idVagaNova = vagaRepository.contarVagas()
        vagaRepository.adicionarCompetenciasNaVaga(idVagaNova, competenciasExigidas)
    }

    List<Vaga> listarVagas() {
        vagaRepository.getAllVagas().collect { vaga ->
            vaga.competenciasExigidas = vagaRepository.getCompetenciasDaVagaPorId(vaga.id)
            vaga
        }
    }

    List<Vaga> getAllVagas() {
        vagaRepository.getAllVagas()
    }
}
