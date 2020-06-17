import java.util.*;

public class ConnectFour {




  private static final int INVALID = 9999;
  private static final char PLAYERF = 'X';
  private static final char PLAYERT = 'O';
  private static Set<Integer> linhaArray[][] = new Set[6][7];

  char tabuleiro[][];
  int jogadas[];   //numero de jogadas em cada coluna
  boolean turn;
  int valorHeur[];
  int heur;

  static {
    for (int i=0; i<6; i++) {
      for (int j=0; j<7; j++) {
        linhaArray[i][j] = new HashSet<Integer>();
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
    System.out.println(1);
  }
  private static int lines(int a, int b, int dirX, int dirY, int cur, Set<Integer> linhaArray[][]) {
    if( ( dirY>0 && b > 2) ||( dirY<0 && b < 3) || ( dirX<0 && a < 3) || ( dirX>0 && a > 3) || b > 5 || a > 6) return cur;

    for( int i = 0; i < 4; i++){
      linhaArray[(i*dirY)+b][(i*dirX)+a].add(cur);
    }
    return lines( a+dirX, b+dirY, dirX, dirY, cur+1 , linhaArray);
  }
  public int getHeur() {
    return heur;
  }



ConnectFour() {
  tabuleiro = new char[6][7];
  jogadas = new int[7];
  for(int i=0; i<6; i++) {
    for(int j=0; j<7; j++) {
      tabuleiro[i][j] = '-';
    }
  }
  heur = 69;
  valorHeur = new int[69];
}

ConnectFour(int x, int valorHeur[], int jogadas[], boolean turn) {

  this.valorHeur = Arrays.copyOf(valorHeur,69);

  this.jogadas = Arrays.copyOf(jogadas,7);

  this.heur = hujoHeuristic(x,jogadas[x]);

  this.turn = !turn;

  this.jogadas[x]++;
}

public int hujoHeuristic(int x, int y) {
  int count  = 0;
  Set<Integer> linhas = linhaArray[y][x];
  int sinal = turn ? -1 : 1;

  for ( Integer k: linhas) {
    if( ( valorHeur[k] * sinal >= 0 ) && ( valorHeur[k] != INVALID ) ) {
      valorHeur[k]+= 1 * sinal;
    }
    else{
       valorHeur[k] = INVALID;
     }
  }
  for( int k = 0; k < 69; k++) {
    if( valorHeur[k] * sinal  == 4 ) return -1;
    else if( ( valorHeur[k] * sinal >= 0 ) && ( valorHeur[k] != INVALID ) )
    count += (valorHeur[k] + sinal) ;
  }
  return count;
}

public boolean play(int i) {
  tabuleiro[ jogadas[i] ][i] = (turn ? PLAYERT:PLAYERF);
  //System.out.println(linhaArray[ jogadas[i] ][i]);
  System.out.println(hujoHeuristic(i,jogadas[i]));
  System.out.println(Arrays.toString(valorHeur));
  jogadas[i]++;
  turn = !turn;
  return false;
}

public LinkedList<Integer> suce() {
  LinkedList<Integer> list = new LinkedList<Integer>();
  for(int i=0; i<7; i++) {
    if(jogadas[i] < 6) list.add(i);
  }
  return list;
}

public ConnectFour apply(int i) {
  return new ConnectFour( i, valorHeur, jogadas, turn);
}


/******************************************************************************/

public void print() {
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
}


}
