#include <stdio.h>

typedef struct list{
  int elem;
  struct list *next;
}*LIST;

typedef int bool;
#define true 1
#define false 0

LIST newList( int n, LIST l );

int head( LIST l );

LIST tail( LIST l );

int length( LIST l );

int elem_n( int n, LIST l );

LIST append( LIST l1, LIST l2);

LIST filter ( bool (*p)(int), LIST l);

LIST addLast( int n, LIST l);

LIST readList( );

LIST del( int n, LIST l);

void printList( LIST l );
