# Algoritmo Particle Swarm Optimization 

O algoritmo Particle Swarm Optimization (PSO) é uma técnica de otimização baseada em população, inspirada no comportamento de rebanhos e cardumes na natureza. Ele é frequentemente usado para resolver problemas de otimização não linear. O PSO simula o movimento cooperativo de partículas em um espaço de busca multidimensional, visando encontrar a melhor solução possível. Cada partícula representa uma possível solução e se move pelo espaço de busca com base em sua própria melhor posição encontrada até o momento e na melhor posição global encontrada por qualquer partícula no enxame. Ao longo das iterações, as partículas ajustam suas velocidades e direções com o objetivo de convergir para a solução ótima, explorando de forma inteligente o espaço de busca. O algoritmo é eficaz na otimização de funções não lineares, independentemente da complexidade das restrições e da presença de múltiplos ótimos locais.

## Funcionamento do Algoritimo 

A classe `PSO` implementa o algoritmo Particle Swarm Optimization. Aqui está uma visão geral das funções e classes e seus respectivos propósitos dentro do algoritmo:

- **`Classe Particula:`**: A classe "Particula" é responsável por armazenar informações relacionadas a cada partícula no algoritmo Particle Swarm Optimization (PSO). Ela mantém registros das posições e velocidades das partículas, bem como as melhores posições encontradas durante a execução do algoritmo. Cada instância da classe é inicializada com valores apropriados para as posições X e Y, além de configurações iniciais para as velocidades. A classe "Particula" desempenha um papel fundamental no rastreamento e na atualização do comportamento das partículas ao longo das iterações do algoritmo PSO.

- **`objectiveFunction`**: A função "objectiveFunction" calcula o valor da função objetivo, levando em consideração a alocação de técnicos para diferentes setores, juntamente com as restrições específicas do problema. Ela considera o tempo de deslocamento, o número de pedidos em cada setor, e as relações entre as variáveis X e Y. A função retorna um valor que reflete a adequação da atribuição considerando os objetivos de minimização de custos e maximização da eficiência.

- **`main`**: A main serve como o ponto de entrada do programa. Ela inicia a execução do algoritmo PSO, solicitando entradas do usuário relacionadas aos detalhes do problema, como o número de pedidos, os pedidos em cada setor e os tempos de deslocamento. A função gera uma população inicial de partículas e itera sobre elas para encontrar a melhor solução possível. Durante as iterações, ela ajusta as posições e velocidades das partículas com base em critérios como a melhor posição global encontrada e as posições locais ótimas. Por fim, a função exibe o melhor valor global e a solução correspondente para as posições X, oferecendo insights valiosos sobre a alocação ideal de técnicos para os diferentes setores, considerando as restrições e os objetivos do problema.

## Exemplo de Uso

O algoritimo é operado através do console, onde o usuário insere inicialmente o número de pedidos, seguido pelos respectivos pedidos. Em seguida, o usuário insere o número de técnicos e, posteriormente, os tempos de deslocamento entre os técnicos e os pedidos.

Aqui está um exemplo para ilustrar o processo:

```java
Insira o número de setores:
4
Insira os pedidos:
1 1 1 1
Insira o número de técnicos
6
Insira os tempos de deslocamentos
24 777 69 73
9  11 17 13 666
22 420 555 432 671
42 4 169 29 528
573 863 7 10 9 
```

# Algoritmo Simplex

O algoritmo Simplex é um método utilizado para resolver problemas de programação linear. Sua principal finalidade é otimizar funções lineares, sujeitas a um conjunto de restrições lineares. A técnica consiste em iterativamente explorar vértices viáveis em um espaço de solução poliedral até encontrar a solução ótima.

## Funcionamento do Algoritmo

A classe `Simplex` implementa o algoritmo Simplex. Aqui está uma visão geral das funções e seu propósito dentro do algoritmo:

- **`simplex`**: Função principal que itera até que a solução ótima seja alcançada. Utiliza funções auxiliares para encontrar colunas e linhas pivô, realizando o pivoteamento para buscar a solução ideal.

- **`solucaoOtimaAlcancada`**: Verifica se a solução ótima foi alcançada no tableau, analisando a última linha para determinar se todos os valores, exceto o último, são não negativos.

- **`encontrarColunaPivo`**: Encontra a coluna pivô para o próximo pivoteamento, identificando o valor mais negativo na última linha (excluindo o último elemento).

- **`encontrarLinhaPivo`**: Determina a linha pivô com base na coluna pivô, calculando a razão entre o termo independente e o elemento na coluna pivô, escolhendo o menor valor positivo como linha pivô.

- **`fazerPivoteamento`**: Realiza as operações de pivoteamento, normalizando a linha pivô e zerando elementos nas mesmas colunas de outras linhas.

- **`exibirTableau`**: Função auxiliar para exibir o estado atual do tableau, permitindo a visualização do processo.

## Exemplo de Uso

Segue um exemplo de como utilizar a classe `Simplex` com um tableau específico:

```java
double[][] tableau = {
    {1, -3, 1, 0, 0, 0, 0},
    {0, 4, -1, 1, 0, 0, 40},
    {0, 2, -5, 0, 1, 0, 10},
    {0, 3, -2, 0, 0, 1, 30}
};

Simplex.simplex(tableau);

System.out.println("Solução Ótima:");
Simplex.exibirTableau(tableau);
```

## Classe Input

A classe `Input` tem a finalidade de ler e armazenar dados de tabelas de técnicos, setores e serviços. Esses dados são essenciais para o funcionamento do algoritmo Simplex, uma vez que fornecem informações fundamentais para a execução do programa.

## Estrutura da Classe

A classe `Input` contém os seguintes atributos:

- `tecnicos`: Uma lista de objetos da classe interna `Tecnico`, representando os técnicos e seus respectivos setores.
- `setores`: Um array de strings que armazena os diferentes setores.
- `servicos`: Uma lista de objetos da classe interna `Servico`, representando os serviços oferecidos.

## Funcionalidades da Classe

A classe `Input` possui duas classes internas: `Tecnico` e `Servico`.

- **Classe `Tecnico`**: Representa um técnico e seu setor de atuação. Possui métodos para retornar informações sobre o técnico, como sua matrícula e setor, e também para atualizar o setor.

- **Classe `Servico`**: Representa um serviço oferecido. Armazena informações como ID do serviço, setor associado, data de abertura e coordenadas geográficas. Fornece métodos para obter detalhes do serviço, como ID, data de abertura, setor e coordenadas.

## Funcionamento do Construtor

O construtor da classe `Input` é responsável por ler os dados dos arquivos de tabelas e inicializar a estrutura de dados necessária para o algoritmo.

- Ele recebe como parâmetros os `BufferedReaders` para as tabelas de técnicos, setores e serviços.
- Lê os dados das tabelas, armazenando os técnicos, setores e serviços em suas respectivas estruturas de dados.

## Método Principal

O método `main` da classe `Input` é o ponto de entrada do programa. Ele solicita ao usuário os caminhos dos arquivos das tabelas e, após a leitura e processamento dos dados, exibe informações sobre os conjuntos de dados lidos.

## Exemplo de Utilização

Aqui está um exemplo simplificado de como usar a classe `Input`:

```java
public static void main(String[] args) throws IOException {
    // Cria um scanner para obter os caminhos dos arquivos
    Scanner scan = new Scanner(System.in);

    // Solicita os caminhos das tabelas de técnicos, setores e serviços
    System.out.print("Tabela de técnicos: ");
    String filePathTecnicos = scan.nextLine();
    System.out.print("Tabela de setores: ");
    String filePathSetores = scan.nextLine();
    System.out.print("Tabela de serviços: ");
    String filePathServicos = scan.nextLine();

    // Lê os arquivos e cria a estrutura de dados a partir das tabelas
    Input dados = new Input(
        new BufferedReader(new FileReader(filePathTecnicos)),
        new BufferedReader(new FileReader(filePathSetores)),
        new BufferedReader(new FileReader(filePathServicos))
    );

    // Exibe informações sobre os dados lidos
    System.out.println("Setores: " + dados.setores.length);
    System.out.println("Técnicos: " + dados.tecnicos.size());
    System.out.println("Serviços: " + dados.servicos.size());

    // Fecha o scanner
    scan.close();
}
```