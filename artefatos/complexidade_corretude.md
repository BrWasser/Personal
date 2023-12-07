# Complexidade e corretude do algoritmo

# Análise de Complexidade

O algoritmo implementa o Particle Swarm Optimization (PSO), um algoritmo de otimização inspirado no comportamento social de pássaros e cardumes de peixes. No contexto do algoritmo, a população é representada por partículas (técnicos) que exploram o espaço de soluções (alocação de técnicos em setores) em busca da melhor solução.

1. **Número de Iterações:**
   - O algoritmo executa um número fixo de iterações (`nIteracoes`). Isso significa que o número de iterações não muda, sendo uma operação constante.

2. **Operações Dentro das Iterações:**
   - Dentro de cada iteração, o algoritmo percorre todas as partículas na população e realiza operações como cálculos de função objetivo, comparações e atualizações de posição e velocidade. O número total de operações é proporcional ao tamanho da população (`populacaoTamanho`).

3. **Funções Específicas:**
   - As funções específicas, como a função objetivo (`funcaoObjetivo`) e o cálculo do índice HG (`indiceHG`), envolvem iterações sobre técnicos, setores e pedidos. A complexidade dessas funções é proporcional ao número de técnicos e setores.

4. **Inicialização da População:**
   - A inicialização da população envolve loops sobre técnicos, setores e pedidos, e é realizada uma vez no início do algoritmo. A complexidade dessa operação é proporcional ao tamanho da população inicial.

**Melhor e Pior Caso:**

1. **Melhor Caso:**
   - O melhor caso ocorre quando a primeira partícula avaliada atinge imediatamente a solução ótima global. Nesse cenário, o algoritmo termina após uma iteração.

2. **Pior Caso:**
   - O pior caso ocorre quando todas as partículas passam pelo número máximo de iterações (`nIteracoes`) sem convergir para a solução ótima. Nesse cenário, a complexidade é determinada pelo número de iterações multiplicado pelo tamanho da população.

**Observações Adicionais:**
- O uso de funções aleatórias (`Random`) para inicialização e atualização das partículas pode influenciar no desempenho do algoritmo, mas a análise acima não leva em consideração a complexidade dessas operações.
- A entrada de dados a partir da entrada padrão (teclado) e a exibição de resultados na saída padrão (console) não impactam significativamente a complexidade do algoritmo.

# Análise Matemática de Complexidade

### Inicialização da População:
```java
for (int i = 0; i < populacaoTamanho; i++) {
    for (int j = 0; j < nTecnicos; j++) {
        for (int k = 0; k < nSetores; k++) {
            for (int l = 0; l < nPedidos; l++) {
                // Operações de inicialização
            }
        }
    }
}
```
Complexidade: O(populacaoTamanho * nTecnicos * nSetores * nPedidos)
Número exato de operações: `populacaoTamanho * nTecnicos * nSetores * nPedidos`

### Loop Principal (Iterações do PSO):
```java
for (int iteracao = 0; iteracao < nIteracoes; iteracao++) {
    for (Particula p : populacao) {
        // Operações de avaliação e atualização
        for (int j = 0; j < nTecnicos; j++) {
            for (int k = 0; k < nSetores; k++) {
                for (int l = 0; l < nPedidos; l++) {
                    // Operações de avaliação
                }
                // Operações de atualização
            }
        }
    }
}
```
Complexidade: O(nIteracoes * populacaoTamanho * nTecnicos * nSetores * nPedidos)
Número exato de operações: `nIteracoes * populacaoTamanho * nTecnicos * nSetores * nPedidos`

### Função Objetivo (`funcaoObjetivo`):
```java
for (int i = 0; i < nTecnicos; i++) {
    for (int j = 0; j < nSetores; j++) {
        for (int k = 0; k < nPedidos; k++) {
            // Operações dentro da função objetivo
        }
    }
}
```
Complexidade: O(nTecnicos * nSetores * nPedidos)
Número exato de operações: `nTecnicos * nSetores * nPedidos`

### Índice HG (`indiceHG`):
```java
for (int i = 0; i < nTecnicos; i++) {
    for (int j = 0; j < nSetores; j++) {
        for (int k = 0; k < nPedidos; k++) {
            // Operações dentro do cálculo do índice HG
        }
    }
}
```
Complexidade: O(nTecnicos * nSetores * nPedidos)
Número exato de operações: `nTecnicos * nSetores * nPedidos`

### Atualização das Partículas:
```java
for (Particula p : populacao) {
    for (int j = 0; j < nTecnicos; j++) {
        for (int k = 0; k < nSetores; k++) {
            for (int l = 0; l < nPedidos; l++) {
                // Operações de atualização das partículas
            }
        }
    }
}
```
Complexidade: O(populacaoTamanho * nTecnicos * nSetores * nPedidos)
Número exato de operações: `populacaoTamanho * nTecnicos * nSetores * nPedidos`

A seguir, uma análise matemática da complexidade para o algoritmo PSO, considerando a notação adequada (O, Ω, Θ).

**Notação O (Big O):**

A notação O é utilizada para representar um limite superior assintótico. Vamos analisar a complexidade para o pior caso.

1. **Melhor Caso (O Notation):**
   - O melhor caso seria dominado pelas operações dentro das iterações e a inicialização da população.
   - Melhor Caso: O(P * (T * S + T * P_total + P_total))

2. **Pior Caso (O Notation):**
   - O pior caso é dominado pelo número de iterações e as operações dentro das iterações.
   - Pior Caso: O(nIteracoes * P * (T * S + T * P_total + P_total))

**Notação Ω (Omega):**

A notação Ω é utilizada para representar um limite inferior assintótico. Vamos analisar a complexidade para o melhor caso.

1. **Melhor Caso (Ω Notation):**
   - O melhor caso ocorreria se a primeira partícula atingisse imediatamente o valor ótimo global. Nesse caso, o algoritmo terminaria após uma iteração.
   - Melhor Caso: Ω(P * (T * S + T * P_total + P_total))

2. **Pior Caso (Ω Notation):**
   - O pior caso ocorreria se todas as partículas passassem pelo número máximo de iterações sem convergir para a solução ótima.
   - Pior Caso: Ω(nIteracoes * P * (T * S + T * P_total + P_total))

**Notação Θ (Theta):**

A notação Θ é utilizada quando a complexidade assintótica é igual tanto para o limite superior quanto para o limite inferior. Em casos práticos, os melhores e piores casos podem ser os mesmos.

1. **Melhor Caso (Θ Notation):**
   - Se o melhor caso e o pior caso têm a mesma complexidade (o que é possível neste contexto), então podemos usar Θ.
   - Melhor Caso: Θ(P * (T * S + T * P_total + P_total))

2. **Pior Caso (Θ Notation):**
   - Se o melhor caso e o pior caso têm a mesma complexidade (o que é possível neste contexto), então podemos usar Θ.
   - Pior Caso: Θ(nIteracoes * P * (T * S + T * P_total + P_total))