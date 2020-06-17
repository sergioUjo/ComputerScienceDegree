import java.util.*;

class SortbyHeuristic implements Comparator<Tabuleiro> {

  public int compare(Tabuleiro a, Tabuleiro b) {
    return ( a.get_manh() - b.get_manh() );
  }
}
class SortForAStar implements Comparator<Tabuleiro> {

  public int compare(Tabuleiro a, Tabuleiro b) {
    return ( ( a.get_manh() + a.get_depth() ) - ( b.get_manh() + b.get_depth() ));
  }
}

public class Tabuleiro{
  private int[] tab = new int [17]; //jogo
  private int blanc;  // coordenadas do branco
  private char action;  //l, r, u, d, null
  private Tabuleiro parent;
  private int manhattanDist;
  private int depth = 0;
  //public int outPlace;


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
  public char get_action(){
    return action;
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
  /*public void nOutside(Tabuleiro dest) {
    for(int i=1; i<17; i++) {
      if(dest.tab[i] != this.tab[i]) this.outPlace++;
    }
  }*/

  public void manhattan(Tabuleiro dest) {
    for(int j=1; j<17; j++) {
      int atual = j;
      int obj = get_pos_obj(dest, this.tab[atual]);
      if( obj == atual ) continue;
      if(obj > atual) {
        while( (atual+4) < obj) {
          this.manhattanDist++;
          atual+=4;
        }
        while( (atual+1) != obj){
          this.manhattanDist++;
          atual++;
        }
      }
      else {
        while( (atual-4) > obj) {
          this.manhattanDist++;
          atual-=4;
        }
        while( (atual-1) != obj){
           this.manhattanDist++;
           atual--;
         }
      }
    }
  }

  public int get_pos_obj(Tabuleiro dest, int val) {
    for(int i=1; i<17; i++)
     if(val == dest.get_tab()[i]) return i;
     return 0;
  }

  // NUMERO DE INVERSÕES DO TABULEIRO ATUAL
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

  public int compare( Tabuleiro current ){
    return  (this.get_manh() + this.get_depth() ) - ( current.get_manh() + current.get_depth() );
  }

  //DEVOLVE FILHOS DO TABULEIRO
  public LinkedList<Tabuleiro> adjs_heuristic(Tabuleiro dest) {
    LinkedList<Tabuleiro> adjs = adjs_jogo();
    for( Tabuleiro aux: adjs){
      aux.manhattan(dest);
      //aux.nOutside(dest);
      //System.out.println(aux.get_manh());
    }
    Collections.sort(adjs, new SortbyHeuristic());
    return adjs;
  }
  public LinkedList<Tabuleiro> adjs_cost_heuri(Tabuleiro current, Tabuleiro dest) {
    LinkedList<Tabuleiro> adjs = adjs_jogo();
    for( Tabuleiro aux: adjs){
      aux.manhattan(dest);
      aux.set_depth(current);
    }
    return adjs;
  }

  public LinkedList<Tabuleiro> adjs_jogo(){
    LinkedList<Tabuleiro> adjs = new LinkedList<Tabuleiro>();

    if( LEFT( blanc ) != -1 ){
      adjs.add( swap( LEFT( blanc ), 'l' ) );
    }
    if( RIGHT( blanc ) != -1 ){
      adjs.add( swap( RIGHT( blanc ), 'r' ) );
    }
    if( DOWN( blanc ) != -1 ){
      adjs.add( swap( DOWN( blanc ), 'd' ) );
    }
    if( UP( blanc ) != -1 ){
      adjs.add( swap( UP( blanc ), 'u' ) );
    }

    return adjs;
  }

  //TROCA POSIÇÃO D COM A POSIÇÃO EM BRANCO
  private Tabuleiro swap( int d, char ac ){
    Tabuleiro novo = new Tabuleiro( this.tab );
    novo.tab[ novo.blanc ] = novo.tab[d];
    novo.tab[d] = 0;
    novo.blanc = d;
    novo.action = ac;
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

  public String toString(){
    String res = "";
    for(int i = 1; i<17; i++){
      res += this.tab[i];
    }
    return res;
  }

  public String print(){
    String res = "";
    for (int i = 1; i<17; i += 4) {
      res+= this.tab[i] + " " + this.tab[i+1] + " " + this.tab[i+2] + " " + this.tab[i+3] + "\n";
    }
    return res;
  }
}
