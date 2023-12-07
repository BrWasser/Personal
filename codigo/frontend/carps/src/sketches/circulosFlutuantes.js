export default function sketchCirculosFlutuantes(p) {
    let dadosCarregados = false;
    let dados = []
    let maxTecnicos;
    let circulos = [];
    let paleta = [
        p.color(174, 198, 207), // Azul pastel
        p.color(119, 221, 119), // Verde pastel
        p.color(253, 253, 150), // Amarelo pastel
        p.color(194, 143, 205), // Lilás pastel
        p.color(255, 179, 171)  // Coral pastel
    ];

    p.updateWithProps = props => {
        dados = props.dados
        maxTecnicos = Math.max(...dados.map(setor => setor.quantidadeDeTecnicosAlocados));
        const espaco = p.width / (dados.length + 1);
        for (let i = 0; i < dados.length; i++) {
            let x = espaco * (i + 1);
            let y = p.height / 2;
            let cor = interpolarCor(i / (dados.length - 1));

            circulos.push({
                x: x,
                y: y,
                tamanho: (dados[i].quantidadeDeTecnicosAlocados / maxTecnicos) * 50,
                cor: cor
            });
        }
        dadosCarregados = true; // Define a flag como verdadeira
        p.redraw()
    };
            

    p.setup = function () {
        p.createCanvas(800, 400);
        p.noLoop()
    };

    p.draw = function () {

        if (!dadosCarregados) {return}

        p.background(240);

        for (let i = 0; i < dados.length; i++) {
            p.fill(circulos[i].cor);
            p.ellipse(circulos[i].x, circulos[i].y, circulos[i].tamanho, circulos[i].tamanho);

            p.textSize(8);
            p.textAlign(p.CENTER, p.TOP);
            p.fill(0);
            p.text(dados[i].nomeDoSetor, circulos[i].x, circulos[i].y + circulos[i].tamanho / 2 + 5);
            p.text(`Técnicos: ${dados[i].quantidadeDeTecnicosAlocados}`, circulos[i].x, circulos[i].y + circulos[i].tamanho / 2 + 20);
        }

        adicionarTitulo("Círculos Estáticos - Quantidade de Técnicos por Setor");
    };

    function interpolarCor(t) {
        let cor1 = paleta[Math.floor(t * (paleta.length - 1))];
        let cor2 = paleta[Math.ceil(t * (paleta.length - 1))];
        return p.lerpColor(cor1, cor2, t * (paleta.length - 1) % 1);
    }

    function adicionarTitulo(titulo) {
        p.textSize(14);
        p.textAlign(p.CENTER);
        p.fill(0);
        p.text(titulo, p.width / 2, 20);
    }
}