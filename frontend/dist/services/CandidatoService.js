import { Candidato } from "../models/Candidato.js";
export class CandidatoService {
    constructor() {
        this.candidatosCadastrados = [];
    }
    cadastrarCandidato(nome, email, cpf, estado, descricao, cep, competencias, idade, telefone, linkedin) {
        this.validadorDados(nome, email, cpf, estado, descricao, cep, competencias, idade, telefone, linkedin);
        let novoCandidato = new Candidato(nome, email, cpf, estado, descricao, cep, competencias, idade, telefone, linkedin);
        this.candidatosCadastrados.push(novoCandidato);
    }
    removerCandidatoPorCpf(cpf) {
        let quantidadeAntiga = this.candidatosCadastrados.length;
        let novaListaDeCastrados = this.candidatosCadastrados.filter(x => x.cpf !== cpf);
        if (quantidadeAntiga <= novaListaDeCastrados.length) {
            return false;
        }
        this.candidatosCadastrados = novaListaDeCastrados;
        return true;
    }
    candidatoCurtirVaga(candidato, vaga) {
        if (candidato.vagasCurtidas.find(x => x === vaga)) {
            return false;
        }
        candidato.vagasCurtidas.push(vaga);
        return true;
    }
    get getCandidatos() {
        return this.candidatosCadastrados;
    }
    getCandidatoPorCpf(cpf) {
        let candidato = this.candidatosCadastrados.find(x => x.cpf === cpf);
        if (!candidato) {
            throw Error("Canditato não encontrado");
        }
        return candidato;
    }
    validadorDados(nome, email, cpf, estado, descricao, cep, competencias, idade, telefone, linkedin) {
        if (!email.match(/\S+@\w+\.\w{2,6}(\.\w{2})?/g)) {
            throw new Error('Email invalido');
        }
        if (!cpf.match(/\d{3}\.\d{3}\.\d{3}-\d{2}/)) {
            throw new Error('CPF com formato inválido');
        }
        if (!cep.match(/\d{5}-\d{3}/)) {
            throw new Error('CEP com formato inválido');
        }
        if (!telefone.match(/\(\d{2}\)\d{4,5}-\d{4}/)) {
            throw new Error('Telefone com formato inválido');
        }
        if (!linkedin.match(/www.linkedin.com\/in\/.+\//)) {
            throw new Error('Link do Linkedin inválido');
        }
    }
}
