import React from 'react';
import { SvgIcon } from '@mui/material';
import '../styles/cadastro.css';
import { useState, useEffect } from 'react';
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod"



/**
 * Função para verificar se as senhas inseridas são iguais.
 * Exibe um alerta se as senhas são diferentes.
 */
function checagemSenha() {
  if (document.querySelector('#senhaInp').value !== document.querySelector('#cSenhaInp').value) {
    alert("A senha e a confirmação da senha são diferentes");
  }
}

/**
 * Função para verificar e estilizar os campos de entrada no formulário.
 */


const verificarInput = () => {
  var input = [
    document.querySelector('#nomeInp').value,
    document.querySelector('#emailInp').value,
    document.querySelector('#senhaInp').value,
    document.querySelector('#cSenhaInp').value
  ];
  var span = ["nomeSpan", "emailSpan", "senhaSpan", "cSenhaSpan"];
  for (var i = 0; i < 4; i++) {
    if (input[i] !== null && input[i] !== "") {
      document.getElementById(span[i]).style.fontSize = "10px";
      document.getElementById(span[i]).style.top = "-10px";
      document.getElementById(span[i]).style.transform = "translateY(0)";
      document.getElementById(span[i]).style.color = "#212121";
    }
  }
};

const createUserFormSchema = z.object({
  name: z.string()
    .min(4, '*Nome deve conter pelo menos 4 caracteres')
    .min(1, '*O nome é obrigatório')
    .transform(name => {
      return name.trim().split(' ').map(word => {
        return word[0].toLocaleUpperCase().concat(word.substring(1))
      }).join(' ')
    }),
  email: z.string()
    .min(1, "*O email é obrigatório")
    .email("*Insira um email válido")
    .toLowerCase(),
  password: z.string()
    .min(1, "*A senha é obrigatória")
    .min(8, "*A senha deve conter pelo menos 8 caracteres")
});


/**
 * Componente funcional para a tela de cadastro.
 *
 * @returns {JSX.Element} Componente React que representa a tela de cadastro.
 */
const TelaCadastro = () => {

  const { handleSubmit, register, formState: { errors } } = useForm({
    mode: "all",
    reValidateMode: "onChange",
    resolver: zodResolver(createUserFormSchema)
  })

  /*variavel que define o output do formulário*/
  const [output, setOutput] = useState('');

  async function createUser(dadosUsuario) {
    setOutput(JSON.stringify(dadosUsuario, null, 2));

    // Objeto com a propriedade email
    const email = {
      email: dadosUsuario.email
    };

    await fetch('http://localhost:3000/users/checkEmail', {
      method: 'POST',
      body: JSON.stringify(email),
      headers: {
        "Content-Type": "application/json"
      }
    }).then(response => {
      // Verifique se a resposta é ok e depois converta para JSON
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Email já cadastrado ou outro erro de servidor');
      }
    }).then(data => {
      if (!data.isEmailTaken) {
        chamadaCriacaoUsuario(dadosUsuario)
      } else {
        alert("Email já cadastrado")
      }
    }).catch(error => {
      console.error("Erro na requisição:", error);
      alert("Erro na requisição: " + error.message);
    });
  }

  async function chamadaCriacaoUsuario(data) {
    await fetch('http://localhost:3000/users/', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        "Content-Type": "application/json"
      }
    }).then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Erro na resposta do servidor');
      }
    }).then(data => {
      console.log(data);
    }).catch(error => {
      console.error('Erro ao fazer a requisição:', error);
    });
  }

  return (
    <div className="tela-de-cadastro" onChange={verificarInput}>
      <a className="logo" href="#">Carps</a>
      <nav id="navbar">
        <div className="nav-links">
          <button><a href="/">Voltar</a></button>
        </div>
      </nav>
      <div className="retangulo">
        <div className="break"></div>
        <p className="title">Criar uma Conta</p>
        <form className="cad" onSubmit={handleSubmit(createUser)}>
          {/* Input para o nome */}
          <div className="divInput" id="divNo">
            <label className="labelInput">
              {errors.name && <span className="spanError">{errors.name.message}</span>}
              <input type="text" className="inputCad" required id="nomeInp" {...register("name")} />
              <span className="spanInput" id="nomeSpan">NOME COMPLETO</span>
            </label>
          </div>
          {/* Input para o email */}
          <div className="divInput" id="divEm">
            <label className="labelInput">
            {errors.email && <span className="spanError">{errors.email.message}</span>}
              <input type="text" className="inputCad" required id="emailInp" {...register("email")} />
              <span className="spanInput" id="emailSpan">E-MAIL</span>
            </label>
          </div>
          {/* Input para a senha */}
          <div className="divInput" id="divSe">
            <label className="labelInput">
            {errors.password && <span className="spanError">{errors.password.message}</span>}
              <input type="password" className="inputCad" required id="senhaInp" {...register("password")} />
              <span className="spanInput" id="senhaSpan">SENHA</span>
            </label>
          </div>
          {/* Input para confirmar a senha */}
          <div className="divInput" id="divCSe">
            <label className="labelInput">
              <input type="password" className="inputCad" required id="cSenhaInp" />
              <span className="spanInput" id="cSenhaSpan">CONFIRME A SENHA</span>
            </label>
          </div>
          <div className="break"></div>
          {/* Botão para criar a conta */}
          <button className="enviar" type="submit" style={{ color: '#FFFFFF' }} onClick={() => {
            checagemSenha()

            console.log()
          }}>CRIAR UMA CONTA</button>
          <div className="break"></div>
          <div className="center">OU</div>
          <div className="break"></div>
          <div className='center'>
            {/* Ícone SVG */}
            <SvgIcon>
              <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="144" height="144" viewBox="0 0 43 43">
                {/* ... (código do ícone SVG) */}
              </svg>
            </SvgIcon>
          </div>
          <div className="break"></div>
        </form>
      </div>

      {/**
     * Importa jQuery e script.js para manipulação de eventos no lado do cliente.
     */}
      <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>
      <script src="./script.js"></script>
    </div>
  );
};

export default TelaCadastro;
