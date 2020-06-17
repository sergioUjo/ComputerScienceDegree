#define MULTIPLIER 31
#define HASH_SIZE 999999
LIST table[HASH_SIZE];

unsigned int hash( char *s );
void insertl( char *s, char *c );
void insert( char *s, int value );
LIST lookup( char *s);
void init_table();
void print( char *c );
