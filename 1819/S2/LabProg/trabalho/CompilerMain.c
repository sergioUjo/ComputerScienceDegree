#include "Elem.h"
#include "Instrucao.h"
#include "Prog_list.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void removeChar(char *s, int c){

    int j, n = strlen(s);
    for (int i=j=0; i<n; i++)
       if (s[i] != c)
          s[j++] = s[i];

    s[j] = '\0';
}
void clean_input(char *s){
  removeChar(s, ')');
  removeChar(s, '(');
  removeChar(s, '=');
}

linked_list loader(char* ficheiro){
  linked_list l = newlinked();
  char temp[100000];
  FILE *fp=fopen(ficheiro, "r");
  char var[50];
  char var1[50];
  char var2[50];

  while (fgets(temp, sizeof(temp), fp)) {
    Instr x=NULL;
    char ope = 'n';
    sscanf(temp, "%[^ (=;] %[^;]", var, temp);
    clean_input( temp );

    if(strcmp(var, "label") == 0){
      removeChar(temp, ' ');
      sscanf(temp, "%s", var1);
      x = mkI(LABEL, mkVar(var1));
    }
    else if(strcmp(var, "if") == 0) {
      sscanf(temp, "%s %s %s", var1, var, var2);
      x = mkIA(IF_I, mkVar(var1), mkVar(var2));
    }
    else if(strcmp(var, "goto") == 0) {
      removeChar(temp, ' ');
      sscanf(temp, "%s", var1);
      x = mkI(GOTO_I, mkVar(var1));
    }
    else if(strcmp(var, "escrever") == 0) {
      removeChar(temp, ' ');
      sscanf(temp, "%s", var1);
      x = mkI(PRINT, mkVar(var1));
    }
    else if(strcmp(var, "ler") == 0) {
      removeChar(temp, ' ');
      sscanf(temp, "%s", var1);
      x = mkI(READ, mkVar(var1));
    }
    else if(strcmp(var, "quit") == 0) {
      x = mkI(GOTO_I, mkVar("Exit"));
    }
    else{
      removeChar(temp, ' ');
      sscanf( temp,"%[^+-*/] %c %s", var1, &ope, var2);
      if( ope != 'n'){
        switch (ope) {

          case '*':
          x = mkIAC(MUL,mkEl(var), mkEl(var1), mkEl(var2));
          break;

          case '+':
          x = mkIAC(ADD,mkEl(var), mkEl(var1), mkEl(var2));
          break;

          case '-':
          x = mkIAC(SUB,mkEl(var), mkEl(var1), mkEl(var2));
          break;

          case '/':
          x = mkIAC(DIV,mkEl(var), mkEl(var1), mkEl(var2));
          break;
        }
      }
      else{
        x = mkIA(ATRIB,mkEl(var), mkEl(var1));
      }

    }
      l = linkedAddLast( x, l);
  }
  return linkedAddLast ( mkI(LABEL, mkVar("Exit")), l);
}

void run(LSTAT l){
  LSTAT aux = l;
  while( aux != NULL){
    Elem temp = InstrExe( head1(aux) );
    if( temp != NULL){
      LSTAT auxx = go_to( temp, l);
      if( auxx == NULL ){
        printf("NULL exeption LABEL, a execução passará para a próxima Instrução\n");
         aux = tail1( aux );
       }
      else aux = auxx;
    }
    else{
      aux = tail1(aux);
    }
  }
}

int main(int argc, char* argv[]){
  if (argc != 2) {
    printf("Forneça um ficheiro para correr\n");
    return -1;
  }
  else{
    if ( access( argv[1], F_OK ) != -1 ){
      printf("A iniciar o programa %s ...\n", argv[1]);
      linked_list l = loader( argv[1] );
      run( l->first );
    }
    else{
      printf("Ficheiro não existe\n");
      return -1;
    }
  }
  return 0;
}
