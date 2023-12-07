import graficoDeBarras from "../../sketches/graficoDeBarras";
import circulosFlutuantes from "../../sketches/circulosFlutuantes"
import { ReactP5Wrapper } from 'react-p5-wrapper';
import { useState, useEffect } from "react";
import "../../styles/animacaoDeSlide.css"

/**
 * Componente que gerencia a visualização de animações gráficas e fornece uma funcionalidade
 * para o usuário exportar um arquivo de dados. O componente permite a navegação entre diferentes
 * animações e oferece um botão de exportação de dados.
 * 
 * @component
 * @param {Object} props - As propriedades do componente.
 * @param {string} props.arquivoExport - O endereço URL do arquivo para exportação.
 * @param {string} props.nomeDaClasse - Uma classe CSS aplicada ao componente para estilização.
 */
const Export = (props) => {
    const [animacao, setAnimacao] = useState(0); // Controla o índice da animação atual
    const [direcaoDaAnimacao, setDirecaoDaAnimacao] = useState(1); // Controla a direção da animação para as transições
    const [elimina, setElimina] = useState(0); // Estado para eliminar a animação anterior após a transição
    const animacaoMaxima = 1; // Define o número máximo de animações disponíveis
    const corBotoes = 'cornflowerBlue'; // Cor para os botões de navegação

    /**
     * Função para iniciar o download do arquivo.
     * Cria um elemento de link temporário para simular o clique e iniciar o download.
     */
    const baixarArquivo = () => {
        const link = document.createElement('a');
        link.href = props.arquivoExport;
        link.setAttribute('download', 'alocacaoIdeal.xlsx'); // Define o nome do arquivo para download
        document.body.appendChild(link);
        link.click(); 
        document.body.removeChild(link);
    }

    /**
     * Avança para a próxima animação.
     */
    const proximaAnimacao = () =>  {
        setDirecaoDaAnimacao(1);
        setAnimacao(animacao + 1);
    }

    /**
     * Retorna para a animação anterior.
     */
    const animacaoAnterior = () => {
        setDirecaoDaAnimacao(-1);
        setAnimacao(animacao - 1);
    }

    // Efeito para lidar com a eliminação da animação anterior da DOM após a transição
    useEffect(() => {
        const timer = setTimeout(() => {
          setElimina(animacao)
        }, 300); // Tempo de atraso para a transição
        return () => clearTimeout(timer);
    }, [animacao]);

    // Renderiza o componente
    return (
        <>
            <div className={props.nomeDaClasse} id="paginaToda" style={{
                display: 'flex',
                flexDirection: "column",
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                paddingBottom:"2%"
            }}>
                <div style={{
                    display: 'flex',
                    flexDirection: "row",
                    justifyContent: 'center',
                    alignItems: 'center',
                }}>
                    {/* Botão para retroceder a animação */}
                    <button onClick={animacao === 0 ? undefined : animacaoAnterior} style={{
                        padding: '10px 20px',
                        borderRadius: '5px',
                        border: 'none',
                        backgroundColor: animacao === 0 ? 'gray' : corBotoes,
                        color: 'white',
                        boxShadow: '0 2px 3px rgba(0,0,0,0.2)',
                        margin: '20px',
                        cursor: 'pointer',
                    }}>Anterior</button>
                    {/* Condicional para exibir a primeira animação se estiver no estado correto */}
                    {elimina === 0 &&
                    <div className={
                        animacao === 0 && direcaoDaAnimacao === -1 ? "entraDaEsquerda" :
                        animacao === 1 && direcaoDaAnimacao === 1 ? "saiParaEsquerda" : undefined
                    }>
                        <ReactP5Wrapper sketch={graficoDeBarras} dados={props.dadosFormatados} ></ReactP5Wrapper>
                    </div>}
                    {/* Condicional para exibir a segunda animação se estiver no estado correto */}
                    {elimina === 1 &&
                    <div className={
                        animacao === 1 && direcaoDaAnimacao === 1 ? "entraDaDireita" :
                        animacao === 0 && direcaoDaAnimacao === -1 ? "saiParaDireita" : undefined
                    }>
                        <ReactP5Wrapper sketch={circulosFlutuantes} dados={props.dadosFormatados}></ReactP5Wrapper>
                    </div>}
                    {/* Botão para avançar a animação */}
                    <button onClick={animacao === animacaoMaxima ? undefined : proximaAnimacao} style={{
                        padding: '10px 20px',
                        borderRadius: '5px',
                        border: 'none',
                        backgroundColor: animacao === animacaoMaxima ? 'gray' : corBotoes,
                        color: 'white',
                        boxShadow: '0 2px 3px rgba(0,0,0,0.2)',
                        margin: '20px',
                        cursor: 'pointer',
                    }}>Próximo</button>
                </div>
                {/* Botão para exportar os dados */}
                <button onClick={baixarArquivo} style={{
                    padding: '10px 20px',
                    borderRadius: '5px',
                    border: 'none',
                    backgroundColor: 'black',
                    color: 'white',
                    boxShadow: '0 2px 3px rgba(0,0,0,0.2)',
                    margin: '0 5px',
                    cursor: 'pointer',
                    marginTop: '5px'
                }}>Exportar <br></br> dados</button>
            </div>
        </>
    )
}

export default Export;