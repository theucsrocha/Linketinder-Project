import { Candidato } from "../models/Candidato.js";
export class CandidatoService {
    constructor() {
        this.candidatosCadastrados = [];
    }
    cadastrarCandidato(nome, email, cpf, estado, descricao, cep, competencias, idade) {
        let novoCandidato = new Candidato(nome, email, cpf, estado, descricao, cep, competencias, idade);
        this.candidatosCadastrados.push(novoCandidato);
        return true;
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
}
