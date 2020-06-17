#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

int main(int argc, char* argv[]) {
  mode m;
  /* check if 2 args available */
  if (argc != 2) {
    printf("WRONG\n");
    return EXIT_FAILURE;
  }
  /* check if argv[2] can be opened and is readable */
  FILE* fp = fopen(argv[1], "r");
  if(fp == NULL) {
    printf("%s: cannot open %s\n", argv[0], argv[2]);
    return EXIT_FAILURE;
  }
  char c;
  int count = 0, count2 = 0;
  /* get one char at a time and print upper or lower case */
  while(fread(&c, 1, 1, fp) != 0) {
    count++;
    if( c == '\n' ) count2 ++;
  }
  /* close file */
  fclose(fp);
  printf("Numero de charateres %d %d\n", count, count2);
  return EXIT_SUCCESS;
}
