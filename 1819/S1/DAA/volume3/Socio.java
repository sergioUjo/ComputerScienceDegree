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
//*****************************************************************

public class Socio{

  static void print(String s){
    String[] arr=s.split(" ");
    //System.out.println(Arrays.toString(s1));
    int count1=0;
    int count2=0;
    for(int i=0; i<arr.length; i++){
      int aux=Integer.parseInt(arr[i]);
      if(aux>=4)count1++;
      else{count2+=aux;}
    }
    System.out.println(count1+" "+count2);
  }

  static Stack<Integer> DFS(Grafo0 g){
    Stack<Integer> stack = new Stack<Integer>();
    boolean[] visitado = new boolean[g.num_vertices()+1];

    for(int i=1; i<=g.num_vertices();i++){
      if(!visitado[i]){
        DFS(i,g,stack,visitado);
      }
    }
    return stack;
  }

  static Stack<Integer> DFS(int v,Grafo0 g,Stack<Integer> stack,boolean[] visitado){
    visitado[v]=true;
    for(Arco y: g.adjs_no(v)){
      if(!visitado[y.extremo_final()]){
        DFS(y.extremo_final(),g,stack,visitado);
      }
    }
    stack.push(v);
    return stack;
  }

  static String algoritmo(Grafo0 g,Grafo0 gt){
    Stack<Integer> c= DFS(g);
    String s="";
    boolean[] visited=new boolean[g.num_vertices()+1];
    while(!c.empty()){
      int v = c.pop();
      if (!visited[v]) {
        s+=DFS(v,gt,new Stack<Integer>(), visited).size()+" ";
      }

    }
    return s;
  }
  
  public static void main(String[] args){
    Scanner stdin=new Scanner(System.in);
    int ncasos = stdin.nextInt();

    for(int i=0; i<ncasos; i++){
      int npess=stdin.nextInt();
      Grafo0 g= new Grafo0(npess);
      Grafo0 gt= new Grafo0(npess);

      for(int j=0; j<npess; j++){
        int pess = stdin.nextInt();
        int rela = stdin.nextInt();
        for(int k=0; k<rela; k++){
          int aux=stdin.nextInt();
          //System.out.println(aux);
          g.insert_new_arc(pess,aux);
          gt.insert_new_arc(aux,pess);
        }
      }
      System.out.println("Caso #"+(i+1));
      print(algoritmo(g,gt));
    }
  }
}
