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


//*************************************************************

public class TL{

  static boolean valid_path( int temp, int[] minmax){

    if( temp >= minmax[0] ){
      if( temp <= minmax[1] ){
        return true;
      }
    }
    return false;
  }

  static int BFS (Grafo0 g, int inicio, int fim ){
    int v,w;
    boolean[] visitado=new boolean[ g.num_vertices() + 1 ];
    Queue<Integer> fila = new LinkedList<Integer>();
    int[] dist = new int[ g.num_vertices() + 1 ];

    fila.add(inicio);
    visitado[ inicio ] = true;
    int min=g.num_arcos()+1;
    do{
      v=fila.remove();
      for(Arco adjs:g.adjs_no(v)){
        w=adjs.extremo_final();
        if(!visitado[w]){
          fila.add(w);
          visitado[w]=true;
          dist[ w ] = dist[ v ] + 1;
          //System.out.println(w);
          if( w == fim ) if( dist [ w ] < min ) min = dist[ w ];
        }
      }
    }while(!fila.isEmpty());
    return min;
  }


  public static void main(String[] args){
    Scanner stdin=new Scanner(System.in);

    int[] temperatura = new int [ 2 ];
    for ( int i = 0; i < 2; i++){
      temperatura [i] = stdin.nextInt();
    }
    int inicio = stdin.nextInt();
    int fim = stdin.nextInt();
    int nnos = stdin.nextInt();
    int nramos = stdin.nextInt();

    Grafo0 g = new Grafo0( nnos );

    int a=0;
    int b=0;
    int[] carac = new int[2];

    for( int j = 0; j < nramos; j++){

      a = stdin.nextInt();
      b = stdin.nextInt();

      for ( int i = 0; i < 2; i++) carac [i] = stdin.nextInt();
      //System.out.println(valid_cage( dimen, medicoes));
      if( valid_path( carac[0], temperatura) ){
        g.insert_new_arc( a, b );
        g.insert_new_arc( b, a );
      }
    }
    int c = BFS( g, inicio, fim);
    //System.out.println(c);
    if ( c == g.num_arcos() + 1 ) System.out.println("Nao");
    else System.out.println( "Sim " + c );

  }
}
