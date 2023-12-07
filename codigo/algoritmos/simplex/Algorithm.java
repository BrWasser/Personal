/*
 *
 * Este algoritmo não está finalizado pois o foco maior da sprint foi fazer com que o Simplex funcionasse
 *
 * */

/*
 *
 * Classe que vincula os dados recebidos em Input.java e os processa para fazer o Simplex
 *
 * */

import inteli.cc6.Input;
import inteli.cc6.Input.Tecnico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Algorithm {
    private final Input dados;
    private final int[] tecBySet;
    private final SimplexMin sol;


    /*
     *
     * Construtor da classe Algorithm, ele recebe os dados fornecidos, cria um tableau com eles e executa o simplex
     * @param d é todos os dados recebidos pelos arquivos .txt
     *
     * */
    public Algorithm(Input d) {
        dados = d;
        int[] secTec = new int[d.setores.length];
        for (Tecnico t : d.tecnicos) {
            for (int i = 0; i < d.setores.length; i++) {
                if (Objects.equals(t.getSetor(), d.setores[i])) {
                    secTec[i] += 1;
                    break;
                }
            }
        }
        int nSet = d.setores.length;
        tecBySet = secTec;
        double[][] consulta = new double[nSet * (nSet - 1)][2];
        int de = 0;
        int l = 0;
        while (l != nSet * (nSet - 1) - 1) {
            for (int j = 0; j < nSet; j++) {
                if (j != de) {
                    consulta[l][0] = de;
                    consulta[l][1] = j;
                    l++;
                }
            }
            de++;
        }
        double[][] tableau = new double[2 * nSet + 1][nSet * (nSet + 2) + 1];
        for (int i = 0; i < nSet * (nSet - 1); i++) {
            tableau[0][i] = -1;
        }
        for (int a = 1; a <= nSet; a++) {
            for (int b = 0; b < consulta.length; b++) {
                if (a - 1 == consulta[b][0]) {
                    tableau[a][b] = -1;
                } else if (a - 1 == consulta[b][1]) {
                    tableau[a][b] = 1;
                }
            }
        }
        int lI = 1;
        for (int k = consulta.length; k < consulta.length + nSet; k++) {
            tableau[lI][k] = -1;
            lI++;
        }
        sol = new SimplexMin(tableau, nSet);
    }


    public static void main(String[] args) throws FileNotFoundException {
        String[] filesPath = new String[3];
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Tabela de tecnicos: ");
            filesPath[0] = scan.nextLine();
            System.out.print("\nTabela de setores: ");
            filesPath[1] = scan.nextLine();
            System.out.print("\nTabela de servicos: ");
            filesPath[2] = scan.nextLine();
            FileReader fileRead1 = new FileReader(filesPath[0]);
            BufferedReader read1 = new BufferedReader(fileRead1);
            FileReader fileRead2 = new FileReader(filesPath[1]);
            BufferedReader read2 = new BufferedReader(fileRead2);
            FileReader fileRead3 = new FileReader(filesPath[2]);
            BufferedReader read3 = new BufferedReader(fileRead3);
            Input dadosTab = new Input(read1, read2, read3);
            Algorithm solucao = new Algorithm(dadosTab);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
