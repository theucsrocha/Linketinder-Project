export class Empresa {
    constructor(nome, email, cnpj, estado, descricao, cep) {
        this.vagas = [];
        this.candidatosCurtidos = [];
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
        this.estado = estado;
        this.descricao = descricao;
        this.cep = cep;
    }
}
