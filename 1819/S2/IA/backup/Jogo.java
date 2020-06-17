
import java.util.*;

public class Jogo{

  static int nMaxExp;

  //Imprimir caminho
  public static void print_path( Tabuleiro dest){
    int nPassos = 0;
    String caminho = "";

    while( dest != null ){
      caminho = dest.print()+ "\n" + caminho;
      dest = dest.get_parent();
      nPassos++;
    }
    //caminho = "[" + caminho;
    System.out.print(caminho);
    System.out.println("Numero de passos--> " + (nPassos-1));
    System.out.println("Memoria utilizada--> " + nMaxExp);
  }



  public static void menu( Scanner stdin ) {
    System.out.println("***Jogo dos 15***");
    System.out.println("***Bem Vindo***\n\n\n");
    Tabuleiro inicio;
    Tabuleiro destino;
    do {
      System.out.print("Configuracao inicial ('0' representa espaco): ");
      inicio = new Tabuleiro(stdin);
      System.out.print("\n"+"Configuracao final ('0' representa espaco): ");
      destino = new Tabuleiro(stdin);
      if(inicio.possible(destino)) break;
      System.out.println("\n\nNao tem solucao!\n\n");
    }while( true );

    while( true ){

      System.out.println("Escolha o algoritmo: ");
      System.out.println("1) Busca em largura (BL ou BFS)");
      System.out.println("2) Busca em profundidade (BP ou DFS)");
      System.out.println("3) Busca em Profundidade Iterativa (BPI ou IDFS)");
      System.out.println("4) Busca gulosa com heuristica");
      System.out.println("5) Busca A*");
      System.out.println("6) Novo jogo");
      System.out.println("7) Sair\n");
      System.out.print(">>> ");

      long start, finish;
      switch ( stdin.nextInt() ){
        case 1:
        start = System.currentTimeMillis();
        BFS(inicio,destino);
        finish = System.currentTimeMillis();
        System.out.println("Tempo de execucao--> " + (finish-start) + "ms\n\n");
        break;
        case 2:
        start = System.currentTimeMillis();
        DFS(inicio,destino);
        finish = System.currentTimeMillis();
        System.out.println("Tempo de execucao--> " + (finish-start) + "ms\n\n");
        break;
        case 3:
        start = System.currentTimeMillis();
        IDFS(inicio,destino);
        finish = System.currentTimeMillis();
        System.out.println("Tempo de execucao--> " + (finish-start) + "ms\n\n");
        break;
        case 4:
        start = System.currentTimeMillis();
        GULOSA(inicio,destino);
        finish = System.currentTimeMillis();
        System.out.println("Tempo de execucao--> " + (finish-start) + "ms\n\n");
        break;
        case 5:
        start = System.currentTimeMillis();
        A_Star(inicio,destino);
        finish = System.currentTimeMillis();
        System.out.println("Tempo de execucao--> " + (finish-start) + "ms\n\n");
        break;
        case 6: System.out.println("Insera a nova configuracao"); inicio = new Tabuleiro(stdin); destino = new Tabuleiro(stdin); break;
        case 7: System.out.println("\nAte a proxima!\n"); return;
        default:System.out.println("\nEscolha uma das opcoes...\n\n");
      }
    }
  }


  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/


  //Busca em largura (BL ou BFS)
  public static void BFS( Tabuleiro inicio, Tabuleiro destino ){

    nMaxExp = 1;
    //String do tabuleiro final
    String dest = destino.toString();

    //Set utilizado para verificar se um dito tabuleiro já foi obtido e a que profundidade
    Set<String> visitado = new HashSet<String>();

    //Queue necessária na implementação da BFS
    Queue<Tabuleiro> fila = new LinkedList<Tabuleiro>();

    visitado.add( inicio.toString() );
    fila.add( inicio );
    Tabuleiro c = null;

    while( !fila.isEmpty() ){
      c = fila.remove();
      nMaxExp--;

      for(Tabuleiro child: c.adjs_jogo()){
        if(!visitado.contains( child.toString() )){
          child.set_parent( c );

          if( child.toString().equals( dest) ) {
            print_path( child );
            return;
          }

          visitado.add( child.toString() );
          nMaxExp++;
          fila.add(child);
        }
      }
    }
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

  //Busca Limitada em profundidade (BLP ou DLS) e Busca gulosa com heuristica
  public static Tabuleiro BLP( Tabuleiro inicio, Tabuleiro destino, int limit, boolean gulosa){
    nMaxExp = 0;
    Set<String> visitado = new HashSet<String>();

    return BLP( inicio, destino, visitado, limit, gulosa);

  }

  public static Tabuleiro BLP(Tabuleiro current, Tabuleiro dest, Set<String> visitado, int limit, boolean gulosa){

    if( visitado.contains( current.toString() ) ) return null; //Caso de paragem, nó já explorado

    if(limit == 0) return null; // Caso de paragem, limite de profundidade atingido

    visitado.add( current.toString() ); // Adiciona o nó ao set de explorados

    for(Tabuleiro child: (gulosa ? current.adjs_heuristic(dest) : current.adjs_jogo()) ){// expande os filhos do tabuleiro
      child.set_depth(current);
      child.set_parent( current ); // Apontador para o pai, necessário para impressão
      if(child.get_depth() > nMaxExp) nMaxExp = child.get_depth();

      if( child.toString().equals( dest.toString() ) ){// Caso de paragem, encontrou a solução
        return child; // Retorna a solução e com ela o caminho
      }

      Tabuleiro temp = BLP(child, dest, visitado, limit-1, gulosa);
      if( temp != null){//Se este ramo encontrar a solução
        return temp; // retorna o ramo
      }
    }
    return null;  //retorna null porque não encontrou a solução
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

  //Busca em Profundidade Iterativa (BPI ou IDFS)
  public static void IDFS( Tabuleiro inicio, Tabuleiro destino){
    for(int i = 1; i< Integer.MAX_VALUE; i++){

      Tabuleiro temp = BLP( inicio, destino, i, false);
      if(temp != null){
        nMaxExp=i;
        print_path( temp );
        break;
      }
    }
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

  //Busca em profundidade(DFS ou BP)
  public static void DFS( Tabuleiro inicio, Tabuleiro destino ){
    print_path( BLP( inicio, destino, 40, false) ) ;
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/


  //Busca gulosa com heuristica
  public static void GULOSA( Tabuleiro inicio, Tabuleiro destino) {
    print_path( BLP( inicio, destino, 40, true) );
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

  // A*
  public static void A_Star( Tabuleiro inicio, Tabuleiro destino) {
    nMaxExp = 1;

    PriorityQueue<Tabuleiro> open = new PriorityQueue<Tabuleiro>(9999999, new SortForAStar());

    Queue<Tabuleiro>  closed = new LinkedList<Tabuleiro>();

    open.add(inicio);

    while( !open.isEmpty() ){
      Tabuleiro q = open.remove();
      nMaxExp--;

      CHILDS:
      for( Tabuleiro child : q.adjs_cost_heuri( q, destino) ){
        child.set_parent(q);

        if( child.toString().equals( destino.toString() ) ){
          print_path( child );
          return;
        }

        for( Tabuleiro t_lista : open ){
          if( child.toString().equals( t_lista.toString() ) ){
            if( child.compare(t_lista) <= 0 )
              continue CHILDS;
          }
        }
        for( Tabuleiro t_lista : closed ){
          if( child.toString().equals( t_lista.toString() ) ){
            if( child.compare(t_lista) <= 0 )
              continue CHILDS;
          }
        }
        open.add(child);
        nMaxExp++;
      }
      closed.add(q);
      nMaxExp++;
    }

  }

  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    menu(in);
    //Tabuleiro inicio = new Tabuleiro(in);
    //Tabuleiro destino = new Tabuleiro(in);
    //System.out.println(inicio.print());
    //System.out.println(inicio.adjs_heuristic(destino));
    //System.out.println(inicio.adjs_jogo());
    //BFS(inicio,destino);
    //DFS(inicio,destino);
    //BLP(inicio,destino,12);
    //IDFS(inicio,destino);
    //GULOSA(inicio,destino);
    //if( inicio.possible( destino ) ) System.out.println("POSSIBLE");
  }
}
