package com.theucsrocha

import com.theucsrocha.entities.Candidato
import com.theucsrocha.entities.Empresa
import com.theucsrocha.entities.Sistema
import com.theucsrocha.entities.Vaga
import com.theucsrocha.repository.CandidatoRepository
import com.theucsrocha.repository.EmpresaRepository
import com.theucsrocha.repository.VagaRepository
import com.theucsrocha.service.CandidatoService
import com.theucsrocha.service.EmpresaService
import com.theucsrocha.service.VagaService
import com.theucsrocha.util.ConnectionFactory

import com.theucsrocha.util.PostgresConnectionFactory
import com.theucsrocha.validator.CompetenciaValidator
import groovy.sql.Sql
import spock.lang.Specification

import java.time.LocalDate

class TestesApp extends Specification {

    def "factory retorna implementacao postgres quando banco informado for postgres"() {
        when:
        def connectionFactory = ConnectionFactory.create("postgres")

        then:
        connectionFactory instanceof PostgresConnectionFactory
    }

    def "factory retorna implementacao mysql quando banco informado for mysql"() {
        when:
        def connectionFactory = ConnectionFactory.create("mysql")

        then:
        connectionFactory instanceof MySqlConnectionFactory
    }

    def "factory falha quando banco informado nao for suportado"() {
        when:
        ConnectionFactory.create("oracle")

        then:
        def erro = thrown(IllegalArgumentException)
        erro.message == "Banco não suportado: oracle"
    }

    def "adicionar candidato delega persistencia e competencias ao dao"() {
        given:
        def sistema = new Sistema(Stub(Sql), Mock(CandidatoService), Mock(EmpresaService), Mock(VagaService))
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
        def sistema = new Sistema(Stub(Sql), Mock(CandidatoService), Mock(EmpresaService), Mock(VagaService))
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
        def sistema = new Sistema(Stub(Sql), Mock(CandidatoService), Mock(EmpresaService), Mock(VagaService))
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
        def sistema = new Sistema(Stub(Sql), Mock(CandidatoService), Mock(EmpresaService), Mock(VagaService))
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
        def sistema = new Sistema(Stub(Sql), Mock(CandidatoService), Mock(EmpresaService), Mock(VagaService))
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
        def sistema = new Sistema(sql, Mock(CandidatoService), Mock(EmpresaService), Mock(VagaService))

        when:
        sistema.close()

        then:
        1 * sql.close()
    }

    def "adicionar candidato falha quando uma competencia nao existe"() {
        given:
        def candidatoRepository = Mock(CandidatoRepository)
        def competenciaValidator = Mock(CompetenciaValidator)
        def service = new CandidatoService(candidatoRepository, competenciaValidator)
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
        service.adicionarCandidato(candidato, ["Java"])

        then:
        1 * competenciaValidator.validarCompetenciasExistentes(["Java"]) >> {
            throw new IllegalArgumentException("Competência não encontrada: Java")
        }
        0 * candidatoRepository._
        def erro = thrown(IllegalArgumentException)
        erro.message == "Competência não encontrada: Java"
    }

    def "adicionar vaga falha quando uma competencia nao existe"() {
        given:
        def vagaRepository = Mock(VagaRepository)
        def competenciaValidator = Mock(CompetenciaValidator)
        def service = new VagaService(vagaRepository, competenciaValidator)
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

        when:
        service.adicionarVaga(vaga, ["Groovy"])

        then:
        1 * competenciaValidator.validarCompetenciasExistentes(["Groovy"]) >> {
            throw new IllegalArgumentException("Competência não encontrada: Groovy")
        }
        0 * vagaRepository._
        def erro = thrown(IllegalArgumentException)
        erro.message == "Competência não encontrada: Groovy"
    }

    def "adicionar candidato usa repositorio e validador injetados"() {
        given:
        def candidatoRepository = Mock(CandidatoRepository)
        def competenciaValidator = Mock(CompetenciaValidator)
        def service = new CandidatoService(candidatoRepository, competenciaValidator)
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
        service.adicionarCandidato(candidato, ["Java"])

        then:
        1 * competenciaValidator.validarCompetenciasExistentes(["Java"])
        1 * candidatoRepository.inserir(candidato)
        1 * candidatoRepository.adicionarCompetenciasNoCandidato("12345678900", ["Java"])
    }

    def "adicionar empresa usa repositorio injetado"() {
        given:
        def empresaRepository = Mock(EmpresaRepository)
        def service = new EmpresaService(empresaRepository)
        def empresa = new Empresa(
                nome: "PastelSoft",
                email: "rh@pastelsoft.com",
                cnpj: "12345678000199",
                descricao: "Software house",
                cep: "40000000",
                senha: "123456"
        )

        when:
        service.adicionarEmpresa(empresa)

        then:
        1 * empresaRepository.inserir(empresa)
    }

    def "adicionar vaga usa repositorio e validador injetados"() {
        given:
        def vagaRepository = Mock(VagaRepository)
        def competenciaValidator = Mock(CompetenciaValidator)
        def service = new VagaService(vagaRepository, competenciaValidator)
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

        when:
        service.adicionarVaga(vaga, ["Groovy"])

        then:
        1 * competenciaValidator.validarCompetenciasExistentes(["Groovy"])
        1 * vagaRepository.inserir(vaga)
        1 * vagaRepository.contarVagas() >> 7
        1 * vagaRepository.adicionarCompetenciasNaVaga(7, ["Groovy"])
    }
}
