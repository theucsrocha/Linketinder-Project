package com.theucsrocha.entities

import java.time.LocalDate

@groovy.transform.TupleConstructor
class Candidato implements IPessoa{
    List<Empresa> empresasCurtidas = []
    List<Competencia> competencias = []
    @Override
    String toString() {
        return """
----------------------------------------
👤 Candidato: $nome
📧 Email: $email
🆔 CPF: $cpf
🎂 Data de Nascimento: $dataNascimento
📮 CEP: $cep
📝 Descrição: $descricaoPessoal
🛠 Competências: ${competencias?.join(", ")}
----------------------------------------
"""
    }

    void curtirEmpresa(Empresa empresa){
        this.empresasCurtidas.add(empresa)
    }

    void adicionarCompetencia(Competencia comp) {
        this.competencias << comp
    }

}
