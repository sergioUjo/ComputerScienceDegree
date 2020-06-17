#include <stdio.h>
#include <string.h>
#include "Fac.h"

ALUNO alunos[MAXALUNOS];
int NAlunos=0;
DISCIPLINA Disc[MAXDISC];
int NDisc=0;

ALUNO newAluno( char *n, char cod[], int d[], int ndisc){
  ALUNO k;
  k.nome= strdup(n);
  k.nd=ndisc;
  int i=0;
  strcpy(k.nome,cod);
  for(i=0; i<k.nd; i++){
    k.disc[i]=d[i];
  }
  alunos[NAlunos++]=k;
  return k;
}
DISCIPLINA newDisc(char *n, int a){
  DISCIPLINA k;
  k.nome = strdup(n);
  k.ano = a;
  Disc[NDisc]=k;
  NDisc++;
  return k;
}
void input(){
  int i=0, ndisci = 0, naluno=0, aux=0;
  scanf("%d", &ndisci);
  printf("ndisc %d\n", ndisci);
  for(i=0; i<ndisci; i++){
    char nome[100];
    scanf ("%[^\n]%*c", nome);
    scanf("%d", &aux);
    newDisc( nome,aux);
    printf("22222222\n");
  }
}
void printAlunos(ALUNO l[]){
  for(int i=0; i<NAlunos; i++){
    printf("Nome: %s \n", l[i].nome);
  }
}
void printDisc(DISCIPLINA d[]){
  for(int i=0; i<NDisc; i++){
    printf("Nome: %s \n", d[i].nome);
  }
}
void printDiscAno(DISCIPLINA d[], int a){
  for(int i=0; i<NDisc; i++){
    if(d[i].ano == a){
      printf("Nome: %s \n", d[i].nome);
    }
  }
}
