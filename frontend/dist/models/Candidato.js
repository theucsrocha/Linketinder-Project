export class Candidato {
    constructor(nome, email, cpf, estado, descricao, cep, competencias, idade, telefone, linkedin) {
        this.vagasCurtidas = [];
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.estado = estado;
        this.descricao = descricao;
        this.cep = cep;
        this.idade = idade;
        this.competencias = competencias;
        this.linkedin = linkedin;
        this.telefone = telefone;
    }
    getCompetenciasComoString() {
        return this.competencias.join(", ");
    }
}
