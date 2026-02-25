package com.theucsrocha.testes;

import com.theucsrocha.entities.Candidato
import com.theucsrocha.entities.Empresa
import com.theucsrocha.entities.Sistema;
import spock.lang.Specification;

public class TestesApp extends Specification {

    def "Testar novo Candidato"() {
        given:
        Sistema sistema = new Sistema();
        def candidato = new Candidato(
                nome: "Matheus",
                email: "",
                cpf: "1",
                idade: 20,
                estado: "",
                cep: "",
                descricaoPessoal: "",
                competencias: ["Java", "Spring"])

        when:
        sistema.adicionarCandidato(candidato);
        def resultado = sistema.candidatos.size()
        then:
        resultado == 6
    }
    def "Testar nova Empresa"() {
        given:
        Sistema sistema = new Sistema();
        def empresa = new Empresa("PastelSoft", "x", "1", "BR", "BA", "desc", "40000", ["Java"])

        when:
        sistema.adicionarEmpresa(empresa);
        def resultado = sistema.empresas.size()
        then:
        resultado == 6
    }

    def "teste para analisar compatibilidade"(){
        given: "Um sistema e uma empresa que exige Java e Spring e um canditado que é compativel"
            def sistema = new Sistema()
            def empresa = new Empresa("Zenvia", "rh@zenvia.com", "123", "Brasil", "SP", "Desc", "01000", ["Java", "Spring"])
            def candidato = new Candidato(nome: "Matheus Rocha", competencias: ["Java", "Spring", "SQL"])
        when:
            def resultado = sistema.verificadorDeCompatibilidade(empresa,candidato)
        then:
            resultado == true
    }

    def "Deve lançar uma expetion para o cpf vazio"() {
        given:
        Sistema sistema = new Sistema();
        def candidato = new Candidato(
                nome: "Matheus",
                email: "",
                cpf: "",
                idade: 20,
                estado: "",
                cep: "",
                descricaoPessoal: "",
                competencias: ["Java", "Spring"])

        when:
        sistema.adicionarCandidato(candidato);
        then:
            thrown(IllegalArgumentException)
    }

    def "Deve lançar uma expetion para o cnpj vazio"() {
        given:
        Sistema sistema = new Sistema();
        def empresa = new Empresa("PastelSoft", "x", "", "BR", "BA", "desc", "40000", ["Java"])

        when:
        sistema.adicionarEmpresa(empresa);
        then:
        thrown(IllegalArgumentException)
    }

}




