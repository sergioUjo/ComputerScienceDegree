#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "Elem.h"
#include "lista.h"
#include "hash.h"


Elem mkEl( char *s ){
  int j=0;
  while ( j < strlen(s) ) {
    if( s[j]<'0' || s[j]>'9') break;
    j++;
  }
  if( j == strlen(s) ){
    return mkInt( atoi(s) );
  }
  else{
    return mkVar(s);
  }
}

Elem mkVar( char *s ){
  Elem new = (Elem) malloc( sizeof ( struct elem ) );
  new -> kind = STRING;
  new -> contents.name = strdup( s );
  return new;
}

Elem mkInt( int n ){
  Elem new = (Elem) malloc( sizeof ( struct elem ) );
  new -> kind = INT_CONST;
  new -> contents.val = n;
  return new;
}

Elem mkElem(){
  Elem new = (Elem) malloc( sizeof ( struct elem ) );
  new -> kind = EMPTY;
  return new;
}

int* getValue( Elem x ){
  if( x->kind==STRING){
    return &lookup( x->contents.name )->elem;
  }
  else{
    return &(x->contents.val);
  }
}

char* getName( Elem x ){
  if( x->kind==STRING){
    return strdup( x->contents.name);
  }
  else{
    return NULL;
  }
}

void printElem( Elem x ){
  switch (x->kind) {
    case EMPTY:
    printf("EMPTY");
    break;

    case INT_CONST:
    printf("%d", x->contents.val);
    break;

    case STRING:
    printf("%s", x->contents.name);
    break;
  }
}
