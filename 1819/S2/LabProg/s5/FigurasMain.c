#include "Figuras.h"
#include <stdio.h>
#include <stdlib.h>

int main(){
  FIG novar = newRect( 2, 3);
  FIG novaq = newQuad( 2 );
  FIG novac = newCirc( 1 );

  printf("quadrado %f\n", area( novaq ) );
  printf("rectangulo %f\n", area( novar ) );
  printf("circulo %f\n", area( novac ) );

  return 0;
}
