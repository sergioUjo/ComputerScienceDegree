typedef enum {EMPTY, INT_CONST, STRING} ElemKind;

typedef struct elem{
  ElemKind kind;
  union{
    int val;
    char *name;
  } contents;
} *Elem;

Elem mkEl( char *s );
Elem mkVar( char *s );
Elem mkInt( int n );
Elem mkElem();
int* getValue( Elem x );
char* getName( Elem x );
void printElem( Elem x );
