/*--------------------------------------------------------------------\
| Definição de um tipo abstracto de dados para listas ligadas simples |
|  os nós são estruturas do tipo NODE (com um valor do tipo int e     |
|  um apontador para o próximo nó (ou NULL, se não existir)           |
|  - Definição do tipo PLIST  como abreviatura de (NODE *)        |
|                                                                     |
|   A.P.Tomás, CC211 (material para prova pratica), DCC-FCUP, 2012    |
|   Last modified: 2012.12.28                                         |
\--------------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>

typedef enum {FALSE,TRUE} BOOL;

typedef struct no {
  int val;
  struct no *prox;
} NODE, *PLIST;

//---- macros para acesso aos campos val e prox

#define PROX(L) ( (L) -> prox)
#define VALNO(L) ( (L) -> val)


//------------------ Protótipos das funções disponíveis -------------
BOOL is_empty_list(PLIST lst);   // verifica se a lista está vazia
PLIST mk_empty_list(void);       // cria lista vazia (retorna NULL)
void destroy_list(PLIST lst);    // liberta memoria (nó a nó)
PLIST add_value(int x,PLIST lst); // acrescenta nó com x; retorna nova lista
PLIST del_value(int x,PLIST lst); // remove primeiro nó com x; retorna nova lista
PLIST del_node(PLIST rno,PLIST lst); // remove nó rno; retorna nova lista
BOOL member(int x,PLIST lst);    // verifica se x está na lista



//--------------------Implementação -------------------------


/*---- Funções auxiliares  ------- */


static PLIST cria_no(int);
static void list_exit_error(char *msg);

static void list_exit_error(char *msg)
{ 
  fprintf(stderr,"Error: %s.\n",msg);
  exit(EXIT_FAILURE);
}


static PLIST cria_no(int x)
{
  PLIST novo = (PLIST) malloc(sizeof(NODE));
  if (novo == NULL) 
    list_exit_error("sem memória disponível");

  PROX(novo) = NULL;
  VALNO(novo) = x;
  return novo;
}  


/*
// ----- implementação alternativa sem usar as macros PROX e VALNO

static PLIST cria_no(int x)
{
  PLIST novo = (PLIST) malloc(sizeof(NODE));
  if (novo == NULL) 
    list_exit_error("sem memória disponível");

  novo -> prox = NULL;
  novo -> val = x;
  return novo;
}  
*/

/*-------------------  Definição das funções públicas --------- */

BOOL is_empty_list(PLIST lst)
{
  if (lst == NULL) 
    return TRUE;
  return FALSE;
}


PLIST mk_empty_list()
{
  return NULL;   // representação da lista vazia
}



void destroy_list(PLIST lst)
{ 
  if (lst == NULL) return;

  destroy_list(PROX(lst));

  free(lst);
}



PLIST add_value(int v,PLIST lst)
{ // colocará o valor à cabeça da lista (não ordena)
  PLIST novo;

  // retirar comentário para não ficar com elementos repetidos
  // if (member(v,lst) == TRUE) return lst;

  novo = cria_no(v);
  PROX(novo) = lst;
  return novo;  // primeiro elemento da nova lista
}


PLIST del_value(int v,PLIST lst)
{ // apaga o primeiro elemento que encontrar com valor igual a v
  PLIST curr=lst, prev = NULL;
  
  while (curr != NULL) {
    if (VALNO(curr) == v) {
      if (prev != NULL)    // verificar se curr era ou não a cabeça da lista
	PROX(prev) = PROX(curr);
      else lst = PROX(curr);
      free(curr);
      return lst; // o primeiro elemento da lista resultante
    }
    prev = curr;
    curr = PROX(curr);
  }

  return lst;   // a lista não foi alterada
}



PLIST del_node(PLIST rno,PLIST lst)
{ // apaga o nó rno se existir
  PLIST curr=lst, prev = NULL;
  
  while (curr != NULL) {
    if (curr == rno) {
      if (prev != NULL)    // verificar se rno era ou não a cabeça da lista
	PROX(prev) = PROX(curr);
      else lst = PROX(curr);
      free(curr);
      return lst; // o primeiro elemento da lista resultante
    }
    prev = curr;
    curr = PROX(curr);
  }

  return lst;   // a lista não foi alterada
}


BOOL member(int x,PLIST lst)
{
  while (lst != NULL) {
    if (VALNO(lst) == x) 
      return TRUE;
    lst = PROX(lst);
  }
  
  return FALSE;
}






