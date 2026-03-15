import { Empresa } from "../models/Empresa.js";
import { Vaga } from "../models/Vaga.js";

export class VagaService{
    vagasCadastradas:Array<Vaga> = []

    cadastrarVaga(cargo:string,descricao:string,competencias:Array<string>,empresa:Empresa):boolean{
                let novaVaga:Vaga = new Vaga(this.vagasCadastradas.length++,cargo,descricao,competencias,empresa)
                this.vagasCadastradas.push(novaVaga)
                return true
            }

    removerVagaPorId(id:number):boolean{
        let quantidadeVagasAntigas = this.vagasCadastradas.length

        let novaListaVagas:Array<Vaga> = this.vagasCadastradas.filter(x => x.id !== id)

        if(novaListaVagas.length >= quantidadeVagasAntigas){
            return false
        }
        this.vagasCadastradas = novaListaVagas
        return true


    }



}