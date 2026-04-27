package com.theucsrocha.model.repository

import com.theucsrocha.model.entities.Candidato
import com.theucsrocha.model.entities.Competencia

interface CandidatoRepository {
    void inserir(Candidato candidato)
    void remover(Candidato candidato)
    List<Candidato> findAll()
    Candidato findByCPF(String cpf)
    void adicionarCompetenciasNoCandidato(String cpf, List<String> competencias)
    List<Competencia> getCompetenciasDoCandidatoPorCpf(String cpf)
}
