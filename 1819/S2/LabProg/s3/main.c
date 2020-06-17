#include "lista.h"
#include <stdio.h>

bool p ( int x ){
  if( x%2==0){
    return true;
  }
  return false;
}

int main(){
  LIST x1 = newList(1,newList(2,newList(3,NULL)));
  LIST x2 = newList(4,newList(3,newList(3,NULL)));

  printf("%d\n", length( x1 ) );
  printf("%d\n", elem_n( 0, x1 ) );
  printList( x1 );
  LIST x3 = append( x1, x2);
  printList( x3 );
  LIST x4 = filter(p, x3);
  printList(x4);
  LIST x5 = addLast(9,x3);
  printList( x5 );
  LIST x6 = del(3,x5);
  printList( x6 );
  return 0;
}
