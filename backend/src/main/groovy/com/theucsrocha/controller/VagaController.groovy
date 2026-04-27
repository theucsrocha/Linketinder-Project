package com.theucsrocha.controller

import com.theucsrocha.model.entities.Vaga
import com.theucsrocha.model.service.VagaService

class VagaController {

    private final VagaService vagaService

    VagaController(VagaService vagaService) {
        this.vagaService = vagaService
    }

    void adicionarVaga(Vaga vaga, List<String> competenciasExigidas) {
        vagaService.adicionarVaga(vaga, competenciasExigidas)
    }

    void listarVagas() {
        vagaService.listarVagas().forEach { println(it) }
    }


}
