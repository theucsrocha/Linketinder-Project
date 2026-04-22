package com.theucsrocha.repository

import com.theucsrocha.entities.Candidato
import com.theucsrocha.entities.Competencia

interface CandidatoRepository {
    void inserir(Candidato candidato)
    void remover(Candidato candidato)
    List<Candidato> findAll()
    Candidato findByCPF(String cpf)
    void adicionarCompetenciasNoCandidato(String cpf, List<String> competencias)
    List<Competencia> getCompetenciasDoCandidatoPorCpf(String cpf)
}
