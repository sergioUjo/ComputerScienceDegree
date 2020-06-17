#include <stdio.h>
int main() {
  int i, j, *p, *q;

  i = 5;
  p = &i;
  printf("i=%d, p=%p\n", i, p);
  *p = 7;
  j = 3;
  printf("i=%d, j=%d, p=%p\n", i, j, p);
  p = &j;
  q = p;
  printf("i=%d, j=%d, p=%p, q=%p\n", i, j, p, q);
  p = &i;
  *q = 2;
  printf("i=%d, j=%d, p=%p, q=%p\n", i, j, p, q);
  return 0;
}
