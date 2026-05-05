package com.theucsrocha.controller

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.theucsrocha.Server
import com.theucsrocha.model.entities.Empresa
import com.theucsrocha.model.service.EmpresaService
import groovy.json.JsonSlurper

class EmpresaController implements HttpHandler {

    private final EmpresaService empresaService
    @Override
    void handle(HttpExchange exchange) throws IOException {
        def metodo = exchange.requestMethod

        try {
            if (metodo == "POST"){
                adicionarEmpresa(exchange)}
            else if (metodo == "GET"){
                getAllEmpresas(exchange)
            }
        }catch (e){
            e.stackTrace
        }
    }

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    void adicionarEmpresa(HttpExchange exchange) {
        def corpo = exchange.requestBody.text
        def dados = new JsonSlurper().parseText(corpo)
        Empresa novaEmpresa = new Empresa(
                nome: dados.nome,
                email: dados.email,
                cnpj: dados.cnpj,
                descricao: dados.descricao,
                cep: dados.cep,
                senha: dados.senha
        )
        empresaService.adicionarEmpresa(novaEmpresa)
        Server.enviarResposta(exchange, 201, [status: "Empresa adicionada com sucesso"])
    }

    void listarEmpresas() {
        empresaService.listarEmpresas().forEach { println(it) }
    }

    Empresa getEmpresaByCNPJ(String cnpj) {
        empresaService.getEmpresaByCNPJ(cnpj)
    }

    List<Empresa> getAllEmpresas(HttpExchange exchange){
        Server.enviarResposta(exchange, 200, empresaService.getAllEmpresas())
        return empresaService.getAllEmpresas()
    }


}
