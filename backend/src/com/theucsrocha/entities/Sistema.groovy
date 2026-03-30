package com.theucsrocha.entities
import com.theucsrocha.dao.CandidatoDao
import com.theucsrocha.dao.EmpresaDao
import groovy.sql.Sql

class Sistema {

    private Sql connectionDB
    CandidatoDao candidatoDao
    EmpresaDao empresaDao

    Sistema(Sql connection){
        this.connectionDB = connection
        candidatoDao = new CandidatoDao(connection)
        empresaDao = new EmpresaDao(connection)

    }

    boolean verificadorDeCompatibilidade(Empresa empresa,Candidato candidato){

        candidato.getCompetencias().containsAll(empresa.getExigencias())
    }

    void listarMatches(List<Empresa> empresas,List<Candidato> candidatoes){
        println("Matches:")
        empresas.each { empresa->
            candidatoes.each {if (it.getEmpresasCurtidas().contains(empresa) && empresa.candidatoesCurtidos.contains(it)){
                println("${empresa.getNome()} <3 ${it.getNome()}")
            }}


        }
    }

    void adicionarCandidato(Candidato novoCandidato){
        candidatoDao.inserir(novoCandidato)
    }

   void adicionarEmpresa(Empresa novaEmpresa){
      empresaDao.inserir(novaEmpresa)
   }

    void listarCandidatos(){
        candidatoDao.findAll().forEach {println(it)}
    }
    void listarEmpresas(){
        empresaDao.findAll().forEach {println(it)}
    }
    void close(){
        connectionDB.close()
    }

}
