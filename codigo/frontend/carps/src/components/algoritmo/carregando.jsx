import gifDeCarregamento from '../../assets/loadingGif.gif'

/**
 * Componente React para exibir uma interface de carregamento com um GIF animado e mensagens de espera.
 * Este componente é útil para indicar ao usuário que um processo está em andamento e que deve aguardar a sua conclusão.
 * 
 * @component
 * @param {Object} props - As propriedades passadas para o componente.
 * @param {string} props.nomeDaClasse - Classe CSS para estilização personalizada do componente.
 */
const Carregando = (props) => {
    return (
        <>
            {/* Contêiner principal */}
            <div className={props.nomeDaClasse} id="paginaToda" style={{
                display: 'flex',
                flexDirection: "column",
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh', // Altura total da tela
                paddingBottom: "2%" // Espaçamento inferior
            }}>
                {/* Imagem do GIF de carregamento */}
                <img src={gifDeCarregamento} style={{width: "5%"}} alt="Carregando..." />
                {/* Mensagens informando ao usuário que a operação está em processamento */}
                <h2 style={{paddingTop: "2%"}}>Por favor aguarde.</h2>
                <h2>Em poucos instantes o algoritmo descobrirá</h2>
                <h2>a alocação ideal de técnicos</h2>
            </div>
        </>
    )
}

export default Carregando;
