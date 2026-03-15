import { Vaga } from "./Vaga.js"
import { Candidato } from "./Candidato.js"


export class Empresa{
    nome:string
    email:string
    cnpj:string
    estado:string
    descricao:string
    cep:string
    vagas:Array<Vaga> = []
    candidatosCurtidos:Array<Candidato> = []

    constructor(nome:string,email:string,cnpj:string,estado:string,descricao:string,cep:string){
        this.nome = nome
        this.email = email
        this.cnpj = cnpj
        this.estado = estado
        this.descricao = descricao
        this.cep = cep


    }
}