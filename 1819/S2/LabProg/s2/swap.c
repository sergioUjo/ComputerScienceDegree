#include <stdio.h>
#include <stdlib.h>

void swap( int *p, int *q){
  int temp;
  temp=*p;
  *p=*q;
  *q=temp;
}
void swap1( int a, int b){
  int temp;
  temp=a;
  a=b;
  b=temp;
}

int comp(char *s){
  char *p=s;
  while(*s) ++s;
  return s-p;
}

//Copia uma String
char *copia( char *d, char *s){
  int i=0;

  for(i=0 ; s[i]!='\0'; i++){
    d[i] = s[i];
  }
  return d;
}

//copia uma String, alocando memória para nova String
char *copiaAlloc(char *s){
  int i=0;
  char *d = malloc( sizeof(char) * comp(s) );
  for(i=0 ; s[i]!='\0'; i++){
    d[i] = s[i];
  }
  return d;
}

char *copiaN(char *d, char *s, int n){
  int i=0;
  for(i=0 ; i<n; i++){
    d[i] = s[i];
  }
  return d;
}

int main(){

  int a=0,b=0, n=0;
  scanf("%d %d",&a, &b );
  swap(&a,&b);
  printf("a %d , b %d \n", a, b);
  swap1(a,b);
  printf("a %d , b %d \n", a, b);

  char s[10];
  scanf("%s", s);

  printf("%s\n", copiaAlloc(s));

  char *d = malloc( sizeof(char) * comp(s));
  printf("%s\n", copia(d,s));
  scanf("%d", &n);

  char *z = malloc( sizeof(char) * n);
  printf("%s\n", copiaN(z,s,n));
  return 0;
}
