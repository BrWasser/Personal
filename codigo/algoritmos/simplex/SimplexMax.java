public class SimplexMax {
    private final double[][] tabela;

    /*
     *
     * Contrutor da classe SimplexMax
     * @param tableau é o modelo feito em uma matrix de doubles
     *
     * */
    public SimplexMax(double[][] tableau) {
        double[][] tab = tableau;
        while (!solOtima(tab)) {
            int colPivo = colunaPivo(tab);
            int linPivo = linhaPivo(tab, colPivo);
            System.out.println("Linha pivo:" + linPivo + " , coluna pivo:" + colPivo);
            System.out.println("Antes do pivo");
            System.out.println(this.mostrarTab(tab) + "\n");
            tab = simplex(tab, linPivo, colPivo);
            System.out.println("Depois do pivo");
            System.out.println(this.mostrarTab(tab) + "\n");
        }
        tabela = tab;
    }

    /*
     *
     * Encontra a coluna correta para fazer o pivoteamento
     * @param tab é o tableau no qual será feita a busca
     * @return um inteiro, representado a coluna na qual o pivô está
     *
     * */
    private int colunaPivo(double[][] tab) {
        double max = 0;
        int pos = 0;
        for (int v = 0; v < tab[0].length - 1; v++) {
            if (tab[0][v] > max) {
                pos = v;
                max = tab[0][v];
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
     * Método que representa a lógica do simplex no programa
     * @param tab é o tableau a ser consultado/alterado
     * @param lin é a linha na qual o pivo se encontra
     * @param col é a coluna na qual o pivo se encontra
     * @return uma matriz de double com um passo do simplex feito
     *
     * */
    private double[][] simplex(double[][] tab, int lin, int col) {
        double pivo = tab[lin][col];
        for (int t = 0; t < tab[0].length; t++) {
            tab[lin][t] /= pivo;
        }
        for (int l = 0; l < tab.length; l++) {
            double prop = tab[l][col];
            if (l != lin) {
                for (int c = 0; c < tab[0].length; c++) {
                    tab[l][c] -= prop * tab[lin][c];
                }
            }
        }
        return tab;
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
            if (a > 0) {
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
                {8, 5, 0, 0, 0},
                {1, 1, 1, 0, 6},
                {9, 5, 0, 1, 45},
        };

        SimplexMax a = new SimplexMax(tableau);

        System.out.println("Solucao otima:");
        System.out.println(a.mostrarTab(a.tabela));

    }
}
