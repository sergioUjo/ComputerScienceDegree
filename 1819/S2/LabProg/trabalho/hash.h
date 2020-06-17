#define MULTIPLIER 31
#define HASH_SIZE 999999
LIST table[HASH_SIZE];

unsigned int hash( char *s );
void insert( char *s, int value );
LIST lookup( char *s);
void init_table();
void printHashV( char *c );
