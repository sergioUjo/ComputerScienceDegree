#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>
int main(int argc, char* argv[]) {

  if (argc != 2) {
    fprintf(stderr, "usage: %s file\n", argv[0]);
    return EXIT_FAILURE;
  }
  open(argv[1], O_RDWR | O_CREAT, 0644);

  return EXIT_SUCCESS;
}
