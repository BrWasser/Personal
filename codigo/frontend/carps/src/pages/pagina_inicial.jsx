/**
 * @file pagina_inicial.jsx
 * @description Componente funcional que representa a página inicial.
 */

import '../styles/PaginaInicial.css';
import LinkedinIcon from '../assets/linkedin-icon.png';

/**
 * Componente funcional que representa a página inicial.
 * @returns {React.FC}
 */
const PaginaInicial = () => {
    return (
        <>
            {/* Logo */}
            <a className="logo" href="#">Carps</a>

            {/* Barra de navegação */}
            <nav id="navbar">
                <div className="nav-links">
                    {/* Botão de cadastro */}
                    <div className="cadastro">
                        <button><a href="/cadastrar">Cadastre-se agora</a></button>
                    </div>
                    {/* Botão de entrar */}
                    <div className="entrar">
                        <button><a href="/entrar">Entrar</a></button>
                    </div>
                </div>
            </nav>

            {/* Container vazio */}
            <div class="cont"></div>

            {/* Seção sobre o Carps */}
            <div class="sobre">
                <p class="sobre-texto">O Carps representa uma inovação tecnológica por meio de um
                    software alimentado por um algoritmo
                    de otimização, destinado a aprimorar a alocação estratégica de técnicos de campo e a distribuição
                    eficiente de recursos. Seu propósito é elevar o padrão de atendimento e o nível de serviço
                    relacionados à instalação e reparo de conectividade para os profissionais da V.tal.</p>
            </div>

            {/* Rodapé */}
            <footer>
                <p>&copy; Consilium 2023</p>

                {/* Links para redes sociais */}
                <div class="redes-sociais">
                    <a href="https://www.facebook.com/vtaloficial/" target="_blank">
                        {/* Ícone do Facebook */}
                        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="24" viewBox="0 0 26 24" fill="none">
                            <path fill-rule="evenodd" clip-rule="evenodd"
                                d="M6.05713 2C3.84799 2 2.05713 3.79086 2.05713 6V18C2.05713 20.2091 3.84799 22 6.05713 22H12.2202V15.1578H9.78787V12.4913H12.2202V10.5249C12.2202 8.2427 13.6922 7 15.843 7C16.8734 7 17.7583 7.07288 18.0164 7.10528V9.49055L16.525 9.49129C15.3555 9.49129 15.1292 10.0177 15.1292 10.7892V12.492H17.9185L17.5553 15.1585H15.1292V22H18.6286C20.8377 22 22.6286 20.2091 22.6286 18V6C22.6286 3.79086 20.8377 2 18.6286 2H6.05713Z"
                                fill="#969696" />
                        </svg>
                    </a>
                    <a href="https://www.instagram.com/vtal_oficial/" target="_blank">
                        {/* Ícone do Instagram */}
                        <svg xmlns="http://www.w3.org/2000/svg" width="26" height="24" viewBox="0 0 26 24" fill="none">
                            <path fill-rule="evenodd" clip-rule="evenodd"
                                d="M13.314 2C10.5206 2 10.1709 2.01167 9.07375 2.06C5.33832 2.22667 3.26232 4.24167 3.09089 7.87667C3.04032 8.94417 3.02832 9.28417 3.02832 12C3.02832 14.7158 3.04032 15.0567 3.09003 16.1233C3.26146 19.755 5.33403 21.7733 9.07289 21.94C10.1709 21.9883 10.5206 22 13.314 22C16.1075 22 16.458 21.9883 17.5552 21.94C21.2872 21.7733 23.3683 19.7583 23.5372 16.1233C23.5878 15.0567 23.5998 14.7158 23.5998 12C23.5998 9.28417 23.5878 8.94417 23.538 7.8775C23.37 4.24917 21.2949 2.2275 17.556 2.06083C16.458 2.01167 16.1075 2 13.314 2ZM13.314 6.865C10.3972 6.865 8.03232 9.16417 8.03232 12C8.03232 14.8358 10.3972 17.1358 13.314 17.1358C16.2309 17.1358 18.5957 14.8367 18.5957 12C18.5957 9.16417 16.2309 6.865 13.314 6.865ZM13.314 15.3333C11.4206 15.3333 9.88546 13.8417 9.88546 12C9.88546 10.1592 11.4206 8.66667 13.314 8.66667C15.2075 8.66667 16.7426 10.1592 16.7426 12C16.7426 13.8417 15.2075 15.3333 13.314 15.3333ZM18.8049 5.4625C18.1226 5.4625 17.5697 6 17.5697 6.6625C17.5697 7.325 18.1226 7.8625 18.8049 7.8625C19.4863 7.8625 20.0383 7.325 20.0383 6.6625C20.0383 6 19.4863 5.4625 18.8049 5.4625Z"
                                fill="#969696" />
                        </svg>
                    </a>
                    <a href="https://www.linkedin.com/company/v-tal/" target="_blank">
                        {/* Ícone do LinkedIn */}
                        <img src={LinkedinIcon} alt="LinkedIn Icon" />
                    </a>
                    <a href="https://www.youtube.com/@vtal4153" target="_blank">
                        {/* Ícone do YouTube */}
                        <svg xmlns="http://www.w3.org/2000/svg" width="26" height="24" viewBox="0 0 26 24" fill="none">
                            <path fill-rule="evenodd" clip-rule="evenodd"
                                d="M19.784 5.15344C16.6949 4.94844 9.81462 4.94927 6.72976 5.15344C3.38948 5.3751 2.99605 7.33677 2.97119 12.5001C2.99605 17.6543 3.38605 19.6243 6.72976 19.8468C9.81548 20.0509 16.6949 20.0518 19.784 19.8468C23.1243 19.6251 23.5178 17.6634 23.5426 12.5001C23.5178 7.34594 23.1278 5.37594 19.784 5.15344ZM10.6855 15.8334V9.16677L17.5426 12.4943L10.6855 15.8334V15.8334Z"
                                fill="#969696" />
                        </svg>
                    </a>
                </div>
            </footer>
        </>
    )
}

export default PaginaInicial;