
import java.util.Arrays;

/**
 * Implementação do algoritmo Simplex para resolver problemas de programação
 * linear.
 */
public class Simplex {

    public final double[][] tableauF;

    /**
     * Executa o algoritmo Simplex.
     * @param tableau Uma matriz representando o tableau do problema de programação linear.
     */
    public Simplex(double[][] tableau) {
        double[][] first = faseUm(tableau);

        while (!solucaoOtimaAlcancada(first)) {
            int colunaPivo = encontrarColunaPivo(first);
            int linhaPivo = encontrarLinhaPivo(first, colunaPivo);
            exibirTableau(first);
            System.out.println("\n");
            first = fazerPivoteamento(first, linhaPivo, colunaPivo);
        }
        System.out.println("\n");
        exibirTableau(first);
        tableauF = first;
    }

    /**
     * Verifica se a solução ótima foi alcançada no tableau.
     * @param tableau O tableau a ser verificado.
     * @return Retorna true se a solução ótima foi alcançada, caso contrário, retorna false.
     */
    private boolean solucaoOtimaAlcancada(double[][] tableau) {
        for (int i = 0; i < tableau[0].length - 1; i++) {
            if (tableau[0][i] < 0) {
                return false;
            }
        }
        return true;
    }

    private double[][] faseUm(double[][] tab) {
        double[][] prim = new double[tab.length][tab[0].length];
        for (int i = 1; i < tab.length; i++) {
            prim[i] = Arrays.copyOf(tab[i], tab[0].length);
        }
        prim[0][tab[0].length - 2] = 1;
        exibirTableau(prim);
        System.out.println("\n");
        int colPivo = encontrarColunaPivoFase1(prim);
        prim = fazerPivoteamento(prim, 1, colPivo);
        exibirTableau(prim);
        System.out.println("\n");
        double[][] novaTab = new double[tab.length][tab[0].length - 1];
        novaTab[0] = remove(tab[0], tab[0].length - 2);
        for (int i = 1; i < tab.length; i++) {
            novaTab[i] = remove(prim[i], tab[0].length - 2);
        }
        ;
        exibirTableau(novaTab);
        System.out.println("\n");
        return novaTab;
    }

    private double[] remove(double[] arr, int id) {
        if (arr == null || id < 0 || id >= arr.length) {
            return arr;
        }
        double[] arrRed = new double[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i == id) {
                continue;
            }
            arrRed[j++] = arr[i];
        }
        return arrRed;
    }

    // Encontra a coluna pivô
    private int encontrarColunaPivo(double[][] tableau) {
        int colunaPivo = 0;
        double valorMinimo = Double.MAX_VALUE;

        for (int i = 0; i < tableau[0].length - 1; i++) {
            if (tableau[0][i] < valorMinimo) {
                valorMinimo = tableau[0][i];
                colunaPivo = i;
            }
        }
        return colunaPivo;
    }

    private int encontrarColunaPivoFase1(double[][] tableau) {
        int colunaPivo = 0;
        double valorMax = 0;

        for (int i = 0; i < tableau[0].length - 1; i++) {
            if (tableau[1][i] > valorMax) {
                valorMax = tableau[1][i];
                colunaPivo = i;
            }
        }
        return colunaPivo;
    }

    // Encontra a linha pivô
    private int encontrarLinhaPivo(double[][] tableau, int colunaPivo) {
        int linhaPivo = 0;
        double razaoMinima = Double.MAX_VALUE;
        for (int i = 1; i < tableau.length; i++) {
            if (tableau[i][colunaPivo] > 0) {
                double razao = tableau[i][tableau[0].length - 1] / tableau[i][colunaPivo];
                if (razao < razaoMinima && razao >= 0) {
                    razaoMinima = razao;
                    linhaPivo = i;
                }
            }
        }
        return linhaPivo;
    }

    // Realiza o pivoteamento no tableau
    private double[][] fazerPivoteamento(double[][] tableau, int linhaPivo, int colunaPivo) {
        int linhas = tableau.length;
        int colunas = tableau[0].length;
        double valorPivo = tableau[linhaPivo][colunaPivo];

        for (int i = 0; i < colunas; i++) {
            tableau[linhaPivo][i] /= valorPivo;
        }

        for (int i = 0; i < linhas; i++) {
            if (i != linhaPivo) {
                double razao = tableau[i][colunaPivo];
                for (int j = 0; j < colunas; j++) {
                    tableau[i][j] -= razao * tableau[linhaPivo][j];
                }
            }
        }
        return tableau;
    }

    /**
     * Função para exibir o tableau.
     * @param tableau O tableau a ser exibido.
     */
    private static void exibirTableau(double[][] tableau) {
        for (double[] linha : tableau) {
            for (double valor : linha) {
                System.out.print(valor + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Método principal para exemplificar o uso do algoritmo Simplex.
     */
    public static void main(String[] args) {

        double[][] tableau = {
                {-1, -3, -1, 0, 0, 0, 0, 0},
                {0, 4, -1, -1, 0, 0, 1, 40},
                {0, 2, -5, 0, 1, 0, 0, 10},
                {0, 3, -2, 0, 0, 1, 0, 30}
        };

        Simplex a = new Simplex(tableau);

        System.out.println("Solucao Otima:");
        exibirTableau(a.tableauF);
    }
}
