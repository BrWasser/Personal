import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
public class PSO {

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
        double soma = 0;

        // Verifica se cada técnico está alocado exatamente a um setor
        for (int i = 0; i < nTecnicos; i++) {
            soma = 0;
            for (int j = 0; j < nSetores; j++) {
                soma += x[i][j];
            }
            if (soma != 1) {
                return Double.MAX_VALUE; // Retorna um valor alto se a alocação for inválida
            }
        }

        // Calcula o número total de pedidos
        int nPedidos = 0;
        for (double pedido : pedidosSetor) {
            nPedidos += pedido;
        }

        // Verifica se cada pedido é atendido por pelo menos um técnico
        for (int i = 0; i < nTecnicos; i++) {
            soma = 0;
            for (int k = 0; k < nPedidos; k++) {
                soma += y[i][k];
            }
            if (soma < 1) {
                return Double.MAX_VALUE; // Retorna um valor alto se algum pedido não for atendido
            }
        }

        // Cálculo do custo do termo 1 na função objetivo
        double custoTermo1 = 0;
        double custoTermo2 = 0;

        double numerador = 0;
        double denominador = 0;
        int passo;

        // Loop sobre os técnicos
        for (int i = 0; i < nTecnicos; i++) {
            passo = 0;
            numerador = 0;
            denominador = 0;

            // Loop sobre os setores
            for (int j = 0; j < nSetores; j++) {
                // Loop sobre os pedidos no setor
                for (int k = passo; k < passo + pedidosSetor[j]; k++) {
                    numerador += x[i][j] * tempoDeslocamento[i][k] * y[i][k];
                    denominador += x[i][j] * y[i][k];
                }
                passo += pedidosSetor[j];
            }

            // Verifica se o denominador é zero
            if (denominador == 0) {
                return Double.MAX_VALUE; // Retorna um valor alto se houver divisão por zero
            }

            // Atualiza o custo do termo 1
            custoTermo1 += numerador / denominador;
        }

        // Cálculo do custo do termo 2 na função objetivo
        for (int i = 0; i < nSetores; i++) {
            numerador = 0;

            // Loop sobre os técnicos
            for (int j = 0; j < nTecnicos; j++) {
                passo = 0;

                // Loop sobre os setores
                for (int k = 0; k < nSetores; k++) {
                    // Verifica se o setor corresponde ao índice atual
                    if (k == i) {
                        // Loop sobre os pedidos no setor
                        for (int l = passo; l < passo + pedidosSetor[k]; l++) {
                            numerador += x[j][k] * y[j][l];
                        }
                    }
                    passo += pedidosSetor[k];
                }
            }

            // Atualiza o custo do termo 2
            custoTermo2 += pedidosSetor[i] - numerador;
        }

        return w1 * custoTermo1 + w2 * custoTermo2 * 2;
    }

    /**
     * Calcula o índice HG para as posições dadas.
     *
     * @param x                 Posições em X
     * @param y                 Posições em Y
     * @param tempoDeslocamento Matriz de tempos de deslocamento
     * @param pedidosSetor      Array de pedidos por setor
     * @return O índice HG calculado
     */
    static double indiceHG(double[][] x, double[][] y, double[][] tempoDeslocamento, double[] pedidosSetor) {

        double nTecnicos = tempoDeslocamento.length; // Número de técnicos a serem alocados
        double nSetores = pedidosSetor.length; // Número de setores
        double soma = 0;

        double custoTermo1 = 0;
        double custoTermo2 = 0;

        double numerador = 0;
        double denominador = 0;

        int passo;

        // Calcula o custo do termo 1 na fórmula do índice HG
        for (int i = 0; i < nTecnicos; i++) {
            passo = 0;
            numerador = 0;
            denominador = 0;

            // Loop sobre os setores
            for (int j = 0; j < nSetores; j++) {
                // Loop sobre os pedidos no setor
                for (int k = passo; k < passo + pedidosSetor[j]; k++) {
                    numerador += x[i][j] * (tempoDeslocamento[i][k] * y[i][k] + 2);
                    denominador += x[i][j] * y[i][k];
                }
                passo += pedidosSetor[j];
            }

            // Verifica se o denominador é zero
            if (denominador == 0) {
                return Double.MAX_VALUE; // Retorna um valor alto se houver divisão por zero
            }

            // Atualiza o custo do termo 1
            custoTermo1 += numerador / denominador;
        }

        // Calcula o custo do termo 2 na fórmula do índice HG
        for (int i = 0; i < nSetores; i++) {
            custoTermo2 += pedidosSetor[i];
        }

        // Calcula o índice HG com base nos custos calculados
        double intHGIndex = (8 * nTecnicos) / (custoTermo1 * custoTermo2);

        return intHGIndex;
    }

    /**
     * Executa o algoritmo PSO para otimização.
     *
     * @param n            Número de setores
     * @param m            Número de técnicos
     * @param pedidos      Array de pedidos por setor
     * @param tempoPedidos Matriz de tempos de deslocamento
     * @return Uma lista contendo a alocação final e o índice HG
     */
    static List<List<Integer>> executarPSO(int n, int m, double[] pedidos, double[][] tempoPedidos) {
        double nTecnicos = tempoPedidos.length;
        double nSetores = pedidos.length;

        double nPedidos = 0;

        for (double pedido : pedidos) {
            nPedidos += pedido;
        }

        // Gerando a população inicial
        List<Particula> populacao = new ArrayList<>();

        // Loop para criar a população inicial
        for (int i = 0; i < populacaoTamanho; i++) {
            Particula p = new Particula(nTecnicos, nSetores, nPedidos);

            // Loop para inicializar a posição das partículas na população
            for (int j = 0; j < nTecnicos; j++) {
                for (int k = 0; k < nSetores; k++) {

                    p.posicaoX[j][k] = 0;

                    for (int l = 0; l < nPedidos; l++) {
                        p.posicaoY[j][l] = new Random().nextDouble() >= 0.5 ? 1 : 0;
                    }
                }
                Random random = new Random();
                p.posicaoX[j][random.nextInt((int) nSetores)] = 1;
            }

            // Adiciona a partícula à população
            populacao.add(p);
        }

        // Inicialização de variáveis para rastrear a melhor solução global
        double melhorValorGlobal = Double.MAX_VALUE;
        double[][] melhorPosicaoGlobalX = new double[(int) nTecnicos][(int) nSetores];
        double[][] melhorPosicaoGlobalY = new double[(int) nTecnicos][(int) nPedidos];

        // Loop principal para as iterações do algoritmo PSO
        for (int iteracao = 0; iteracao < nIteracoes; iteracao++) {
            // Loop sobre cada partícula na população
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

                        // Loop para atualizar a posição Y
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

        List<List<Integer>> listaFinal = new ArrayList<>();

        // Loop para criar a lista final de alocação e índice HG
        for (int i = 0; i < nTecnicos; i++) {
            listaFinal.add(y.subList(i * (int) nSetores, (i + 1) * (int) nSetores));
        }

        List<Integer> subListaFinal = new ArrayList<>();
        subListaFinal.add(indiceHG(melhorPosicaoGlobalX, melhorPosicaoGlobalY, tempoPedidos, pedidos));

        listaFinal.add(subListaFinal);

        return listaFinal;
    }

    /**
     * Função principal para executar o algoritmo PSO.
     *
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira o número de setores");
        int n = scanner.nextInt();
        double[] pedidos = new double[n];

        int qtdPedidos = 0;

        System.out.println("Insira a quantidade de pedidos em cada setor:");
        
        // Loop para receber a quantidade de pedidos em cada setor
        for (int i = 0; i < n; i++) {
            pedidos[i] = scanner.nextDouble();
            qtdPedidos += pedidos[i];
        }

        System.out.println("Insira o número de técnicos");
        int m = scanner.nextInt();
        double[][] tempoPedidos = new double[m][qtdPedidos];

        System.out.println("Insira os tempos de deslocamentos");

        // Inicialização da matriz de tempos de deslocamentos com valores máximos
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < qtdPedidos; j++) {
                tempoPedidos[i][j] = Double.MAX_VALUE;
            }
        }

        // Loop para receber os tempos de deslocamentos
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < qtdPedidos; j++) {
                tempoPedidos[i][j] = scanner.nextDouble();
            }
        }

        List<List<Integer>> listaFinal = executarPSO(n, m, pedidos, tempoPedidos);
        System.out.println(listaFinal);
    }
}