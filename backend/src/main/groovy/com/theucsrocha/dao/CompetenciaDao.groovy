package com.theucsrocha.dao

import com.theucsrocha.entities.Competencia
import groovy.sql.Sql

class CompetenciaDao {
    private Sql db

    CompetenciaDao(Sql connection) {
        this.db = connection
    }

    void inserir(Competencia competencia) {
        String query = "INSERT INTO COMPETENCIA (NOME) VALUES (?)"
        db.executeInsert(query, [competencia.nome])
    }

    Competencia findByNome(String nome) {
        String query = "SELECT * FROM COMPETENCIA C WHERE C.NOME = ?"
        Competencia competencia
        db.eachRow(query, [nome], { competencia = new Competencia(it.id_competencia, it.nome) })
        if (competencia == null) {
            return null
        }
        return competencia
    }
}
