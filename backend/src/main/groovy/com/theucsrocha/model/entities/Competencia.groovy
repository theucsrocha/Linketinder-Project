package com.theucsrocha.model.entities
class Competencia{
    Integer id
    String nome

    Competencia(Integer id,String nome){
        this.id = id
        this.nome = nome
    }
    Competencia(String nome){
        this.nome = nome
    }

    boolean ehIgual(Competencia outra) {
        return this.nome.equalsIgnoreCase(outra.nome)
    }
    String toString() {
        return this.nome
    }
}
