import { Empresa } from "../models/Empresa.js";
export class EmpresaService {
    constructor() {
        this.empresasCadastradas = [];
    }
    cadastrarEmpresa(nome, email, cnpj, estado, descricao, cep) {
        let novoEmpresa = new Empresa(nome, email, cnpj, estado, descricao, cep);
        this.empresasCadastradas.push(novoEmpresa);
        return true;
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
}
