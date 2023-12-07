import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa uma partícula no algoritmo PSO.
 */
class Particula {
    double[][] posicaoX;
    double[][] posicaoY;

    double[][] velocidadeX;
    double[][] velocidadeY;

    double[][] melhorPosicaoX;
    double[][] melhorPosicaoY;

    double[][] melhorPosicaoGlobalX;
    double[][] melhorPosicaoGlobalY;

    double melhorValor;

    /**
     * Constrói uma nova partícula com base no número de funcionários, setores e
     * pedidos.
     *
     * @param nFuncionarios Número de funcionários
     * @param nSetores      Número de setores
     * @param nPedidos      Número de pedidos
     */
    Particula(double nFuncionarios, double nSetores, double nPedidos) {
        this.posicaoX = new double[(int) nFuncionarios][(int) nSetores];
        this.posicaoY = new double[(int) nFuncionarios][(int) nPedidos];

        this.velocidadeX = new double[(int) nFuncionarios][(int) nSetores];
        this.velocidadeY = new double[(int) nFuncionarios][(int) nPedidos];

        this.melhorPosicaoX = new double[(int) nFuncionarios][(int) nSetores];
        this.melhorPosicaoY = new double[(int) nFuncionarios][(int) nPedidos];

        this.melhorValor = Double.MAX_VALUE;
    }
}

/**
 * Implementação do algoritmo PSO para otimização.
 */
public class PSOInputTxt {

    static Double populacaoTamanho = 100.0;
    static Double nIteracoes = 100.0;

    /**
     * Função de objetivo para o algoritmo PSO.
     *
     * @param x                 Posições em X
     * @param y                 Posições em Y
     * @param tempoDeslocamento Matriz de tempos de deslocamento
     * @param pedidosSetor      Array de pedidos por setor
     * @param w1                Peso 1
     * @param w2                Peso 2
     * @return O valor da função objetivo
     */
    static double funcaoObjetivo(double[][] x, double[][] y, double[][] tempoDeslocamento, double[] pedidosSetor,
            double w1, double w2) {

        double nTecnicos = tempoDeslocamento.length; // Número de técnicos a serem alocados
        double nSetores = pedidosSetor.length; // Número de setores
        double sum = 0;

        // Verifica se cada técnico é alocado exatamente a um setor
        for (int i = 0; i < nTecnicos; i++) {
            sum = 0;
            for (int j = 0; j < nSetores; j++) {
                sum += x[i][j];
            }
            if (sum != 1) {
                return Double.MAX_VALUE;
            }
        }

        int nPedidos = 0;
        for (double pedido : pedidosSetor) {
            nPedidos += pedido;
        }

        // Verifica se cada pedido é alocado a pelo menos um técnico
        for (int i = 0; i < nTecnicos; i++) {
            sum = 0;
            for (int k = 0; k < nPedidos; k++) {
                sum += y[i][k];
            }
            if (sum < 1) {
                return Double.MAX_VALUE;
            }
        }

        double custoTermo1 = 0;
        double custoTermo2 = 0;

        double numerador = 0;
        double denominador = 0;

        int passo;

        // Calcula o custo do termo 1
        for (int i = 0; i < nTecnicos; i++) {
            passo = 0;
            numerador = 0;
            denominador = 0;

            for (int j = 0; j < nSetores; j++) {
                for (int k = passo; k < passo + pedidosSetor[j]; k++) {
                    numerador += x[i][j] * tempoDeslocamento[i][k] * y[i][k];
                    denominador += x[i][j] * y[i][k];
                }
                passo += pedidosSetor[j];
            }

            if (denominador == 0) {
                return Double.MAX_VALUE;
            }

            custoTermo1 += numerador / denominador;
        }

        // Calcula o custo do termo 2
        for (int i = 0; i < nSetores; i++) {
            numerador = 0;

            for (int j = 0; j < nTecnicos; j++) {
                passo = 0;

                for (int k = 0; k < nSetores; k++) {
                    if (k == i) {
                        for (int l = passo; l < passo + pedidosSetor[k]; l++) {
                            numerador += x[j][k] * y[j][l];
                        }
                    }
                    passo += pedidosSetor[k];
                }
            }

            custoTermo2 += pedidosSetor[i] - numerador;
        }

        return w1 * custoTermo1 + w2 * custoTermo2 * 2;
    }

    /**
     * Função principal para executar o algoritmo PSO.
     *
     * @param args Argumentos de linha de comando
     * @throws IOException Exceção de entrada/saída
     */
    public static void main(String[] args) throws IOException {
        String nomeArquivo = null;

        // Verifica se há um argumento de linha de comando iniciando com "-file=" para especificar o nome do arquivo
        for (String arg : args) {
            if (arg.startsWith("-file=")) {
                nomeArquivo = arg.substring(6);
            }
        }
        if (nomeArquivo == null)
            return;

        List<String> linhas = new ArrayList<>();

        // Lê as linhas do arquivo e armazena em uma lista
        BufferedReader entrada = new BufferedReader(new FileReader(nomeArquivo));
        try {
            String linha = null;
            while ((linha = entrada.readLine()) != null) {
                linhas.add(linha);
            }
        } finally {
            entrada.close();
        }

        int n = Integer.parseInt(linhas.get(0));
        double[] pedidos = new double[n];

        int qtdPedidos = 0;

        // Lê os pedidos alocados em cada setor da segunda linha do arquivo
        String[] partes = linhas.get(1).split("\\s+");

        for (int i = 0; i < partes.length; i++) {
            pedidos[i] = Double.parseDouble(partes[i]);
            qtdPedidos += pedidos[i];
        }

        int m = Integer.parseInt(linhas.get(2));

        double[][] tempoPedidos = new double[m][qtdPedidos];

        String[] linha;

        for (int i = 0; i < m; i++) {
            linha = linhas.get(3 + i).split("\\s+");
            for (int j = 0; j < qtdPedidos; j++) {
                tempoPedidos[i][j] = Double.parseDouble(linha[j]);
            }
        }

        double nTecnicos = m;
        double nSetores = n;

        double nPedidos = 0;

        // Calcula o número total de pedidos
        for (double pedido : pedidos) {
            nPedidos += pedido;
        }

        // Gerando a população inicial
        List<Particula> populacao = new ArrayList<>();

        // Loop para criar a população inicial
        for (int i = 0; i < populacaoTamanho; i++) {

            Particula p = new Particula(nTecnicos, nSetores, nPedidos);

            for (int j = 0; j < nTecnicos; j++) {
                for (int k = 0; k < nSetores; k++) {

                    p.posicaoX[j][k] = 0;

                    // Loop para inicializar posições Y da partícula
                    for (int l = 0; l < nPedidos; l++) {
                        p.posicaoY[j][l] = new Random().nextDouble() >= 0.5 ? 1 : 0;
                    }

                }
                Random random = new Random();
                p.posicaoX[j][random.nextInt((int) nSetores)] = 1;
            }

            populacao.add(p);
        }

        double melhorValorGlobal = Double.MAX_VALUE;
        double[][] melhorPosicaoGlobalX = new double[(int) nTecnicos][(int) nSetores];
        double[][] melhorPosicaoGlobalY = new double[(int) nTecnicos][(int) nPedidos];

        /*
         * Iniciando o algoritmo de BPSO para encontrar a melhor solução de valores
         * x[i][j] e y[i][j][k]
         */
        for (int iteracao = 0; iteracao < nIteracoes; iteracao++) {

            // Loop para cada partícula na população
            for (Particula p : populacao) {
                double valorAtual = funcaoObjetivo(p.posicaoX, p.posicaoY, tempoPedidos, pedidos, 1, 1);

                if (valorAtual < p.melhorValor) {
                    p.melhorValor = valorAtual;

                    System.arraycopy(p.posicaoX, 0, p.melhorPosicaoX, 0, (int) nTecnicos);
                    System.arraycopy(p.posicaoY, 0, p.melhorPosicaoY, 0, (int) nTecnicos);
                }

                if (valorAtual < melhorValorGlobal) {
                    melhorValorGlobal = valorAtual;

                    for (int i = 0; i < nTecnicos; i++) {
                        for (int j = 0; j < nSetores; j++) {
                            melhorPosicaoGlobalX[i][j] = p.posicaoX[i][j];
                            for (int k = 0; k < nPedidos; k++) {
                                melhorPosicaoGlobalY[i][k] = p.posicaoY[i][k];
                            }
                        }
                    }
                }

                // Loop para calcular e atualizar a velocidade e posição X e Y
                for (int j = 0; j < nTecnicos; j++) {
                    for (int k = 0; k < nSetores; k++) {

                        p.velocidadeX[j][k] = p.velocidadeX[j][k]
                                + (p.melhorPosicaoX[j][k] - p.posicaoX[j][k])
                                + (melhorPosicaoGlobalX[j][k] - p.posicaoX[j][k]);

                        p.posicaoX[j][k] = p.posicaoX[j][k] + p.velocidadeX[j][k];

                        Random random = new Random();

                        if (random.nextDouble() < 1 / (1 + Math.exp(-p.posicaoX[j][k]))) {
                            p.posicaoX[j][k] = 1;
                        } else {
                            p.posicaoX[j][k] = 0;
                        }

                        for (int l = 0; l < nPedidos; l++) {
                            double r1 = new Random().nextDouble();
                            double r2 = new Random().nextDouble();

                            p.velocidadeY[j][l] = p.velocidadeY[j][l]
                                    + r1 * (p.melhorPosicaoY[j][l] - p.posicaoY[j][l])
                                    + r2 * (melhorPosicaoGlobalY[j][l] - p.posicaoY[j][l]);

                            p.posicaoY[j][l] = p.posicaoY[j][l] + p.velocidadeY[j][l];

                            if (random.nextDouble() < 1 / (1 + Math.exp(-p.posicaoY[j][l]))) {
                                p.posicaoY[j][l] = 1;
                            } else {
                                p.posicaoY[j][l] = 0;
                            }
                        }
                    }
                }
            }
        }

        /*
         * Iniciando o algoritmo de BSPO para encontrar a melhor solução de valores
         * x[i][j] e y[i][j][k]
         */
        List<Integer> y = new ArrayList<>();
        for (int i = 0; i < nTecnicos; i++) {
            for (int j = 0; j < nSetores; j++) {
                y.add((int) melhorPosicaoGlobalX[i][j]);
            }
        }

        System.out.println("\n");

        // Loop para exibir a alocação final dos técnicos
        for (int i = 0; i < nTecnicos * nSetores; i++) {
            if (y.get(i) == 1) {
                System.out.println(
                        "Técnico " + (int) (i / nSetores + 1) + " alocado ao setor " + (int) (i % nSetores + 1));
            }
        }

        List<List<Integer>> finalList = new ArrayList<>();

        // Loop para criar a lista final de alocação
        for (int i = 0; i < nTecnicos; i++) {
            finalList.add(y.subList(i * (int) nSetores, (i + 1) * (int) nSetores));
            System.out.println(y.subList(i * (int) nSetores, (i + 1) * (int) nSetores));
        }
    }
}
