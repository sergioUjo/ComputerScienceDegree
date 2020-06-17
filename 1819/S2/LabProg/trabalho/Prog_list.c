#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "Elem.h"
#include "Instrucao.h"
#include "Prog_list.h"

linked_list newlinked(){
  linked_list new =(linked_list) malloc(sizeof(struct linked));
  new->first = NULL;
  new->last = NULL;
  return new;
}

linked_list linkedAddLast(Instr n, linked_list l){
  LSTAT new = (LSTAT) malloc(sizeof(struct lista));
  new = newList1( n , NULL);
  if (l->first == NULL) {
    l->first = new;
    l->last = new;
  }
  else{
    l->last->next = new;
    l->last = new;
  }
  return l;
}

LSTAT newList1(Instr n,LSTAT l ){
  LSTAT new =(LSTAT) malloc(sizeof(struct lista));
  new-> elem = n;
  new-> next = l;
  return new;
}

Instr head1(LSTAT l){
  if( l == NULL ){
    printf( "Lista vazia\n" );
    return NULL;
  }
  return l->elem;
}

LSTAT tail1(LSTAT l){
  if( ( l == NULL ) ){
    printf( "Lista vazia\n" );
    return NULL;
  }
  return l->next;
}


void printList1( LSTAT l ){
  if( l == NULL ) {
    printf("\n");
    return;
  }
  printInstr(l->elem);
  printList1( tail1( l ) );
}

LSTAT append1(LSTAT l1, LSTAT l2){
  if( l1 == NULL ) return l2;
  return newList1( head1(l1), append1( tail1(l1) ,l2 ) );
}

LSTAT go_to(Elem x,LSTAT l){
  while( l != NULL){
    if( strcmp( getName(head1(l)->first), getName(x) ) == 0){
      if( head1(l)->op==LABEL) return l;
    }
    l= tail1(l);
  }
  return NULL;
}
