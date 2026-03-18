import { Candidato } from "./models/Candidato.js"
import { CandidatoService } from "./services/CandidatoService.js";
import { EmpresaService } from "./services/EmpresaService.js";
import { VagaService } from "./services/VagaService.js";

let competenciasUsuarioCadastroAtual:Array<string> = []
let candidatoService:CandidatoService = new CandidatoService
let empresaService:EmpresaService = new EmpresaService
let vagaService: VagaService = new VagaService();
let graficoCompetencias: any = null

const COMPETENCIAS_FIXAS: Array<string> = [
  "Java",
  "Python",
  "JavaScript",
  "TypeScript",
  "C#",
  "SQL",
  "Docker",
  "AWS"
]

// Executa somente quando houver ambiente DOM (browser)
if (typeof window !== 'undefined' && typeof document !== 'undefined') {
    document.addEventListener('DOMContentLoaded', () => {
        // Seleciona todos os botões e seções
        const navButtons = document.querySelectorAll<HTMLButtonElement>('.nav-btn');
        const tabSections = document.querySelectorAll<HTMLElement>('.tab-section');

        function inicializarNavegacao() {
            navButtons.forEach(btn => {
                btn.addEventListener('click', () => {
                    const targetTabId = btn.getAttribute('data-tab');

                    // 1. Remove 'active' de todos os botões
                    navButtons.forEach(b => b.classList.remove('active'));
                    // 2. Remove 'active' de todas as seções
                    tabSections.forEach(s => s.classList.remove('active'));

                    // 3. Adiciona 'active' no botão atual
                    btn.classList.add('active');
                    // 4. Adiciona 'active' na seção correspondente
                    if (targetTabId) {
                        const targetSection = document.getElementById(targetTabId);
                        targetSection?.classList.add('active');
                    }
                });
            });
        }

        // Chama a função ao carregar o app
        inicializarNavegacao();
    });
}

function contarCandidatosPorCompetencia(): Array<number> {
  return COMPETENCIAS_FIXAS.map((competenciaBase) => {
    let total = 0
    const competenciaBaseLower = competenciaBase.toLowerCase()

    candidatoService.getCandidatos.forEach((c) => {
      const temCompetencia = (c.competencias || []).some((comp) => comp.toLowerCase() === competenciaBaseLower)
      if (temCompetencia) {
        total += 1
      }
    })

    return total
  })
}

function atualizarGraficoCompetencias() {
  const canvas = document.getElementById("grafico-competencias") as HTMLCanvasElement | null
  if (!canvas) return

  const chartJs = (window as any).Chart
  if (!chartJs) return

  const dados = contarCandidatosPorCompetencia()

  if (graficoCompetencias) {
    graficoCompetencias.data.labels = COMPETENCIAS_FIXAS
    graficoCompetencias.data.datasets[0].data = dados
    graficoCompetencias.update()
    return
  }

  graficoCompetencias = new chartJs(canvas, {
    type: "bar",
    data: {
      labels: COMPETENCIAS_FIXAS,
      datasets: [
        {
          label: "Candidatos por competência",
          data: dados,
          backgroundColor: "rgba(70, 130, 180, 0.75)",
          borderColor: "rgba(70, 130, 180, 1)",
          borderWidth: 1
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            precision: 0
          }
        }
      }
    }
  })
}


//BOTAO CADASTRAR
const btnCadastrar = document.getElementById("btn-cadastrar-candidato");
if (btnCadastrar) {
  btnCadastrar.addEventListener('click', (e) => {
    e.preventDefault?.();
    const nome = (document.getElementById("candidato-nome") as HTMLInputElement).value;
    const email = (document.getElementById("candidato-email") as HTMLInputElement).value;
    const linkedin = (document.getElementById("candidato-linkedin") as HTMLInputElement).value;
    const telefone = (document.getElementById("candidato-telefone") as HTMLInputElement).value;
    const cpf = (document.getElementById("candidato-cpf") as HTMLInputElement).value;
    const idade = parseInt((document.getElementById("candidato-idade") as HTMLInputElement).value);
    const estado = (document.getElementById("candidato-estado") as HTMLInputElement).value;
    const cep = (document.getElementById("candidato-cep") as HTMLInputElement).value;
    const descricao = (document.getElementById("candidato-descricao") as HTMLInputElement).value;
    const competencias = competenciasUsuarioCadastroAtual.slice();

    try{
      candidatoService.cadastrarCandidato(nome, email, cpf, estado, descricao, cep, competencias, idade,telefone,linkedin)
      alert("Candidato cadastrado com sucesso");
      (document.getElementById("candidato-nome") as HTMLInputElement).value = "";
      (document.getElementById("candidato-email") as HTMLInputElement).value = "";
      (document.getElementById("candidato-cpf") as HTMLInputElement).value = "";
      (document.getElementById("candidato-idade") as HTMLInputElement).value = "";
      (document.getElementById("candidato-estado") as HTMLInputElement).value = "";
      (document.getElementById("candidato-cep") as HTMLInputElement).value = "";
      (document.getElementById("candidato-linkedin") as HTMLInputElement).value = "";
      (document.getElementById("candidato-telefone") as HTMLInputElement).value = "";
      (document.getElementById("candidato-descricao") as HTMLInputElement).value = "";
      (document.getElementById("candidato-competencias-input") as HTMLInputElement).value = "";
      competenciasUsuarioCadastroAtual = [];
      atualizarListagemDeCandidatos();
      atualizarGraficoCompetencias();
    }
    catch(e){
      if(e instanceof Error){
        alert(e.message);
      }
    }
    
  });
}



//BOTAO ADICONAR COMPETENCIAS DO CADASTRAR CANDIDATO
const btnAdicionarConpetencias = document.getElementById("btn-add-competencia-candidato");
if (btnAdicionarConpetencias) {
  btnAdicionarConpetencias.onclick = () => {
    const input = (document.getElementById("candidato-competencias-input") as HTMLInputElement);
    const val = input.value.trim();
    if (val) {
      competenciasUsuarioCadastroAtual.push(val);
      input.value = "";
    }
  };
}


//ATUALIZAR LISTAGEM DE CANDIDATOS
function atualizarListagemDeCandidatos() {
  const corpoTabela = document.getElementById("tabela-candidatos-body");
  if (!corpoTabela) return;
  corpoTabela.innerHTML = "";

  if (candidatoService.getCandidatos.length === 0) {
    corpoTabela.innerHTML = '<tr class="empty-state"><td colspan="5">Nenhum candidato cadastrado ainda</td></tr>';
    return;
  }

  for (let i = 0; i < candidatoService.getCandidatos.length; i++) {
    const c = candidatoService.getCandidatos[i];
    corpoTabela.innerHTML +=
      "<tr>" +
        "<td>" + (c.idade ?? "") + "</td>" +
        "<td>" + (c.estado ?? "") + "</td>" +
        "<td>" + (c.descricao ?? "") + "</td>" +
        "<td>" + (typeof c.getCompetenciasComoString === "function" ? c.getCompetenciasComoString() : (c.competencias || []).join(", ")) + "</td>" +
        '<td class="actions-cell">' +
            '<button type="button" class="btn-remove" title="Remover" data-cpf="' + c.cpf + '">✕</button>' +
        "</td>" +
      "</tr>";
  }
}

//LISTENER PARA O BOTAO DE REMOVER
const tabelaCandidatosBody = document.getElementById("tabela-candidatos-body");
if (tabelaCandidatosBody) {
  tabelaCandidatosBody.addEventListener("click", (ev) => {
    const target = ev.target as HTMLElement;
    if (target && target.matches(".btn-remove")) {
      const cpf = target.getAttribute("data-cpf") || "";
      if (cpf && candidatoService.removerCandidatoPorCpf(cpf)) {
        atualizarListagemDeCandidatos();
        atualizarGraficoCompetencias();
      } else {
        alert("Não foi possível remover o candidato.");
      }
    }
  });
}



//BOTAO CADASTRAR EMPRESA
const btnCadastrarEmpresa = document.getElementById("btn-cadastrar-empresa");
if (btnCadastrarEmpresa) {
  btnCadastrarEmpresa.addEventListener('click', (e) => {
    e.preventDefault?.();
    const nome = (document.getElementById("empresa-nome") as HTMLInputElement).value;
    const email = (document.getElementById("empresa-email") as HTMLInputElement).value;
    const cnpj = (document.getElementById("empresa-cnpj") as HTMLInputElement).value;
    const estado = (document.getElementById("empresa-estado") as HTMLInputElement).value;
    const cep = (document.getElementById("empresa-cep") as HTMLInputElement).value;
    const descricao = (document.getElementById("empresa-descricao") as HTMLInputElement).value;
   
    try{
    empresaService.cadastrarEmpresa(nome, email, cnpj, estado, descricao, cep,) 
      alert("Empresa cadastrada com sucesso");
      (document.getElementById("empresa-nome") as HTMLInputElement).value = "";
      (document.getElementById("empresa-email") as HTMLInputElement).value = "";
      (document.getElementById("empresa-cnpj") as HTMLInputElement).value = "";
      (document.getElementById("empresa-estado") as HTMLInputElement).value = "";
      (document.getElementById("empresa-cep") as HTMLInputElement).value = "";
      (document.getElementById("empresa-descricao") as HTMLInputElement).value = "";
    } catch(e){
      if(e instanceof Error){
        alert(e.message)
      }
    }
  });
}

// ===== VAGAS: modal simples para criar vaga =====
const btnAbrirModalVaga = document.getElementById("btn-abrir-modal-vaga");
const modalCriarVaga = document.getElementById("modal-criar-vaga");
const btnFecharModalVaga = document.getElementById("btn-fechar-modal-vaga");
const btnFecharModalVagaCancel = document.getElementById("btn-fechar-modal-vaga-cancel");
const formCriarVaga = document.getElementById("form-criar-vaga") as HTMLFormElement | null;
const selectEmpresas = document.getElementById("vaga-empresa-select") as HTMLSelectElement | null;
const tabelaVagasBody = document.getElementById("tabela-vagas-body");

function abrirModalVaga(){
  if(!modalCriarVaga) return
  modalCriarVaga.classList.add("open")
  modalCriarVaga.setAttribute("aria-hidden","false")
  populateEmpresasSelect()
}

function fecharModalVaga(){
  if(!modalCriarVaga) return
  modalCriarVaga.classList.remove("open")
  modalCriarVaga.setAttribute("aria-hidden","true")
}

function populateEmpresasSelect(){
  if(!selectEmpresas) return
  selectEmpresas.innerHTML = '<option value="">Selecione uma empresa...</option>'
  empresaService.getEmpresas.forEach(emp => {
    const opt = document.createElement("option")
    opt.value = emp.cnpj
    opt.textContent = emp.nome
    selectEmpresas.appendChild(opt)
  })
}

btnAbrirModalVaga?.addEventListener("click", ()=> abrirModalVaga())
btnFecharModalVaga?.addEventListener("click", ()=> fecharModalVaga())
btnFecharModalVagaCancel?.addEventListener("click", (e)=>{ e.preventDefault?.(); fecharModalVaga() })

// Simples função para atualizar listagem de vagas 
function atualizarListagemDeVagas(){
  if(!tabelaVagasBody) return
  tabelaVagasBody.innerHTML = ""
  if(!vagaService || !vagaService.vagasCadastradas || vagaService.vagasCadastradas.length === 0){
    tabelaVagasBody.innerHTML = '<tr class="empty-row"><td colspan="4">Nenhuma vaga cadastrada ainda.</td></tr>'
    return
  }

  vagaService.vagasCadastradas.forEach(v => {
    tabelaVagasBody.innerHTML += `<tr>` +
      `<td>${v.id ?? ''}</td>` +
      `<td>${v.cargo ?? ''}</td>` +
      `<td>${v.descricao ?? ''}</td>` +
      `<td>${(v.competencias || []).join(", ")}</td>` +
    `</tr>`
  })
}

// Handler de submissão do formulário de criar vaga
if(formCriarVaga){
  formCriarVaga.addEventListener('submit', (e)=>{
    e.preventDefault()
    if(!selectEmpresas) return alert('Selecione uma empresa')
    const empresaCnpj = selectEmpresas.value
    if(!empresaCnpj) return alert('Selecione uma empresa')
    const empresa = empresaService.getEmpresas.find(x => x.cnpj === empresaCnpj)
    if(!empresa) return alert('Empresa não encontrada')

    const cargo = (document.getElementById('vaga-cargo') as HTMLInputElement).value.trim()
    const descricao = (document.getElementById('vaga-descricao') as HTMLTextAreaElement).value.trim()
    const competenciasRaw = (document.getElementById('vaga-competencias-input') as HTMLInputElement).value.trim()
    const competencias = competenciasRaw ? competenciasRaw.split(',').map(s => s.trim()).filter(Boolean) : []

    if(!cargo || !descricao) return alert('Preencha cargo e descrição')

    if(vagaService.cadastrarVaga(cargo, descricao, competencias, empresa)){
      alert('Vaga criada com sucesso')
      formCriarVaga.reset()
      fecharModalVaga()
      atualizarListagemDeVagas()
    } else {
      alert('Erro ao criar vaga')
    }
  })
}

// Inicializa listagem ao carregar o script
atualizarListagemDeVagas()
atualizarGraficoCompetencias()

