package com.theucsrocha.entities

class Sistema {

    static boolean verificadorDeCompatibilidade(Empresa empresa,Candidato candidato){

        candidato.getCompetencias().containsAll(empresa.getExigencias())
    }

    static void listarMatches(List<Empresa> empresas,List<Candidato> candidatoes){
        println("Matches:")
        empresas.each { empresa->
            candidatoes.each {if (it.getEmpresasCurtidas().contains(empresa) && empresa.candidatoesCurtidos.contains(it)){
                println("${empresa.getNome()} <3 ${it.getNome()}")
            }}


        }
    }

}
