#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>


int main(int argc, char* argv[]) {
  FILE* fptr;
  if (argc <= 3){
    if(argc == 3 )fptr = fopen(argv[2], "r");
    else if( argc == 2)fptr = fopen(argv[1], "r");

    if( fptr == NULL){
      printf("File doesn't exist\n");
    }
    else{
      char c;
      c = fgetc(fptr);
      while (c != EOF)
      {
        if( strcmp(argv[1], "-u") == 0){
          printf ("%c", tolower(c));
        }

        else if( strcmp(argv[1], "-l") == 0){
          printf ("%c", toupper(c));
        }
        else{
          printf("%c", c);
        }
        c = fgetc(fptr);
      }
    }
  }
  return EXIT_SUCCESS;
}
