typedef enum {NORMAL , PRIORITY} FilaKind;

typedef struct caixa{
  int eta;
  int clientes;
  int produtos;
  int espera;
  int numero;
  int velocidade;
  FilaKind kind;
  union {
    struct {QUEUE* fila;};
    struct {PQUEUE* pfila;};
  };
} *Caixa;

Caixa mk_new( int valor );
Caixa mk_new_p(int valor);

//MODIFICADORES
void disponivel( Caixa c, int valor);
void cliente_atendido( Caixa c );
void actualiza_produtos( Caixa c, int valor );
void actualiza_espera( Caixa c, int valor);
void remove_cliente( Caixa c );
void caixa_enqueue(Cliente v, Caixa c);
//Seletores
int pronta(Caixa c);
int clientes(Caixa c);
int produtos(Caixa c);
int espera(Caixa c);
int numero(Caixa c);
int tempo_processamento(Caixa c);
Cliente peek_caixa(Caixa c);

//Reconhecedores
int vazia(Caixa c);
void print_caixa(Caixa c);
