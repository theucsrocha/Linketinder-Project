package com.theucsrocha.model.dao

import com.theucsrocha.model.entities.Competencia
import com.theucsrocha.model.entities.Vaga
import com.theucsrocha.repository.VagaRepository
import groovy.sql.Sql

class VagaDao implements VagaRepository {
    private Sql db
    private CompetenciaDao competenciaDao
    private EmpresaDao empresaDao
   VagaDao(Sql connection){
        this.db = connection
        competenciaDao = new CompetenciaDao(connection)
       empresaDao = new EmpresaDao(connection)
    }

    void inserir(Vaga vaga){
        String query = "INSERT INTO VAGA (NOME,LOCAL_VAGA,CNPJ_EMPRESA,DESCRICAO) VALUES (?,?,?,?)"
        db.executeInsert(query,[vaga.nome,vaga.local,vaga.empresa.cnpj,vaga.descricao])
    }

    void adicionarCompetenciasNaVaga(Integer vagaID,List<String> competencias){
        String query = "INSERT INTO COMPETENCIA_VAGA (ID_VAGA,ID_COMPETENCIA) VALUES (?,?)"

        competencias.forEach {competencia ->
            db.executeInsert(query,[vagaID,competenciaDao.findByNome(competencia).id])
        }
    }
    int contarVagas() {
        String query = "SELECT COUNT(*) as total FROM VAGA"
        def row = db.firstRow(query)
        return row.total as int
    }

    List<Vaga> getAllVagas(){
        List<Vaga> vagas = []
        String query = "SELECT * FROM VAGA"
        db.eachRow(query,{
            vagas.add(new Vaga(
                    id: it.id_vaga,
                    nome: it.nome,
                    local: it.local_vaga,
                    descricao: it.descricao,
                    empresa: empresaDao.findByCNPJ(it.cnpj_empresa)
            ))
        })
        return vagas
    }

    List<Competencia> getCompetenciasDaVagaPorId(Integer id){
        String query = "SELECT C.NOME FROM COMPETENCIA_VAGA CV INNER JOIN COMPETENCIA C ON CV.ID_COMPETENCIA = C.ID_COMPETENCIA WHERE CV.ID_VAGA = ?"
        List<Competencia> competencias = []
        db.eachRow(query,[id],{competencias.add(new Competencia(it.nome))})
        return competencias
    }





}
