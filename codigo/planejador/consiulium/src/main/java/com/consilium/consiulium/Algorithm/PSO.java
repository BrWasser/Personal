package com.consilium.consiulium.Algorithm;

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
     * Constrói uma nova partícula com base no número de funcionários, setores e pedidos.
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
    static Double nIteracoes = 10.0;

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
    static double objectiveFunction(double[][] x, double[][] y, double[][] tempoDeslocamento, double[] pedidosSetor, double w1, double w2) {

        double nTecnicos = tempoDeslocamento.length; // número de técnicos a serem alocados
        double nSetores = pedidosSetor.length; // número de setores
        double sum = 0;

        /* 
        restrição em que cada técnico deve ser alocado a um único setor
        */

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

        /* 
        restrição de relação de X com Y, em que se um setor for escolhido 
        o técnico deve ir a pelo menos um pedido neste setor
        */

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

        /* 
        calculo do 1° termo da função objetivo
        */

        int passo;

        for (int i = 0; i < nTecnicos; i++) {
            passo = 0;
            numerador = 0;
            denominador = 0;

            for (int j = 0; j < nSetores; j++) {
                for (int k = passo; k < passo+pedidosSetor[j]; k++) {
                    numerador += x[i][j]*tempoDeslocamento[i][k]*y[i][k];
                    denominador += x[i][j]*y[i][k];
                }
                passo += pedidosSetor[j];
            }

            if (denominador == 0) {
                return Double.MAX_VALUE;
            }

            custoTermo1 += numerador/denominador;
        }

        /* 
        calculo do 2° termo da função objetivo, que ele minimiza a quantidade de pedidos não atendidos
        */


        for (int i = 0; i < nSetores; i++) {
            numerador = 0;

            for (int j = 0; j < nTecnicos; j++) {
                passo = 0;

                for (int k = 0; k < nSetores; k++) {
                    if (k == i) {
                        for (int l = passo; l < passo+pedidosSetor[k]; l++) {
                            numerador += x[j][k]*y[j][l]; 
                        }
                    }
                    passo += pedidosSetor[k];
                }
            }

            custoTermo2 += pedidosSetor[i] - numerador;
        }

        return w1*custoTermo1 + w2*custoTermo2*2;
    }

    public static double HGIndex(double[][] x, double[][] y, double[][] tempoDeslocamento, double[] pedidosSetor) {

        double nTecnicos = tempoDeslocamento.length; // número de técnicos a serem alocados
        double nSetores = pedidosSetor.length; // número de setores
        double sum = 0;

        double custoTermo1 = 0;
        double custoTermo2 = 0;

        double numerador = 0;
        double denominador = 0;

        int passo;

        for (int i = 0; i < nTecnicos; i++) {
            passo = 0;
            numerador = 0;
            denominador = 0;

            for (int j = 0; j < nSetores; j++) {
                for (int k = passo; k < passo+pedidosSetor[j]; k++) {
                    numerador += x[i][j]*(tempoDeslocamento[i][k]*y[i][k]+2);
                    denominador += x[i][j]*y[i][k];
                }
                passo += pedidosSetor[j];
            }

            if (denominador == 0) {
                return Double.MAX_VALUE;
            }

            custoTermo1 += numerador/denominador;
        }

        for (int i = 0; i < nSetores; i++) {
            custoTermo2 += pedidosSetor[i] ;
        }

        double intHGIndex = (8 * nTecnicos*nTecnicos)/(custoTermo1*custoTermo2);

        return intHGIndex;
    }

    public static class ResultContainer {
        private List<List<Integer>> finalList;
        private double hGIndex;
    
        public ResultContainer(List<List<Integer>> finalList, double hGIndex) {
            this.finalList = finalList;
            this.hGIndex = hGIndex;
        }
    
        public List<List<Integer>> getFinalList() {
            return finalList;
        }
    
        public double getHGIndex() {
            return hGIndex;
        }
    }

    public static ResultContainer runPSO(int n, int m, double[] pedidos, double[][] tempoPedidos) {
        double nTecnicos = tempoPedidos.length; 
        double nSetores = pedidos.length;

        double nPedidos = 0;

        for (double pedido : pedidos) {
            nPedidos += pedido;
        }

        /* 
        Gerando a população inicial
        */
        List<Particula> populacao = new ArrayList<>();

        for (int i = 0; i < populacaoTamanho; i++) {

            Particula p = new Particula(nTecnicos, nSetores, nPedidos);

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

            populacao.add(p);
        }

        double melhorValorGlobal = Double.MAX_VALUE;
        double[][] melhorPosicaoGlobalX = new double[(int) nTecnicos][(int) nSetores];
        double[][] melhorPosicaoGlobalY = new double[(int) nTecnicos][(int) nPedidos];

        /* 
        Iniciando o algoritmo de BPSO para encontrar a melhor solução de valores x[i][j] e y[i][j][k]
        */

        for (int iteration = 0; iteration < nIteracoes; iteration++) {

            System.out.println("Iteração " + iteration + " de " + nIteracoes + " melhorGlobal " + melhorValorGlobal + " HG " + HGIndex(melhorPosicaoGlobalX, melhorPosicaoGlobalY, tempoPedidos, pedidos));

            for (Particula p : populacao) {

                double currentValue = objectiveFunction(p.posicaoX, p.posicaoY, tempoPedidos, pedidos, 1, 1);

                if (currentValue < p.melhorValor) {

                    p.melhorValor = currentValue;

                    System.arraycopy(p.posicaoX, 0, p.melhorPosicaoX, 0, (int) nTecnicos);
                    System.arraycopy(p.posicaoY, 0, p.melhorPosicaoY, 0, (int) nTecnicos);
                }

                if (currentValue < melhorValorGlobal) {

                    melhorValorGlobal = currentValue;

                    for (int i = 0; i < nTecnicos; i++) {
                        for (int j = 0; j < nSetores; j++) {
                            melhorPosicaoGlobalX[i][j] = p.posicaoX[i][j];
                            for (int k = 0; k < nPedidos; k++) {
                                melhorPosicaoGlobalY[i][k] = p.posicaoY[i][k];
                            }
                        }
                    }
                }

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
        Iniciando o algoritmo de BSPO para encontrar a melhor solução de valores x[i][j] e y[i][j][k]
        */

        List<Integer> y = new ArrayList<>();
        for (int i = 0; i < nTecnicos; i++) {
            for (int j = 0; j < nSetores; j++) {
                y.add((int) melhorPosicaoGlobalX[i][j]);
            }
        }

        System.out.println("\n");

        for (int i = 0; i < nTecnicos*nSetores; i++) {

            if (y.get(i) == 1) {
                System.out.println("Técnico " + (int) (i / nSetores + 1) + " alocado ao setor " + (int) (i % nSetores + 1));
            }
        }
        
        List<List<Integer>> finalList = new ArrayList<>();

        for (int i = 0; i < nTecnicos; i++){
            finalList.add(y.subList(i* (int) nSetores, (i+1)* (int) nSetores));
        }

        double hGIndex = HGIndex(melhorPosicaoGlobalX, melhorPosicaoGlobalY, tempoPedidos, pedidos);

        ResultContainer resultContainer = new ResultContainer(finalList, hGIndex);

        return resultContainer;
    }

    /**
     * Função principal para executar o algoritmo PSO. 
     *
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {}
}