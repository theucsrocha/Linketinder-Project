package com.theucsrocha.model.entities
import java.time.LocalDate

trait IPessoa {
    String nome
    String email
    String cpf
    LocalDate dataNascimento
    String cep
    String descricaoPessoal
    String senha
}
