import { Empresa } from "./Empresa"
import { Candidato } from "./Candidato"
export class Vaga{
    id:number = 0
    cargo:string
    descricao:string
    competencias:Array<string>
    empresa:Empresa
    curtidas:Array<Candidato> = []

    constructor(id:number,cargo:string,descricao:string,competencias:Array<string>,empresa:Empresa){
        this.id = id
        this.cargo = cargo
        this.descricao = descricao
        this.competencias = competencias
        this.empresa = empresa

    }
}