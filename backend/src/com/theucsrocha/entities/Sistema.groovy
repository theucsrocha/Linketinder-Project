package com.theucsrocha.entities

class Sistema {

    public def empresas =  [
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
    public def candidatos =  [
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

    boolean verificadorDeCompatibilidade(Empresa empresa,Candidato candidato){

        candidato.getCompetencias().containsAll(empresa.getExigencias())
    }

    void listarMatches(List<Empresa> empresas,List<Candidato> candidatoes){
        println("Matches:")
        empresas.each { empresa->
            candidatoes.each {if (it.getEmpresasCurtidas().contains(empresa) && empresa.candidatoesCurtidos.contains(it)){
                println("${empresa.getNome()} <3 ${it.getNome()}")
            }}


        }
    }

    void adicionarCandidato(Candidato novoCandidato){
        if(novoCandidato.cpf.isEmpty()){
            throw new IllegalArgumentException("O cpf não pode ser vazio")
        }
        candidatos.add(novoCandidato);
    }

   void adicionarEmpresa(Empresa novaEmpresa){
       if(novaEmpresa.cnpj.isEmpty()){
           throw new IllegalArgumentException("O cnpj não pode ser vazio")
       }
       empresas.add(novaEmpresa)
   }
}
