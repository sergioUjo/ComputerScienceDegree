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


public class MSS{
  static int calc( int[] array, Grafo0 g){
    int count=0;

    for( int i = 0; i < array.length-1; i+=2 ){
      //System.out.println(array[i]+" "+array[i+2] );
      if(g.find_arc(array[i],array[i+2])==null)  g.insert_new_arc(array[i],array[i+2]);
      if(g.find_arc(array[i+2],array[i])==null)  g.insert_new_arc(array[i+2],array[i]);
      count+=array[i+1];
    }
    return count;
  }
  public static void main( String[] args ){
    Scanner stdin= new Scanner(System.in);
    int ntraj = stdin.nextInt();
    int nnos = stdin.nextInt();
    Grafo0 g= new Grafo0(nnos);

    for( int i = 0; i < ntraj; i++){
      int tt = stdin.nextInt();
      int temp[] = new int[ 2*tt -1 ];
      for ( int j = 0; j < temp.length; j++){
        temp[j] = stdin.nextInt();
      }
      System.out.println( "Trajeto "+(i+1)+": "+ calc( temp, g )) ;
    }

    for( int i = 1; i<=nnos ; i++){
      System.out.println("No "+i+": "+g.adjs_no(i).size());
    }
  }
}
