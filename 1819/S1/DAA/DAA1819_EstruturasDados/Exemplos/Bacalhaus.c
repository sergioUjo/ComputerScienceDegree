/*------------------------------------------------------\
|       Bacalhaus congelados                            |
|  Exemplos disponiveis  -- Prova pratica  2017         |
|  A.P. Tomas (Dez 2017)                                |
\------------------------------------------------------*/


// inclui grafo.h explicitamente para poder testar no Mooshak
//-----------------------------------------------------------


#include <stdio.h>
#include <stdlib.h>



//=========== grafo.h =============================

#define MAXVERTS 20000
// dado de bacalhaus congelados


typedef struct arco {
  int no_final;
  int valor;
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
void insert_new_arc(int i, int j, int valor, GRAFO *g);
/* insere arco (i,j) no grafo, bem como o seu peso; não evita repetições */
void remove_arc(ARCO *arco, int i, GRAFO *g);
/* retira adjacente arco da lista de adjacentes de i */
ARCO *find_arc(int i, int j, GRAFO *g);
// retorna um apontador para o arco (i,j) ou NULL se não existir 

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
// qual o extremo final de arco
#define VALOR_ARCO(arco) ((arco) -> valor)
// qual o valor no arco


//======  protótipos de funções auxiliares (privadas) ======
static ARCO* cria_arco(int j, int valor);
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
void insert_new_arc(int i, int j, int valor, GRAFO *g)
{ /* insere arco (i,j) no grafo g, bem como o seu label  */

  ARCO *arco = cria_arco(j,valor);
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
static ARCO *cria_arco(int j, int valor)
{ // cria um novo adjacente
  ARCO *arco = (ARCO *) malloc(sizeof(ARCO));
  if (arco == NULL) {
    fprintf(stderr,"ERROR: cannot create arc\n");
    exit(EXIT_FAILURE);
  }
  EXTREMO_FINAL(arco) = j;
  VALOR_ARCO(arco) = valor;
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



//=============================== 
//    Bacalhaus congelados 
//=============================== 


GRAFO *ler_construir_grafo(){
  int nvs, nramos, i, j, tij, aux;
  GRAFO *g;     // tipo definido em grafos.h

  scanf("%d%d",&nvs,&nramos);
  g = new_graph(nvs);    // funcao definida em grafos.h
  while(nramos > 0) {
    scanf("%d%d%d%d",&i,&j,&tij,&aux);  // custo nao usado
    insert_new_arc(i,j,tij,g);
    insert_new_arc(j,i,tij,g);
    nramos--;
  }
  return g;
}


int temp_arco(int i, int j, GRAFO *g) {
  ARCO *adjs = find_arc(i,j,g);     // definida em grafo.h

  return VALOR_ARCO(adjs);
}


void ler_analisa(int nvc,GRAFO *g) {
  int tmpMin, tmpMax, prev, curr, t;
  scanf("%d %d",&prev,&curr);
  t = temp_arco(prev,curr,g);  
  tmpMin = tmpMax = t;
  nvc -= 2;
  while (nvc != 0) {
    prev = curr;
    scanf("%d",&curr);
    t = temp_arco(prev,curr,g);
    if (t < tmpMin) tmpMin = t;
    else if (t > tmpMax)
      tmpMax = t;
    nvc--;
  }
  printf("%d %d\n",tmpMin,tmpMax);
}

int main() {
  int nvc;
  GRAFO *g= ler_construir_grafo();
  
  scanf("%d",&nvc);
  while(nvc != 0) {
    ler_analisa(nvc,g);
    scanf("%d",&nvc);
  }

  destroy_graph(g);   // desnecessario porque liberta memoria quando processo termina
  return 0;
}
