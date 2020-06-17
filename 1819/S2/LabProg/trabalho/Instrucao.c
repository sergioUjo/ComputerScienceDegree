#include <stdio.h>
#include <stdlib.h>

#include "Elem.h"
#include "Instrucao.h"
#include "lista.h"
#include "hash.h"

Instr mkIAC(Opkind ope, Elem x, Elem y, Elem z){
  Instr new = (Instr) malloc(sizeof(struct instr));
  new->op=ope;
  new->first=x;
  new->second=y;
  new->third=z;
  return new;
}
Instr mkIA(Opkind ope, Elem x, Elem y){
  Instr new = (Instr) malloc(sizeof(struct instr));
  new->op=ope;
  new->first=x;
  new->second=y;
  new->third=mkElem();
  return new;
}
Instr mkI(Opkind ope, Elem x){
  Instr new = (Instr) malloc(sizeof(struct instr));
  new->op=ope;
  new->first=x;
  new->second=mkElem();
  new->third=mkElem();
  return new;
}
void printInstr(Instr i){
  int ope = i->op;
  printf("Operação = %d\n",ope);
  printf("First =");
  printElem(i->first);
  printf("\nSecond =");
  printElem(i->second);
  printf("\nthird =");
  printElem(i->third);
}

Elem InstrExe( Instr i){
  int* x=malloc(sizeof(int));
  int* y=malloc(sizeof(int));
  switch (i->op) {
    case ADD:
    x=getValue(i->second);
    y=getValue(i->third);
    if ( x == NULL || y == NULL ) {
      printf("Atribuição não efetuada, variavel não atribuida!\n");
      break;
    }
    else
    insert(getName( i->first),(*x)+(*y));
    break;

    case SUB:
    x=getValue(i->second);
    y=getValue(i->third);
    if ( x == NULL || y == NULL ) {
      printf("Atribuição não efetuada, variavel não atribuida!\n");
      break;
    }
    else
    insert(getName( i->first),(*x)-(*y));
    break;

    case MUL:
    x=getValue(i->second);
    y=getValue(i->third);
    if ( x == NULL || y == NULL ) {
      printf("Atribuição não efetuada, variavel não atribuida!\n");
      break;
    }
    else
    insert(getName( i->first),(*x)*(*y));
    break;

    case DIV:
    x=getValue(i->second);
    y=getValue(i->third);
    if ( x == NULL || y == NULL ) {
      printf("Atribuição não efetuada, variavel não atribuida!\n");
      break;
    }
    else{
      if( (*y) == 0 ) printf("Divisão por zero, atribuição não efetuada\n");
      else
      insert(getName( i->first),(*x)/(*y));
    }
    break;

    case ATRIB:
    x=getValue(i->second);
    if ( x == NULL ) {
      printf("Atribuição não efetuada, variavel não atribuida!\n");
      break;
    }
    else
    insert(getName( i->first),(*x));
    break;

    case IF_I:
    if( lookup(getName(i->first)) != NULL){
      return i->second;
    }
    break;

    case PRINT:
    printHashV( getName(i->first) );
    break;

    case READ:
    printf("Insira o valor para %s: ", getName( i->first ) );
    scanf("%d", y);
    insert( getName(i->first), (*y));
    break;

    case GOTO_I:
    return i->first;
    break;

    default:
    return NULL;
    break;
  }
  return NULL;
}
