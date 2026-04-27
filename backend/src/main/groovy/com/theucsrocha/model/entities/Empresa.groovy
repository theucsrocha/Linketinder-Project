package com.theucsrocha.model.entities
@groovy.transform.TupleConstructor
class Empresa {
    String nome
    String email
    String cnpj
    String descricao
    String cep
    String senha
    List<Candidato> candidatoesCurtidos = []

    @Override
    String toString() {
        return """
----------------------------------------
🏢 Empresa: $nome
📧 Email: $email
🆔 CNPJ: $cnpj
📮 CEP: $cep
📝 Descrição: $descricao
----------------------------------------
"""
    }

    void curtirCandidato(Candidato candidato){
        this.candidatoesCurtidos.add(candidato)
    }
}
