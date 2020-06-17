#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "lista.h"
#include "hash.h"

unsigned int hash( char *s ){
  unsigned int h;
  unsigned char *p;
  h=0;
  for (p = (unsigned char*) s; *p != '\0'; p++) {
    h = MULTIPLIER * h + *p;
  }
  return h;
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

void insert( char *s, int value ){
  LIST l = lookup (s);
  if( l == NULL ){
     table[ hash(s) ] = newList(value,s,table[ hash(s) ]);
  }
  else{
    l->elem = value;
  }
}

void init_table(){
  for( int i = 0; i < HASH_SIZE; i++){
    table[i] = NULL;
  }
}

void printHashV( char *c ){
  LIST l = lookup (c);
  if( l == NULL){
    printf("Variavel não atribuida\n");
  }
  else
  printf("%s = %d\n", l->string, l->elem);
}
