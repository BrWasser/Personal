import React from "react";
import '../styles/entrar.css';
import { Link } from 'react-router-dom';

import email_icon from '../assets/email.png';
import password_icon from '../assets/password.png';

/**
 * Componente funcional para a página de login.
 * @returns {JSX.Element} Elemento JSX que representa a interface de login.
 */
const Entrar = () => {
  return (
    <div className="tela-de-cadastro">
      {/* Barra de navegação */}
      <nav id="navbar">
        <a className="logo" href="/">Carps</a>
        <div className="nav-links">
          {/* Botão para voltar */}
          <button><a href="/">Voltar</a></button>
        </div>
      </nav>

      {/* Container principal */}
      <div className="container">
        {/* Cabeçalho da página */}
        <div className="header">
          <div className="text">Entrar</div>
          <div className="underline"></div>
        </div>

        {/* Campos de entrada para e-mail e senha */}
        <div className="inputs">
          {/* Campo de e-mail */}
          <div className="input">
            <img src={email_icon} alt="Email" />
            <input type="email" placeholder="Digite seu e-mail" />
          </div>

          {/* Campo de senha */}
          <div className="input">
            <img src={password_icon} alt="Password" />
            <input type="password" placeholder="Digite sua senha" />
          </div>
        </div>

        {/* Link para redefinir a senha */}
        <div className="esqueci_senha"><span>Esqueceu a senha?</span></div>

        {/* Botão de submissão */}
        <div className="submit">
          {/* Utiliza o componente Link do react-router para navegar para a página /algoritmo */}
          <Link to="/algoritmo" className="link-submeter">Avançar</Link>
        </div>
      </div>
    </div>
  );
};

export default Entrar;
