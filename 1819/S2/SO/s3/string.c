#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#define MAX_STR_SIZE 64

int main(int argc, char* argv[]) {

  char* p1 = (char*)malloc(MAX_STR_SIZE * sizeof(char));
  char* p2 = (char*)malloc(MAX_STR_SIZE * sizeof(char));
  char* lp1 = (char*)malloc(MAX_STR_SIZE * sizeof(char));


  int result = strcmp(argv[1], argv[2]);
  if (result == 0)
  printf("the strings are the same\n");
  else if (result < 0)
  printf("%s < %s\n", argv[1], argv[2]);
  else
  printf("%s > %s\n", argv[2], argv[1]);

  strcpy(p1, argv[1]);
  strcpy(p2, argv[2]);

  for(int i=0 ; p1[i]!='\0'; i++){
    lp1[i] = tolower( p1[i] );
  }
  if( strstr(p2, p1) != NULL ){
    printf("OCOREE\n");
  }
  else{
    printf("NÃO OCOREE\n");
  }
  int count = 0;
  char* temp = strdup( p2 );

   while(strstr( temp, p1) != NULL){
    strcpy(temp, &temp[strlen(p1)]);
    count++;
  }

  printf("OCOREE %d VEZES\n", count);
  printf("lp1 holds:%s\n", lp1);
  printf("p1 holds:%s\n", p1);
  printf("p2 holds:%s\n", p2);
  strcat(p1,p2);
  printf("p1 holds:%s\n", p1);
  strcat(p2,p1);
  printf("p2 holds:%s\n", p2);
  return EXIT_SUCCESS;

}
