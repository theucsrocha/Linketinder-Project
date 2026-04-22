package com.theucsrocha.repository

import com.theucsrocha.entities.Competencia
import com.theucsrocha.entities.Vaga

interface VagaRepository {
    void inserir(Vaga vaga)
    void adicionarCompetenciasNaVaga(Integer vagaId, List<String> competencias)
    int contarVagas()
    List<Vaga> getAllVagas()
    List<Competencia> getCompetenciasDaVagaPorId(Integer id)
}
