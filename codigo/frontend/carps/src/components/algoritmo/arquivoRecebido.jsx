import { useState, useEffect } from "react";
import iconeDeArquivo from "../../assets/arquivoIcon.png";
import BarraDeProgresso from "./barraDeProgresso";

/**
 * Componente React que exibe informações de um arquivo recebido e permite a interação do usuário.
 * Este componente exibe o nome do arquivo, uma barra de progresso e botões para excluir o arquivo ou prosseguir para o próximo frame.
 *
 * @component
 * @param {Object} props - As propriedades passadas para o componente.
 * @param {function} props.proximoFrame - Função para avançar para o próximo frame.
 * @param {function} props.adicionaArquivo - Função para adicionar o arquivo à lista.
 * @param {Object} props.arquivo - O arquivo que está sendo processado.
 * @param {function} props.setArquivo - Função para definir o estado do arquivo.
 */
const ArquivoRecebido = (props) => {
  // State para controlar a porcentagem de progresso do arquivo.
  const [porcentagem, setPorcentagem] = useState(0);

  // Efeito para incrementar a barra de progresso.
  useEffect(() => {
    if (porcentagem < 100) {
      const timer = setTimeout(() => {
        setPorcentagem(porcentagem + 1);
      }, 50);
      return () => clearTimeout(timer);
    }
  }, [porcentagem]);

  // Função para lidar com o evento de clique nos botões.
  const lidaComClick = () => {
    props.proximoFrame();
    props.adicionaArquivo(props.arquivo);
  };

  // Renderização do componente.
  return (
    <>
      <div style={{
          height:"100%",
          width:"100%",
          display:"flex",
          flexDirection:"column",
          justifyContent: 'flex-start',
          alignItems: 'center',
      }}>
        {/* Área para o ícone e nome do arquivo */}
        <div id="arquivo" style={{
            paddingTop:"10%",
            height:"30%",
            display:"flex",
            flexDirection:"column",
            justifyContent: 'center',
            alignItems: 'center',
        }}>
          <img src={iconeDeArquivo} style={{ width: '35px'}} alt={'Ícone de arquivo'} />
          <h3 style={{
              maxWidth: '100%',
              whiteSpace: 'nowrap',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
              textAlign: 'left'
          }}>
            {props.arquivo.name}
          </h3>
        </div>
        {/* Área para a barra de progresso */}
        <div id="arquivo" style={{
            height:"20%",
            width:"80%",
            display:"flex",
            flexDirection:"column",
            justifyContent: 'flex-start',
            alignItems: 'center',
        }}>
          <BarraDeProgresso concluido={porcentagem} />
        </div>
        {/* Área para os botões de ação */}
        <div id="arquivo" style={{
            width:"100%",
            paddingTop:"10%",
            height:"30%",
            display:"flex",
            flexDirection:"row",
            justifyContent: 'center',
            alignItems: 'center',
        }}>
          <button onClick={() => props.setArquivo([])} style={{
              padding: '10px 20px',
              borderRadius: '5px',
              border: 'none',
              backgroundColor: 'red',
              color: 'white',
              boxShadow: '0 2px 3px rgba(0,0,0,0.2)',
              margin: '0 5px',
              cursor: 'pointer',
          }}>Excluir</button>
          <div style={{width:"30%"}}></div>
          <button onClick={porcentagem === 100 ? lidaComClick : undefined} style={{
              padding: '10px 20px',
              borderRadius: '5px',
              border: 'none',
              backgroundColor: porcentagem === 100 ? 'green' : 'gray',
              color: 'white',
              boxShadow: '0 2px 3px rgba(0,0,0,0.2)',
              margin: '0 5px',
              cursor: porcentagem === 100 ? 'pointer' : 'default',
          }}>Próximo</button>
        </div>
      </div>
    </>
  );
}

export default ArquivoRecebido;
