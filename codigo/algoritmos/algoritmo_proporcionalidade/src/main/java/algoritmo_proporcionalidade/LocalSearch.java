package algoritmo_proporcionalidade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
LocalSearch(): É o construtor da classe que inicia o processo de busca local. Ele faz sentido estar aqui porque é responsável por preparar o ambiente necessário para a execução do algoritmo, incluindo a leitura de dados dos arquivos.

lerCaminhoValido(Scanner scanner, String mensagem): Auxilia no processo de entrada de dados, garantindo que os caminhos dos arquivos fornecidos sejam válidos. Essencial para a inicialização adequada dos dados que o algoritmo necessita.

calculaDiferencaTotalDePorcentagem(): Calcula a soma das diferenças absolutas de porcentagem entre técnicos e serviços, indicando o quão balanceada está a distribuição atual. É relevante para o objetivo de equilibrar a carga de trabalho entre os setores.

calculaPontoCentral(double[] coordenadas): Determina o ponto central de um polígono que representa um setor. Fundamental para o cálculo de distâncias, que é parte integral da minimização da distância total percorrida.

alocaQtdIdealDeTecnicos(): Realiza a alocação ideal de técnicos nos setores para equilibrar a proporção de técnicos por serviço. É o núcleo do algoritmo de busca local.

mostraSetores(): Apresenta informações detalhadas sobre cada setor, como nome, quantidade de técnicos e serviços. Fornece transparência sobre o estado inicial e ajuda na verificação dos resultados do algoritmo.

mostraQtdIdealDeAlocacao(): Exibe a quantidade ideal de técnicos que cada setor deve ter após a execução do algoritmo. Serve como uma forma direta de visualizar o resultado do processo de alocação.

getDistanciaTotal(): Retorna a distância total percorrida, o que é útil para avaliar a eficiência do algoritmo na minimização da distância percorrida.

adicionarDistancia(double distancia): Acrescenta uma distância ao total acumulado, mantendo o controle sobre a distância total que será percorrida pelos técnicos.

calcularDistanciaEntrePontos(double[] coordenadasPonto1, double[] coordenadasPonto2): Computa a distância em quilômetros entre dois pontos geográficos, sendo crítico para o cálculo da distância total.

encontraIndiceDoMenor(double[] lista): Identifica o índice do menor valor em um array, uma operação necessária durante a alocação gulosa de técnicos para setores próximos.

alocacaoInicialDosEnviosDeTecnicos(): Realiza a distribuição inicial dos técnicos baseada em uma abordagem gulosa, escolhendo sempre o setor mais próximo, o que é uma parte essencial do algoritmo para iniciar a distribuição dos técnicos.

contaFrequencias(List<Setor> lista): Contabiliza quantos técnicos cada setor recebe e envia, fornecendo uma visão quantitativa das movimentações realizadas.

mostraDeOndeRecebeEParaOndeManda(): Apresenta um resumo das movimentações de técnicos entre setores, o que é importante para entender como os técnicos estão sendo realocados.

Esses métodos estão agrupados na classe LocalSearch porque juntos eles compõem o algoritmo completo de busca local para a alocação de técnicos. Eles representam as etapas lógicas desde a entrada de dados até a apresentação do resultado final, seguindo o fluxo necessário para a solução do problema de alocação e otimização.
 */


/**
 * Classe LocalSearch que realiza a alocação de técnicos por setores
 * baseada em uma busca local para equilibrar a quantidade de serviços.
 * Ela também explicíta de onde para onde os técnicos devem se deslocar para minimizar a distância total percorrida.
 * Ela recebe os dados em csv e retorna a alocação final no terminal.
 * A alocação a fim de equilibrar a porcentagem é ótima, mas a minimização da distância percorrida não necessariamente, por se tratar de um algoritmo guloso.
 */
public class LocalSearch {
    private ArrayList<Setor> setores = new ArrayList<Setor>();
    private int qtdTecnicos;
    private int qtdServicos;
    private double distanciaTotal;
    private double[][] matrizDeDistancias;

    /**
     * Construtor que inicializa o processo de busca local.
     * Lê os dados de arquivos especificados pelo usuário e inicializa a alocação dos técnicos.
     */
    public LocalSearch() {

        Scanner scanner = new Scanner(System.in); // Cria um objeto Scanner

        // Define as variáveis para armazenar os caminhos dos arquivos
        String dadosTecnicos;
        String dadosSetores;
        String dadosPoligonos;

        // Método para ler um caminho de arquivo válido
        dadosTecnicos = lerCaminhoValido(scanner, "Digite o caminho para o arquivo de dados dos técnicos, (por exemplo .\\codigo\\dados\\tecnicos_setor_pr_curitiba.txt: ");
        dadosSetores = lerCaminhoValido(scanner, "Digite o caminho para o arquivo de dados dos serviços, (por exemplo .\\codigo\\dados\\servicos_setembro_pr_curitiba.txt: ");
        dadosPoligonos = lerCaminhoValido(scanner, "Digite o caminho para o arquivo de dados dos polígonos, (por exemplo .\\codigo\\dados\\poligonos.txt: ");

        //String dadosTecnicos = ".\\codigo\\dados\\tecnicos_setor_pr_curitiba.txt";
        //String dadosSetores = ".\\codigo\\dados\\servicos_setembro_pr_curitiba.txt";
        //String dadosPoligonos = ".\\codigo\\dados\\poligonos.txt";

        // Lê os dados dos técnicos
        ArrayList<String> listaDeSetoresDeTecnicos = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dadosTecnicos))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(";");
                
                if(i > 0) {
                    String setor = columns[3];
                    listaDeSetoresDeTecnicos.add(setor);
                }
                i++;
            }
            qtdTecnicos = i - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lê os dados dos serviços
        ArrayList<String> listaDeSetoresDeServicos = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dadosSetores))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                
                if(i > 0) {
                    String setor = columns[5];
                    listaDeSetoresDeServicos.add(setor);
                }
                i++;
            }
            qtdServicos = i - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lê os dados dos polígonos
        ArrayList<double[]> listaDePoligonos = new ArrayList<double[]>();
        ArrayList<String> listaDoNomeDosPoligonos = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(dadosPoligonos));) {
            String[] columns = null;
            int i = 0;
            while ((columns = csvReader.readNext()) != null) {
                if(i > 0) {
                    double latMin = Double.parseDouble(columns[3].replace(",", "."));
                    double latMax = Double.parseDouble(columns[4].replace(",", "."));
                    double longMin = Double.parseDouble(columns[5].replace(",", "."));
                    double longMax = Double.parseDouble(columns[6].replace(",", "."));
                    double[] poligono = new double[] {latMin, latMax, longMin, longMax};
                    listaDePoligonos.add(poligono);
                    listaDoNomeDosPoligonos.add(columns[0]);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        // Constrói a lista de objetos do tipo Setor
        HashSet<String> setoresUnicos = new HashSet<String>(listaDeSetoresDeTecnicos);
        for (String setor : setoresUnicos) {
            int quantidadeDeTecnicosNoSetor = 0;
            int quantidadeDeServicosNoSetor = 0;
            double[] centro = new double[2];
            // Calcula a quantidade de tecnicos no setor
            for (String valor : listaDeSetoresDeTecnicos) {
                if (valor.equals(setor)) {quantidadeDeTecnicosNoSetor++;}
            }
            // Calcula a quantidade de servicos no setor
            for (String valor : listaDeSetoresDeServicos) {
                if (valor.equals(setor)) {quantidadeDeServicosNoSetor++;}
            }
            // Calcula a o ponto central do setor
            for (int i = 0; i < listaDoNomeDosPoligonos.size(); i++) {
                if (listaDoNomeDosPoligonos.get(i).equals(setor)) {
                    centro = calculaPontoCentral(listaDePoligonos.get(i));
                }
            }
            // Adiciona o setor a lista de setores
            setores.add(new Setor(quantidadeDeTecnicosNoSetor, setor, quantidadeDeServicosNoSetor, ((double) quantidadeDeTecnicosNoSetor / (double) qtdTecnicos) * 100, ((double) quantidadeDeServicosNoSetor / (double) qtdServicos) * 100, centro, qtdTecnicos));
        }

        // Constrói a matriz de distâncias entre os centros dos setores
        matrizDeDistancias = new double[setores.size()][setores.size()];
        for (int i = 0; i < setores.size(); i++) {
            matrizDeDistancias[i] = new double[setores.size()];
            for (int j = 0; j < setores.size(); j++) {
                if (i == j) {matrizDeDistancias[i][j] = Double.MAX_VALUE;}
                else {
                    matrizDeDistancias[i][j] = calcularDistanciaEntrePontos(setores.get(i).getCentro(), setores.get(j).getCentro());
                }
            }
        }

    }

    /**
     * Solicita ao usuário um caminho de arquivo válido repetidamente até que um seja fornecido.
     *
     * @param scanner Objeto Scanner para ler a entrada do usuário.
     * @param mensagem Mensagem de solicitação para exibir ao usuário.
     * @return Uma string contendo o caminho do arquivo validado.
     */
    // Método auxiliar para ler um caminho de arquivo válido
    private String lerCaminhoValido(Scanner scanner, String mensagem) {
        String caminhoDoArquivo;
        File arquivo;
        while (true) {
            System.out.println(mensagem);
            caminhoDoArquivo = scanner.nextLine();
            arquivo = new File(caminhoDoArquivo);
            if (arquivo.exists() && !arquivo.isDirectory()) {
                return caminhoDoArquivo;
            } else {
                System.out.println("O caminho não é válido. Por favor, insira um caminho válido.");
            }
        }
    }

    /**
     * Calcula a diferença total de porcentagem entre técnicos e serviços em todos os setores.
     *
     * @return A soma das diferenças absolutas de porcentagem.
     */
    private double calculaDiferencaTotalDePorcentagem() {
        double difTotal = 0;
        for (Setor setor : setores) {
            double dif = Math.abs(setor.getDiferencaFinalDePorcentagem());
            difTotal += dif;
        }
        return difTotal;
    }

    /**
     * Calcula o ponto central de um polígono representado por coordenadas.
     *
     * @param coordenadas Um array de doubles representando as coordenadas mínimas e máximas de latitude e longitude.
     * @return Um array de doubles contendo as coordenadas do ponto central.
     */
    private double[] calculaPontoCentral(double[] coordenadas) {
        return new double[] {(coordenadas[0] + coordenadas[1]) / (double) 2, (coordenadas[2] + coordenadas[3]) / (double) 2};
    }

    /**
     * Aloca a quantidade ideal de técnicos para cada setor com base na diferença de porcentagem.
     */
    public void alocaQtdIdealDeTecnicos() {
        while (true) {
            int numeroDeVezesQueADifNaoMudou = 0;
            Setor setorQuePrecisaReceberMaisTecnicos = setores.get(0);
            Setor setorQuePrecisaEnviarMaisTecnicos = setores.get(0);
            for (Setor setor : setores) {
                if (setor.getDiferencaFinalDePorcentagem() < setorQuePrecisaReceberMaisTecnicos.getDiferencaFinalDePorcentagem()) {
                    setorQuePrecisaReceberMaisTecnicos = setor;
                } else if (setor.getDiferencaFinalDePorcentagem() > setorQuePrecisaEnviarMaisTecnicos.getDiferencaFinalDePorcentagem()) {
                    setorQuePrecisaEnviarMaisTecnicos = setor;
                }
            }
            double antigaDif = calculaDiferencaTotalDePorcentagem();
            setorQuePrecisaReceberMaisTecnicos.adicionaTecnico();
            setorQuePrecisaEnviarMaisTecnicos.removeTecnico();
            double novaDif = calculaDiferencaTotalDePorcentagem();
            if (novaDif > antigaDif) {
                setorQuePrecisaReceberMaisTecnicos.removeTecnico();
                setorQuePrecisaEnviarMaisTecnicos.adicionaTecnico();
                break;
            } else if (novaDif == antigaDif) {
                numeroDeVezesQueADifNaoMudou++;
            }
            if (numeroDeVezesQueADifNaoMudou > 30) { break;}
        }
    }

    /**
     * Exibe informações sobre cada setor, incluindo nome, quantidade inicial e final de técnicos, serviços e porcentagens.
     */
    public void mostraSetores() {
        for (Setor setor : setores) {
            System.out.println("Nome: " + setor.getNome() + " / " + "Técnicos: " + setor.getTecnicosIniciais() + " / " + "Serviços: " + setor.getServicos() + " / " +
                   "Porcentagem de técnicos: " + String.format("%.2f", setor.getPorcentagemInicialDoTotalDeTecnicos()) + "% / " +
                   "Porcentagem de serviços: " + String.format("%.2f", setor.getPorcentagemDoTotalDeServicos()) + "%" + 
                   " / lat: " + String.format("%.2f", setor.getCentro()[0]) + " / long: " + String.format("%.2f", setor.getCentro()[1]));
        }
    }

    /**
     * Exibe a quantidade ideal de alocação de técnicos para cada setor.
     */
    public void mostraQtdIdealDeAlocacao() {
        for (Setor setor : setores) {
            System.out.println("Nome: " + setor.getNome() + "  /  " + "Técnicos iniciais: " + setor.getTecnicosIniciais() + "  /  Técnicos finais: " + (setor.getTecnicosIniciais() + setor.getNovosTecnicos()));
        }
    }

    /**
     * Retorna a distância total calculada entre os pontos.
     *
     * @return A distância total como um double.
     */
    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    /**
     * Adiciona uma distância ao total acumulado de distâncias.
     *
     * @param distancia A distância a ser adicionada ao total.
     */
    public void adicionarDistancia(double distancia) {
        distanciaTotal += distancia;
    }

    /**
     * Calcula a distância entre dois pontos em quilômetros.
     *
     * @param coordenadasPonto1 array contendo a latitude e longitude do ponto 1 em graus.
     * @param coordenadasPonto2 array contendo a latitude e longitude do ponto 2 em graus.
     * @return a distância em quilômetros entre os dois pontos.
     */
    public double calcularDistanciaEntrePontos(double[] coordenadasPonto1, double[] coordenadasPonto2) {
        double raioDaTerra = 6371.0;
        double lat1Radianos = Math.toRadians(coordenadasPonto1[0]);
        double lon1Radianos = Math.toRadians(coordenadasPonto1[1]);
        double lat2Radianos = Math.toRadians(coordenadasPonto2[0]);
        double lon2Radianos = Math.toRadians(coordenadasPonto2[1]);

        // Componentes da fórmula Haversine
        double deltaLat = lat2Radianos - lat1Radianos;
        double deltaLon = lon2Radianos - lon1Radianos;
        double semiCordaAoQuadrado = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                                      Math.cos(lat1Radianos) * Math.cos(lat2Radianos) *
                                      Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double distanciaAngular = 2 * Math.atan2(Math.sqrt(semiCordaAoQuadrado), Math.sqrt(1 - semiCordaAoQuadrado));
        
        // Distância em quilômetros
        return raioDaTerra * distanciaAngular;
    }

    /**
     * Encontra o índice do menor valor em um array de doubles.
     *
     * @param lista O array de doubles para procurar.
     * @return O índice do menor valor no array.
     */
    private int encontraIndiceDoMenor(double[] lista) {
        int indice = 0;
        double menorValor = Integer.MAX_VALUE;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i] < menorValor) {
                menorValor = lista[i];
                indice = i;
            }
        }
        return indice;
    }

    /**
     * Realiza a alocação inicial dos técnicos enviando-os para setores próximos de forma gulosa.
     */
    public void alocacaoInicialDosEnviosDeTecnicos() {
        for (int i = 0; i < setores.size(); i++) {
            // Constrói uma cópia da matriz de distâncias entre os setores
            double[][] copiaDaMatrizDeDistancias = new double[setores.size()][setores.size()];
            for (int j = 0; j < setores.size(); j++) {
                for (int k = 0; k < setores.size(); k++) {
                    copiaDaMatrizDeDistancias[i][k] = matrizDeDistancias[i][k];
                }
        }
            // Manda os técnicos de forma gulosa, pensando sempre no setor mais próximo
            while (setores.get(i).getNovosTecnicos() > setores.get(i).getDeOndeRecebe().size()) {
                int proximoMaisPerto = encontraIndiceDoMenor(copiaDaMatrizDeDistancias[i]);
                if (setores.get(proximoMaisPerto).getNovosTecnicos() + setores.get(proximoMaisPerto).getParaOndeManda().size() < 0) {
                    System.out.println(calcularDistanciaEntrePontos(setores.get(0).getCentro(), setores.get(1).getCentro()));
                    setores.get(i).recebeDe(setores.get(proximoMaisPerto));
                    setores.get(proximoMaisPerto).enviaPara(setores.get(i));
                } else {
                    copiaDaMatrizDeDistancias[i][proximoMaisPerto] = Integer.MAX_VALUE;
                }
            }
            while (Math.abs(setores.get(i).getNovosTecnicos()) < setores.get(i).getParaOndeManda().size()) {
                int proximoMaisPerto = encontraIndiceDoMenor(copiaDaMatrizDeDistancias[i]);
                if (setores.get(proximoMaisPerto).getNovosTecnicos() - setores.get(proximoMaisPerto).getDeOndeRecebe().size() > 0) {
                    System.out.println(matrizDeDistancias[i][proximoMaisPerto]);
                    distanciaTotal += matrizDeDistancias[i][proximoMaisPerto];
                    setores.get(i).enviaPara(setores.get(proximoMaisPerto));
                    setores.get(proximoMaisPerto).recebeDe(setores.get(i));
                } else {
                    copiaDaMatrizDeDistancias[i][proximoMaisPerto] = Integer.MAX_VALUE;
                }
            }
        }
    }

    /**
     * Conta as frequências dos setores em uma lista de setores.
     *
     * @param lista A lista de setores a ser analisada.
     * @return Um mapa associando cada setor à sua frequência na lista.
     */
    public static Map<Setor, Integer> contaFrequencias(List<Setor> lista) {
        Map<Setor, Integer> mapaDeFrequencias = new HashMap<>();
        for (Setor nome : lista) {
            mapaDeFrequencias.put(nome, mapaDeFrequencias.getOrDefault(nome, 0) + 1);
        }
        return mapaDeFrequencias;
    }

    /**
     * Exibe de quais setores cada setor recebe técnicos e para quais setores envia técnicos.
     */
    public void mostraDeOndeRecebeEParaOndeManda() {
        for (Setor setor : setores) {
            System.out.println("Setor: " + setor.getNome());

            System.out.println("De onde recebe técnicos: ");
            Map<Setor, Integer> frequenciasRecebe = contaFrequencias(setor.getDeOndeRecebe());
            for (Map.Entry<Setor, Integer> entrada : frequenciasRecebe.entrySet()) {
                System.out.println(entrada.getKey().getNome() + ": " + entrada.getValue() + " técnicos");
            }

            System.out.println("Para onde envia técnicos: ");
            Map<Setor, Integer> frequenciasEnvia = contaFrequencias(setor.getParaOndeManda());
            for (Map.Entry<Setor, Integer> entrada : frequenciasEnvia.entrySet()) {
                System.out.println(entrada.getKey().getNome() + ": " + entrada.getValue() + " técnicos");
            }

            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    /**
     * O método principal que cria uma instância de LocalSearch e inicia o processo de alocação.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        LocalSearch localSearch = new LocalSearch();
        //localSearch.mostraSetores();
        System.out.println("Diferença inicial entre a porcentagem de técnicos e serviços presentes nos setores: " + localSearch.calculaDiferencaTotalDePorcentagem());
        localSearch.alocaQtdIdealDeTecnicos();
        System.out.println("Diferença inicial entre a porcentagem de técnicos e serviços presentes nos setores: " + localSearch.calculaDiferencaTotalDePorcentagem());
        localSearch.mostraQtdIdealDeAlocacao();
        localSearch.alocacaoInicialDosEnviosDeTecnicos();
        localSearch.mostraDeOndeRecebeEParaOndeManda();
        System.out.println();
        System.out.println("Distância total percorrida pelos técnicos deslocados: " + 
                   String.format("%.2f", localSearch.getDistanciaTotal()) + " km");
    }
}