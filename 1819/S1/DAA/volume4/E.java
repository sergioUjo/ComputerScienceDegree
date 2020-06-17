import java.util.*;
import java.lang.*;

class Arco {
  int no_final;
  int valor;

  Arco(int fim, int v){
    no_final = fim;
    valor = v;
  }

  int extremo_final() {
    return no_final;
  }

  int valor_arco() {
    return valor;
  }

  void novo_valor(int v) {
    valor = v;
  }
}


class No {
  //int label;
  LinkedList<Arco> adjs;

  No() {
    adjs = new LinkedList<Arco>();
  }
}


class Grafo {
  No verts[];
  int nvs, narcos;

  public Grafo(int n) {
    nvs = n;
    narcos = 0;
    verts  = new No[n+1];
    for (int i = 0 ; i <= n ; i++)
    verts[i] = new No();
    // para vertices numerados de 1 a n (posicao 0 nao vai ser usada)
  }

  public int num_vertices(){
    return nvs;
  }

  public int num_arcos(){
    return narcos;
  }

  public LinkedList<Arco> adjs_no(int i) {
    return verts[i].adjs;
  }

  public void insert_new_arc(int i, int j, int valor_ij){
    verts[i].adjs.addFirst(new Arco(j,valor_ij));
    narcos++;
  }

  public Arco find_arc(int i, int j){
    for (Arco adj: adjs_no(i))
    if (adj.extremo_final() == j) return adj;
    return null;
  }
}

//*********************************************************
class Qnode {
  int vert;
  int vertkey;

  Qnode(int v, int key) {
    vert = v;
    vertkey = key;
  }
}

class Heapmin {
  private static int posinvalida = 0;
  int sizeMax,size;

  Qnode[] a;
  int[] pos_a;

  Heapmin(int vec[], int n) {
    a = new Qnode[n + 1];
    pos_a = new int[n + 1];
    sizeMax = n;
    size = n;
    for (int i = 1; i <= n; i++) {
      a[i] = new Qnode(i,vec[i]);
      pos_a[i] = i;
    }

    for (int i = n/2; i >= 1; i--)
    heapify(i);
  }

  boolean isEmpty() {
    if (size == 0) return true;
    return false;
  }

  int extractMin() {
    int vertv = a[1].vert;
    swap(1,size);
    pos_a[vertv] = posinvalida;  // assinala vertv como removido
    size--;
    heapify(1);
    return vertv;
  }

  void decreaseKey(int vertv, int newkey) {

    int i = pos_a[vertv];
    a[i].vertkey = newkey;

    while (i > 1 && compare(i, parent(i)) < 0) {
      swap(i, parent(i));
      i = parent(i);
    }
  }


  void insert(int vertv, int key)
  {
    if (sizeMax == size)
    new Error("Heap is full\n");

    size++;
    a[size].vert = vertv;
    pos_a[vertv] = size;   // supondo 1 <= vertv <= n
    decreaseKey(vertv,key);   // diminui a chave e corrige posicao se necessario
  }

  void write_heap(){
    System.out.printf("Max size: %d\n",sizeMax);
    System.out.printf("Current size: %d\n",size);
    System.out.printf("(Vert,Key)\n---------\n");
    for(int i=1; i <= size; i++)
    System.out.printf("(%d,%d)\n",a[i].vert,a[i].vertkey);

    System.out.printf("-------\n(Vert,PosVert)\n---------\n");

    for(int i=1; i <= sizeMax; i++)
    if (pos_valida(pos_a[i]))
    System.out.printf("(%d,%d)\n",i,pos_a[i]);
  }

  private int parent(int i){
    return i/2;
  }
  private int left(int i){
    return 2*i;
  }
  private int right(int i){
    return 2*i+1;
  }

  private int compare(int i, int j) {
    if (a[i].vertkey < a[j].vertkey)
    return -1;
    if (a[i].vertkey == a[j].vertkey)
    return 0;
    return 1;
  }


  private void heapify(int i) {
    int l, r, smallest;

    l = left(i);
    if (l > size) l = i;

    r = right(i);
    if (r > size) r = i;

    smallest = i;
    if (compare(l,smallest) < 0)
    smallest = l;
    if (compare(r,smallest) < 0)
    smallest = r;

    if (i != smallest) {
      swap(i, smallest);
      heapify(smallest);
    }

  }

  private void swap(int i, int j) {
    Qnode aux;
    pos_a[a[i].vert] = j;
    pos_a[a[j].vert] = i;
    aux = a[i];
    a[i] = a[j];
    a[j] = aux;
  }

  private boolean pos_valida(int i) {
    return (i >= 1 && i <= size);
  }
}



//***************************************************************

public class E{

  static boolean valid_cage( int[] dimen, int[] minmax){
    //System.out.println(Arrays.toString(dimen)+" "+Arrays.toString(minmax));
    if( ( dimen[0] >= minmax[0]) ){
      if( (dimen[1] >= minmax[2]) ){
        if( dimen[2] >= minmax[4] ) return true;
      }
    }
    return false;
  }

  static int check_route( Grafo g, int dist, int[] pai, int fim, int inicio ){
    int min=99999999;
    for( int i = 0; i < dist; i++ ){
      //System.out.println(fim + " " + pai[ fim ]);
      if( g.find_arc( fim, pai[ fim ] ) == null ) return -1;
      int temp  = g.find_arc( fim, pai[ fim ] ).valor_arco();
      if( temp < min ) min = temp;
      fim= pai[ fim ];
      if ( fim == inicio ) return min;
    }
    return min;
  }
  static int distr_alg(Grafo g, int inicio, int fim){
    int[] pai = new int[ g.num_vertices() + 1 ];
    int[] dist = new int[ g.num_vertices() + 1 ];
    boolean[] ok = new boolean[ g.num_vertices() + 1 ];
    Queue<Arco> arc = new LinkedList< Arco >();

    for(int i=0; i<dist.length; i++){ dist[i]=9999999;}
    int v;
    dist [inicio] = 0;
    Heapmin heap = new Heapmin(dist,g.num_vertices());

    while(!heap.isEmpty()){
      v=heap.extractMin();
      if( g.find_arc( pai[ v ], v ) != null ) arc.add( g.find_arc( pai[v], v ) );
      ok[v]=true;
      for(Arco y: g.adjs_no(v)){
        int w= y.extremo_final();
        if(!ok[ w ] && ( -1 * y.valor_arco() < dist[ w ] ) ){
          dist[ w ] =  -1 * y.valor_arco();
          pai[ w ] = v;
          heap.decreaseKey( w, dist[ w ] );
        }
      }
    }
    return check_route( g, arc.size(), pai, fim, inicio );

  }
  public static void main(String[] args){
    Scanner stdin=new Scanner(System.in);

    int[] medicoes = new int [ 5 ];
    for ( int i = 0; i < 5; i++){
      medicoes [i] = stdin.nextInt();
    }
    int inicio = stdin.nextInt();
    int fim = stdin.nextInt();

    Grafo g = new Grafo( 1000000);
    int a=0;
    int b=0;
    int[] dimen = new int[3];

    while( true ){
      a = stdin.nextInt();
      if ( a == -1 ) break;
      b = stdin.nextInt();

      for ( int i = 0; i < 3; i++) dimen [i] = stdin.nextInt();
      //System.out.println(valid_cage( dimen, medicoes));
      if( valid_cage( dimen, medicoes) ){
        g.insert_new_arc( a, b, dimen[1]);
        g.insert_new_arc( b, a, dimen[1]);
      }

    }
    int c = distr_alg( g, inicio, fim);
    if( c == -1 ) {
      System.out.println( 0 );
    }
    else if ( c > medicoes[3]){
      System.out.println( medicoes[3] );
    }
    else{
      System.out.println( c );
    }
  }
}
