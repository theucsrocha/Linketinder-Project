package com.theucsrocha.controller

import com.theucsrocha.model.entities.Candidato
import com.theucsrocha.model.entities.Empresa
import com.theucsrocha.model.entities.Vaga
import groovy.sql.Sql
import com.theucsrocha.model.service.CandidatoService
import com.theucsrocha.model.service.EmpresaService
import com.theucsrocha.model.service.VagaService

class Sistema {

    private final Sql connectionDB
    CandidatoService candidatoService
    EmpresaService empresaService
    VagaService vagaService

    Sistema(Sql connection, CandidatoService candidatoService, EmpresaService empresaService, VagaService vagaService){
        this.connectionDB = connection
        this.candidatoService = candidatoService
        this.empresaService = empresaService
        this.vagaService = vagaService
    }

    boolean verificadorDeCompatibilidade(Empresa empresa, Candidato candidato){
        def competenciasDoCandidato = candidato.competencias*.nome ?: []
        def competenciasDaVaga = vagaService.listarVagas()
                .findAll { it.empresa?.cnpj == empresa.cnpj }
                .collectMany { it.competenciasExigidas*.nome ?: [] }
                .unique()
        return competenciasDoCandidato.containsAll(competenciasDaVaga)
    }

    void listarMatches(List<Empresa> empresas,List<Candidato> candidatos){
        println("Matches:")
        empresas.each { empresa->
            candidatos.each {if (it.getEmpresasCurtidas().contains(empresa) && empresa.candidatoesCurtidos.contains(it)){
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

    void adicionarVaga(Vaga vaga, List<String> competenciasExigidas){
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


    Empresa getEmpresaByCNPJ(String cnpj){
        return empresaService.getEmpresaByCNPJ(cnpj)
    }

    Candidato getCandidatoByCPF(String cpf){
        return candidatoService.getCandidatoByCPF(cpf)
    }

}
