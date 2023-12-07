/**
 * @file contato.jsx
 * @description Página de Contato que exibe um formulário para enviar mensagens.
 */

import React from 'react';
import '../styles/contato.css';
import Sidebar from '../components/Sidebar';

/**
 * Utiliza o componente Sidebar para a navegação lateral.
 *
 * @component
 * @returns {JSX.Element} Elemento JSX representando a página de Contato.
 */
const Contato = () => {
    return (
        <>
            {/* Componente Sidebar para navegação lateral */}
            <Sidebar />

            {/* Seção de Contato */}
            <div className='contato'>
                <h1>Entre em Contato</h1>
                <p className='label'>Nos envie suas dúvidas, sugestões ou comentários.</p>
                <form>
                    {/* Campo de entrada de texto para a mensagem */}
                    <input className="input-contato" type="text" placeholder='Escreva aqui...' /><br></br>

                    {/* Botão para enviar a mensagem */}
                    <button className="btn"><a href="#">ENVIAR MENSAGEM</a></button>
                </form>
            </div>
        </>
    );
}

export default Contato;