#include <sys/wait.h>
#include <sys/socket.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <ctype.h>
#define SOCK0 0
#define SOCK1 1
#define DATA0 "In every walk with nature..."
#define DATA1 "...one receives far more than he seeks."
/* by John Muir */
int main(int argc, char* argv[]) {
  int sockets[2];
  char buf[1024];
  pid_t pid;
  if (socketpair(AF_UNIX, SOCK_STREAM, 0, sockets) < 0) {
    perror("opening stream socket pair");
    exit(1);
  }
  if ((pid = fork()) < 0) {
    perror("fork");
    return EXIT_FAILURE;
  }
  else if (pid == 0) {
    /* this is the child */
    close(sockets[SOCK0]);
    if (read(sockets[SOCK1], buf, sizeof(buf)) < 0)
    perror("reading stream message");
    printf("message from %d-->%s\n", getppid(), buf);
    char *temp = buf;
    while (*temp) {
      *temp = toupper((char)*temp);
      temp++;
    }
    temp -= 1024;
    if (write(sockets[SOCK1], buf, sizeof(DATA1)) < 0)
    perror("writing stream message");
    close(sockets[SOCK1]);
    /* leave gracefully */
    return EXIT_SUCCESS;
  }
  else {
    /* this is the parent */
    close(sockets[SOCK1]);
    FILE *file;
    file = fopen(argv[1], "r");
    fread(&buf, sizeof(char), 1024, file);
    if (write(sockets[SOCK0], buf, sizeof(DATA0)) < 0)
    perror("writing stream message");
    if (read(sockets[SOCK0], buf, sizeof(buf)) < 0)
    perror("reading stream message");
    printf("message from %d-->%s\n", pid, buf);
    close(sockets[SOCK0]);
    /* wait for child and exit */
    if (waitpid(pid, NULL, 0) < 0) {
      perror("did not catch child exiting");
      return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
  }
}
