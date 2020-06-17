typedef enum {FALSE,TRUE} BOOL;


typedef struct fila {
  int inicio, fim, nmax;
  Cliente *queue;
} QUEUE;


// criar fila com capacidade para n inteiros
QUEUE *mk_empty_queue(int n);
// colocar valor na fila
void enqueue(Cliente v,QUEUE *f);
// retirar valor na fila
Cliente dequeue(QUEUE *f);
// valor do trata_primeiro
Cliente peek(QUEUE *f);
// verificar se a fila está vazia
BOOL queue_is_empty(QUEUE *f);
// verificar se a fila não admite mais elementos
BOOL queue_is_full(QUEUE *f);
// liberta fila
void free_queue(QUEUE *f);
// excreve a fila
void print_queue(QUEUE *q);
