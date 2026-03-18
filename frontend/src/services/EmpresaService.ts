 import { Candidato } from "../models/Candidato.js";
import { Empresa } from "../models/Empresa.js";
import { Vaga } from "../models/Vaga.js";

export class EmpresaService{
    empresasCadastradas:Array<Empresa> = []

    cadastrarEmpresa(nome:string,email:string,cnpj:string,estado:string,descricao:string,cep:string):void{
            this.validadorDados(nome,email,cnpj,estado,descricao,cep)
            let novoEmpresa:Empresa = new Empresa(nome,email,cnpj,estado,descricao,cep)
            this.empresasCadastradas.push(novoEmpresa)
        
        }
    
        removerEmpresaPorCpf(cnpj:string):boolean{
            let quantidadeAntiga = this.empresasCadastradas.length
    
            let novaListaDeCastrados = this.empresasCadastradas.filter(x=> x.cnpj !== cnpj)
            if(quantidadeAntiga <= novaListaDeCastrados.length){
                return false
            }
            this.empresasCadastradas = novaListaDeCastrados
            return true
    
            
        }
    
        EmpresaCurtirCandidato(empresa:Empresa,candidato:Candidato):boolean{
            if(empresa.candidatosCurtidos.find(x=> x === candidato)){
                return false
            }
            empresa.candidatosCurtidos.push(candidato)
            return true
        }
    
        get getEmpresas(){
            return this.empresasCadastradas
        }

        validadorDados(nome:string,email:string,cnpj:string,estado:string,descricao:string,cep:string){
        if(!email.match(/\S+@\w+\.\w{2,6}(\.\w{2})?/g)){
            throw new Error('Email invalido')
        }
        if(!cnpj.match(/\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}/)){
            throw new Error('CNPJ com formato inválido')
        }
        if(!cep.match(/\d{5}-\d{3}/)){
            throw new Error('CEP com formato inválido')
        }
        
}

        



}