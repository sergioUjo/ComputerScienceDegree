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

public class SP{
  static int max=0;

  static boolean DFSUtil(int v,boolean visited[],boolean recStack[],Grafo0 g) {

    if (recStack[v])return true;

    if (visited[v]){
      return false;
    }
    visited[v] = true;

    recStack[v] = true;
    // Recur for all the vertices adjacent to this vertex
    //System.out.println(g.adjs_no(v)+"dsadsadsa ");
    for(Arco x: g.adjs_no(v)){
      int n = x.extremo_final();
      //System.out.println(n);
      if (DFSUtil(n, visited,recStack,g)){return true;}
    }
    recStack[v] = false;
    return false;
  }
  static void depth(int i,int depth,Grafo0 g){
    //System.out.println(i+"ssss");
    for(Arco x: g.adjs_no(i)){
      if(depth>max)max=depth;
    //  System.out.println(depth);
      depth(x.extremo_final(),(depth+1),g);
    }
  }

  static boolean DFS(int n,Grafo0 g) {
    boolean visited[] = new boolean[n+1];
    boolean recStack[] = new boolean[n+1];
    for (int i = 0; i < n; i++) {
      if ( DFSUtil(i+1, visited,recStack,g)) {
        return true;
      }
      depth(i+1,1,g);
    }
    return false;
  }
//FIm de DFS -----------------------------------------------------------------------------------------

  static boolean analyser(int[] p,int[][] pref){
    Grafo0 g=new Grafo0(pref.length);
  //  System.out.println(Arrays.toString(p));
    for(int i=0; i<pref.length;i++){
      int k=0;
      while(pref[i][k]!=p[i]){
        //System.out.println(pref[i][k]+" "+(p[i]));
        if(g.find_arc(p[i], pref[i][k])==null){
          g.insert_new_arc(p[i], pref[i][k]);
        }
        k++;
      }
    }
    return DFS(pref.length,g);
  }
  //FIM de analyser -------------------------------------------------------------------
  public static void main(String[] agrs){
    Scanner stdin=new Scanner(System.in);
    int ntra=stdin.nextInt();
    int[][] pref=new int[ntra][ntra];
    for(int i=0;i<ntra;i++){
      for(int j=0; j<ntra;j++){
        pref[i][j]=stdin.nextInt();
      }
    }
    int nprop=stdin.nextInt();
    for(int i=0;i<nprop; i++){
      int atr[]=new int[ntra];
      for(int j=0;j<ntra;j++){
        atr[j]=stdin.nextInt();
      }

      if(analyser(atr,pref)){
        System.out.println("Indeterminado (nao Pareto-optima)");
      }
      else{
        System.out.println(max);
        max=0;
      }
    }
  }
}
