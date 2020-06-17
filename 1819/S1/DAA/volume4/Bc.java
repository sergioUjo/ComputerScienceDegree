import java.util.Scanner;
import java.util.LinkedList;

class Arco {
  int no_final;
  int valor0;
  int valor1;

  Arco(int fim, int v0, int v1){
    no_final = fim;
    valor0  = v0;
    valor1 = v1;
  }

  int extremo_final() {
    return no_final;
  }

  int valor0_arco() {
    return valor0;
  }

  int valor1_arco() {
    return valor1;
  }

  void novo_valor0(int v) {
    valor0 = v;
  }

  void novo_valor1(int v) {
    valor1 = v;
  }
}


class No {
  //int label;
  LinkedList<Arco> adjs;

  No() {
    adjs = new LinkedList<Arco>();
  }
}


class Grafo2 {
  No verts[];
  int nvs, narcos;

  public Grafo2(int n) {
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

  public void insert_new_arc(int i, int j, int valor0, int valor1){
    verts[i].adjs.addFirst(new Arco(j,valor0,valor1));
    narcos++;
  }

  public Arco find_arc(int i, int j){
    for (Arco adj: adjs_no(i))
    if (adj.extremo_final() == j) return adj;
    return null;
  }
}

public class Bc{
  static void percurso(int n, Scanner in, Grafo2 b){
    int prev=in.nextInt();
    int curr=in.nextInt();
    //System.out.println(prev+" "+curr);
    int temp=b.find_arc(prev,curr).valor0_arco();
    int min=temp;
    int max=temp;
    //System.out.println(max);
    for(int i=0; i<n-2; i++){
      prev=curr;
      curr=in.nextInt();
      temp=b.find_arc(prev,curr).valor0_arco();
      if(temp>max){
        max=temp;
      }
      else if(temp<min){
        min=temp;
      }
      //System.out.println(prev+" "+curr);

    }
    System.out.println(min+" "+max);
  }
  public static void main(String[] agrs){
    Scanner stdin=new Scanner(System.in);
    int nnos= stdin.nextInt();
    int narcos=stdin.nextInt();
    Grafo2 b=new Grafo2(nnos);
    for (int k=0;k<narcos;k++) {
      int i=stdin.nextInt();
      int j=stdin.nextInt();
      int v0=stdin.nextInt();
      int v1=stdin.nextInt();
      b.insert_new_arc(i,j,v0,v1);
      b.insert_new_arc(j,i,v0,v1);
    }
    //System.out.println(" *1* ");
    int nsaltos=stdin.nextInt();
    //System.out.println(nsaltos);
    while(nsaltos!=0){
      percurso(nsaltos,stdin,b);
      nsaltos=stdin.nextInt();
    }
  }
}
