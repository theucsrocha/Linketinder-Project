import { Candidato } from "../models/Candidato.js";
import { Vaga } from "../models/Vaga.js";

export class CandidatoService{
    candidatosCadastrados:Array<Candidato> = []

    cadastrarCandidato(nome:string,email:string,cpf:string,estado:string,descricao:string,cep:string,competencias:Array<string>,idade:number):boolean{
        let novoCandidato:Candidato = new Candidato(nome,email,cpf,estado,descricao,cep,competencias,idade)
        this.candidatosCadastrados.push(novoCandidato)
        return true
    }

    removerCandidatoPorCpf(cpf:string):boolean{
        let quantidadeAntiga = this.candidatosCadastrados.length

        let novaListaDeCastrados = this.candidatosCadastrados.filter(x=> x.cpf !== cpf)
        if(quantidadeAntiga <= novaListaDeCastrados.length){
            return false
        }
        this.candidatosCadastrados = novaListaDeCastrados
        return true

        
    }

    candidatoCurtirVaga(candidato:Candidato,vaga:Vaga):boolean{
        if(candidato.vagasCurtidas.find(x=> x === vaga)){
            return false
        }
        candidato.vagasCurtidas.push(vaga)
        return true
    }

    get getCandidatos(){
        return this.candidatosCadastrados
    }

    getCandidatoPorCpf(cpf:string):Candidato{
        let candidato = this.candidatosCadastrados.find(x=> x.cpf === cpf)

        if(!candidato){
            throw Error("Canditato não encontrado")
        }
        return candidato
    }
}

