import java.util.*;

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
// COMEÇO DO PROGRAMA

public class SR{
  ///CONTAINS ORI AND dest
  /*static boolean contains(int ori,int des, int[] per){
  boolean fori=false,fdes=false;
  for(int i=0, i<per.length;i+=2){
  if(fori&&fdes)return true;

  if(per[i]==ori)fori=true;
  if(per[i]==des)fdes=true;
}
return false;
}*/
//FIM contains
static int rotaSolver(Grafo rede,int [] per,int nelem,int ori,int des){
  int t=0;
  boolean fori=false,fdes=false;
  //System.out.println(Arrays.toString(per));
  for(int i=0; i<(per.length-2); i+=2){

    if(per[i]==ori){
      fori=true;
    }
    if(!fdes){
      if(fori){
        if(per[i+1]<nelem){
          //System.out.println("elem");
          return -1;
        }
      }
      if(rede.find_arc(per[i],per[i+2])==null)return -1;
      t+=rede.find_arc(per[i],per[i+2]).valor_arco();
    }
    if(fori&&per[i+2]==des)fdes=true;
  }
  if(fori&&fdes){
    return t;
  }
  return -1;
}
//FIM de Solver -------------------------------------------------------------------
public static void main(String[] agrs){
  Scanner stdin=new Scanner(System.in);
  int nelem=stdin.nextInt();
  int ori=stdin.nextInt();
  int des=stdin.nextInt();

  //Criação do mapa
  Grafo rede= new Grafo(stdin.nextInt());
  int ramos=stdin.nextInt();
  for(int i=0; i<ramos; i++){
    rede.insert_new_arc(stdin.nextInt(),stdin.nextInt(),stdin.nextInt());
  }
  int nrotas=stdin.nextInt();
  int tmin=ramos+1;
  int min=0;
  for(int i=0; i<nrotas;i++){
    //LER O CAMINHO
    int nnos=stdin.nextInt();

    int[] aaux=new int[nnos+nnos-1];
    for(int j=0; j<aaux.length; j++){
      aaux[j]=stdin.nextInt();
    }
    //FIM CAMINHO

    int temp=rotaSolver(rede,aaux,nelem,ori,des);
    //System.out.println(temp);
    if(temp==-1){
      continue;
    }
    if(temp<tmin){
      tmin=temp;
      min=i+1;
    }
  }
  if(tmin==ramos+1){
    System.out.println("Impossivel");
  }
  else{
    System.out.println("Reserva na rota "+min+": "+ tmin);
  }
}
