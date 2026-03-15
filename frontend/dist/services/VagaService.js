import { Vaga } from "../models/Vaga.js";
export class VagaService {
    constructor() {
        this.vagasCadastradas = [];
    }
    cadastrarVaga(cargo, descricao, competencias, empresa) {
        let novaVaga = new Vaga(this.vagasCadastradas.length++, cargo, descricao, competencias, empresa);
        this.vagasCadastradas.push(novaVaga);
        return true;
    }
    removerVagaPorId(id) {
        let quantidadeVagasAntigas = this.vagasCadastradas.length;
        let novaListaVagas = this.vagasCadastradas.filter(x => x.id !== id);
        if (novaListaVagas.length >= quantidadeVagasAntigas) {
            return false;
        }
        this.vagasCadastradas = novaListaVagas;
        return true;
    }
}
