import java.util.*;


class Qnode {
    int vert;
    int vertkey;

    Qnode(int v, int key) {
	vert = v;
	vertkey = key;
    }
}

class Heapmax {
    private static int posinvalida = 0;
    int sizeMax,size;

    Qnode[] a;
    int[] pos_a;

    Heapmax(int vec[], int n) {
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

    int extractMax() {
	int vertv = a[1].vert;
	swap(1,size);
	pos_a[vertv] = posinvalida;  // assinala vertv como removido
	size--;
	heapify(1);
	return vertv;
    }

    void increaseKey(int vertv, int newkey) {

	int i = pos_a[vertv];
	a[i].vertkey = newkey;

	while (i > 1 && compare(i, parent(i)) > 0) {
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
	increaseKey(vertv,key);   // aumenta a chave e corrige posicao se necessario
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
	int l, r, largest;

	l = left(i);
	if (l > size) l = i;

	r = right(i);
	if (r > size) r = i;

	largest = i;
	if (compare(l,largest) > 0)
	    largest = l;
	if (compare(r,largest) > 0)
	    largest = r;

	if (i != largest) {
	    swap(i, largest);
	    heapify(largest);
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


public class EQD{
  static void graph_from( int[] array, Grafo g, int n){

    for( int i = 0; i < array.length-1; i+=2 ){
      if(g.find_arc(array[i],array[i+2])==null) g.insert_new_arc(array[i],array[i+2], n);
      if(g.find_arc(array[i+2],array[i])==null) g.insert_new_arc(array[i+2],array[i], n);

    }

  }
  static boolean invalid( int a, int b, int dimin, int dimax){
    if(a < dimin && a!=-1 )return true;

    if(b < dimin && b!=-1 )return true;
    return false;
  }

  static void dis(int origem, Grafo g, int n){
    int dist[] = new int[g.num_vertices()+1];
    int pai[] = new int[g.num_vertices()+1];
    dist[origem]=9999999;
    Heapmax heap= new Heapmax(dist, g.num_vertices());

    while( !heap.isEmpty() ){
      int c = heap.extractMax();

      for(Arco y: g.adjs_no(c)){
        if(y.valor_arco()==-1)continue;
        int w=y.extremo_final();
        if( dist[ w ] < Math.min(dist[c],y.valor_arco()) ){
          dist[ w ] = Math.min(dist[c],y.valor_arco());
          pai[w]=c;
          heap.increaseKey( w, dist[ w ] );
        }
      }
      //System.out.println(c +"-->" +Arrays.toString(dist));
    }
    boolean flag=false;
    for(int i=1; i < dist.length; i++){
      if(dist[i]!=n && i!=origem){
        flag=true;
        System.out.println("No "+i+": "+dist[i]);
      }
    }
    if(!flag)System.out.println("Ok todos destinos!");
  }
  public static void main( String[] args ){
    Scanner stdin= new Scanner(System.in);

    int origem = stdin.nextInt();
    int dimin = stdin.nextInt();
    int dimax = stdin.nextInt();

    int ntraj = stdin.nextInt();
    int nnos = stdin.nextInt();
    Grafo g= new Grafo(nnos);

    for( int i = 0; i < ntraj; i++){
      int tt = stdin.nextInt();
      int temp[] = new int[ 2*tt -1 ];
      for ( int j = 0; j < temp.length; j++){
        temp[j] = stdin.nextInt();
      }
      graph_from(temp,g,dimax);
    }
    int specs = stdin.nextInt();

    for( int i = 0; i<specs ; i++){
      int a = stdin.nextInt();
      int b = stdin.nextInt();
      int c = stdin.nextInt();
      int d = stdin.nextInt();
      if ( invalid( c, d, dimin, dimax) ){
        if(g.find_arc( b, a)!=null){
          g.find_arc( b, a).novo_valor(-1);
          g.find_arc( a, b).novo_valor(-1);
        }
      }
      else{
        int aux=0;
        if( c == -1 ) aux = d;
        else if( d == -1 ) aux = c;
        else aux = Math.min(c,d);
        if( aux > dimax) aux = dimax;
        if(g.find_arc( a, b)!=null){
          g.find_arc( a, b).novo_valor(aux);
          g.find_arc( b, a).novo_valor(aux);

        }

      }
    }
    dis(origem,g,dimax);
  }
}
