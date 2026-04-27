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

    void close(){
        connectionDB.close()
    }






}
