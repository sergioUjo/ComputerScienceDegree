#include "lista.h"
#include <stdio.h>
#include <stdlib.h>

LIST newList(int n, LIST l ){
  LIST new = malloc(sizeof(struct list));
  new-> elem = n;
  new-> next = l;
  return new;
}

int head(LIST l){
  if( l == NULL ){
    printf( "Lista vazia\n" );
    return -1;
  }
  return l->elem;
}

LIST tail(LIST l){
  if( ( l == NULL ) ){
    printf( "Lista vazia\n" );
    return NULL;
  }
  return l->next;
}

int length( LIST l ){
  if( l == NULL ) return 0;
  return 1 + length( tail( l ));
}

int elem_n( int n, LIST l){
  if( l == NULL ) return -1;

  if( n == 0 ) return l-> elem;

  return elem_n( n-1, l-> next);
}

LIST append( LIST l1, LIST l2){
  if( l1 == NULL ) return l2;

  return newList(head( l1 ), append( tail( l1 ), l2 ) );
}

LIST filter ( bool (*p)(int), LIST l){
  if( l == NULL ) return NULL;
  int x = head( l );
  LIST r = tail( l );
  if( p( x ) ){
    return newList( x, filter( p, r) );
  }
  return filter( p, r);
}

void printList( LIST l ){
  if( l == NULL ) {
    printf("\n");
    return;
  }
  printf("%d ", l->elem);
  printList( tail( l ) );
}

LIST addLast( int n, LIST l){
  if( l == NULL ) return newList( n, NULL);
  return newList( head( l ), addLast( n, tail(l) ) );
}

LIST readList( ){
  while()
}

LIST del( int n, LIST l){
  if( l == NULL ) return NULL;
  if( head( l ) == n ) return del( n , tail( l ) );
  return newList( head( l ), del( n, tail( l ) ) );
}
/*
LIST map( int (*f)(int), LIST l){
  if( l == NULL ) return NULL;
  int x = head( l );
  LIST r = tail( l );
  return newList( f(x), map( f,r) );
}*/
