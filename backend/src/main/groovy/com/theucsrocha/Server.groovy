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
import com.theucsrocha.model.repository.CompetenciaRepository
import com.theucsrocha.model.service.CandidatoService
import com.theucsrocha.model.service.EmpresaService
import com.theucsrocha.model.service.VagaService
import com.theucsrocha.model.util.ConnectionFactory
import com.theucsrocha.model.util.IConnectionFactory
import com.theucsrocha.model.validator.CompetenciaValidator
import groovy.json.JsonOutput
import groovy.sql.Sql

class Server {
    static void main(String[] args) {
        def port = 8080
        def server = HttpServer.create(new InetSocketAddress(port), 0)

        try {

            IConnectionFactory connectionFactory = ConnectionFactory.create("postgres")
            Sql sql = connectionFactory.getConnection()

            CompetenciaRepository competenciaRepository = new CompetenciaDao(sql)
            CompetenciaValidator competenciaValidator = new CompetenciaValidator(competenciaRepository)

            CandidatoService candidatoService = new CandidatoService(new CandidatoDao(sql), competenciaValidator)
            EmpresaService empresaService = new EmpresaService(new EmpresaDao(sql))
            VagaService vagaService = new VagaService(new VagaDao(sql), competenciaValidator)


            server.createContext("/api/candidatos", new CandidatoController(candidatoService))
            server.createContext("/api/empresas", new EmpresaController(empresaService))
            server.createContext("/api/vagas", new VagaController(vagaService,empresaService))

            server.start()
            println "Servidor Web rodando na porta ${port}"
            println "Endpoints disponíveis: /api/candidatos, /api/vagas, /api/empresas"

        } catch (Exception e) {
            println "Erro ao iniciar o servidor: " + e.message
        }
    }

      static void enviarResposta(HttpExchange exchange, int status, Object data) {
        String json = JsonOutput.toJson(data)
        exchange.responseHeaders.add("Content-Type", "application/json")
        exchange.sendResponseHeaders(status, json.getBytes().length)
        exchange.getResponseBody().with {
            write(json.getBytes())
            flush()
            close()
        }
    }
}