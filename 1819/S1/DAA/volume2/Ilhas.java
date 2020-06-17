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
//************************************************************

public class Ilhas{
  static int[] pais;
  static void set_dad(boolean[] pai, int max){
    for(int i=1; i<pai.length;i++){
      if(pai[i]){
        pais[i]=max;
      }
    }
  }
  static void BFS_visit(int s, Grafo0 g,boolean[] visitado){
    int v,w=g.num_vertices();
    Queue<Integer> fila = new LinkedList<Integer>();
    boolean[] pai=new boolean[visitado.length];
    visitado[s]=true;pai[s]=true;
    fila.add(s);
    int max=s;
    do{
      v=fila.remove();
      for(Arco adjs:g.adjs_no(v)){
        w=adjs.extremo_final();
        if(visitado[w]==false){
          visitado[w]=true;pai[w]=true;
          fila.add(w);
          if(w>max)max=w;
        }
      }
    }while(!fila.isEmpty());
    set_dad(pai,max);
  }
  public static void main(String[] args){
    Scanner stdin=new Scanner(System.in);
    int nnos=stdin.nextInt();
    int r=stdin.nextInt();
    Grafo0 g=new Grafo0(nnos);
    pais=new int[nnos+1];
    boolean visitado[] = new boolean[nnos+1];
    for(int i=0; i<r; i++){
      int k=stdin.nextInt();
      int j=stdin.nextInt();
      g.insert_new_arc(k,j);
      g.insert_new_arc(j,k);
    }
    for(int i=0; i<=nnos; i++){
      if(!visitado[i]){
        BFS_visit(i,g,visitado);
      }
    }
    int nper=stdin.nextInt();
    for(int i=0; i<nper; i++){
      int no= stdin.nextInt();
      if(no>=nnos){
        System.out.println("No "+no+": "+no);
      }else{
        System.out.println("No "+no+": "+pais[no]);
      }
    }
  }
}
