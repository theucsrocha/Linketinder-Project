package com.theucsrocha.model.dao

import com.theucsrocha.model.entities.Candidato
import com.theucsrocha.entities.Competencia
import com.theucsrocha.repository.CandidatoRepository
import groovy.sql.Sql

class CandidatoDao implements CandidatoRepository {
    private Sql db
    private CompetenciaDao competenciaDao

    CandidatoDao(Sql connection){
        this.competenciaDao  = new CompetenciaDao(connection)
        this.db = connection

    }

    void inserir(Candidato candidato){
        String query = "INSERT INTO CANDIDATO(CPF,NOME,DATA_NASCIMENTO,CEP,SENHA,DESCRICAO,EMAIL) VALUES (?,?,?,?,?,?,?)"
        db.executeInsert(query,[candidato.cpf,candidato.nome,candidato.dataNascimento,candidato.cep,candidato.senha,candidato.descricaoPessoal,candidato.email])
    }

    void remover(Candidato candidato){
        String query = "DELETE FROM CANDIDATO C WHERE C.CPF = ? "
        db.execute(query,candidato.cpf)
    }

    List<Candidato> findAll(){
        List<Candidato> candidatos = []
        String query = "SELECT * FROM CANDIDATO"
        db.eachRow(query) { row ->

            candidatos.add(new Candidato(
                    nome: row.nome,
                    email: row.email,
                    cpf: row.cpf,
                    dataNascimento: row.data_nascimento.toLocalDate(),
                    cep: row.cep,
                    descricaoPessoal: row.descricao,
                    senha: row.senha
            ))
        }
        return candidatos
    }

    Candidato findByCPF(String cpf){
        Candidato candidato
        String query = "SELECT * FROM CANDIDATO C WHERE C.CPF=?"
        db.eachRow(query,[cpf],{row->
            candidato = new Candidato(
                    nome: row.nome,
                    email: row.email,
                    cpf: row.cpf,
                    dataNascimento: row.datanascimento,
                    cep: row.cep,
                    descricaoPessoal: row.descricaopessoal,
                    senha: row.senha
            )

        })
        return candidato

    }

    void adicionarCompetenciasNoCandidato(String cpf,List<String> competencias){
        String query = "INSERT INTO COMPETENCIA_CANDIDATO (ID_COMPETENCIA,CPF_CANDIDATO) VALUES (?,?)"

        competencias.forEach {competencia ->
            db.executeInsert(query,[competenciaDao.findByNome(competencia).id,cpf])
        }
    }

    List<Competencia> getCompetenciasDoCandidatoPorCpf(String cpf){
        String query = "SELECT C.NOME FROM COMPETENCIA_CANDIDATO CD INNER JOIN COMPETENCIA C ON CD.ID_COMPETENCIA = C.ID_COMPETENCIA WHERE CD.CPF_CANDIDATO = ?"
        List<Competencia> competencias = []
        db.eachRow(query,[cpf],{competencias.add(new Competencia(it.nome))})
        return competencias
    }

}
