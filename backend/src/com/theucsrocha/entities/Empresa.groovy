package com.theucsrocha.entities

@groovy.transform.TupleConstructor
class Empresa {
    String nome
    String email
    String cnpj
    String pais
    String estado
    String descricao
    String cep
    List<String> exigencias;
    List<Candidato> candidatoesCurtidos = []

    @Override
    String toString() {
        return """
----------------------------------------
🏢 Empresa: $nome
📧 Email: $email
🆔 CNPJ: $cnpj
🌎 País: $pais
📍 Estado: $estado
📮 CEP: $cep
📝 Descrição: $descricao
💼 Exigências: ${exigencias.join(", ")}
----------------------------------------
"""
    }

    void curtirCandidato(Candidato candidato){
        this.candidatoesCurtidos.add(candidato)
    }
}
