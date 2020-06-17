typedef struct list{
  int elem;
  char *string;
  struct list *next;
}*LIST;

typedef int bool;
#define true 1
#define false 0

LIST newList( int n, char *c, LIST l );

char* head( LIST l );

LIST tail( LIST l );

int length( LIST l );

int elem_n( int n, LIST l );

LIST append( LIST l1, LIST l2);

LIST addLast( int n, char *c, LIST l);

void printList( LIST l );
