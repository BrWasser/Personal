package algoritmo_proporcionalidade;

import java.util.ArrayList;

/**
 * A classe Setor representa um setor dentro de uma organização, com recursos e cargas de trabalho específicas.
 * Ela contém informações sobre o número de técnicos, serviços, e as interações com outros setores.
 */
public class Setor {
    private int tecnicosIniciais;
    private String nome;
    private int servicos;
    private double porcentagemInicialDoTotalDeTecnicos;
    private double porcentagemDoTotalDeServicos;
    private double[] centro;
    private int totalDeTecnicos;
    private int novosTecnicos;
    private ArrayList<Setor> deOndeRecebe = new ArrayList<>();
    private ArrayList<Setor> paraOndeManda = new ArrayList<>();

    /**
     * Construtor para inicializar um novo Setor com os parâmetros especificados.
     *
     * @param tecnicosIniciais                     Número inicial de técnicos no setor.
     * @param nome                                 Nome do setor.
     * @param servicos                             Quantidade de serviços que o setor oferece.
     * @param porcentagemInicialDoTotalDeTecnicos  Porcentagem inicial de técnicos do setor em relação ao total.
     * @param porcentagemDoTotalDeServicos         Porcentagem de serviços que o setor oferece em relação ao total.
     * @param centro                               Coordenadas centrais do setor.
     * @param totalDeTecnicos                      Número total de técnicos considerados na organização.
     */
    public Setor(int tecnicosIniciais, String nome, int servicos, double porcentagemInicialDoTotalDeTecnicos, double porcentagemDoTotalDeServicos, double[] centro, int totalDeTecnicos) {
        this.tecnicosIniciais = tecnicosIniciais;
        this.nome = nome;
        this.servicos = servicos;
        this.porcentagemInicialDoTotalDeTecnicos = porcentagemInicialDoTotalDeTecnicos;
        this.porcentagemDoTotalDeServicos = porcentagemDoTotalDeServicos;
        this.centro = centro;
        this.totalDeTecnicos = totalDeTecnicos;
    }

    /**
     * Obtém o número inicial de técnicos no setor.
     *
     * @return O número inicial de técnicos.
     */
    public int getTecnicosIniciais() {
        return tecnicosIniciais;
    }

    /**
     * Obtém o nome do setor.
     *
     * @return O nome do setor.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o número de serviços prestados pelo setor.
     *
     * @return A quantidade de serviços.
     */
    public int getServicos() {
        return servicos;
    }

    /**
     * Obtém a porcentagem inicial de técnicos no setor em relação ao total de técnicos.
     *
     * @return A porcentagem inicial de técnicos.
     */
    public double getPorcentagemInicialDoTotalDeTecnicos() {
        return porcentagemInicialDoTotalDeTecnicos;
    }

    /**
     * Calcula e retorna a porcentagem final de técnicos no setor após as alterações.
     *
     * @return A porcentagem final de técnicos no setor.
     */
    public double getPorcentagemFinalDoTotalDeTecnicos() {
        return ((double) (tecnicosIniciais + novosTecnicos) / totalDeTecnicos) * 100;
    }

    /**
     * Obtém a porcentagem de serviços prestados pelo setor em relação ao total de serviços.
     *
     * @return A porcentagem de serviços.
     */
    public double getPorcentagemDoTotalDeServicos() {
        return porcentagemDoTotalDeServicos;
    }

    /**
     * Obtém as coordenadas do centro do setor.
     *
     * @return Um array de double representando as coordenadas.
     */
    public double[] getCentro() {
        return centro;
    }

    /**
     * Obtém o número de novos técnicos alocados ao setor.
     *
     * @return O número de novos técnicos.
     */
    public int getNovosTecnicos() {
        return novosTecnicos;
    }

    /**
     * Incrementa o número de novos técnicos no setor.
     */
    public void adicionaTecnico() {
        novosTecnicos++;
    }

    /**
     * Decrementa o número de novos técnicos no setor.
     */
    public void removeTecnico() {
        if (novosTecnicos > 0) {
            novosTecnicos--;
        }
    }

    /**
     * Calcula a diferença percentual entre a porcentagem final de técnicos e a porcentagem de serviços.
     *
     * @return A diferença percentual calculada.
     */
    public double getDiferencaFinalDePorcentagem() {
        return getPorcentagemFinalDoTotalDeTecnicos() - porcentagemDoTotalDeServicos;
    }

    /**
     * Obtém a lista de setores dos quais este setor recebe técnicos.
     *
     * @return Uma lista de setores.
     */
    public ArrayList<Setor> getDeOndeRecebe() {
        return deOndeRecebe;
    }

    /**
     * Obtém a lista de setores para os quais este setor envia técnicos.
     *
     * @return Uma lista de setores.
     */
    public ArrayList<Setor> getParaOndeManda() {
        return paraOndeManda;
    }

    /**
     * Registra que este setor recebe técnicos de outro setor.
     *
     * @param setor O setor do qual este recebe técnicos.
     */
    public void recebeDe(Setor setor) {
        deOndeRecebe.add(setor);
    }

    /**
     * Registra que este setor envia técnicos para outro setor.
     *
     * @param setor O setor para o qual este envia técnicos.
     */
    public void enviaPara(Setor setor) {
        paraOndeManda.add(setor);
    }
}
