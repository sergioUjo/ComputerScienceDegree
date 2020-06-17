#include <stdlib.h>
#include <math.h>
#include "vector.h"

vector* vector_new(double x1, double y1, double z1){
  vector* k = (vector*) malloc( sizeof(vector) );
  k->x = x1;
  k->y = y1;
  k->z = z1;
  return k;
}
vector* vector_add(vector* a, vector* b){
  return vector_new( a->x + b->x, a->y + b->y, a->z + b->z);
}
vector* vector_sub(vector* a, vector* b){
  return vector_new( a->x - b->x, a->y - b->y, a->z - b->z);
}
vector* vector_scale(double e, vector* a){
  return vector_new( e * a->x, e * a->y, e * a->z );
}
/*
vector* vector_vprod(vector* a, vector* b){

}*/
double vector_sprod(vector* a, vector* b){
  return (a->x * b->x) + (a->y * b->y) + (a->z * b->z);
}
double vector_mod(vector* a){
  return sqrt( pow( a->x, 2) + pow( a->y, 2) +pow( a->z, 2) );
}
