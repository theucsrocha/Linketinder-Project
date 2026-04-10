package com.theucsrocha

import groovy.sql.Sql
import com.theucsrocha.entities.Candidato
import com.theucsrocha.entities.Empresa
import com.theucsrocha.entities.Sistema
import com.theucsrocha.entities.Vaga
import com.theucsrocha.service.CandidatoService
import com.theucsrocha.service.EmpresaService
import com.theucsrocha.service.VagaService
import spock.lang.Specification

import java.time.LocalDate

class TestesApp extends Specification {

    def "adicionar candidato delega persistencia e competencias ao dao"() {
        given:
        def sistema = new Sistema(Stub(Sql))
        def candidatoService = Mock(CandidatoService)
        sistema.candidatoService = candidatoService
        def candidato = new Candidato(
                nome: "Matheus",
                email: "matheus@email.com",
                cpf: "12345678900",
                dataNascimento: LocalDate.of(2000, 1, 1),
                cep: "40000000",
                descricaoPessoal: "Backend",
                senha: "123456"
        )
        def competencias = ["Java", "Spring"]

        when:
        sistema.adicionarCandidato(candidato, competencias)

        then:
        1 * candidatoService.adicionarCandidato(candidato, competencias)
    }

    def "adicionar empresa delega insercao ao dao"() {
        given:
        def sistema = new Sistema(Stub(Sql))
        def empresaService = Mock(EmpresaService)
        sistema.empresaService = empresaService
        def empresa = new Empresa(
                nome: "PastelSoft",
                email: "rh@pastelsoft.com",
                cnpj: "12345678000199",
                descricao: "Software house",
                cep: "40000000",
                senha: "123456"
        )

        when:
        sistema.adicionarEmpresa(empresa)

        then:
        1 * empresaService.adicionarEmpresa(empresa)
    }

    def "adicionar vaga usa o id retornado pela contagem do dao"() {
        given:
        def sistema = new Sistema(Stub(Sql))
        def vagaService = Mock(VagaService)
        sistema.vagaService = vagaService
        def empresa = new Empresa(
                nome: "PastelSoft",
                email: "rh@pastelsoft.com",
                cnpj: "12345678000199",
                descricao: "Software house",
                cep: "40000000",
                senha: "123456"
        )
        def vaga = new Vaga(
                nome: "Pessoa Desenvolvedora Groovy",
                descricao: "Atuar no backend",
                local: "Remoto",
                empresa: empresa
        )
        def competencias = ["Groovy", "PostgreSQL"]

        when:
        sistema.adicionarVaga(vaga, competencias)

        then:
        1 * vagaService.adicionarVaga(vaga, competencias)
    }

    def "buscar empresa por cnpj delega ao dao"() {
        given:
        def sistema = new Sistema(Stub(Sql))
        def empresaService = Mock(EmpresaService)
        sistema.empresaService = empresaService
        def empresa = new Empresa(
                nome: "PastelSoft",
                email: "rh@pastelsoft.com",
                cnpj: "12345678000199",
                descricao: "Software house",
                cep: "40000000",
                senha: "123456"
        )

        when:
        def resultado = sistema.getEmpresaByCNPJ("12345678000199")

        then:
        1 * empresaService.getEmpresaByCNPJ("12345678000199") >> empresa
        resultado == empresa
    }

    def "buscar candidato por cpf delega ao dao"() {
        given:
        def sistema = new Sistema(Stub(Sql))
        def candidatoService = Mock(CandidatoService)
        sistema.candidatoService = candidatoService
        def candidato = new Candidato(
                nome: "Matheus",
                email: "matheus@email.com",
                cpf: "12345678900",
                dataNascimento: LocalDate.of(2000, 1, 1),
                cep: "40000000",
                descricaoPessoal: "Backend",
                senha: "123456"
        )

        when:
        def resultado = sistema.getCandidatoByCPF("12345678900")

        then:
        1 * candidatoService.getCandidatoByCPF("12345678900") >> candidato
        resultado == candidato
    }

    def "close encerra a conexao com o banco"() {
        given:
        def sql = Mock(Sql)
        def sistema = new Sistema(sql)

        when:
        sistema.close()

        then:
        1 * sql.close()
    }
}
