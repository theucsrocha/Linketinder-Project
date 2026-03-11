import { Candidato } from "../models/Candidato";
import { Empresa } from "../models/Empresa";
import { Vaga } from "../models/Vaga";

export class EmpresaService{
    empresasCadastradas:Array<Empresa> = []

    cadastrarEmpresa(nome:string,email:string,cnpj:string,estado:string,descricao:string,cep:string):boolean{
            let novoEmpresa:Empresa = new Empresa(nome,email,cnpj,estado,descricao,cep)
            this.empresasCadastradas.push(novoEmpresa)
            return true
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

        



}