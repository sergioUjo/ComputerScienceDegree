import java.util.*;

// Comparador de Tabuleiros, comparando apenas a heuristica manhattan.
class SortbyHeuristic implements Comparator<Tabuleiro> {
  public int compare(Tabuleiro a, Tabuleiro b) {
    return ( a.get_manh() - b.get_manh() );
  }
}

// Comparador de Tabuleiros, comparando a heuristica manhattan e a profundidade a que foi descoberto.
class SortForAStar implements Comparator<Tabuleiro> {
  public int compare(Tabuleiro a, Tabuleiro b) {
    return ( ( a.get_manh() + a.get_depth() ) - ( b.get_manh() + b.get_depth() ));
  }
}
/*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

public class Tabuleiro{
  private int[] tab = new int [17]; // tabuleiro de jogo
  private int blanc;  // coordenadas do branco
  private Tabuleiro parent; // Apontador para o tabuleiro que o gerou
  private int manhattanDist; // heuristica manhattan
  private int depth = 0; // profundidade a que se encontrou o tabuleiro


  //Getters e Setters
  public void set_depth(Tabuleiro ori){
    depth = ori.get_depth() + 1;
  }
  public int get_depth(){
    return this.depth;
  }
  public int get_blanc() {
    return this.blanc;
  }
  public void set_parent(Tabuleiro pai){
    parent = pai;
  }
  public Tabuleiro get_parent(){
    return parent;
  }
  public int get_manh(){
    return manhattanDist;
  }
  public int[] get_tab(){
    return tab;
  }

  // CONSTRUTORES
  Tabuleiro(Scanner in){
    for( int i = 1; i < 17; i++ ){
      tab [i] = in.nextInt();
      if( tab[i] == 0 ) blanc = i;
    }
  }
  Tabuleiro(int [] array){
    for (int i = 1; i< array.length; i++) {
      tab[i]=array[i];
      if( tab[i] == 0 ) blanc = i;
    }
  }

  //FUNCOES DIVERSAS

  //Calcular a heuristica do tabuleiro
  public void manhattan(Tabuleiro dest) {
    for(int j=1; j<17; j++) {
      int atual = j;
      int obj = get_pos_obj(dest, this.tab[atual]); //posição da peça no tabuleiro destino
      if( obj == atual || this.tab[atual] == 0 ) continue; // se já estiver posicionad ou for a branca

      while( ( (obj-1) / 4 ) != ( (atual-1) / 4 ) ) {
        if ( atual < obj) {
          this.manhattanDist++;
          atual+=4;
        }
        else if ( atual > obj){
          this.manhattanDist++;
          atual-=4;
        }
      }
      while( obj != atual ) {
        if ( atual < obj) {
          this.manhattanDist++;
          atual++;
        }
        else if ( atual > obj){
          this.manhattanDist++;
          atual--;
        }
      }
    }
  }

  //Devolve a posição de uma peça num tabuleiro
  public int get_pos_obj(Tabuleiro dest, int val) {
    for(int i=1; i<17; i++)
     if(val == dest.get_tab()[i]) return i;
     return 0;
  }

  // Verifica solvabilidade de um tabuleiro em relação a um destino
  public boolean possible( Tabuleiro destino ){
    int inver = 0, inver2 = 0;
    //contagem inversoes
    for (int i = 0; i < tab.length; i++ ) {
      for (int j = i+1; j < tab.length; j++ ) {
        if( ( this.tab[i] > this.tab[j] ) && ( this.tab[j] != 0 && this.tab[i] != 0)){
          inver++;
        }
        if( ( destino.get_tab()[i] > destino.get_tab()[j] ) && ( destino.get_tab()[j] != 0 && destino.get_tab()[i] != 0)){
          inver2++;
        }
      }
    }
    boolean poss1, poss2;
    poss1 = (inver%2 == 0) == ( ( (this.blanc-1) / 4)%2 == 1 );
    poss2 = (inver2%2 == 0) == ( ( (destino.get_blanc()-1) / 4) % 2 == 1 );

    return poss1 == poss2;
  }


  //DEVOLVE FILHOS DO TABULEIRO
  public LinkedList<Integer> actions(){
    LinkedList<Integer> adjs = new LinkedList<Integer>();

    if( LEFT( blanc ) != -1 ){
      adjs.add( LEFT( blanc ) );
    }
    if( RIGHT( blanc ) != -1 ){
      adjs.add( RIGHT( blanc ) );
    }
    if( DOWN( blanc ) != -1 ){
      adjs.add( DOWN( blanc ) );
    }
    if( UP( blanc ) != -1 ){
      adjs.add( UP( blanc ) );
    }

    return adjs;
  }


  public Tabuleiro apply_cost_heuri(int action, Tabuleiro dest) {
    Tabuleiro aux = apply( action );
    aux.manhattan(dest);
    return aux;
  }

  //TROCA POSIÇÃO D COM A POSIÇÃO EM BRANCO
  public Tabuleiro apply( int d ){
    Tabuleiro novo = new Tabuleiro( this.tab );
    novo.tab[ novo.blanc ] = novo.tab[d];
    novo.tab[d] = 0;
    novo.blanc = d;
    novo.set_depth(this);
    return novo;
  }

  //DEVOLVE INDICE DE MOVIMENTO PARA A ESQUERDA
  private int LEFT( int i ){
    if( ( i - 1 ) % 4 == 0 ) return -1;
    return i - 1;
  }
  //DEVOLVE INDICE DE MOVIMENTO PARA A DIREITA
  private int RIGHT( int i ){
    if( ( i ) % 4 == 0 ) return -1;
    return i + 1;
  }
  //DEVOLVE INDICE DE MOVIMENTO PARA CIMA
  private int UP( int i ){
    if( i < 5 ) return -1;
    return i - 4;
  }
  //DEVOLVE INDICE DE MOVIMENTO PARA BAIXO
  private int DOWN( int i ){
    if( i > 12 ) return -1;
    return i + 4;
  }

  //DELVOLVE UMA STRING SIMPLES DO Tabuleiro
  public String toString(){
    String res = "";
    for(int i = 1; i<17; i++){
      res += this.tab[i];
    }
    return res;
  }

  //DEVOLVE UMA STRING MAIS SIMPÁTICA DO Tabuleiro
  public String print(){
    String res = "";
    for (int i = 1; i<17; i += 4) {
      res+= this.tab[i] + " " + this.tab[i+1] + " " + this.tab[i+2] + " " + this.tab[i+3] + "\n";
    }
    return res;
  }
}
