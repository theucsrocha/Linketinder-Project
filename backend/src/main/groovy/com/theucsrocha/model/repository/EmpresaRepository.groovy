package com.theucsrocha.model.repository

import com.theucsrocha.model.entities.Empresa

interface EmpresaRepository {
    void inserir(Empresa empresa)
    void remove(Empresa empresa)
    List<Empresa> findAll()
    Empresa findByCNPJ(String cnpj)
}
