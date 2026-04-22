package com.theucsrocha.repository

import com.theucsrocha.entities.Competencia

interface CompetenciaRepository {
    void inserir(Competencia competencia)
    Competencia findByNome(String nome)
}
