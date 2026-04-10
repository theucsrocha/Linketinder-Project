package com.theucsrocha.entities
import com.theucsrocha.dao.CandidatoDao
import com.theucsrocha.dao.CompetenciaDao
import com.theucsrocha.dao.EmpresaDao
import com.theucsrocha.dao.VagaDao
import com.theucsrocha.service.CandidatoService
import com.theucsrocha.service.EmpresaService
import com.theucsrocha.service.VagaService
import com.theucsrocha.util.ConnectionFactory
import com.theucsrocha.validator.CompetenciaValidator

import java.time.LocalDate
import java.time.format.DateTimeParseException

class App {
    static void main(String[] ignoredArgs) {
        Sistema sistema
        def opcaoSelecionada = 0
        def leitorEntrada = System.in.newReader()

        try {
            def sql = ConnectionFactory.create()
            println "Conectado com sucesso ao banco: " + sql.firstRow("SELECT current_database()")[0]
            def competenciaRepository = new CompetenciaDao(sql)
            def competenciaValidator = new CompetenciaValidator(competenciaRepository)
            def candidatoService = new CandidatoService(new CandidatoDao(sql), competenciaValidator)
            def empresaService = new EmpresaService(new EmpresaDao(sql))
            def vagaService = new VagaService(new VagaDao(sql), competenciaValidator)
            sistema = new Sistema(sql, candidatoService, empresaService, vagaService)

        } catch (Exception e) {
            println "Erro ao conectar: " + e.message
            sistema = null
        }

        println("Seja bem vindo ao LinkerTinder!")

        while(opcaoSelecionada != 11){
            println("\n--- Opções ---")
            println("1 - Listar todos os candidatos")
            println("2 - Listar todas as empresas")
            println("3 - Listar todos as compatibilidades")
            println("4 - Realizar curtida como candidato")
            println("5 - Realizar curtida como empresa")
            println("6 - Listar todos os matches <3")
            println("7 - Adicionar novo Candidato")
            println("8 - Adicionar nova Empresa")
            println("9 - Adicionar nova Vaga")
            println("10 - Listar vagas")
            println("11 - Sair")
            print("Digite sua opção:")

            def opcaoInformada = leitorEntrada.readLine()
            if (!opcaoInformada?.isInteger()) {
                println("Opção inválida! Digite um número.")
                continue
            }
            opcaoSelecionada = opcaoInformada.toInteger()

            switch (opcaoSelecionada){
                case 1:
                   sistema.listarCandidatos()
                    break

                case 2:
                    sistema.listarEmpresas()
                    break

                case 3:
                    sistema.getAllEmpresas().each { empresa ->
                        println("Candidatos compativeis com a empresa ${empresa.nome}:")
                        sistema.getAllCandidatos().each { candidato ->
                            if (sistema.verificadorDeCompatibilidade(empresa, candidato)) {
                                println(candidato.getNome())
                            }
                        }
                    }
                    break

                case 4:
                    println("Digite o cpf do Canditato:")
                    def cpfCandidato = leitorEntrada.readLine()
                    Candidato candidato = sistema.getCandidatoByCPF(cpfCandidato)
                    if (candidato == null) { println("Candidato não encontrado"); break }

                    println("Digite o cnpj da empresa:")
                    def cnpjEmpresa = leitorEntrada.readLine()
                    Empresa empresa = sistema.getEmpresaByCNPJ(cnpjEmpresa)
                    if (empresa == null) { println("Empresa não encontrada"); break }

                    candidato.curtirEmpresa(empresa)
                    println("Curtida registrada!")
                    break

                case 5:
                    println("Digite o cnpj da Empresa:")
                    def cnpjEmpresa = leitorEntrada.readLine()
                    Empresa empresa = sistema.getEmpresaByCNPJ(cnpjEmpresa)
                    if (empresa == null) { println("Empresa não encontrada"); break }

                    println("Digite o cpf do Candidato:")
                    def cpfCandidato = leitorEntrada.readLine()
                    Candidato candidato = sistema.getCandidatoByCPF(cpfCandidato)
                    if (candidato == null) { println("Candidato não encontrado"); break }

                    empresa.curtirCandidato(candidato)
                    println("Curtida registrada!")
                    break

                case 6:
                    sistema.listarMatches(sistema.getAllEmpresas(),sistema.getAllCandidatos())
                    break

                case 7:
                    println("\n--- Cadastro de Candidato ---")
                    print("Nome: "); def nomeCandidato = leitorEntrada.readLine()
                    print("Email: "); def emailCandidato = leitorEntrada.readLine()
                    print("CPF: "); def cpfCandidato = leitorEntrada.readLine()
                    print("Data de Nascimento (yyyy-MM-dd): "); def dataNascimentoTexto = leitorEntrada.readLine()
                    print("CEP: "); def cepCandidato = leitorEntrada.readLine()
                    print("Descrição pessoal: "); def descricaoPessoal = leitorEntrada.readLine()
                    print("Senha: "); def senhaCandidato = leitorEntrada.readLine()
                    print("Competências (separe por vírgula): ")
                    def competenciasCandidato = leitorEntrada.readLine().split(",").collect { it.trim() }.findAll { !it.isBlank() }

                    try {
                        def dataNascimento = LocalDate.parse(dataNascimentoTexto)
                        def novoCandidato = new Candidato(
                                nome: nomeCandidato,
                                email: emailCandidato,
                                cpf: cpfCandidato,
                                dataNascimento: dataNascimento,
                                cep: cepCandidato,
                                descricaoPessoal: descricaoPessoal,
                                senha: senhaCandidato
                        )
                        sistema.adicionarCandidato(novoCandidato, competenciasCandidato)
                        println("Candidato adicionado com sucesso!")
                    } catch (DateTimeParseException ex) {
                        println("Data inválida. Use o formato yyyy-MM-dd.")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 8:
                    println("\n--- Cadastro de Empresa ---")
                    print("Nome: "); def nomeEmpresa = leitorEntrada.readLine()
                    print("Email: "); def emailEmpresa = leitorEntrada.readLine()
                    print("CNPJ: "); def cnpjEmpresa = leitorEntrada.readLine()
                    print("Descrição: "); def descricaoEmpresa = leitorEntrada.readLine()
                    print("CEP: "); def cepEmpresa = leitorEntrada.readLine()
                    print("Senha: "); def senhaEmpresa = leitorEntrada.readLine()

                    try {
                        def novaEmpresa = new Empresa(
                                nome: nomeEmpresa,
                                email: emailEmpresa,
                                cnpj: cnpjEmpresa,
                                descricao: descricaoEmpresa,
                                cep: cepEmpresa,
                                senha: senhaEmpresa
                        )
                        sistema.adicionarEmpresa(novaEmpresa)
                        println("Empresa adicionada com sucesso!")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 9:
                    println("\n--- Cadastro de Vaga ---")
                    print("Nome: "); def nomeVaga = leitorEntrada.readLine()
                    print("Descrição: "); def descricaoVaga = leitorEntrada.readLine()
                    print("Local: "); def localVaga = leitorEntrada.readLine()
                    print("CNPJ da empresa: "); def cnpjVaga = leitorEntrada.readLine()
                    print("Competências exigidas (separe por vírgula): ")
                    def competenciasVaga = leitorEntrada.readLine().split(",").collect { it.trim() }.findAll { !it.isBlank() }

                    Empresa empresaDaVaga = sistema.getEmpresaByCNPJ(cnpjVaga)
                    if (empresaDaVaga == null) {
                        println("Empresa não encontrada")
                        break
                    }

                    try {
                        def vagaNova = new Vaga(
                                nome: nomeVaga,
                                descricao: descricaoVaga,
                                local: localVaga,
                                empresa: empresaDaVaga
                        )
                        sistema.adicionarVaga(vagaNova, competenciasVaga)
                        println("Vaga adicionada com sucesso!")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 10:
                    sistema.listarVagas()
                    break

                case 11:
                    println("Saindo...")
                    sistema.close()
                    break

                default:
                    println("Opcao invalida")
                    break
            }
        }

    }

}
