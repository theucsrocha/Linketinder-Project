package com.theucsrocha.entities

class App {
    static void main(String[] args) {
        Sistema sistema = new Sistema()
        def opcao = 0
        def leitor = System.in.newReader()

        println("Seja bem vindo ao LinkerTinder!")

        while(opcao != 9){
            println("\n--- Opções ---")
            println("1 - Listar todos os candidatos")
            println("2 - Listar todas as empresas")
            println("3 - Listar todos as compatibilidades")
            println("4 - Realizar curtida como candidato")
            println("5 - Realizar curtida como empresa")
            println("6 - Listar todos os matches <3")
            println("7 - Adicionar novo Candidato")
            println("8 - Adicionar nova Empresa")
            println("9 - Sair")
            print("Digite sua opção:")

            def entrada = leitor.readLine()
            if (!entrada?.isInteger()) {
                println("Opção inválida! Digite um número.")
                continue
            }
            opcao = entrada.toInteger()

            switch (opcao){
                case 1:
                    sistema.candidatos.each { println(it) }
                    break

                case 2:
                    sistema.empresas.each { println(it) }
                    break

                case 3:
                    sistema.empresas.each { empresa ->
                        println("Candidatos compativeis com a empresa ${empresa.getNome()}:")
                        sistema.candidatos.each { candidato ->
                            if (sistema.verificadorDeCompatibilidade(empresa, candidato)) {
                                println(candidato.getNome())
                            }
                        }
                    }
                    break

                case 4:
                    println("Digite o cpf do Canditato:")
                    def cpf = leitor.readLine()
                    Candidato candidato = sistema.candidatos.find { it.getCpf() == cpf }
                    if (candidato == null) { println("Candidato não encontrado"); break }

                    println("Digite o cnpj da empresa:")
                    def cnpj = leitor.readLine()
                    Empresa empresa = sistema.empresas.find { it.getCnpj() == cnpj }
                    if (empresa == null) { println("Empresa não encontrada"); break }

                    candidato.curtirEmpresa(empresa)
                    println("Curtida registrada!")
                    break

                case 5:
                    println("Digite o cnpj da Empresa:")
                    def cnpjEmp = leitor.readLine()
                    Empresa empresaEmp = sistema.empresas.find { it.getCnpj() == cnpjEmp }
                    if (empresaEmp == null) { println("Empresa não encontrada"); break }

                    println("Digite o cpf do Candidato:")
                    def cpfCand = leitor.readLine()
                    Candidato candidatoCand = sistema.candidatos.find { it.getCpf() == cpfCand }
                    if (candidatoCand == null) { println("Candidato não encontrado"); break }

                    empresaEmp.curtirCandidato(candidatoCand)
                    println("Curtida registrada!")
                    break

                case 6:
                    sistema.listarMatches(sistema.empresas, sistema.candidatos)
                    break

                case 7:
                    println("\n--- Cadastro de Candidato ---")
                    print("Nome: "); def n = leitor.readLine()
                    print("Email: "); def e = leitor.readLine()
                    print("CPF: "); def c = leitor.readLine()
                    print("Idade: "); def i = leitor.readLine().toInteger()
                    print("Competências (separe por vírgula): ")
                    def comps = leitor.readLine().split(",").collect { it.trim() }

                    try {
                        def novoC = new Candidato(nome: n, email: e, cpf: c, idade: i, competencias: comps)
                        sistema.adicionarCandidato(novoC)
                        println("Candidato adicionado com sucesso!")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 8:
                    println("\n--- Cadastro de Empresa ---")
                    print("Nome: "); def ne = leitor.readLine()
                    print("Email: "); def ee = leitor.readLine()
                    print("CNPJ: "); def cj = leitor.readLine()
                    print("Exigências (separe por vírgula): ")
                    def exig = leitor.readLine().split(",").collect { it.trim() }

                    try {
                        def novaE = new Empresa(nome: ne, email: ee, cnpj: cj, exigencias: exig, pais: "Brasil", estado: "BA", descricao: "Empresa cadastrada via App", cep: "00000-000")
                        sistema.adicionarEmpresa(novaE)
                        println("Empresa adicionada com sucesso!")
                    } catch (IllegalArgumentException ex) {
                        println("Erro ao adicionar: ${ex.message}")
                    }
                    break

                case 9:
                    println("Saindo...")
                    break

                default:
                    println("Opcao invalida")
                    break
            }
        }
    }
}