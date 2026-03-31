package com.theucsrocha.entities
import com.theucsrocha.dao.CandidatoDao
import com.theucsrocha.dao.CompetenciaDao
import com.theucsrocha.dao.EmpresaDao
import com.theucsrocha.dao.VagaDao
import groovy.sql.Sql

class Sistema {

    private Sql connectionDB
    CandidatoDao candidatoDao
    EmpresaDao empresaDao
    VagaDao vagaDao
    CompetenciaDao competenciaDao

    Sistema(Sql connection){
        this.connectionDB = connection
        candidatoDao = new CandidatoDao(connection)
        empresaDao = new EmpresaDao(connection)
        vagaDao = new VagaDao(connection)
        competenciaDao = new CompetenciaDao(connection)

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

    void adicionarCandidato(Candidato novoCandidato,List<String> competencias){
        candidatoDao.inserir(novoCandidato)
        candidatoDao.adicionarCompetenciasNoCandidato(novoCandidato.cpf,competencias)
    }

   void adicionarEmpresa(Empresa novaEmpresa){
      empresaDao.inserir(novaEmpresa)
   }

    void adicionarVaga(Vaga vaga,List<String> competenciasExigidas){
        vagaDao.inserir(vaga)
        int idVagaNova = vagaDao.contarVagas()
        vagaDao.adicionarCompetenciasNaVaga(idVagaNova,competenciasExigidas)


    }

    void listarCandidatos(){
        candidatoDao.findAll().forEach {
            it.competencias = candidatoDao.getCompetenciasDoCandidatoPorCpf(it.cpf)
            println(it)}
    }
    void listarEmpresas(){
        empresaDao.findAll().forEach {println(it)}
    }
    void listarVagas(){
        vagaDao.getAllVagas().forEach {
            it.competenciasExigidas = vagaDao.getCompetenciasDaVagaPorId(it.id)
            println(it) }
    }
    void close(){
        connectionDB.close()
    }

    List<Empresa> getAllEmpresas(){
        return empresaDao.findAll()
    }
    List<Candidato> getAllCandidatos(){
        return candidatoDao.findAll()
    }
    List<Vaga> getAllVagas(){
        return vagaDao.getAllVagas()
    }

    Empresa getEmpresaByCNPJ(String cnpj){
        return empresaDao.findByCNPJ(cnpj)
    }

    Candidato getCandidatoByCPF(String cpf){
        return candidatoDao.findByCPF(cpf)
    }

}
