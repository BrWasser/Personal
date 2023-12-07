import React from 'react';
import '../../styles/barraDeProgresso.css';

/**
 * Componente de barra de progresso que exibe o progresso em uma barra colorida.
 * @component
 * @param {Object} props - Propriedades do componente.
 * @param {number} props.concluido - Valor que representa a porcentagem concluída da barra.
 * @returns {JSX.Element} Retorna o JSX do componente de barra de progresso.
 */
const BarraDeProgresso = (props) => {
  const { concluido } = props;

  // Estilos em linha para mudança dinâmica de largura
  const estilosDoPreenchimento = {
    height: '100%',
    width: `${concluido}%`,
    backgroundColor: 'purple',
    borderRadius: 50,
    transition: 'width 0.2s ease-in-out', // Transição suave para mudança de largura
    textAlign: 'center', // Se você quiser colocar texto dentro do preenchimento
  };

  // Estilos do container
  const estilosDoContainer = {
    height: 20,
    width: '100%',
    backgroundColor: '#e0e0de',
    borderRadius: 50,
    margin: '50px 0',
  };

  return (
    <div style={estilosDoContainer}>
      <div style={estilosDoPreenchimento}>
        {/* Opcional: Adicionando uma etiqueta de porcentagem dentro do preenchimento */}
        <span className="etiqueta-da-barra-de-progresso">{`${concluido}%`}</span>
      </div>
    </div>
  );
};

export default BarraDeProgresso;
