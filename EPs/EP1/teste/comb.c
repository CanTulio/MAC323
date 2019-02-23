#include <stdio.h>
#include <stdlib.h>

// TODO : Remover o calculador de combs, usar o i do for pra isso. O condicional do for vai ser um cara que verifica se todos numeros ja sao o maximo
// TODO : atualmente funciona PERFEITAMENTE, mas esse é um dos codigos mais feios que ja fiz KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK

void inicializaVetor(int* v, int tam) {
    for (int i = 0; i < tam; i ++) {
        v[i] = i+1;
    }
}

void imprimeVetor(int* v, int tam) {
    for (int j = 0; j < tam; j++) {
        printf("%d, ", v[j]);
    }
    printf("\n");
}


int Ncombs(int total, int n, int k ) {
    int *v  = malloc(sizeof(int)*k);
    // for (int i = 0; i < k; i ++) {
    //     v[i] = i+1;
    //     // printf("%d", v[i]);
    // }
    inicializaVetor(v, k);
    int ultimo = k - 1; // casa do ultimo item do vetor
    int max = n; // maximo valor que a ultima casa pode assumir. A iésima casa pode assumir no maximo  o valor  max = (n-k) + (i-1)
    int aux, indice;
    int verificador = 1;
    int i;
    // for( int i = 0; i < total; i++) {
    imprimeVetor(v, k);
    for( i = 0; verificador == 1; i++) {
        if (v[ultimo] < max) { // aqui não pode ser <= porque se eu aumentar ele vira max+1
            // printf("O maximo vale %d e o v[ultimo] vale %d\n", max, v[ultimo]);
            v[ultimo]++;
        }
        else { //backtracking
            while( v[ultimo] >= max) {
                ultimo--;
                max = n-k+ultimo+1; //conferir isso -> estava errado!
            }
            if (v[ultimo] == max-1 && ultimo == 0) { // a ultima combinação a ser contabilizada é aquela que o  primeiro elemento atingiu valor maximo.
                verificador = 0;
            }
            aux = v[ultimo];
            indice = ultimo;
            while(indice < k){
                v[indice] = ++aux;
                indice++;
            }
            ultimo = k-1;
        }
        max = n-k+ultimo+1;
        imprimeVetor(v, k);
    }

    return i;
}


int main() {

    int n, k;
    printf("Digite o numero de termos\n");
    scanf("%d", &n);
    printf("Digite o intervalo da combinação\n");
    scanf("%d", &k);
    // int total = combinacao(n, k);
    int total = Ncombs(total, n, k);

    printf("\ntemos %d combs", total);
    
    
    return 0;
}