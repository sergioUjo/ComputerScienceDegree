
import java.util.*;

public class TarJava{


  static int DurMin;
  static Grafo0 g;
  static Grafo0 gt;
  static int Dv[];
  static int Tv[];
  static int ES[];
  static int EF[];
  static int LF[];
  static int min;

  public static void caminhCritico(){
    int GrauE[] = new int[ g.num_vertices()+1 ];

    for( int i = 1; i<ES.length; i++){
      for( Arco adj : g.adjs_no(i)){
        GrauE[adj.extremo_final()]++;
      }
    }

    Stack<Integer> s = new Stack<Integer>();
    for( int i = 1; i<ES.length; i++){
      if( GrauE[i] == 0){
        s.add( i );
      }
    }
    DurMin = -1;
    int vf = 0;

    while( !s.isEmpty() ){
      int v = s.pop();
      if( DurMin < ES[v] + Dv[v] ){DurMin = ES[v] + Dv[v]; vf = v;}
      for( Arco z : g.adjs_no( v ) ){
        int w = z.extremo_final();
        if( ES[w] < ( ES[v] + Dv[v] ) ){
          ES[w] = ES[v] + Dv[v];
        }
        GrauE[w]--;
        if( GrauE[w] == 0 ) s.add(w);
      }
    }
  }


  public static void caminhCritico1(){
    int GrauS[] = new int[ gt.num_vertices()+1 ];

    for (int i = 1 ; i < LF.length ; i++)
      LF[i] = DurMin;

    for( int i = 1; i<LF.length; i++){
      for( Arco adj : g.adjs_no(i)){
        GrauS[adj.extremo_final()]++;
      }
    }

    Stack<Integer> s = new Stack<Integer>();
    for( int i = 1; i<LF.length; i++){
      if( GrauS[i] == 0){
        s.add( i );
      }
    }

    while( !s.isEmpty() ){
      int v = s.pop();
      for( Arco z : gt.adjs_no( v ) ){
        int w = z.extremo_final();
        System.out.println(LF[w] +" "+ ( LF[v] - Dv[v] ));
        if( LF[w] > ( LF[v] - Dv[v] ) ){
          LF[w] = LF[v] - Dv[w];
        }
        GrauS[w]--;
        if( GrauS[w] == 0 ) s.add(w);
      }
    }
  }

  public static int CriticalNeededWorkers( Dominio inter[], boolean cr ) {
    int minworkers = 0;
    int currentneeded = 0;

    for (int i=0 ; i < DurMin ; i++) {
      for (int j=1 ; j < inter.length; j++) {
        if ( ( cr ? ES[j] == LF[j]-Dv[j] : inter[j].size()==1 ) ) {
          if (inter[j].min == i) currentneeded += Tv[j];
          else if (EF[j] == i) currentneeded-=Tv[j];
          if (currentneeded > minworkers) minworkers = currentneeded;
        }
      }
    }
    return minworkers;
  }


  public static int NeededWorkers(int start[]) {
    int minworkers = 0;
    int currentneeded = 0;

    //calculate earliest finish
    for (int i = 1 ; i< start.length; i++) {
      EF[i] = start[i] + Dv[i];
    }

    for (int i=0 ; i < DurMin ; i++) {
      for (int j=1 ; j < start.length ; j++) {
        if (start[j] == i) currentneeded += Tv[j];
        else if (EF[j] == i) currentneeded-=Tv[j];
        if (currentneeded > minworkers) minworkers = currentneeded;
      }
    }

    return minworkers;
  }

/*
  public static int minES(int id, int start[]){
    if( start[id] == LS[id] ) return 0;
    else if(g.adjs_no(id).size()==0) return 0;
    else{
      int min= Integer.MAX_VALUE;
      for (Arco adj : g.adjs_no(id)){
        int w = adj.extremo_final();
        if( start[w] < min) min = start[w];
      }
      return min-EF[id];
    }
  }*/

  public static void possible(Dominio start[], int id){
    for (Arco adj : g.adjs_no(id)){
      int w = adj.extremo_final();
      if (start[w].min < (start[id].min + Dv[id])){
        start[w].min = start[id].min + Dv[id];
        possible( start, w);
      }
    }
  }

  public static void minWorkers( Dominio start[]){

    Queue<Dominio[]> activeset = new LinkedList<Dominio[]>();
    activeset.add(start);
    int [] currentbest = ES;
    while( !activeset.isEmpty() ){
      Dominio k[] = activeset.remove();
      int nk;
      for( nk=1; nk<k.length; nk++){ if( k[nk].size()!=1 ) break;}
      for( Dominio child[] : generateChild(nk, k) ){
        if ( CriticalNeededWorkers( child,false ) > min) continue;

        else if ( defined( child) ){
          min = CriticalNeededWorkers( child,false );
        }
        else activeset.add(child);
      }
    }
  }
  static List<Dominio[]> generateChild(int nk, Dominio k[]){
    List<Dominio[]> res = new LinkedList<Dominio[]>();

    for( int i = 1; i < k[nk].size(); i++){
      Dominio temp[] = k.clone();
      temp[nk] = new Dominio( k[nk].min + i);
      possible( temp , nk);
      res.add( temp );
    }
    return res;
  }
  static boolean defined( Dominio k[]){
    for( int i =1; i<k.length; i++){
      if( k[i].size()!=1) return false;
    }
    return true;
  }


  public static  void main( String[] args ){

    Scanner stdin = new Scanner(System.in);
    int nTarefas = stdin.nextInt();
    g = new Grafo0(nTarefas);
    gt = new Grafo0(nTarefas);
    Dv = new int[ nTarefas+1 ];
    Tv = new int[ nTarefas+1 ];
    ES = new int[ nTarefas+1 ];
    EF = new int[ nTarefas+1 ];
    LF = new int[ nTarefas+1 ];

    int neededworkers;
    int criticalworkers;

    for( int i = 0; i < nTarefas; i++){
      int id = stdin.nextInt();
      int nprece = stdin.nextInt();

      for( int j=0; j<nprece; j++){
        int temp = stdin.nextInt();
        g.insert_new_arc( id, temp);
        gt.insert_new_arc( temp, id);
      }
      Dv[ id ] = stdin.nextInt();
      Tv[ id ] = stdin.nextInt();
    }
    caminhCritico(); //Calcular ES
    caminhCritico1(); //Calcular LF
    System.out.println(Arrays.toString( ES ));
    System.out.println(Arrays.toString( LF ));


    min = NeededWorkers( ES );
    System.out.println("Duracao min: " + DurMin );
    System.out.println("Number of workers(ES):" + min);

    //CriticalNeededWorkers(scheE);
    Dominio[] sample= new Dominio[ nTarefas +1 ];
    for(int i = 1 ; i < nTarefas +1; i++){
      sample[i]=  new Dominio( ES[i], LF[i]-Dv[i]);
    }
    System.out.println("Number of workers(Cr):" +  CriticalNeededWorkers( sample, true ));
    System.out.println(Arrays.toString(sample));
    minWorkers( sample );
    System.out.println(min);
  }
}

class Dominio{
  int min;
  int max;
  Dominio(int mi, int ma){
    min = mi;
    max = ma;
  }
  Dominio( int valor ){
    min = valor;
    max = valor;
  }

  public int size(){ return max-min+1; }

  public String toString(){
    return "<" + min + "," + max +">";
  }
}
