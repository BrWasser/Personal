import React, { useEffect, useState } from 'react';
import QuadradoDeImportacao from './quadradoDeImportacao';
import '../../styles/input.css'

/**
 * Componente React que gerencia a sequência de animações de importação para diferentes tipos de arquivos.
 * O componente manipula as transições entre diferentes estados de animação e controla a renderização
 * condicional do componente `QuadradoDeImportacao` com base no estado atual da animação.
 *
 * @component
 * @param {Object} props - As propriedades do componente.
 * @param {function} props.proximaAnimacao - Função para avançar para a próxima animação no componente pai.
 * @param {function} props.adicionaArquivo - Função para adicionar o arquivo ao estado no componente pai.
 * @param {string} props.nomeDaClasse - Uma classe CSS aplicada ao componente para estilização.
 */
const Input = (props) => {
  const [animacao, setAnimacao] = useState(0); // Estado que controla o índice da animação atual
  const [mata, setMata] = useState(0); // Estado que determina quando remover a animação anterior
  const [entra, setEntra] = useState(0); // Estado que determina quando iniciar a nova animação

  /**
   * Efeito colateral que avança para a próxima animação após três frames.
   * @function
   * @listens out
   */
  useEffect(() => {
    if(animacao === 2) props.proximaAnimacao(); // Avança para a próxima animação no componente pai quando atinge o limite
    const timer = setTimeout(() => {
      setMata(animacao) // Remove a animação anterior após um delay
      setEntra(animacao) // Inicia a nova animação após um delay
    }, 1000); // Delay de 1 segundo
    return () => clearTimeout(timer); // Limpa o timer quando o componente é desmontado ou antes de re-executar o efeito
  }, [animacao]);

  // Avança para o próximo frame da animação
  const proximoFrame = () => {
    setAnimacao(animacao + 1)
  }

  // Renderiza o componente
  return (
    <>
      <div className={props.nomeDaClasse} id="paginaToda" style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          height: '100vh'
      }}>
          {/* Renderiza o componente `QuadradoDeImportacao` para diferentes tipos de arquivos com animações de entrada e saída */}
          {mata < 1 && <QuadradoDeImportacao proximoFrame={proximoFrame} adicionaArquivo={props.adicionaArquivo} tipo="pedidos" nomeDaClasse={animacao === 1 ? "slideSai" : ""} />}
          {entra >= 1 && mata < 2 && <QuadradoDeImportacao proximoFrame={proximoFrame} adicionaArquivo={props.adicionaArquivo} tipo="técnicos" nomeDaClasse={"slideEntra"} />}
      </div>
    </>
  );
};

export default Input;
