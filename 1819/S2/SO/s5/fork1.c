#include <stdio.h>
#include <string.h>

void read ( char * value){
  value = strtok( value, " =()");
  while (value) {
    printf ("%s -- ",value);
    value = strtok( NULL, " =()");
  }
}

int main (int agrc, char * argv[]){
    FILE *myfile = fopen ( argv[1], "r" );

    char line[9999];

    // loop through each entry in Assignment1file
    while(fgets(line, sizeof(line), myfile)){
      //printf("%s", line);
      sscanf( line, "%s %s %d  %d", weekday, month, &day, &year );
    }

    fclose(myfile);
    return 0;
}
