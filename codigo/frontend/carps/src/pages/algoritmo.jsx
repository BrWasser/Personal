import { useEffect, useState } from 'react'
import Papa from 'papaparse'
import Excel from 'exceljs';
import Input from '../components/algoritmo/input'
import Sidebar from '../components/Sidebar'
import Carregando from '../components/algoritmo/carregando'
import Export from '../components/algoritmo/export'
import '../styles/algoritmo.css'

/**
 * Componente que coordena as etapas de um algoritmo de processamento, incluindo importação de arquivos,
 * exibição do estado de carregamento e exportação de resultados.
 *
 * @returns {JSX.Element} O componente Algoritmo que gerencia o fluxo do algoritmo.
 */
const Algoritmo = () => {
    const [arquivos, setArquivos] = useState([]); // Estado para armazenar os arquivos importados
    const [animacao, setAnimacao] = useState(0); // Estado para controlar a animação atual
    const [elimina, setElimina] = useState(0); // Estado para controlar a eliminação de componentes após animações
    const [arquivoExport, setArquivoExport] = useState(null); // Estado para armazenar o arquivo a ser exportado
    const [dados, setDados] = useState(null); // Dados de alocação recebidos pelo algoritmo
    const [dadosFormatadosParaGrafico, setDadosFormatadosParaGrafico] = useState(null) // Dados em formato json com nomes dos setores e quantidade de técnicos alocados
    const [nomesSetores, setNomesSetores] = useState(null) // Nomes de todos os setores na ordem que aparecem no csv
    const [nomesTecnicos, setNomesTecnicos] = useState(null); // Nomes de todos os técnicos na ordem que aparecem no csv
    const [matriculasTecnicos, setMatriculasTecnicos] = useState(null)
    const [indiceDeHabilidadeDeGestao, setIndiceDeHabilidadeDeGestao] = useState(null); // Indice que indica se há técnicos sobrando (positivo) ou faltando (negativo)

    // Função para adicionar um novo arquivo ao estado
    const adicionaArquivo = (arquivo) => {
        setArquivos([...arquivos, arquivo]);
    }

    // Função para avançar para a próxima animação
    const proximaAnimacao = () => {
        setAnimacao(animacao + 1);
    }

    // Efeito para lidar com a transição entre as fases do algoritmo
    useEffect(() => {
        const timer = setTimeout(() => {
            setElimina(animacao)
            if (animacao === 1) { processaDados() }
        }, 2000);
        return () => clearTimeout(timer);
    }, [animacao]);

    // Efeito para avançar automaticamente quando o arquivo de exportação está pronto
    useEffect(() => {
        if (dadosFormatadosParaGrafico) {
            proximaAnimacao();
        }
    }, [arquivoExport])

    // Gera os dados formatados para as visualizações gráficas e o csv
    useEffect(() => {
        if (dados) {
            // Objeto para armazenar a contagem de técnicos por setor
            const contagemPorSetor = {};
            for (let k = 0; k < dados[0].length; k++) {
                contagemPorSetor[nomesSetores[k]] = 0;
            }
            // Iterar sobre todas as linhas da matriz, exceto a última
            for (let i = 0; i < dados.length; i++) {
                for (let j = 0; j < dados[i].length; j++) {
                    // Se a célula for 1, significa que um técnico está alocado neste setor
                    if (dados[i][j] === 1) {
                    // Se o setor já estiver no dicionário, incrementa a contagem
                        contagemPorSetor[nomesSetores[j]]++;
                    }
                }
            }

            // Converter o objeto em uma array de objetos conforme o formato desejado
            setDadosFormatadosParaGrafico(Object.keys(contagemPorSetor).map(key => ({
            nomeDoSetor: key,
            quantidadeDeTecnicosAlocados: contagemPorSetor[key]
            })));

            // Gera o csv
            geraXls()
        }
    }, [dados])

    // Função que pega os nomes dos técnicos e setores, envia os dados de entrada e recebe os dados de alocação
    const processaDados = async () => {

        try {
            const nomesUnicosSetor = await salvaNomesUnicosNaColunaDoCsv(arquivos[0], 'setor');
            const nomesUnicosTecnicos = await salvaNomesUnicosNaColunaDoCsv(arquivos[1], 'Nome');
            const matriculasUnicasTecnicos = await salvaNomesUnicosNaColunaDoCsv(arquivos[1], 'Matrícula WFM');
            setNomesSetores(nomesUnicosSetor)
            setNomesTecnicos(nomesUnicosTecnicos)
            setMatriculasTecnicos(matriculasUnicasTecnicos)
        } catch (error) {
            console.error('Erro ao processar os arquivos:', error);
        }
        await enviaArquivosERecebeDados()
    };

    const geraXls = async() => {
    
        let colunas = [...nomesSetores]
        colunas.unshift('nome')
        colunas.unshift('matricula')
    
        let nomesLinhas = [...nomesTecnicos]
        let matriculasLinhas = [...matriculasTecnicos]
    
        const workbook = new Excel.Workbook();
        const sheet = workbook.addWorksheet('Dados');
    
        // Adiciona as colunas
        sheet.columns = colunas.map(col => ({ header: col, key: col }));
    
        // Adiciona os dados
        dados.forEach((linha, indice) => {
            const nomeDoTecnico = nomesLinhas[indice];
            const numeroDaMatricula = matriculasLinhas[indice];
            const row = sheet.addRow([numeroDaMatricula, nomeDoTecnico, ...linha]);
    
            // Colorir células baseado nos valores
            linha.forEach((valor, colIndex) => {
                if (valor === 1) {
                    row.getCell(colIndex + 3).fill = {
                        type: 'pattern',
                        pattern:'solid',
                        fgColor:{ argb:'FF32CD32' }
                    };
                } else if (valor === 0) {
                    row.getCell(colIndex + 3).fill = {
                        type: 'pattern',
                        pattern:'solid',
                        fgColor:{ argb:'FFCD5C5C' },
                    };
                }
                row.getCell(colIndex + 3).alignment = { horizontal: 'center' }
            });
        });
    
        // Adiciona o índice de habilidade de gestão
        sheet.addRow([]);
        sheet.addRow(['Índice de Habilidade de Gestão']);
        sheet.addRow([indiceDeHabilidadeDeGestao])
    
        // Escreve o arquivo XLS
        const buffer = await workbook.xlsx.writeBuffer();
        const blob = new Blob([buffer], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
        const fileUrl = URL.createObjectURL(blob);
    
        setArquivoExport(fileUrl);
    }

    // Função para enviar os arquivos e receber os dados de alocação
    const enviaArquivosERecebeDados = async() => {
        const formData = new FormData();
        formData.append('dfPedidos', arquivos[0]);
        formData.append('dfTecnicos', arquivos[1]);

        try {
        const response = await fetch('/algorithms/pso', {
            method: 'POST',
            body: formData,
        });

        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        const jsonResponse = await response.json();
        setDados(jsonResponse.finalList)
        setIndiceDeHabilidadeDeGestao(jsonResponse.hgindex)
        console.log('Resposta recebida:', jsonResponse);
        } catch (error) {
        console.error('Erro ao enviar os arquivos:', error);
        }
    }

    const salvaNomesUnicosNaColunaDoCsv = (arquivo, coluna) => {
        return new Promise((resolve, reject) => {
            Papa.parse(arquivo, {
                complete: (resultado) => {
                    const nomes = resultado.data.map(row => row[coluna]).filter(Boolean);
                    const nomesUnicos = [...new Set(nomes)];
                    resolve(nomesUnicos);
                },
                header: true
            });
        });
    }
    
    // Renderiza o componente
    return (
        <><div style={{height:"100vh", width:"100vw", overflow:"hidden"}}>
            <Sidebar />
            {elimina < 1 && <Input adicionaArquivo={adicionaArquivo} proximaAnimacao={proximaAnimacao} nomeDaClasse={animacao > 0 ? "elementoFadeOut" : ""} />}
            {elimina === 1 && <Carregando nomeDaClasse={animacao === 1 ? "elementoFadeIn" : "elementoFadeOut"} />}
            {elimina === 2 && <Export dadosFormatados={dadosFormatadosParaGrafico} arquivoExport={arquivoExport} nomeDaClasse={animacao === 2 ? "elementoFadeIn" : ""} />}
        </div></>
    )
}

export default Algoritmo;
