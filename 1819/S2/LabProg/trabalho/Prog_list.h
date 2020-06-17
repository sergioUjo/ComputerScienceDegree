
typedef struct lista{
  Instr elem;
  struct lista *next;
}*LSTAT;

typedef struct linked {
    LSTAT first;
    LSTAT last;
}*linked_list;

linked_list newlinked();

LSTAT newList1( Instr n, LSTAT l );

Instr head1( LSTAT l );

LSTAT tail1( LSTAT l );

LSTAT append1(LSTAT l1, LSTAT l2);

linked_list linkedAddLast(Instr n, linked_list l);

void printList1( LSTAT l );

LSTAT go_to( Elem x ,LSTAT l);
