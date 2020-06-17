import java.util.*;

public class ConnectFour {

  private static final int INVALID = 9999;
  private static final int WIN = 1139;
  private static final char PLAYERF = 'O';
  private static final char PLAYERT = 'X';
  private static List<Integer> linhaArray[][] = new List[6][7];

  private char tabuleiro[][];
  private int jogadas[];   //numero de jogadas em cada coluna
  private boolean turn;
  private int valorHeur[];
  private int heurF;
  private int heurT;

  static {
    for (int i=0; i<6; i++) {
      for (int j=0; j<7; j++) {
        linhaArray[i][j] = new ArrayList<Integer>();
      }
    }
    int k = 0;
    for (int i=0; i<6; i++) {
      k=lines(0,i,1,0,k,linhaArray);
    }
    for (int i=0; i<7; i++) {
      k=lines(i,0,0,1,k,linhaArray);
    }
    for (int i=0; i<3; i++) {
      k=lines(0,i,1,1,k,linhaArray);
      k=lines(i+1,0,1,1,k,linhaArray);
    }
    for (int i=5; i>=3; i--) {
      k=lines(0,i,1,-1,k,linhaArray);
      k=lines(6-i,5,1,-1,k,linhaArray);
    }
  }
  private static int lines(int a, int b, int dirX, int dirY, int cur, List<Integer> linhaArray[][]) {
    if( ( dirY>0 && b > 2) ||( dirY<0 && b < 3) || ( dirX<0 && a < 3) || ( dirX>0 && a > 3) || b > 5 || a > 6) return cur;

    for( int i = 0; i < 4; i++){
      linhaArray[(i*dirY)+b][(i*dirX)+a].add(cur);
    }
    return lines( a+dirX, b+dirY, dirX, dirY, cur+1 , linhaArray);
  }


  // CONSTRUTORES

  ConnectFour(boolean flag) {
    tabuleiro = new char[6][7];
    jogadas = new int[7];
    for(int i=0; i<6; i++) {
      for(int j=0; j<7; j++) {
        tabuleiro[i][j] = '-';
      }
    }
    valorHeur = new int[69];
    turn = flag;
  }

  ConnectFour(int x, int valorHeur[], int jogadas[], boolean turn, int heurF, int heurT) {
    this.turn= turn;
    this.heurF = heurF ; this.heurT = heurT;

    this.valorHeur = Arrays.copyOf(valorHeur,69);

    this.jogadas = Arrays.copyOf(jogadas,7);

    hujoHeuristic(x,jogadas[x]);
    this.turn = !turn;

    this.jogadas[x]++;
  }
  //**********************************************************************************

  //GETTERS AND SETTERS
  public int getHeur( boolean turn) { return turn ? heurT : heurF; }
  public char [][] getTab(){ return tabuleiro; }
  public int [] getJogadas(){ return jogadas; }  //numero de jogadas em cada coluna
  public boolean getTurn(){ return turn; }
  public int [] getValHeur(){ return valorHeur; }

  //***************************************************************

  private void hujoHeuristic(int x, int y) {
    List<Integer> linhas = linhaArray[y][x];
    for ( Integer k: linhas) {
      if( valorHeur[k] == INVALID ) continue;

      else if( turn ){
        if( valorHeur[k] <= 0 ){
          valorHeur[k]--;
          heurT += valueOfLine( valorHeur[k], true);
        }
        else{
          heurF -= valueOfLine( valorHeur[k], false );
          valorHeur[k] = INVALID;
        }
      }

      else{
        if( (valorHeur[k] >= 0 ) ){
          valorHeur[k]++;
          heurF += valueOfLine(valorHeur[k], true);
        }
        else{
          heurT -= valueOfLine(valorHeur[k], false);
          valorHeur[k] = INVALID;
        }
      }
    }
  }

  private int valueOfLine( int x, boolean add_sub){
    if( x > 0 ){
      if( x == 1 ) return 1;
      else if( x == 2 ) return add_sub ? 9 : 10;
      else if( x == 3) return add_sub ? 40 : 50;
      else return WIN;
    }
    else{
      if( x == -1 ) return -1;
      else if( x == -2 ) return add_sub ? -9 : -10;
      else if( x == -3) return add_sub ? -40 : -50;
      else return -WIN;
    }
  }
  public boolean isTie(){
    return heurF == 0 && heurT == 0;
  }

  public boolean play(int i) {
    if( turn ){
      if ( i < 0 || i > 6){
        return false;
      }
      else if( jogadas[i] > 5){
        return false;
      }
    }
    tabuleiro[ jogadas[i] ][i] = (turn ? PLAYERT:PLAYERF);
    hujoHeuristic(i,jogadas[i]);
    //System.out.println(Arrays.toString(valorHeur));
    jogadas[i]++;
    turn = !turn;
    return true;
  }

  public List<Integer> suce() {
    List<Integer> list = new ArrayList<Integer>();
    for(int i=0; i<7; i++) {
      if(jogadas[i] < 6) list.add(i);
    }
    return list;
  }

  public ConnectFour apply(int i) {
    return new ConnectFour( i, valorHeur, jogadas, turn, heurF, heurT);
  }


  /******************************************************************************/

  public void print() {
    System.out.println("IA ("+ heurF + ") JOGADOR(" + heurT+ ")" );
    for(int i=5; i>=0; i--) {
      System.out.print("|");
      for(int j=0; j<7; j++) {
        System.out.print("   " + tabuleiro[i][j] + "   |");
      }
      System.out.println();
    }
    System.out.println("_________________________________________________________");
    System.out.print("|");
    for(int j=0; j<7; j++) {
      System.out.print("   " + j + "   |");
    }
    System.out.println();
  }
}
