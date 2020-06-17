#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>


int main(int argc, char* argv[]) {
  FILE *fpr;
  fpr = fopen (argv[1],"r");
  int count = 0;
  char* str[100];
  if (fpr!=NULL){
    while ( str != EOF){
      fscanf(fpr, str);
      count++;
    }
  }
  return EXIT_SUCCESS;
}
