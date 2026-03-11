import { Empresa } from "../models/Empresa";
import { Vaga } from "../models/Vaga";

export class VagaService{
    vagasCadastradas:Array<Vaga> = []

    cadastrarVaga(cargo:string,descricao:string,competencias:Array<string>,empresa:Empresa):boolean{
                let novaVaga:Vaga = new Vaga(this.vagasCadastradas.length++,cargo,descricao,competencias,empresa)
                this.vagasCadastradas.push(novaVaga)
                return true
            }


}