package com.theucsrocha.model.validator

import com.theucsrocha.repository.CompetenciaRepository

class CompetenciaValidator {

    private final CompetenciaRepository competenciaRepository

    CompetenciaValidator(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository
    }

    void validarCompetenciasExistentes(List<String> competencias) {
        competencias.each { competencia ->
            if (competenciaRepository.findByNome(competencia) == null) {
                throw new IllegalArgumentException("Competência não encontrada: ${competencia}")
            }
        }
    }
}
