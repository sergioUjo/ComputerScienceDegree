import java.util.*;
import java.lang.Math;

public class Play {
  private static final int WIN = 900;
  private static final int INFINITY = Integer.MAX_VALUE;

  //Alpha-Beta
  static int alphaBeta(ConnectFour state) {
    int maxT = -1;
    int max = -INFINITY;
    for( Integer action : state.suce() ){
      int temp = maxValue(state.apply(action), 11, -INFINITY, INFINITY);
      if(max<=temp){
        max=temp;
        maxT= action;
      }
    }
    return maxT;
  }

  static int maxValue(ConnectFour state, int depth, int a, int b) {

    if(state.getHeur( false ) > WIN) return INFINITY;

    else if( state.isTie() || depth == 0) return state.getHeur( false );

    int v = -INFINITY;

    for(Integer action : state.suce()) {
      v = Math.max(v, minValue(state.apply(action), depth-1, a, b));
      if(v >= b) return v;
      a = Math.max(a,v);
    }
    return v;
  }

  static int minValue(ConnectFour state, int depth, int a, int b) {

    if(state.getHeur( true ) < -WIN ) return -INFINITY;

    else if( state.isTie() || depth == 0) return state.getHeur( true );

    int v = INFINITY;

    for(Integer action : state.suce()) {
      v = Math.min(v, maxValue(state.apply(action), depth-1, a, b));
      if(v <= a) return v;
      b = Math.min(b,v);
    }
    return v;
  }

  /***************************************************/

  //MINI MAX
  static int miniMaxDecision(ConnectFour state) {
    int maxT = -1;
    int max = -INFINITY;
    for( Integer action : state.suce() ){
      int temp =minValue(state.apply(action), 8);
      if(max<=temp){
        max=temp;
        maxT= action;
      }
    }
    return maxT;
  }

  static int minValue(ConnectFour state, int depth) {

    if(state.getHeur( true ) < -WIN ) return -INFINITY;

    else if( state.isTie() || depth == 0 ) return state.getHeur(true);

    int v = INFINITY;

    for(Integer action : state.suce()) {
      v = Math.min(v, maxValue(state.apply(action), depth-1));
    }

    return v;
  }

  static int maxValue(ConnectFour state, int depth) {

    if(state.getHeur( false ) > WIN) return INFINITY;

    else if( state.isTie() || depth == 0) return state.getHeur( false );

    int v = -INFINITY;

    for(Integer action : state.suce()) {
      v = Math.max(v, minValue(state.apply(action), depth-1));
    }

    return v;
  }

  /******************************************/
  static void repeat(Node node) {
    Set<Node> visitados = new HashSet<Node>();
    visitados.add(node);

    while( !node.isLeaf() ) {
      node = select(node);
      visitados.add(node);
    }

    Node newChild = expand(node);

    int value= rollOut(newChild);

    visitados.add(newChild);

    for(Node temp : visitados) {
      temp.updateStats(value);
    }
  }

  static Node select(Node no) {
    Node selected = null;
    double max = -INFINITY;
    for(Node sucessor : no.getSucc()){
      if(ucb(sucessor, no.getNumVis() ) >= max) {
        max = ucb(sucessor, no.getNumVis() );
        selected = sucessor;
      }
    }
    return selected;
  }
  static Node expand( Node no ){
    if( no.getNumVis() == 0 ) return no;
    if( no.expand()){
      //no.clearNodeTab();
      return no.getSucc().get(0);
    }
    return no;
  }

  static double ucb(Node no, int x) {
    if(no.getNumVis() == 0) return INFINITY;
    return ( no.getScore() / no.getNumVis() )+ ( 0.7172 * Math.sqrt( ( 2*Math.log(x) ) / no.getNumVis() ) );
  }

  static int rollOut(Node no) {
    ConnectFour tab = no.getNodeTab();
    while(true) {
      if(tab.getHeur( false) > WIN){
        return 1;
      }
      else if ( tab.isTie() ) {
        return 0;
      }
      else if (tab.getHeur( true ) < -WIN) {
        return -1;
      }

      int rdm = new Random().nextInt(tab.suce().size());
      tab = tab.apply(tab.suce().get(rdm));
    }
}

  //MCTS
  static int monteCarlo(ConnectFour state) {
    Node root = new Node(state);
    root.expand();
    for(int i = 0; i<1000000; i++) {
      repeat(root);
    }
    double max = -INFINITY;
    int i = 0;
    int pos = 0;
    for(Node sucessor : root.getSucc()) {
      //System.out.println("score:"+ucb(sucessor, root.getNumVis()));
      if( ucb( sucessor, root.getNumVis() ) >= max ) {
        max = ucb( sucessor, root.getNumVis() );
        pos = i;
      }
      i++;
    }
    return state.suce().get(pos);
  }

  private static void menu(Scanner in) {
    System.out.println("***Quatro em Linha***");
    System.out.println("***Bem Vindo***\n\n\n");
    boolean flag1, flag2;
    while(true) {
      System.out.println("Escolha o algoritmo para a IA: ");
      System.out.println("1) MiniMax");
      System.out.println("2) Alpha-Beta");
      System.out.println("3) Monte Carlo");
      System.out.println("4) Sair\n");
      System.out.print(">>> ");
      switch ( in.nextInt() ){
        case 1:
        jogar(false,true,in);
        break;
        case 2:
        jogar(true,false,in);
        break;
        case 3:
        jogar(false,false,in);
        break;
        case 4:
        System.out.println("\nAte a proxima!\n"); return;
        default:
        System.out.println("\nEscolha uma das opcoes...\n\n");
        break;
      }
    }
  }

  private static void jogar(boolean f1, boolean f2, Scanner in) {
    long start, finish;
    int opc=0;
    boolean turn;
    System.out.println("Quem começa a jogar?");
    System.out.println("1) EU");
    System.out.println("2) IA");
    while( true ){
      opc = in.nextInt();
      if(opc == 1){
        turn = true;
        break;
      }
      else if( opc == 2){
        turn = false;
        break;
      }
      else{
        System.out.println("Escolha uma das opcoes...");
      }
    }
    clearScreen();
    ConnectFour jogo = new ConnectFour( turn );
    jogo.print();
    for(int i=0; i<42; i++) {
      if( turn ){
        System.out.print("\nA tua jogada: ");
      }
      int jogada = turn ? in.nextInt() : (f1 ? alphaBeta(jogo) : (f2 ? miniMaxDecision(jogo) : monteCarlo(jogo) ) );
      while( !jogo.play(jogada) ){
        System.out.println("Jogada Inválida, selecione outra!");
        jogada =in.nextInt();
      }
      if (jogo.getHeur(turn) > WIN || jogo.getHeur(turn) < -WIN) break;
      clearScreen();
      jogo.print();
      turn = !turn;

    }
    jogo.print();
    if(jogo.isTie()) System.out.println("Empate");
    else {
      System.out.println("\n\nVitoria de: " + (turn ? 'X': 'O') + "\n");
    }
  }
  //*****************************************************************************************
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    menu(in);
  }
}
