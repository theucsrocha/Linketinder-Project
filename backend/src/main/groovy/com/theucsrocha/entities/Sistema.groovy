package com.theucsrocha.entities
import com.theucsrocha.service.CandidatoService
import com.theucsrocha.service.EmpresaService
import com.theucsrocha.service.VagaService
import groovy.sql.Sql

class Sistema {

    private final Sql connectionDB
    CandidatoService candidatoService
    EmpresaService empresaService
    VagaService vagaService

    Sistema(Sql connection){
        this.connectionDB = connection
        candidatoService = new CandidatoService(connection)
        empresaService = new EmpresaService(connection)
        vagaService = new VagaService(connection)
    }

    boolean verificadorDeCompatibilidade(Empresa empresa,Candidato candidato){
        def competenciasDoCandidato = candidato.competencias*.nome ?: []
        def competenciasDaVaga = vagaService.listarVagas()
                .findAll { it.empresa?.cnpj == empresa.cnpj }
                .collectMany { it.competenciasExigidas*.nome ?: [] }
                .unique()
        return competenciasDoCandidato.containsAll(competenciasDaVaga)
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
        candidatoService.adicionarCandidato(novoCandidato, competencias)
    }

   void adicionarEmpresa(Empresa novaEmpresa){
      empresaService.adicionarEmpresa(novaEmpresa)
   }

    void adicionarVaga(Vaga vaga,List<String> competenciasExigidas){
        vagaService.adicionarVaga(vaga, competenciasExigidas)
    }

    void listarCandidatos(){
        candidatoService.listarCandidatos().forEach { println(it) }
    }
    void listarEmpresas(){
        empresaService.listarEmpresas().forEach { println(it) }
    }
    void listarVagas(){
        vagaService.listarVagas().forEach { println(it) }
    }
    void close(){
        connectionDB.close()
    }

    List<Empresa> getAllEmpresas(){
        return empresaService.getAllEmpresas()
    }
    List<Candidato> getAllCandidatos(){
        return candidatoService.listarCandidatos()
    }
    List<Vaga> getAllVagas(){
        return vagaService.listarVagas()
    }

    Empresa getEmpresaByCNPJ(String cnpj){
        return empresaService.getEmpresaByCNPJ(cnpj)
    }

    Candidato getCandidatoByCPF(String cpf){
        return candidatoService.getCandidatoByCPF(cpf)
    }

}
