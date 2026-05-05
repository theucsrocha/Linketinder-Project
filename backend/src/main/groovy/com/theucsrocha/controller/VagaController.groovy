package com.theucsrocha.controller

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.theucsrocha.Server
import com.theucsrocha.model.entities.Vaga
import com.theucsrocha.model.service.EmpresaService
import com.theucsrocha.model.service.VagaService
import groovy.json.JsonSlurper

class VagaController implements HttpHandler {

    private final VagaService vagaService
    private final EmpresaService empresaService
    @Override
    void handle(HttpExchange exchange) throws IOException {
        def metodo = exchange.requestMethod

        try {
            if (metodo == "POST"){
                adicionarVaga(exchange)}
            else if (metodo == "GET"){
                getAllVagas(exchange)
            }
        }catch (e){
            e.stackTrace
        }
    }
    VagaController(VagaService vagaService,EmpresaService empresaService) {
        this.vagaService = vagaService
        this.empresaService = empresaService
    }

    void adicionarVaga(HttpExchange exchange) {
        def corpo = exchange.requestBody.text
        def dados = new JsonSlurper().parseText(corpo)
        def vagaNova = new Vaga(
                nome: dados.nome,
                descricao: dados.descricao,
                local: dados.local,
                empresa: empresaService.getEmpresaByCNPJ(dados.empresa)
        )

        vagaService.adicionarVaga(vagaNova,dados.competencias)
        Server.enviarResposta(exchange, 201, [ status: "Vaga adicionada com sucesso"])
    }

    void listarVagas() {
        vagaService.listarVagas().forEach { println(it) }
    }
    void getAllVagas(HttpExchange exchange) {
        Server.enviarResposta(exchange, 200, vagaService.getAllVagas()) }
    }



