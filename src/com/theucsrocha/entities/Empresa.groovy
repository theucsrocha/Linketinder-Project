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
}
