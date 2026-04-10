package com.theucsrocha.dao
import com.theucsrocha.entities.Empresa
import com.theucsrocha.repository.EmpresaRepository
import groovy.sql.Sql

class EmpresaDao implements EmpresaRepository {
    private Sql db
    EmpresaDao(Sql connection){
        this.db = connection
    }

    void inserir(Empresa empresa){
        String query = "INSERT INTO EMPRESA (CNPJ,NOME,DESCRICAO,SENHA,CEP,EMAIL) VALUES (?,?,?,?,?,?)"
        db.executeInsert(query,[empresa.cnpj,empresa.nome,empresa.descricao,empresa.senha,empresa.cep,empresa.email])
    }

    void remove(Empresa empresa){
        String query = "DELETE FROM EMPRESA E WHERE E.CNPJ = ?"
        db.execute(query,empresa.cnpj)
    }

    List<Empresa> findAll(){
        List<Empresa> empresas = []
        String query = "SELECT * FROM EMPRESA"
        db.eachRow(query,{row->
            empresas.add(new Empresa(
                    cnpj: row.cnpj,
                    nome: row.nome,
                    descricao: row.descricao,
                    senha: row.senha,
                    cep: row.cep,
                    email: row.email

            ))
        })
        return empresas
    }

    Empresa findByCNPJ(String cnpj){
        Empresa empresa
        String query = "SELECT * FROM EMPRESA C WHERE C.CNPJ=?"
        db.eachRow(query,[cnpj],{row->
            empresa = new Empresa(
                    cnpj: row.cnpj,
                    nome: row.nome,
                    descricao: row.descricao,
                    senha: row.senha,
                    cep: row.cep,
                    email: row.email
            )

        })
        return empresa

    }
}
