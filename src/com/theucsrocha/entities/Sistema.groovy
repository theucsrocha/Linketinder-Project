package com.theucsrocha.entities

class Sistema {

    static boolean verificadorDeCompatibilidade(Empresa empresa,Candidato candidato){

        candidato.getCompetencias().containsAll(empresa.getExigencias())
    }

}
