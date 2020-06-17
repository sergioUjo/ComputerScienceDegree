/*--------------------------------------------------------------------\
|                                                                     |
|  Definição dum tipo abstracto de dados  STACK:                      |
|    ("pilha" com elementos do tipo int)                              |
|                                                                     |
|   A.P.Tomás, CC211 (material para prova pratica), DCC-FCUP, 2012    |
|   Last modified: 2012.12.28                                         |
\--------------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>


// definição de BOOL 
typedef enum {FALSE,TRUE} BOOL;


//-----------------------------------------------------------------------
// definicao do tipo STACK (top indica a primeira posicao livre e
// a stack pode ter maxstack elementos no maximo; maxstack fixado na criacao)
//-----------------------------------------------------------------------

typedef struct {
  int top, maxstack;
  int *stack;
} STACK;

// protótipos das funções para interface a uma pilha 
STACK *st_make_empty(int nmax);
void st_destroy(STACK *pstack);
BOOL st_is_empty(STACK *pstack);
BOOL st_is_full(STACK *pstack);
void st_push(STACK *pstack,int x);
int st_pop(STACK *pstack);



// ------------------- implementacao ------------------------------------

// funcao auxiliar (privada)

static void st_exit_error(char *msg);

static void st_exit_error(char *msg)
{ 
  fprintf(stderr,"Error: %s.\n",msg);
  exit(EXIT_FAILURE);
}


STACK *st_make_empty(int nmax)
{ 
  if (nmax <= 0) 
    st_exit_error("invalid stack");

  STACK *ps = (STACK *) malloc(sizeof(STACK));
  if (ps == NULL) 
    st_exit_error("invalid stack");

  ps -> stack = (int *) malloc(sizeof(int)*nmax);
  if (ps -> stack == NULL) 
    st_exit_error("invalid stack");
  
  ps -> top = 0;  
  ps -> maxstack = nmax;
  return ps;
}

BOOL st_is_empty(STACK *ps) 
{
  if (ps == NULL) 
    st_exit_error("invalid stack");

  if (ps -> top == 0) 
    return TRUE;
  return FALSE;
}

BOOL st_is_full(STACK *ps)
{ 
  if (ps == NULL) 
    st_exit_error("invalid stack");

  if (ps -> top == ps -> maxstack) 
    return TRUE;
  return FALSE;
}

void st_destroy(STACK *ps)
{ 
  if (ps == NULL) 
    st_exit_error("invalid stack");

  free(ps -> stack);
  free(ps);
}


void st_push(STACK *ps, int x)
{ 
  if (st_is_full(ps) == TRUE) 
    st_exit_error("stack is full");

  if (ps -> stack == NULL)     
    st_exit_error("invalid stack");

  ps -> stack[ps -> top] = x;
  ps -> top++; 
}

int st_pop(STACK *ps)
{ 
  if (st_is_empty(ps)== TRUE) 
    st_exit_error("stack is empty");

  if (ps -> stack == NULL)     
    st_exit_error("invalid stack");

  ps -> top--;  
  return ps -> stack[ps ->top]; 
}
