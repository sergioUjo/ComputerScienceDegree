#include "frac.h"
#include <stdio.h>

FRAC newFrac(int x, int y){
  FRAC k;
  k.num = x;
  k.den = y;
  return k;
}

int num(FRAC x){
  return x.num;
}

int den(FRAC x){
  return x.den;
}

FRAC add(FRAC x, FRAC y){
  int k = x.den;
  int z = y.den;
  y.num = y.num*k;
  y.den = y.den*k;
  x.num = x.num*z;
  x.den = x.den*z;
  int t = x.num + y.num;
  return simplify( newFrac(t, x.den) );
}
FRAC sub(FRAC x, FRAC y){
  int k = x.den;
  int z = y.den;
  y.num = y.num*k;
  y.den = y.den*k;
  x.num = x.num*z;
  x.den = x.den*z;
  int t = x.num - y.num;
  return simplify( newFrac(t, x.den) );
}

FRAC mult(FRAC x, FRAC y){
  int tnum = x.num * y.num;
  int tden = x.den * y.den;
  return simplify( newFrac(tnum, tden) );
}

FRAC div(FRAC x, FRAC y){
  int tnum = x.num * y.den;
  int tden = x.den * y.num;
  return simplify( newFrac(tnum, tden) );
}

int MDC(int a, int b){
  while (b != 0){
    int r = a % b;
    a = b;
    b = r;
  }
  return a;
}

FRAC simplify(FRAC x){
  int t = MDC(x.num, x.den);
  return newFrac(x.num/t, x.den/t);
}

void printFRAC(FRAC x){
  printf("%d / %d \n", x.num , x.den);
}
