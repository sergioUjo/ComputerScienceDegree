#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

int f(char* content) {
  char *str = strdup(content);
  int result = 2;
  return result;
}
int main(int argc, char* argv[]) {
  if (argc == 2)
  printf("result = %d\n", f(argv[1]));
  return EXIT_SUCCESS;
}
