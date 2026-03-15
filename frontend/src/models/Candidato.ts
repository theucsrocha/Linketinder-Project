import { Vaga } from "./Vaga.js"

export class Candidato{
    nome:string
    email:string
    cpf:string
    idade:number
    estado:string
    cep:string
    descricao:string
    competencias:Array<string>
    vagasCurtidas: Array<Vaga> = []

     constructor(nome:string,email:string,cpf:string,estado:string,descricao:string,cep:string,competencias:Array<string>,idade:number){
        this.nome = nome
        this.email = email
        this.cpf = cpf
        this.estado = estado
        this.descricao = descricao
        this.cep = cep
        this.idade = idade
        this.competencias = competencias


    }

    getCompetenciasComoString(): string {
  return this.competencias.join(", ")
}
}