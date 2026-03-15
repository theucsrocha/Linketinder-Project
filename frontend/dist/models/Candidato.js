export class Candidato {
    constructor(nome, email, cpf, estado, descricao, cep, competencias, idade) {
        this.vagasCurtidas = [];
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.estado = estado;
        this.descricao = descricao;
        this.cep = cep;
        this.idade = idade;
        this.competencias = competencias;
    }
    getCompetenciasComoString() {
        return this.competencias.join(", ");
    }
}
