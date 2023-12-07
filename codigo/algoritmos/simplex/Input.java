package inteli.cc6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Input para leitura e armazenamento de dados de tabelas de técnicos,
 * setores e serviços.
 */
public class Input {

    public final ArrayList<Tecnico> tecnicos;
    public final String[] setores;
    public final ArrayList<Servico> servicos;

    /**
     * Classe que armazena os dados de cada técnico.
     */
    public static class Tecnico {
        private final String id;
        private String sec;

        /**
         * Construtor da classe Tecnico.
         *
         * @param i Matrícula do técnico.
         * @param s Setor do técnico.
         */
        public Tecnico(String i, String s) {
            id = i;
            sec = s;
        }

        /**
         * Obtém a matrícula do técnico.
         *
         * @return A matrícula do técnico.
         */
        public String getMatricula() {
            return id;
        }

        /**
         * Obtém o setor do técnico.
         *
         * @return O setor do técnico.
         */
        public String getSetor() {
            return sec;
        }

        /**
         * Define o setor do técnico.
         *
         * @param set Novo setor do técnico.
         */
        public void setSetor(String set) {
            sec = set;
        }

        /**
         * Inicializa os dados dos técnicos a partir do BufferedReader.
         *
         * @param tec BufferedReader para a tabela de técnicos.
         * @return Lista de técnicos.
         * @throws IOException se ocorrer um erro durante a leitura do arquivo.
         */
        public static ArrayList<Tecnico> initializeTecnicos(BufferedReader tec) throws IOException {
            // Código de inicialização dos técnicos
        }
    }

    /**
     * Classe que armazena os dados de cada serviço.
     */
    public static class Servico {
        private final String ba;
        private final String setor_a;
        private final String data_ab;
        private final String latitude;
        private final String longitude;

        /**
         * Construtor da classe Servico.
         *
         * @param id     Identificador do serviço.
         * @param set    Setor do serviço.
         * @param dat    Data de abertura do serviço.
         * @param lat    Latitude do serviço.
         * @param lon    Longitude do serviço.
         */
        public Servico(String id, String set, String dat, String lat, String lon) {
            ba = id;
            setor_a = set;
            data_ab = dat;
            latitude = lat;
            longitude = lon;
        }

        /**
         * Obtém o identificador do serviço.
         *
         * @return O identificador do serviço.
         */
        public String getId() {
            return ba;
        }

        /**
         * Obtém a data de abertura do serviço.
         *
         * @return A data de abertura do serviço.
         */
        public String abertura() {
            return data_ab;
        }

        /**
         * Obtém o setor do serviço.
         *
         * @return O setor do serviço.
         */
        public String getSetor() {
            return setor_a;
        }

        /**
         * Obtém as coordenadas do serviço.
         *
         * @return Um array contendo a latitude e longitude do serviço.
         */
        public String[] getCoordenada() {
            return new String[]{latitude, longitude};
        }

        /**
         * Inicializa os dados dos serviços a partir do BufferedReader.
         *
         * @param serv BufferedReader para a tabela de serviços.
         * @return Lista de serviços.
         * @throws IOException se ocorrer um erro durante a leitura do arquivo.
         */
        public static ArrayList<Servico> initializeServicos(BufferedReader serv) throws IOException {
            // Código de inicialização dos serviços
        }
    }

    /**
     * Construtor que lê os dados dos arquivos de tabelas e inicializa a estrutura
     * de dados.
     *
     * @param tec  BufferedReader para a tabela de técnicos.
     * @param set  BufferedReader para a tabela de setores.
     * @param serv BufferedReader para a tabela de serviços.
     * @throws IOException se ocorrer um erro durante a leitura dos arquivos.
     */
    public Input(BufferedReader tec, BufferedReader set, BufferedReader serv) throws IOException {
        tecnicos = Tecnico.initializeTecnicos(tec);
        setores = Setor.initializeSetores(set);
        servicos = Servico.initializeServicos(serv);
    }

    /**
     * Método principal para execução do programa.
     * Lê os caminhos dos arquivos do usuário e exibe informações sobre os dados lidos.
     *
     * @param args Argumentos da linha de comando (não utilizados neste exemplo).
     * @throws IOException se ocorrer um erro durante a leitura dos arquivos.
     */
    public static void main(String[] args) throws IOException {
        String[] filesPath = new String[3];
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Tabela de técnicos: ");
            filesPath[0] = scan.nextLine();
            System.out.print("\nTabela de setores: ");
            filesPath[1] = scan.nextLine();
            System.out.print("\nTabela de serviços: ");
            filesPath[2] = scan.nextLine();
            FileReader fileRead1 = new FileReader(filesPath[0]);
            BufferedReader read1 = new BufferedReader(fileRead1);
            FileReader fileRead2 = new FileReader(filesPath[1]);
            BufferedReader read2 = new BufferedReader(fileRead2);
            FileReader fileRead3 = new FileReader(filesPath[2]);
            BufferedReader read3 = new BufferedReader(fileRead3);
            Input dados = new Input(read1, read2, read3);
            System.out.println("\nSetores: " + dados.setores.length);
            System.out.println("\nTécnicos: " + dados.tecnicos.size());
            System.out.println("\nServiços: " + dados.servicos.size());
        }
    }
}
