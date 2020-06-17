
typedef struct pqueue {
  QUEUE *queue;
  QUEUE *mqueue;
} PQUEUE;

// criar fila com capacidade para n inteiros
PQUEUE *mk_empty_pqueue(int n);
// colocar valor na fila
void penqueue(Cliente v,PQUEUE *f);
// retirar valor na fila
Cliente pdequeue(PQUEUE *f);
// valor do trata_primeiro
Cliente ppeek(PQUEUE *f);
// verificar se a fila está vazia
BOOL pqueue_is_empty(PQUEUE *f);
// verificar se a fila não admite mais elementos
BOOL pqueue_is_full(PQUEUE *f);
// liberta fila
void free_pqueue(PQUEUE *f);
// excreve a fila
void print_pqueue(PQUEUE *q);
