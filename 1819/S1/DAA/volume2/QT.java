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

public class QT{
  static void acessivel(int no,Grafo0 g){
    int v,w;
    boolean[] visitado=new boolean[27];
    Queue<Integer> fila = new LinkedList<Integer>();
    fila.add(no);
    visitado[no]=true;
    int count=0;
    do{
      v=fila.remove();
      for(Arco adjs:g.adjs_no(v)){
        w=adjs.extremo_final();
        if(!visitado[w]){
          fila.add(w);
          visitado[w]=true;
          //System.out.println((char)(w+64));
          count++;
        }
      }
    }while(!fila.isEmpty());
    System.out.println(count);
  }
  static void resolve(String s1, String s2,Grafo0 g){
    int size=Math.min(s1.length(),s2.length());
    for(int i=0; i<size;i++){
      if(s1.charAt(i)!=s2.charAt(i)){
        g.insert_new_arc(((int)s1.charAt(i)-64),((int)s2.charAt(i)-64));
        break;
      }
    }
  }
  public static void main(String[] agrs){
    Scanner stdin=new Scanner(System.in);
    String prev=stdin.nextLine();
    String cur=stdin.nextLine();
    if(cur.equals("#")){
      String garbage=stdin.nextLine();
      System.out.println(0);}
    else{
      Grafo0 alfa=new Grafo0(26);
      do{
        resolve(prev,cur,alfa);
        prev=cur;
        cur=stdin.nextLine();
      }while(!cur.equals("#"));
      int p=(int)stdin.nextLine().charAt(0)-64;
      //System.out.println(p);
      acessivel(p,alfa);
    }
  }
}
