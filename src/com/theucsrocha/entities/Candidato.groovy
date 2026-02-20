package com.theucsrocha.entities

@groovy.transform.TupleConstructor
class Candidato implements IPessoa{
    List<String> competencias
    @Override
    String toString() {
        return """
----------------------------------------
👤 Candidato: $nome
📧 Email: $email
🆔 CPF: $cpf
🎂 Idade: $idade
📍 Estado: $estado
📮 CEP: $cep
📝 Descrição: $descricaoPessoal
🛠 Competências: ${competencias?.join(", ")}
----------------------------------------
"""
    }

}
