package com.theucsrocha.service

import com.theucsrocha.entities.Empresa
import com.theucsrocha.repository.EmpresaRepository

class EmpresaService {

    private final EmpresaRepository empresaRepository

    EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository
    }

    void adicionarEmpresa(Empresa empresa) {
        empresaRepository.inserir(empresa)
    }

    List<Empresa> listarEmpresas() {
        empresaRepository.findAll()
    }

    List<Empresa> getAllEmpresas() {
        empresaRepository.findAll()
    }

    Empresa getEmpresaByCNPJ(String cnpj) {
        empresaRepository.findByCNPJ(cnpj)
    }
}
