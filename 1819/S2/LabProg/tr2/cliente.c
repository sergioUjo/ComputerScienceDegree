#include "cliente.h"

#include <stdio.h>
#include <stdlib.h>

Cliente mk_cliente(int n,int e){
  Cliente new = (Cliente) malloc(sizeof( struct cliente ));
  new->NumItems = n;
  new->Entrada = e;
  new->Prioridade = (n<=10) ? 1 : 0;
  return new;
}

int artigos(Cliente c){
  return c->NumItems;
}

int entrada(Cliente c){
  return c->Entrada;
}

void printCliente(Cliente c){
  printf("[ %d : %d ]", c->NumItems, c->Entrada);
}
