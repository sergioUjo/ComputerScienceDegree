#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>


int main(int argc, char* argv[]) {
  FILE *fpr, *fpw;
  fpw = fopen( argv[2], "wb");
  fpr = fopen (argv[1],"r");

  if (fpr!=NULL){
    char ch;
    while ((ch = fgetc(fpr)) != EOF){
      fputc(ch, fpw);
    }
  }
  return EXIT_SUCCESS;
}
