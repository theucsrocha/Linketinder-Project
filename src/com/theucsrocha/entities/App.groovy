package com.theucsrocha.entities

class App {
    static void main(String[] args) {
        def empresas = [
                new Empresa(
                        "Tech Solutions",
                        "contato@techsolutions.com",
                        "12.345.678/0001-90",
                        "Brasil",
                        "SP",
                        "Empresa focada em backend Java.",
                        "01000-000",
                        ["Java", "Spring"]
                ),
                new Empresa(
                        "DataCorp",
                        "rh@datacorp.com",
                        "22.333.444/0001-55",
                        "Brasil",
                        "MG",
                        "Empresa especializada em dados.",
                        "30000-000",
                        ["Python", "SQL"]
                ),
                new Empresa(
                        "CloudSys",
                        "jobs@cloudsys.com",
                        "55.666.777/0001-88",
                        "Brasil",
                        "RS",
                        "Infraestrutura cloud.",
                        "90000-000",
                        ["Docker", "AWS"]
                ),
                new Empresa(
                        "FrontDev",
                        "talentos@frontdev.com",
                        "66.777.888/0001-99",
                        "Brasil",
                        "RJ",
                        "Frontend moderno.",
                        "20000-000",
                        ["Angular", "TypeScript"]
                ),
                new Empresa(
                        "FullStack Solutions",
                        "carreiras@fullstack.com",
                        "99.888.777/0001-22",
                        "Brasil",
                        "BA",
                        "Projetos fullstack.",
                        "40000-000",
                        ["Java", "Angular", "Spring"]
                )
        ]

        def candidatos = [
                new Candidato(
                        nome: "Matheus Rocha",
                        email: "matheus@email.com",
                        cpf: "123.456.789-00",
                        idade: 20,
                        estado: "BA",
                        cep: "40000-000",
                        descricaoPessoal: "Backend developer.",
                        competencias: ["Java", "Spring", "SQL"]
                ),
                new Candidato(
                        nome: "Ana Souza",
                        email: "ana@email.com",
                        cpf: "987.654.321-00",
                        idade: 25,
                        estado: "SP",
                        cep: "01000-000",
                        descricaoPessoal: "Especialista frontend.",
                        competencias: ["Angular", "TypeScript", "HTML"]
                ),
                new Candidato(
                        nome: "Carlos Lima",
                        email: "carlos@email.com",
                        cpf: "111.222.333-44",
                        idade: 30,
                        estado: "MG",
                        cep: "30000-000",
                        descricaoPessoal: "Engenheiro de dados.",
                        competencias: ["Python", "SQL", "Machine Learning"]
                ),
                new Candidato(
                        nome: "Fernanda Alves",
                        email: "fernanda@email.com",
                        cpf: "555.666.777-88",
                        idade: 28,
                        estado: "RS",
                        cep: "90000-000",
                        descricaoPessoal: "Cloud engineer.",
                        competencias: ["Docker", "AWS", "Kubernetes"]
                ),
                new Candidato(
                        nome: "Lucas Mendes",
                        email: "lucas@email.com",
                        cpf: "999.888.777-66",
                        idade: 22,
                        estado: "BA",
                        cep: "40000-000",
                        descricaoPessoal: "Fullstack developer.",
                        competencias: ["Java", "Angular", "Spring", "SQL"]
                )
        ]
        def opcao = 0
        println("Seja bem vindo ao LinkerTinder!")

        while(opcao!=4){
        println("Opções:")
        println("1 - Listar todos os candidatos")
        println("2 - Listar todas as empresas")
        println("3 - Listar todos os Matches <3")
        println("4 - Sair")
        print("Digite sua opção:")
        opcao = System.in.newReader().readLine().toInteger()

        switch (opcao){
            case 1: {
                candidatos.each { println(it) }
                break
            }
            case 2:{
                empresas.each {println(it)}
                break
            }
            case 3:{
                empresas.each {
                    println("Candidatos compativeis com a empresa ${it.getNome()}:")
                    for(Candidato c in candidatos) {
                        if (Sistema.verificadorDeCompatibilidade(it,c)){
                            println(c.getNome())
                        }
                    }
                }
                break
            }
            case 4:{
                println("Saindo...")
                break
            }
            default:{
                println("Opcao invalida")
                break
            }

        }
    }}


}
