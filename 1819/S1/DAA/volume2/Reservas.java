import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;
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
//*****************************************************************

public class Reservas{
  static int valid(Arco g,int lug){
    if(g==null){return -2;}
    if(g.valor0_arco()<lug){return -1;}
    return g.valor1_arco()*lug;
  }
  static void removelug(Grafo2 g,int lug,int[] per){
    for(int i=0;i<per.length-1;i++){
      Arco temp=g.find_arc(per[i],per[i+1]);
      temp.novo_valor0(temp.valor0_arco()-lug);
    }
  }
  static String reserva(int lug,Grafo2 g,int[] per){
    int valor=0;
    for(int i=0; i<per.length-1;i++){
      int aux=valid(g.find_arc(per[i],per[i+1]),lug);
      if(aux==-1){
        return "Sem lugares suficientes em ("+per[i]+","+per[i+1]+")";
      }
      else if(aux==-2){
        return "("+per[i]+","+per[i+1]+")"+" inexistente";
      }
      else{
        valor+=aux;
      }
    }
    removelug(g,lug,per);
    return "Total a pagar: " +valor;
  }

  public static void main(String[] args){
    Scanner stdin=new Scanner(System.in);
    int nnos=stdin.nextInt();
    int nramos=stdin.nextInt();
    Grafo2 liga=new Grafo2(nnos);
    for(int i=0; i<nramos; i++){
      liga.insert_new_arc(stdin.nextInt(),stdin.nextInt(),stdin.nextInt(),stdin.nextInt());
    }
    int nreser= stdin.nextInt();
    for (int i=0;i<nreser;i++) {
      int lug=stdin.nextInt();
      int per=stdin.nextInt();
      int nos[]=new int [per];
      for(int j=0; j<per; j++){
        nos[j]=stdin.nextInt();
      }
    //  System.out.println(Arrays.toString(nos));
      System.out.println(reserva(lug,liga,nos));
    }
  }
}
