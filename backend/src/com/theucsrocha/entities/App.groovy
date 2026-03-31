package com.theucsrocha.entities
import com.theucsrocha.util.ConnectionFactory

import java.time.LocalDate
import java.time.format.DateTimeParseException

class App {
    static void main(String[] args) {
        Sistema sistema
        def opcao = 0
         def leitor = System.in.newReader()

        try {
            def sql = ConnectionFactory.create()
            println "Conectado com sucesso ao banco: " + sql.firstRow("SELECT current_database()")[0]
            sistema = new Sistema(sql)

        } catch (Exception e) {
            println "Erro ao conectar: " + e.message
            sistema = null
        }

        println("Seja bem vindo ao LinkerTinder!")

        while(opcao != 11){
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

            def entrada = leitor.readLine()
            if (!entrada?.isInteger()) {
                println("Opção inválida! Digite um número.")
                continue
            }
            opcao = entrada.toInteger()

            switch (opcao){
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
                    def cpf = leitor.readLine()
                    Candidato candidato = sistema.getCandidatoByCPF(cpf)
                    if (candidato == null) { println("Candidato não encontrado"); break }

                    println("Digite o cnpj da empresa:")
                    def cnpj = leitor.readLine()
                    Empresa empresa = sistema.getEmpresaByCNPJ(cnpj)
                    if (empresa == null) { println("Empresa não encontrada"); break }

                    candidato.curtirEmpresa(empresa)
                    println("Curtida registrada!")
                    break

                case 5:
                    println("Digite o cnpj da Empresa:")
                    def cnpjEmp = leitor.readLine()
                    Empresa empresaEmp = sistema.getEmpresaByCNPJ(cnpjEmp)
                    if (empresaEmp == null) { println("Empresa não encontrada"); break }

                    println("Digite o cpf do Candidato:")
                    def cpfCand = leitor.readLine()
                    Candidato candidatoCand = sistema.getCandidatoByCPF(cpfCand)
                    if (candidatoCand == null) { println("Candidato não encontrado"); break }

                    empresaEmp.curtirCandidato(candidatoCand)
                    println("Curtida registrada!")
                    break

                case 6:
                    sistema.listarMatches(sistema.getAllEmpresas(),sistema.getAllCandidatos())
                    break

                case 7:
                    println("\n--- Cadastro de Candidato ---")
                    print("Nome: "); def n = leitor.readLine()
                    print("Email: "); def e = leitor.readLine()
                    print("CPF: "); def c = leitor.readLine()
                    print("Data de Nascimento (yyyy-MM-dd): "); def dataNascimentoTexto = leitor.readLine()
                    print("CEP: "); def cep = leitor.readLine()
                    print("Descrição pessoal: "); def descricao = leitor.readLine()
                    print("Senha: "); def senha = leitor.readLine()
                    print("Competências (separe por vírgula): ")
                    def comps = leitor.readLine().split(",").collect { it.trim() }.findAll { !it.isBlank() }

                    try {
                        def dataNascimento = LocalDate.parse(dataNascimentoTexto)
                        def novoC = new Candidato(
                                nome: n,
                                email: e,
                                cpf: c,
                                dataNascimento: dataNascimento,
                                cep: cep,
                                descricaoPessoal: descricao,
                                senha: senha
                        )
                        sistema.adicionarCandidato(novoC,comps)
                        println("Candidato adicionado com sucesso!")
                    } catch (DateTimeParseException ex) {
                        println("Data inválida. Use o formato yyyy-MM-dd.")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 8:
                    println("\n--- Cadastro de Empresa ---")
                    print("Nome: "); def ne = leitor.readLine()
                    print("Email: "); def ee = leitor.readLine()
                    print("CNPJ: "); def cj = leitor.readLine()
                    print("Descrição: "); def desc = leitor.readLine()
                    print("CEP: "); def cepEmpresa = leitor.readLine()
                    print("Senha: "); def senhaEmpresa = leitor.readLine()

                    try {
                        def novaE = new Empresa(
                                nome: ne,
                                email: ee,
                                cnpj: cj,
                                descricao: desc,
                                cep: cepEmpresa,
                                senha: senhaEmpresa
                        )
                        sistema.adicionarEmpresa(novaE)
                        println("Empresa adicionada com sucesso!")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 9:
                    println("\n--- Cadastro de Vaga ---")
                    print("Nome: "); def nomeVaga = leitor.readLine()
                    print("Descrição: "); def descricaoVaga = leitor.readLine()
                    print("Local: "); def localVaga = leitor.readLine()
                    print("CNPJ da empresa: "); def cnpjVaga = leitor.readLine()
                    print("Competências exigidas (separe por vírgula): ")
                    def competenciasVaga = leitor.readLine().split(",").collect { it.trim() }.findAll { !it.isBlank() }

                    Empresa empresaDaVaga = sistema.getEmpresaByCNPJ(cnpjVaga)
                    if (empresaDaVaga == null) {
                        println("Empresa não encontrada")
                        break
                    }

                    try {
                        def novaVaga = new Vaga(
                                nome: nomeVaga,
                                descricao: descricaoVaga,
                                local: localVaga,
                                empresa: empresaDaVaga
                        )
                        sistema.adicionarVaga(novaVaga, competenciasVaga)
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
