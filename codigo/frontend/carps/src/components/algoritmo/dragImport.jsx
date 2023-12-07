import iconeDeUpload from '../../assets/uploadIcon.png'

/**
 * Componente React para upload de arquivos via drag-and-drop.
 * Permite que os usuários arrastem arquivos para uma área definida ou cliquem para selecionar arquivos manualmente.
 *
 * @component
 * @param {Object} props - As propriedades passadas para o componente.
 * @param {function} props.setArquivo - Função para atualizar o estado com os arquivos selecionados.
 * @param {string} props.tipo - Tipo de arquivo esperado para informar ao usuário.
 */
const DragImport = (props) => {

    // Trata o evento de arrastar arquivos sobre a área definida
    const lidaComDragOver = (e) => {
        e.preventDefault(); // Previne o comportamento padrão do navegador
        // Aqui poderia haver lógica para mudança da cor da borda, por exemplo
    };

    // Trata o evento de soltar arquivos na área definida
    const lidaComDrop = (e) => {
        e.preventDefault(); // Previne que o navegador abra o arquivo
        props.setArquivo(e.dataTransfer.files); // Atualiza o estado com os arquivos arrastados
    };

    // Trata a seleção de arquivos quando o usuário utiliza o input de arquivo
    const lidaComSelecaoDeArquivo = (e) => {
        props.setArquivo(e.target.files); // Atualiza o estado com os arquivos selecionados
    };

    /**
     * Renderiza o componente de área de arrastar e soltar.
     * @returns {JSX.Element}
     */
    return (
        <>
            {/* Área superior para input de arrastar e soltar */}
            <div id="parteDeCimaInput" style={{
                display: 'flex',
                justifyContent: 'center',
                height: "80%",
                width: "100%"
            }}>
                {/* Linha pontilhada que indica onde o usuário deve arrastar os arquivos */}
                <div id="linhaPontilhada" style={{
                    display: "flex",
                    flexDirection: "column",
                    border: "0.1rem dashed #818B3E",
                    borderRadius: "0.33rem",
                    width: "80%",
                    height: "80%",
                    backgroundColor: "#FFFFFF"
                }}
                    onDragOver={lidaComDragOver}
                    onDrop={lidaComDrop}
                >
                    {/* Ícone e instruções para o usuário */}
                    <div style={{
                        display: 'flex',
                        flexDirection: "column",
                        justifyContent: 'center',
                        alignItems: 'center',
                        height: "33%"
                    }}> <img src={iconeDeUpload} style={{ width: '10%', height: '50%' }} alt="Ícone de upload"></img> </div>
                    <div style={{
                        display: 'flex',
                        flexDirection: "column",
                        justifyContent: 'center',
                        alignItems: 'center',
                        textAlign: 'center',
                        paddingLeft: "8%",
                        paddingRight: "8%",
                        height: "33%"
                    }}> <h1>Arraste o arquivo de {props.tipo} para esta área</h1></div>
                    <div style={{
                        display: 'flex',
                        flexDirection: "row",
                        justifyContent: 'center',
                        alignItems: 'center',
                        height: "33%",
                        width: "100%"
                    }}><p>Ou&nbsp;</p><b>selecione o arquivo&nbsp;</b><p>na pasta.</p> </div>
                </div>
            </div>
            {/* Botão escondido e label para importação de arquivo */}
            <div id="divBotaoInput">
                <label htmlFor="botao_de_input_escondido" style={{
                    backgroundColor: "#212121",
                    color: "#FFFFFF",
                    paddingLeft: "7rem",
                    paddingRight: "7rem",
                    paddingTop: "1.2rem",
                    paddingBottom: "1.2rem",
                    fontFamily: "sans-serif",
                    borderRadius: "2rem",
                    cursor: "pointer",
                    marginTop: "1rem"
                }}>IMPORTAR</label>
                <input type="file" id="botao_de_input_escondido" hidden onChange={lidaComSelecaoDeArquivo} />
            </div>
        </>
    )
}

export default DragImport;
