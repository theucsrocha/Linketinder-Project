package com.theucsrocha.model.repository

import com.theucsrocha.model.entities.Competencia

interface CompetenciaRepository {
    void inserir(Competencia competencia)
    Competencia findByNome(String nome)
}
