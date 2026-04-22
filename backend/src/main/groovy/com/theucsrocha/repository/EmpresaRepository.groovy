package com.theucsrocha.repository

import com.theucsrocha.entities.Empresa

interface EmpresaRepository {
    void inserir(Empresa empresa)
    void remove(Empresa empresa)
    List<Empresa> findAll()
    Empresa findByCNPJ(String cnpj)
}
