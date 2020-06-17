#include "lista.h"
#include "hash.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

unsigned int hash( char *s ){
  unsigned int h;
  unsigned char *p;
  h=0;
  for (p = (unsigned char*) s; *p != '\0'; p++) {
    h = MULTIPLIER * h + *p;
  }
  return h;
}

void insert( char *s, int value ){
  if( lookup( s ) == NULL ){
     table[ hash(s) ] = newList(value,s,table[ hash(s) ]);
  }
}
void insertl( char *s, char *c ){
  LIST l = lookup( s );
  if( l != NULL ){
     table[ hash(c) ] = newList(l->elem,c,table[ hash(c) ]);
  }
}

LIST lookup( char *s){
    LIST l =  table[ hash(s) ];
    while( l != NULL){
      if( strcmp( l->string, s) == 0){
        return l;
      }
      l= tail(l);
    }
    return NULL;
}

void init_table(){
  for( int i = 0; i < HASH_SIZE; i++){
    table[i] = NULL;
  }
}

void print( char *c ){
  printList( lookup (c) );
}
