import java.util.*;


class Arco {
    int no_final;

    Arco(int fim){
	no_final = fim;
    }

    int extremo_final() {
	return no_final;
    }
}


class No {
    //int label;
    LinkedList<Arco> adjs;

    No() {
	adjs = new LinkedList<Arco>();
    }
}


class Grafo0 {
    No verts[];
    int nvs, narcos;

    public Grafo0(int n) {
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

    public void insert_new_arc(int i, int j){
	verts[i].adjs.addFirst(new Arco(j));
        narcos++;
    }

    public Arco find_arc(int i, int j){
	for (Arco adj: adjs_no(i))
	    if (adj.extremo_final() == j) return adj;
	return null;
    }
}


public class T1{
  static void bfs(Grafo0 g, int inicio, int nas[]){
    boolean visited[] = new boolean[ g.num_vertices() + 1 ];
    Queue<Integer> fila = new LinkedList<Integer>();

    visited[ inicio ] = true;

    fila.add(inicio);

    do{
      int v = fila.remove();
      for( Arco y: g.adjs_no( v ) ){
        int w = y.extremo_final();
        if( !visited[w] ){
          visited[ w ] = true;
          fila.add( w );
        }
      }
    }while( !fila.isEmpty() );

    int max = 0;
    for( int i = 1; i < nas.length; i++ ){
      if( visited[ i ] ){
        if( nas[i] > nas[max] ) max = i;
      }
    }
    if( max == 0 ){
      System.out.println( "Impossivel" );
    }
    else{
      System.out.println( max );
    }
  }

  public static void main( String[] args ){
    Scanner stdin = new Scanner( System.in );
    int nsuper = stdin.nextInt();
    int nas[] = new int[ nsuper + 1 ];

    for( int i = 1; i <= nsuper; i++){
      nas[i] = stdin.nextInt();
    }
    Grafo0 g = new Grafo0(nsuper);

    int nlig = stdin.nextInt();

    for( int i = 0; i < nlig; i++){
      int a = stdin.nextInt();
      int b = stdin.nextInt();
      g.insert_new_arc(a,b);
      g.insert_new_arc(b,a);
    }
    int ncasos = stdin.nextInt();


    for( int i=1; i <= ncasos; i++){
      int caso = stdin.nextInt();
      if(nas[caso] != 0 ){
        System.out.println(caso);
      }
      else{
        bfs( g, caso, nas );
      }
    }
  }
}
