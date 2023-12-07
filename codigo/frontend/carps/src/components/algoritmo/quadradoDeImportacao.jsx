import { useEffect, useState } from 'react'
import DragImport from './dragImport'
import ArquivoRecebido from './arquivoRecebido'

/**
 * Componente que encapsula a funcionalidade de importação de arquivos, alternando entre
 * a interface de arrastar e soltar e a visualização de um arquivo que foi recebido.
 *
 * @component
 * @param {Object} props - As propriedades do componente.
 * @param {string} props.nomeDaClasse - Classe CSS para estilização do componente.
 * @param {string} props.tipo - Descrição do tipo de arquivo esperado para a importação.
 * @param {function} props.proximoFrame - Função chamada para avançar para o próximo frame no processo de importação.
 * @param {function} props.adicionaArquivo - Função para adicionar o arquivo importado a uma lista ou estado superior.
 */
const QuadradoDeImportacao = (props) => {

    const [arquivo, setArquivo] = useState([]); // Estado que mantém o(s) arquivo(s) importado(s)

    // Renderiza o componente
    return (
        <div id="divCentral" className={props.nomeDaClasse} style={{
            display: 'flex',
            flexDirection: "column",
            justifyContent: 'center',
            alignItems: 'center',
            width:'45%',
            height: '70%',
            border: "0.1rem solid #E0E0E0",
            borderRadius: "1rem",
            backgroundColor:"#F0F0F0"
        }}>
            {/* Condicionalmente renderiza o componente DragImport ou ArquivoRecebido dependendo de o arquivo ter sido selecionado ou não */}
            {arquivo.length == 0 && <DragImport tipo={props.tipo} setArquivo={setArquivo} />}
            {arquivo.length > 0 && <ArquivoRecebido proximoFrame={props.proximoFrame} adicionaArquivo={props.adicionaArquivo} arquivo={arquivo[0]} setArquivo={setArquivo} />}
        </div>
    )
}

export default QuadradoDeImportacao;
