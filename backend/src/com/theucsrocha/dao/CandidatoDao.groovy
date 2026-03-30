import com.theucsrocha.entities.Candidato
import com.theucsrocha.entities.Empresa
import groovy.sql.Sql

import java.time.LocalDate

class CandidatoDao{
    private Sql db

    CandidatoDao(Sql connection){
        this.db = connection
    }

    void inserir(Candidato candidato){
        String query = "INSERT INTO CANDIDATO(CPF,NOME,DATA_NASCIMENTO,CEP,SENHA,DESCRICAO,EMAIL) VALUES ?,?,?,?,?,?,?"
        db.executeInsert(query,candidato.cpf,candidato.nome,candidato.dataNascimento,candidato.cep,candidato.senha,candidato.descricaoPessoal,candidato.email)
    }

    void remover(Candidato candidato){
        String query = "DELETE FROM CANDIDATO C WHERE C.CPF = ? "
        db.execute(query,candidato.cpf)
    }

    List<Candidato> findAll(){
        List<Candidato> candidatos = []
        String query = "SELECT * FROM CANDIDATO"
        db.eachRow(query) { row ->

            candidatos.add(new Candidato(
                    nome: row.nome,
                    email: row.email,
                    cpf: row.cpf,
                    dataNascimento: row.datanascimento,
                    cep: row.cep,
                    descricaoPessoal: row.descricaopessoal,
                    senha: row.senha
            ))
        }
        return candidatos
    }

    Candidato findByCPF(String CPF){
        Candidato candidato
        String query = "SELECT * FROM CANDIDATO C WHERE C.CPF=?"
        db.eachRow(query,{row->
            candidato = new Candidato(
                    nome: row.nome,
                    email: row.email,
                    cpf: row.cpf,
                    dataNascimento: row.datanascimento,
                    cep: row.cep,
                    descricaoPessoal: row.descricaopessoal,
                    senha: row.senha
            )

        })
        if (candidato == null) {
            println("Candidato não encontrado")
            return null

        }
        return candidato

    }
}