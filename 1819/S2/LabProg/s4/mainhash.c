#include "lista.h"
#include "hash.h"
#include <stdio.h>
#include <stdlib.h>

int main(){
  init_table();
  insert("xy", 10);
  print( "xy" );
  insertl("xy", "aaa");
  print("aaa");
  return 0;
}
