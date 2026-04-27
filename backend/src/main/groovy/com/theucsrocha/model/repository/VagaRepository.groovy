package com.theucsrocha.model.repository

import com.theucsrocha.model.entities.Competencia
import com.theucsrocha.model.entities.Vaga

interface VagaRepository {
    void inserir(Vaga vaga)
    void adicionarCompetenciasNaVaga(Integer vagaId, List<String> competencias)
    int contarVagas()
    List<Vaga> getAllVagas()
    List<Competencia> getCompetenciasDaVagaPorId(Integer id)
}
