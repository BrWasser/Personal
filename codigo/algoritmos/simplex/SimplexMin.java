/*
 *
 * O algoritmo SimplexMin não está finalizado pois mesmo com pesquisas e consultas com os professores,
 * não foi identificado o erro de lógca, contudo, exemplos testados em outras ferramentas que possuem
 * respostas fáceis não são resolvidos corretamente.
 * A partir da próxima sprint, este algoritmo será feito por uma biblioteca
 *
 * */


import java.util.Arrays;

/*
 *
 * Contrutor da classe SimplexMin
 * @param tableau é o modelo feito em uma matrix de doubles
 * @param set é o número de setores presentes no modelo
 *
 * */

public class SimplexMin {
    private final double[][] tabela;
    private final int nSet;

    public SimplexMin(double[][] tableau, int set) {
        nSet = set;
        double[][] mem = new double[tableau.length][tableau[0].length];
        for (int a = mem[0].length - set - 1; a < mem[0].length - 1; a++) {
            mem[0][a] = -1;
        }
        for (int i = 1; i < tableau.length; i++) {
            mem[i] = Arrays.copyOf(tableau[i], tableau[0].length);
        }
        int count = 0;
        while (!solOtima(mem)) {
            int colPivo = colunaPivo(mem);
            int linPivo = linhaPivo(mem, colPivo);
            //System.out.println("Linha pivo:" + linPivo + " , coluna pivo:" + colPivo);
            //System.out.println("Antes do simplex:\n" + this.mostrarTab(mem) + "\n");
            mem = simplex(mem, linPivo, colPivo);
            //System.out.println("Depois do simplex:\n" + this.mostrarTab(mem) + "\n");
            count++;
        }
        int tamaIn = tableau[0].length;
        double[][] memDn = new double[tableau.length][tamaIn - set];
        for (int l = 1; l < tableau.length; l++) {
            System.arraycopy(mem[l], 0, memDn[l], 0, memDn[0].length - 1);
            memDn[l][memDn[0].length - 1] = mem[l][mem[0].length - 1];
        }
        double[] linIn = Arrays.copyOf(tableau[0], tableau[0].length);
        while (linIn.length != tamaIn - set) {
            linIn = remove(linIn, linIn.length - 2);
        }
        System.out.println();
        memDn[0] = linIn;
        while (!solOtima(mem)) {
            int colPivo = colunaPivo(memDn);
            int linPivo = linhaPivo(memDn, colPivo);
            //System.out.println("Antes do simplex:\n" + this.mostrarTab(mem) + "\n");
            memDn = simplex(memDn, linPivo, colPivo);
            //System.out.println("Depois do simplex:\n" + this.mostrarTab(mem) + "\n");
        }
        tabela = memDn;

    }

    /*
     *
     * Encontra a coluna correta para fazer o pivoteamento
     * @param tab é o tableau no qual será feita a busca
     * @return um inteiro, representado a coluna na qual o pivô está
     *
     * */
    private int colunaPivo(double[][] tab) {
        double min = Double.MAX_VALUE;
        int pos = 0;
        for (int v = 0; v < tab[0].length - 1; v++) {
            if (tab[0][v] < min) {
                pos = v;
                min = tab[0][v];
            }
        }
        return pos;
    }

    /*
     *
     * Encontra a linha correta para fazer o pivoteamento
     * @param tab é o tableau no qual será feita a busca
     * @param colP é a coluna a qual o pivô está
     * @return um inteiro, representado a linha na qual o pivô está
     *
     * */
    private int linhaPivo(double[][] tab, int colP) {
        double min = Double.MAX_VALUE;
        int lin = 0;
        for (int r = 1; r < tab.length; r++) {
            double div = tab[r][tab[0].length - 1] / tab[r][colP];
            if (div < min && div > 0 && tab[r][tab[0].length - 1] > 0) {
                min = tab[r][tab[0].length - 1] / tab[r][colP];
                lin = r;
            }
        }
        return lin;
    }

    /*
     *
     * Método que remove um elemento de um array
     * @param arr é array o qual será alterado
     * @param id é posição no array arr do item a ser retirado
     * @return um array de double sem o item que estava na posição especificada
     *
     * */
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

    /*
     *
     * Método que representa a lógica do simplex no programa
     * @param tab é o tableau a ser consultado/alterado
     * @param lin é a linha na qual o pivo se encontra
     * @param col é a coluna na qual o pivo se encontra
     * @return uma matriz de double com um passo do simplex feito
     *
     * */
    private double[][] simplex(double[][] tab, int lin, int col) {
        double pivo = tab[lin][col];
        System.out.println(pivo);
        double[][] memoria = new double[tab.length][tab[0].length];
        for (int t = 0; t < memoria.length; t++) {
            memoria[t] = Arrays.copyOf(tab[t], tab[t].length);
        }
        for (int t = 0; t < tab[0].length; t++) {
            memoria[lin][t] /= pivo;
        }
        for (int l = 0; l < tab.length; l++) {
            double prop = tab[l][col];
            if (l != lin) {
                for (int c = 0; c < tab[0].length; c++) {
                    memoria[l][c] -= prop * memoria[lin][c];
                }
            }
        }
        return memoria;
    }

    /*
     *
     * Método que avalia se o tableau está na solução ótima
     * @param tab é o tableau a ser verificado
     * @return um booleano que informa se está na solução ótima ou não
     *
     * */
    private boolean solOtima(double[][] tabela) {
        for (double a : tabela[0]) {
            if (a < 0) {
                return false;
            }
        }
        return true;
    }

    /*
     *
     * Método que imprime o tableau
     * @param tab é tableau a ser impresso
     * @return uma string com todos os dados do tableau
     *
     * */
    public String mostrarTab(double[][] tab) {
        StringBuilder res = new StringBuilder();
        for (double[] lin : tab) {
            for (int j = 0; j < tab[0].length; j++) {
                res.append(lin[j]).append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        double[][] tableau = {
                {-1, -1, 0, 0, 0, 0, 0, 0, 0},
                {1, -1, -1, 0, 0, 0, 1, 0, 1},
                {-1, 1, 0, -1, 0, 0, 0, 1, -1},
                {1, 0, 0, 0, 1, 0, 0, 0, 3},
                {0, 1, 0, 0, 0, 1, 0, 0, 2},
        };


        SimplexMin a = new SimplexMin(tableau, 2);

        System.out.println("Solucao otima:");
        System.out.println(a.mostrarTab(a.tabela));

    }
}
