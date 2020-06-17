#include "frac.h"
#include <stdio.h>

int main(){
  //FALTA SOMAR NUMEROS EM SÉRIE
  //int a,b,a1,b1;
  char c[500];
  scanf("%s", c);
  printf("%s\n", c);
  /*FRAC x = newFrac(a,b);
  FRAC y = newFrac(a1,b1);
  FRAC k;*/

  switch (c) {

    case ('+'):
    k = add(x,y);
    printFRAC(k);
    break;

    case ('*'):
    k = mult(x,y);
    printFRAC(k);
    break;

    case ('/'):
    k = div(x,y);
    printFRAC(k);
    break;

    case ('-'):
    k = sub(x,y);
    printFRAC(k);
    break;

    default:
    printf("ERROR\n");
    break;
  }

  return 0;
}
