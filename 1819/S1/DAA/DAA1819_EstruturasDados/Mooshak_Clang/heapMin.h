/*-----------------------------------------------------------------------*\
|  Exemplo de implementacao de fila de prioridade (por heap de mínimo)    |
|                                                                         |
|   A.P.Tomás, CC2001 (material para prova pratica), DCC-FCUP, 2017       |
|   Last modified: 2017.12.18                                             |
\*-----------------------------------------------------------------------*/

#include<stdio.h>
#include<stdlib.h>

typedef struct qnode {
  int vert, vertkey;
} QNODE;

typedef struct heapMin {
  int sizeMax, size;
  QNODE *a;
  int *pos_a;
} HEAPMIN;

//---------  protótipos das funções disponíveis --------------------

HEAPMIN *build_heap_min(int v[], int n);
void insert(int v, int key, HEAPMIN *q);
int extractMin(HEAPMIN *q);   // retorna v 
void decreaseKey(int v, int newkey, HEAPMIN *q);
int heap_isEmpty(HEAPMIN *q);   // retorna 1 ou 0

void write_heap(HEAPMIN *q);
void destroy_heap(HEAPMIN *q);


//----------------- definição das funções e macros ---------------------

#define POSINVALIDA 0

#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i)+1)
#define PARENT(i) ((i)/2)

static void heapify(int i,HEAPMIN *q);
static void swap(int i,int j,HEAPMIN *q);
static int compare(int i, int j, HEAPMIN *q);
static int pos_valida(int i,HEAPMIN *q);

static int compare(int i, int j, HEAPMIN *q){
  if (q -> a[i].vertkey < q -> a[j].vertkey)
    return -1;
  if (q -> a[i].vertkey == q -> a[j].vertkey)
    return 0;
  return 1;
}


static int pos_valida(int i, HEAPMIN *q) {
  return (i >= 1 && i <= q -> size);
}

int extractMin(HEAPMIN *q) {
  int vertv = q -> a[1].vert;
  swap(1,q->size,q);
  q -> pos_a[vertv] = POSINVALIDA;  // assinala vertv como removido
  q -> size--;
  heapify(1,q);
  return vertv;
}

void decreaseKey(int vertv, int newkey, HEAPMIN *q){
  int i = q -> pos_a[vertv];
  q -> a[i].vertkey = newkey;

  while(i > 1 && compare(i,PARENT(i),q) < 0){
    swap(i,PARENT(i),q);
    i = PARENT(i);
  }
}


static void heapify(int i,HEAPMIN *q) {
  // para heap de minimo
  int l, r, smallest;
  l = LEFT(i);
  if (l > q -> size) l = i;
  r = RIGHT(i);
  if (r > q -> size) r = i;
  
  smallest = i;
  if (compare(l,smallest,q) < 0) 
    smallest = l;
  if (compare(r,smallest,q) < 0) 
    smallest = r;
  
  if (i != smallest) {
    swap(i,smallest,q);
    heapify(smallest,q);
  }
}

static void swap(int i,int j,HEAPMIN *q){
  QNODE aux;
  q -> pos_a[q -> a[i].vert] = j;
  q -> pos_a[q -> a[j].vert] = i;
  aux = q -> a[i];
  q -> a[i] = q -> a[j];
  q -> a[j] = aux;
}



HEAPMIN *build_heap_min(int vec[], int n){
  // supor que vetor vec[.] guarda elementos nas posições 1 a n
  // cria heapMin correspondente em tempo O(n)
  HEAPMIN *q = (HEAPMIN *)malloc(sizeof(HEAPMIN));
  int i;
  q -> a = (QNODE *) malloc(sizeof(QNODE)*(n+1));
  q -> pos_a = (int *) malloc(sizeof(int)*(n+1));
  q -> sizeMax = n; // posicao 0 nao vai ser ocupada
  q -> size = n;   
  for (i=1; i<= n; i++) {
    q -> a[i].vert = i;
    q -> a[i].vertkey = vec[i];
    q -> pos_a[i] = i;  // posicao inicial do elemento i na heap
  }

  for (i=n/2; i>=1; i--) 
    heapify(i,q);
  return q;
}


void insert(int vertv, int key, HEAPMIN *q)
{ 
  if (q -> sizeMax == q -> size) {
    fprintf(stderr,"Heapmin is full\n");
    exit(EXIT_FAILURE);
  }
  q -> size++;
  q -> a[q->size].vert = vertv;
  q -> pos_a[vertv] = q -> size;   // supondo 1 <= vertv <= n
  decreaseKey(vertv,key,q);   // diminui a chave e corrige posicao se necessario
}


int heap_isEmpty(HEAPMIN *q){
  if (q -> size == 0) return 1;
  return 0;
}


// --------- auxiliar para ver conteudo  ---------------------
void write_heap(HEAPMIN *q){
  int i;

  printf("Max size: %d\n", q -> sizeMax);
  printf("Current size: %d\n", q -> size);
  
  printf("(Vert,Key)\n---------\n");
  for(i=1; i <= q -> size; i++)
    printf("(%d,%d)\n",q->a[i].vert,q->a[i].vertkey);

  printf("-------\n(Vert,PosVert)\n---------\n");

  for(i=1; i <= q -> sizeMax; i++)
    if (pos_valida(q -> pos_a[i],q))
      printf("(%d,%d)\n",i,q->pos_a[i]);
}


void destroy_heap(HEAPMIN *q){
  if (q != NULL) {
    free(q -> a);
    free(q -> pos_a);
    free(q);
  }
}
    
