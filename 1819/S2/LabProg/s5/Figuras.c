#include "Figuras.h"
#include <stdio.h>
#include <stdlib.h>

FIG newQuad( int l ){
  FIG new = (FIG) malloc( sizeof(struct fig) );
  new -> kind = QUADRADO;
  new -> lado = l;
  return new;
}
FIG newRect( int l, int c ){
  FIG new = (FIG) malloc( sizeof(struct fig) );
  new -> kind = RECTANGULO;
  new -> lar = l;
  new -> comp = c;
  return new;
}
FIG newCirc( int r ){
  FIG new = (FIG) malloc( sizeof(struct fig) );
  new -> kind = CIRCULO;
  new -> raio = r;
  return new;
}

float area( FIG x){
  switch ( x->kind ){
    case QUADRADO:
    return x->lado*x->lado;
    break;

    case RECTANGULO:
    return x->lar * x->comp;
    break;

    case CIRCULO:
    return 3.14*x->raio*x->raio;
    break;

    default:
    return -1;
    break;
  }
  return -1;
}
