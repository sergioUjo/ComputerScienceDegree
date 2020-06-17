#include "cliente.h"
#include "fila.h"
#include "Pqueue.h"
#include "caixa.h"

#include <stdio.h>
#include <stdlib.h>

void trata_primeiro( int passo, Caixa caixa_actual){

  Cliente cliente = peek_caixa(caixa_actual);
  int espera  = passo - pronta( caixa_actual );
  int processados = espera * tempo_processamento( caixa_actual );

  if ( processados >= artigos( cliente ) ){
    disponivel( caixa_actual, passo + 1);
    cliente_atendido( caixa_actual );
    actualiza_produtos(caixa_actual, artigos(cliente) );
    actualiza_espera( caixa_actual, passo - entrada(cliente) );
    remove_cliente( caixa_actual );
    printf("--> Cliente atendido na caixa %d\n", numero(caixa_actual) );
  }
}
void trata_clientes(int passo, Caixa lista_caixas[], int ncaixas){
  for( int i = 0; i < ncaixas; i++){
    if ( vazia( lista_caixas[i] ) ) disponivel( lista_caixas[i], passo);
    else trata_primeiro(passo, lista_caixas[i]);
  }
}

int caixas_cheias( Caixa lista_caixas[], int ncaixas){
  for( int i = 0; i < ncaixas; i++){
    if ( vazia( lista_caixas[i] ) -1 ) return 1;
  }
  return 0;
}

void mostra_caixas( Caixa lista_caixas[], int ncaixas){
  for( int i = 0; i < ncaixas; i++){
    print_caixa( lista_caixas[i] );
  }
}

void processa_resultados( Caixa caixas[], int ncaixas){
  for( int i = 0; i < ncaixas; i++){
    printf("Caixa %d: (%d produtos por ciclo)\n", numero( caixas[i] ), tempo_processamento( caixas[i] ));
    if ( clientes( caixas[i] ) != 0 ){
      printf("%d clientes atendidos, media %.1d produtos/cliente\n",  clientes( caixas[i] ), ( produtos( caixas[i] )/clientes( caixas[i] )));
    }
    else{
      printf("%d clientes atendidos\n", clientes( caixas[i] ));
    }
  }
}
int escolha_caixa(Cliente c, int n_caixas){
  if (c->NumItems <= 10){
    return 0;
  }
  else {
    return rand()%(n_caixas);
  }
}

void simulador( int afluencia, int apetencia, int n_caixas, int n_ciclos) {
  //inicializa caixas
  Caixa arcaixa[n_caixas];
  arcaixa[0]=mk_new_p(1);
  for (int i=1;i<n_caixas;i++){
    arcaixa[i] = mk_new(i+1);
  }
  //simulação
  int t=0;
  for (;t<n_ciclos+1;t++){
    printf("== CICLO %d==\n", t);
    trata_clientes(t,arcaixa,n_caixas);
    if ( rand()%100 <= afluencia){
      int produtos = (rand()%(3*apetencia-1))+2;
      Cliente new = mk_cliente (produtos,t);
      printf("-->Criando cliente com %d produtos.\n", produtos);
      int caixa_escolhida = escolha_caixa(new, n_caixas);
      caixa_enqueue(new,arcaixa[caixa_escolhida]);
    }
    mostra_caixas(arcaixa, n_caixas);
  }
  printf("== FECHO DE CAIXAS ==\n");
  t=n_ciclos + 1;
  while (caixas_cheias(arcaixa,n_caixas)){
    printf("== CICLO %d==\n", t);
    trata_clientes(t,arcaixa, n_caixas);
    mostra_caixas(arcaixa, n_caixas);
    t+=1;
  }
  processa_resultados(arcaixa, n_caixas);
}

int main(int argc, char const *argv[]) {
  simulador( 100, 15, 5, 10);
  return 0;
}
