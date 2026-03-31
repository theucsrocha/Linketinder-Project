package com.theucsrocha.entities
class Vaga{
    Integer id
    String nome
    String descricao
    String local
    List<Competencia> competenciasExigidas = []
    Empresa empresa

    @Override
    String toString() {
        return """
----------------------------------------
💼 Vaga: $nome
📍 Local: $local
📝 Descrição: $descricao
🛠 Competências: ${competenciasExigidas?.join(", ")}
----------------------------------------
"""
    }
}
