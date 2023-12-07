/**
 * Função para configurar e renderizar um gráfico de barras usando a biblioteca p5.js.
 * Este gráfico exibe a quantidade de técnicos alocados por setor.
 * 
 * @param {object} p - O objeto p5.js que será usado para desenhar o gráfico.
 */
export default function graficoDeBarras (p) {
    // Dados de entrada para o gráfico, representando setores e a quantidade de técnicos alocados.
    let dados =[];

    // Determina o valor máximo de técnicos para a escala do gráfico.
    let maxTecnicos;

    p.updateWithProps = props => {
        console.log(props)
        dados = props.dados
        maxTecnicos = Math.max(...dados.map(setor => setor.quantidadeDeTecnicosAlocados));
        p.redraw()
    };
    
    // Função setup que inicializa o ambiente gráfico.
    p.setup = function () {
        p.createCanvas(800, 400); // Define o tamanho da tela de desenho.
        p.noLoop(); // Assegura que a função draw() não seja continuamente executada.
    };

    // Função draw que renderiza o gráfico.
    p.draw = function () {
        p.background(240); // Define a cor de fundo.

        desenharEixos(); // Chama a função para desenhar os eixos do gráfico.
        adicionarMarcacoesEixoY(maxTecnicos); // Adiciona as marcações no eixo Y.

        let espaco = 10; // Espaço entre as barras.
        let larguraBarra = (p.width - 50 - espaco * (dados.length + 1)) / dados.length; // Calcula a largura das barras.

        // Loop para desenhar as barras do gráfico.
        for (let i = 0; i < dados.length; i++) {
            // Calcula as posições e dimensões da barra.
            let x = espaco + i * (larguraBarra + espaco) + 40;
            let y = p.height - (dados[i].quantidadeDeTecnicosAlocados / maxTecnicos) * (p.height - 100) - 50;
            let alturaBarra = (dados[i].quantidadeDeTecnicosAlocados / maxTecnicos) * (p.height - 100);

            p.fill('#800080'); // Define a cor da barra.
            p.rect(x, y, larguraBarra, alturaBarra); // Desenha a barra.

            // Adiciona o texto com o nome do setor abaixo da barra.
            p.textSize(8);
            p.textAlign(p.CENTER);
            p.fill(0);
            p.text(dados[i].nomeDoSetor, x + larguraBarra / 2, p.height - 35);
        }

        // Função para adicionar o título do gráfico.
        adicionarTitulo("Gráfico de Barras - Quantidade de Técnicos por Setor");
    };

    // Desenha os eixos do gráfico.
    function desenharEixos() {
        p.stroke(0);
        p.line(40, 50, 40, p.height - 50); // Eixo Y.
        p.line(40, p.height - 50, p.width - 10, p.height - 50); // Eixo X.
    }

    // Adiciona as marcações no eixo Y.
    function adicionarMarcacoesEixoY(maxTecnicos) {
        p.textSize(10);
        p.textAlign(p.RIGHT);
        p.fill(0);

        // Adiciona cinco marcações de valor no eixo Y.
        for (let i = 0; i <= 5; i++) {
            let valor = (maxTecnicos / 5) * i;
            let y = p.height - (valor / maxTecnicos) * (p.height - 100) - 50;
            
            p.stroke(150);
            p.line(35, y, p.width - 10, y); // Linha de marcação.

            p.noStroke();
            p.text(valor.toFixed(0), 35, y + 5); // Texto de marcação.
        }
    }

    // Adiciona o título ao gráfico.
    function adicionarTitulo(titulo) {
        p.textSize(16);
        p.textAlign(p.CENTER);
        p.fill(0);
        p.text(titulo, p.width / 2, 20); // Posiciona o título no topo do canvas.
    }
}
