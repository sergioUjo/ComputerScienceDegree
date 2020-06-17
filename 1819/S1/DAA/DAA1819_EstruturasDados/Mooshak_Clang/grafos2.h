/*-------------------------------------------------------------------*\
|  TAD para implementacao de grafo dirigido com DOIS pesos (int)      |
|                                                                     |
|    Versão simplificada;  |V| <=  MAXVERTS;                          |
|     Assume-se que os vértices são numerados de 1 a |V|.             |
|                                                                     |
|   A.P.Tomás, CC2001 (material para prova pratica), DCC-FCUP, 2017   |
|   Last modified: 2017.12.18                                         |
\--------------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>

#define MAXVERTS 1000
// numero maximo de vertices (alterar se necessario)


typedef struct arco {
  int no_final;
  int valor[2];    // DOIS valores
  struct arco *prox;
} ARCO;

typedef struct no {
  //int label;
  ARCO *adjs;
} NO;

typedef struct graph {
  NO verts[MAXVERTS+1];  // nós implicitamente numerados de 1 a nvs
  int nvs, narcos;
} GRAFO;

//--- protótipos das funções disponíveis----------------------------
//    ATENÇÃO AOS TIPOS DE ARGUMENTOS E DE RESULTADO


GRAFO *new_graph(int nverts);
/* cria um grafo com nverts vertices e sem arcos */
void destroy_graph(GRAFO *g);
/* liberta o espaço reservado na criação do grafo */
void insert_new_arc(int i, int j, int valor0, int valor1, GRAFO *g);
/* insere arco (i,j) no grafo, bem como os dois pesos; não evita repetições */
void remove_arc(ARCO *arco, int i, GRAFO *g);
/* retira adjacente arco da lista de adjacentes de i */
ARCO *find_arc(int i, int j, GRAFO *g);
/* retorna um apontador para o arco (i,j) ou NULL se não existir */

//--- macros de acesso aos campos da estrutura --------------------------

#define NUM_VERTICES(g) ( (g) -> nvs )
// numero de vertices
#define NUM_ARCOS(g) ( (g) -> narcos )
// numero de arcos
#define ADJS_NO(i,g) ( (g) -> verts[i].adjs )
// primeiro arco da lista de adjacentes do nó i
#define PROX_ADJ(arco) ((arco) -> prox)
// proximo adjacente 
#define ADJ_VALIDO(arco) (((arco) != NULL))
// se arco é válido
#define EXTREMO_FINAL(arco) ((arco) -> no_final)
// acesso ao extremo final de arco
#define VALOR1_ARCO(arco) ((arco) -> valor[0])
// acesso ao valor1 do arco
#define VALOR2_ARCO(arco) ((arco) -> valor[1])
// acesso ao valor2 do arco

//======  protótipos de funções auxiliares (privadas) ======
static ARCO* cria_arco(int, int valor1,int valor2);
static void free_arcs(ARCO *);


//======  Implementação (definição das funções) ========================

// para criar um grafo com nverts vertices e sem ramos
GRAFO *new_graph(int nverts)
{
  if (nverts > MAXVERTS) {
    fprintf(stderr,"Erro: %d > MAXVERTS\n",nverts);
    exit(EXIT_FAILURE);
  }
  GRAFO *g = (GRAFO *) malloc(sizeof(GRAFO));
  if (g == NULL) { 
    fprintf(stderr,"Erro: falta memoria\n");
    exit(EXIT_FAILURE);
  }

  NUM_VERTICES(g) = nverts;  
  NUM_ARCOS(g) = 0;
  while (nverts) {
    ADJS_NO(nverts,g) = NULL;
    nverts--;
  }
  return g;
}


// para destruir um grafo criado
void destroy_graph(GRAFO *g)
{ int i;
  if (g != NULL) {
    for (i=1; i<= NUM_VERTICES(g); i++) 
      free_arcs(ADJS_NO(i,g));
    free(g);
  }
}

// para inserir um novo arco num grafo
void insert_new_arc(int i, int j, int valor1, int valor2, GRAFO *g)
{ /* insere arco (i,j) no grafo g, bem como os seus pesos  */

  ARCO *arco = cria_arco(j,valor1,valor2);
  PROX_ADJ(arco) = ADJS_NO(i,g);
  ADJS_NO(i,g) = arco;  // novo adjacente fica à cabeça da lista
  NUM_ARCOS(g)++;
}

// para remover um arco de um grafo (se existir na lista de adjs[i])
void remove_arc(ARCO *arco, int i, GRAFO *g)
{ 
  if (arco != NULL) {
    ARCO *aux = ADJS_NO(i,g), *prev = NULL;
    while (aux != arco && ADJ_VALIDO(aux)) {
      prev = aux;
      aux = PROX_ADJ(aux);
    }
    if (aux == arco) {
      if (prev == NULL) {
	ADJS_NO(i,g)  = PROX_ADJ(arco);
      } else PROX_ADJ(prev) = PROX_ADJ(arco);
      free(arco);
      NUM_ARCOS(g)--;
    }
  }
}

// retorna um apontador para o arco (i,j) ou NULL se não existir 
ARCO *find_arc(int i, int j, GRAFO *g){
  ARCO *adj = ADJS_NO(i,g);

  while(adj != NULL && EXTREMO_FINAL(adj) != j)
    adj = PROX_ADJ(adj);

  return adj;
}
    

// ----  as duas funcoes abaixo sao auxiliares nao publicas ----

// reservar memoria para um novo arco e inicializa-lo
static ARCO *cria_arco(int j, int valor1, int valor2)
{ // cria um novo adjacente
  ARCO *arco = (ARCO *) malloc(sizeof(ARCO));
  if (arco == NULL) {
    fprintf(stderr,"ERROR: cannot create arc\n");
    exit(EXIT_FAILURE);
  }
  EXTREMO_FINAL(arco) = j;
  VALOR1_ARCO(arco) = valor1;
  VALOR2_ARCO(arco) = valor2;
  PROX_ADJ(arco) = NULL;
  return arco;
}

// libertar uma lista de arcos 
static void free_arcs(ARCO *arco)
{ // liberta lista de adjacentes 
  if (arco == NULL) return;
  free_arcs(PROX_ADJ(arco));
  free(arco);
}
