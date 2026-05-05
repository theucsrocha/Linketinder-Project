package com.theucsrocha

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import com.theucsrocha.controller.CandidatoController
import com.theucsrocha.controller.EmpresaController
import com.theucsrocha.controller.VagaController
import com.theucsrocha.model.dao.CandidatoDao
import com.theucsrocha.model.dao.CompetenciaDao
import com.theucsrocha.model.dao.EmpresaDao
import com.theucsrocha.model.dao.VagaDao
import com.theucsrocha.model.service.CandidatoService
import com.theucsrocha.model.service.EmpresaService
import com.theucsrocha.model.service.VagaService
import com.theucsrocha.model.util.ConnectionFactory
import com.theucsrocha.model.validator.CompetenciaValidator
import groovy.json.JsonOutput

class Server {
    static void main(String[] args) {
        def port = 8080
        def server = HttpServer.create(new InetSocketAddress(port), 0)

        try {
            // Reutilizando a sua lógica de conexão e serviços
            def connectionFactory = ConnectionFactory.create("postgres")
            def sql = connectionFactory.getConnection()

            def competenciaRepository = new CompetenciaDao(sql)
            def competenciaValidator = new CompetenciaValidator(competenciaRepository)

            def candidatoService = new CandidatoService(new CandidatoDao(sql), competenciaValidator)
            def empresaService = new EmpresaService(new EmpresaDao(sql))
            def vagaService = new VagaService(new VagaDao(sql), competenciaValidator)

            // Registrando os seus Controllers como Endpoints
            server.createContext("/api/candidatos", new CandidatoController(candidatoService))
            server.createContext("/api/empresas", new EmpresaController(empresaService))

            server.start()
            println "🚀 Servidor Web rodando na porta ${port}"
            println "Endpoints disponíveis: /api/candidatos, /api/vagas"

        } catch (Exception e) {
            println "❌ Erro ao iniciar o servidor: " + e.message
        }
    }

      static void enviarResposta(HttpExchange exchange, int status, Object data) {
        def json = JsonOutput.toJson(data)
        exchange.responseHeaders.add("Content-Type", "application/json")
        exchange.sendResponseHeaders(status, json.getBytes().length)
        exchange.getResponseBody().with {
            write(json.getBytes())
            flush()
            close()
        }
    }
}