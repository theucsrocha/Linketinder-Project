package com.theucsrocha.controller

import com.theucsrocha.model.entities.Empresa
import com.theucsrocha.model.service.EmpresaService

class EmpresaController {

    private final EmpresaService empresaService

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    void adicionarEmpresa(Empresa empresa) {
        empresaService.adicionarEmpresa(empresa)
    }

    void listarEmpresas() {
        empresaService.listarEmpresas().forEach { println(it) }
    }

    Empresa getEmpresaByCNPJ(String cnpj) {
        empresaService.getEmpresaByCNPJ(cnpj)
    }

    List<Empresa> getAllEmpresas(){
        return empresaService.getAllEmpresas()
    }
}
