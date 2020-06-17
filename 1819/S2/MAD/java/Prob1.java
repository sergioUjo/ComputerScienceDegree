import java.util.*;

public class Prob1{


  static int DurMin;
  static Grafo0 g;
  static Grafo0 gt;
  static int Dv[];
  static int Tv[];
  static int ES[];
  static int LF[];
  static int min;
  static int[] sol;
  static boolean flag;

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
        for( Arco adj : gt.adjs_no(i)){
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
        if( LF[w] > ( LF[v] - Dv[v] ) ){
          LF[w] = LF[v] - Dv[v];
        }
        GrauS[w]--;
        if( GrauS[w] == 0 ) s.add(w);
      }
    }
  }


  public static int NeededWorkers(int start[]) {
    int minworkers = 0;
    int currentneeded = 0;

    for (int i=0 ; i < DurMin ; i++) {
      for (int j=1 ; j < start.length ; j++) {
        if (start[j] == i) currentneeded += Tv[j];
        else if (start[j]+Dv[j] == i) currentneeded-=Tv[j];
      }
      if (currentneeded > minworkers) minworkers = currentneeded;
    }

    return minworkers;
  }




  public static int CriticalNeededWorkers(int start[]) {
    int minworkers = 0;
    int currentneeded = 0;

    for (int i=0 ; i < DurMin ; i++) {
      for (int j=1 ; j < start.length ; j++) {
        if (start[j] == LF[j] - Dv[j]) {
          if (start[j] == i) currentneeded += Tv[j];
          else if (start[j]+Dv[j] == i) currentneeded-=Tv[j];
        }
      }
      if (currentneeded > minworkers) minworkers = currentneeded;
    }
    return minworkers;
  }



  public static void minWorkers(int id, int start[]){
    if( id == start.length ){
      int TNW = NeededWorkers( start );
      if( TNW == min){ flag = true; }
      else if ( TNW < min ) {
        min = TNW;
        sol=start;
        flag = false;
      }
    }
    else if( ES[id] == LF[id]- Dv[id] ){   minWorkers(id+1, start); }
    else if( CriticalNeededWorkers(start) > min){ return;}
    else{
      for( int i = 0; i <= (LF[id] - start[id] - Dv[id] ); i++){
        int temp[] = start.clone();
        temp[ id ] += i;
        propagate( temp, id );
        minWorkers(id+1, temp);
      }
    }
  }

  public static void propagate(int start[], int id){
    for (Arco adj : g.adjs_no(id)){
      int w = adj.extremo_final();
      if (start[w] < start[id] + Dv[id]){
        start[w] = start[id] + Dv[id];
        propagate( start, w);
      }
    }
  }




  public static  void main( String[] args ){

    Scanner stdin = new Scanner(System.in);
    int nTarefas = stdin.nextInt();
    g = new Grafo0(nTarefas);
    gt = new Grafo0(nTarefas);
    Dv = new int[ nTarefas+1 ];
    Tv = new int[ nTarefas+1 ];
    ES = new int[ nTarefas+1 ];
    LF = new int[ nTarefas+1 ];
    int neededworkers;
    int criticalworkers;

    for( int i = 0; i < nTarefas; i++){
      int id = stdin.nextInt();
      int nprece = stdin.nextInt();

      for( int j=0; j<nprece; j++){
        int temp = stdin.nextInt();
        g.insert_new_arc( id,temp);
        gt.insert_new_arc( temp, id);
      }
      Dv[ id ] = stdin.nextInt();
      Tv[ id ] = stdin.nextInt();
    }

    caminhCritico(); //Calcular ES
    caminhCritico1(); // Calcular LF

    min = NeededWorkers( ES );
    sol = ES;
    System.out.println("Duracao min: " + DurMin );
    System.out.println("Number of needed workers(ES): " + min);
    System.out.println("Trabalhadores Criticos: " + CriticalNeededWorkers( ES ) );

    minWorkers(1,ES);
    System.out.println("Solução minima: " + min + "\n" + Arrays.toString( sol ));

    System.out.println( flag ? "Há solução alternativa." : "Não existe solução alternativa.");
  }
}
