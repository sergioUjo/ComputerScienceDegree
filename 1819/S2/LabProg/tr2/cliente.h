typedef struct cliente {
  int NumItems;
  int Entrada;
  int Prioridade;
} *Cliente;

Cliente mk_cliente(int n,int e);
int artigos(Cliente c);
int entrada(Cliente c);
void printCliente(Cliente c);
