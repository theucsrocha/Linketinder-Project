package com.theucsrocha.entities
class Competencia{
    Integer id
    String nome

    boolean ehIgual(Competencia outra) {
        return this.nome.equalsIgnoreCase(outra.nome)
    }
    String toString() {
        return this.nome
    }
}