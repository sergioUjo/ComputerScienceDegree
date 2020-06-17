#include "cliente.h"
#include "fila.h"
#include "Pqueue.h"

#include <stdio.h>
#include <stdlib.h>

// criar fila com capacidade para n inteiros
PQUEUE *mk_empty_pqueue(int n){
  PQUEUE *q = (PQUEUE *) malloc(sizeof(PQUEUE));
  q -> queue = mk_empty_queue(n);
  q -> mqueue = mk_empty_queue(n);
  return q;
}
// colocar valor na fila
void penqueue(Cliente v,PQUEUE *f){
  if( v->Prioridade ){
    enqueue(v,f->mqueue);
  }
  else{
    enqueue(v,f->queue);
  }
}
// retirar valor na fila
Cliente pdequeue(PQUEUE *f){
  if( queue_is_empty(f->mqueue) ){
    return dequeue(f->queue);
  }
  else{
    return dequeue(f->mqueue);
  }
}
// valor do trata_primeiro
Cliente ppeek(PQUEUE *f){
  if( queue_is_empty(f->mqueue) ){
    return peek(f->queue);
  }
  else{
    return peek(f->mqueue);
  }
}
// verificar se a fila está vazia
BOOL pqueue_is_empty(PQUEUE *f){
  return queue_is_empty(f->queue) && queue_is_empty(f->mqueue);
}
// verificar se a fila não admite mais elementos
BOOL pqueue_is_full(PQUEUE *f){
  return queue_is_full(f->mqueue) && queue_is_full(f->queue);
}
// liberta fila
void free_pqueue(PQUEUE *f){
  free_queue(f->mqueue);
  free_queue(f->queue);
}
// excreve a fila
void print_pqueue(PQUEUE *q){
  print_queue(q->mqueue);
  print_queue(q->queue);
}
