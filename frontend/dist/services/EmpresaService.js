import { Empresa } from "../models/Empresa.js";
export class EmpresaService {
    constructor() {
        this.empresasCadastradas = [];
    }
    cadastrarEmpresa(nome, email, cnpj, estado, descricao, cep) {
        this.validadorDados(nome, email, cnpj, estado, descricao, cep);
        let novoEmpresa = new Empresa(nome, email, cnpj, estado, descricao, cep);
        this.empresasCadastradas.push(novoEmpresa);
    }
    removerEmpresaPorCpf(cnpj) {
        let quantidadeAntiga = this.empresasCadastradas.length;
        let novaListaDeCastrados = this.empresasCadastradas.filter(x => x.cnpj !== cnpj);
        if (quantidadeAntiga <= novaListaDeCastrados.length) {
            return false;
        }
        this.empresasCadastradas = novaListaDeCastrados;
        return true;
    }
    EmpresaCurtirCandidato(empresa, candidato) {
        if (empresa.candidatosCurtidos.find(x => x === candidato)) {
            return false;
        }
        empresa.candidatosCurtidos.push(candidato);
        return true;
    }
    get getEmpresas() {
        return this.empresasCadastradas;
    }
    validadorDados(nome, email, cnpj, estado, descricao, cep) {
        if (!email.match(/\S+@\w+\.\w{2,6}(\.\w{2})?/g)) {
            throw new Error('Email invalido');
        }
        if (!cnpj.match(/\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}/)) {
            throw new Error('CNPJ com formato inválido');
        }
        if (!cep.match(/\d{5}-\d{3}/)) {
            throw new Error('CEP com formato inválido');
        }
    }
}
