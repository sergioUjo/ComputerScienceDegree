#include "cliente.h"
#include "fila.h"
#include "Pqueue.h"
#include "caixa.h"

#include <stdio.h>
#include <stdlib.h>

Caixa mk_new( int valor ){
  Caixa new = (Caixa) malloc( sizeof( struct caixa ) );
  new-> kind = NORMAL ;
  new-> fila = mk_empty_queue(100);
  new-> eta = 0;
  new-> clientes = 0;
  new-> produtos = 0;
  new-> espera = 0;
  new-> numero = valor;
  new-> velocidade = (rand() % 4) +2;
  return new;
}
Caixa mk_new_p(int valor){
  Caixa new = (Caixa) malloc(sizeof (struct caixa));
  new-> kind = PRIORITY;
  new-> pfila = mk_empty_pqueue(100);
  new-> eta = 0;
  new-> clientes = 0;
  new-> produtos = 0;
  new-> espera = 0;
  new-> numero = valor;
  new-> velocidade = (rand() % 4) +2;
  return new;
}

//MODIFICADORES
void disponivel( Caixa c, int valor){
  c-> eta = valor;
}

void cliente_atendido( Caixa c ){
  c-> clientes++;
}

void actualiza_produtos( Caixa c, int valor ){
  c-> produtos += valor;
}

void actualiza_espera( Caixa c, int valor){
  c-> espera += valor;
}

void remove_cliente( Caixa c ){
  if ( c->kind == PRIORITY ) {
    pdequeue(c->pfila);
  }
  else dequeue(c->fila);
}
//Seletores

int pronta(Caixa c){
  return c->eta;
}

int clientes(Caixa c){
  return c->clientes;
}

int produtos(Caixa c){
  return c->produtos;
}

int espera(Caixa c){
  return c->espera;
}
int numero(Caixa c){
  return c->numero;
}

int tempo_processamento(Caixa c){
  return c->velocidade;
}
Cliente peek_caixa(Caixa c){
  if ( c->kind == PRIORITY ) {
    return ppeek(c->pfila);
  }
  else return peek(c->fila);
}
void caixa_enqueue(Cliente v, Caixa c){
  c->kind ? penqueue(v, c->pfila) : enqueue( v,c->fila);
}
//Reconhecedores

int vazia( Caixa c ){
  return c->kind ? pqueue_is_empty(c->pfila): queue_is_empty(c->fila);
}

void print_caixa(Caixa c){
  printf("Caixa %d (%d): ",c->numero,c->velocidade);
  c->kind ? print_pqueue( c->pfila) : print_queue( c->fila);
  printf("\n");
}
