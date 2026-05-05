package com.theucsrocha.controller

import com.theucsrocha.Server
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.theucsrocha.model.entities.Candidato
import com.theucsrocha.model.service.CandidatoService

class CandidatoController implements HttpHandler {

    private final CandidatoService candidatoService

    @Override
    void handle(HttpExchange exchange) throws IOException {
        def metodo = exchange.requestMethod

        try {
            if (metodo == "POST"){
                adicionarCandidato(exchange)}
            else if (metodo == "GET"){
                getAllCandidatos(exchange)
            }
        }catch (e){
            e.stackTrace
        }
    }

    CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService
    }
    //Candidato candidato, List<String> competencias
    void adicionarCandidato(HttpExchange exchange) {
        def corpo = exchange.requestBody.text
        def dados = new JsonSlurper().parseText(corpo)

        Candidato novoCandidato = new Candidato(
                nome: dados.nome,
                email: dados.email,
                cpf: dados.cpf,
                dataNascimento: dados.dataNascimento,
                cep: dados.cep,
                descricaoPessoal: dados.descricaoPessoal,
                senha: dados.senha
        )

        candidatoService.adicionarCandidato(novoCandidato,dados.competencias)
        Server.enviarResposta(exchange, 201, [ status: "Candidato adicionado com sucesso"])
    }

    void listarCandidatos() {
        candidatoService.listarCandidatos().forEach { println(it) }
    }

    List<Candidato> getAllCandidatos(HttpExchange exchange){
        Server.enviarResposta(exchange, 200, candidatoService.getAllCandidatos())
        return candidatoService.getAllCandidatos()
    }

    Candidato getCandidatoByCPF(String cpf) {
        candidatoService.getCandidatoByCPF(cpf)
    }



}
