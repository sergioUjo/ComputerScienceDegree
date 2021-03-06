
import java.util.*;

public class Jogo{

  static int nMaxExp;

  //Imprimir caminho
  public static void print_path( Tabuleiro dest){
    String caminho = "";

    if( dest == null ) {
      System.out.println("Não encontrou solução");
      return ;
    }
    int nPassos = dest.get_depth();

    while( dest != null ){
      caminho = dest.print()+ "\n" + caminho;
      dest = dest.get_parent();
    }
    System.out.print(caminho);
    System.out.println("Numero de passos--> " + nPassos );
    System.out.println("Memoria utilizada--> " + nMaxExp );
  }

  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/


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
        System.out.println("Tempo de execucao--> " + (finish-start) + " ms\n\n");
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
        case 6: System.out.println("Insera a nova configuracao");
        do {
          System.out.print("Configuracao inicial ('0' representa espaco): ");
          inicio = new Tabuleiro(stdin);
          System.out.print("\n"+"Configuracao final ('0' representa espaco): ");
          destino = new Tabuleiro(stdin);
          if(inicio.possible(destino)) break;
          System.out.println("\n\nNao tem solucao!\n\n");
        }while( true );
        break;
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

    while( !fila.isEmpty() ){
      Tabuleiro c = fila.remove();
      nMaxExp--;

      for(Integer action: c.actions()){

        Tabuleiro child = c.apply( action );

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
  public static Tabuleiro BLP( Tabuleiro inicio, Tabuleiro destino, int limit){

    Map<String,Integer> visitado = new HashMap<String, Integer>();

    return BLP( inicio, destino.toString(), visitado, limit);
  }

  public static Tabuleiro BLP(Tabuleiro current, String dest, Map<String,Integer> visitado, int limit){

    if(limit == 0) return null; // Caso de paragem, limite de profundidade atingido
    else if( visitado.getOrDefault( current.toString(), Integer.MAX_VALUE ) < current.get_depth() ) return null; //Caso de paragem, nó já explorado
    else{
      visitado.put( current.toString(), current.get_depth() ); // Adiciona o nó ao set de explorados

      for(Integer action: current.actions() ){// expande os filhos do tabuleiro
        Tabuleiro child = current.apply( action );

        child.set_parent( current ); // Apontador para o pai, necessário para impressão

        if( child.toString().equals( dest ) ){// Caso de paragem, encontrou a solução
          return child; // Retorna a solução e com ela o caminho
        }
        else{
          Tabuleiro temp = BLP(child, dest, visitado, limit-1);
          if( temp != null){//Se este ramo encontrar a solução
            return temp; // retorna o ramo
          }
        }
      }
      return null;  //retorna null porque não encontrou a solução
    }
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

  //Busca em Profundidade Iterativa (BPI ou IDFS)
  public static void IDFS( Tabuleiro inicio, Tabuleiro destino){
    for(int i = 0; i< Integer.MAX_VALUE; i++){

      Tabuleiro temp = BLP( inicio, destino, i);
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
    nMaxExp = 0;

    Set<String> visitado = new HashSet<String>();

    print_path( DFS( inicio, destino.toString(), visitado, 40) ) ;
  }

  public static Tabuleiro DFS(Tabuleiro current, String dest, Set<String> visitado, int limit){

    if( visitado.contains( current.toString() )  || limit == 0 ) return null; //Caso de paragem, nó já explorado
    else{

      visitado.add( current.toString() ); // Adiciona o nó ao set de explorados

      for(Integer action: current.actions() ){// expande os filhos do tabuleiro
        Tabuleiro child = current.apply( action );

        child.set_parent( current ); // Apontador para o pai, necessário para impressão
        if(child.get_depth() > nMaxExp) nMaxExp = child.get_depth();

        if( child.toString().equals( dest ) ){// Caso de paragem, encontrou a solução
          return child; // Retorna a solução e com ela o caminho
        }
        else{
          Tabuleiro temp = DFS(child, dest, visitado, limit-1);
          if( temp != null){//Se este ramo encontrar a solução
            return temp; // retorna o ramo
          }
        }
      }
      return null;  //retorna null porque não encontrou a solução
    }
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/


  //Busca gulosa com heuristica
  public static void GULOSA( Tabuleiro inicio, Tabuleiro destino) {
    nMaxExp = 1;

    PriorityQueue<Tabuleiro> queue = new PriorityQueue<Tabuleiro>(9999999, new SortbyHeuristic());

    Set<String> visitado = new HashSet<String>();

    visitado.add( inicio.toString() );
    queue.add( inicio );

    while( !queue.isEmpty() ){

      Tabuleiro current = queue.remove();
      nMaxExp--;

      for(Integer action: current.actions() ){// expande os filhos do tabuleiro
        Tabuleiro child = current.apply_cost_heuri( action, destino);

        if( !visitado.contains( child.toString() ) ){

          child.set_parent( current ); // Apontador para o pai, necessário para impressão

          visitado.add( current.toString() ); // Adiciona o nó ao set de explorados

          if( child.toString().equals( destino.toString() ) ){// Caso de paragem, encontrou a solução
            print_path( child );
            return;
          }
          queue.add(child);
          nMaxExp++;
        }
      }
    }
  }

  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

  // A*
  public static void A_Star( Tabuleiro inicio, Tabuleiro destino) {
    nMaxExp = 1;

    PriorityQueue<Tabuleiro> queue = new PriorityQueue<Tabuleiro>(9999999, new SortForAStar());

    Set<String> visitado = new HashSet<String>();

    queue.add( inicio );

    while( !queue.isEmpty() ){
      Tabuleiro current = queue.remove();
      nMaxExp--;
      if( visitado.contains( current.toString() ) ) continue;
      visitado.add( current.toString() );

      for(Integer action: current.actions() ){// expande os filhos do tabuleiro
        Tabuleiro child = current.apply_cost_heuri( action, destino);

        child.set_parent( current ); // Apontador para o pai, necessário para impressão

        if( child.toString().equals( destino.toString() ) ){// Caso de paragem, encontrou a solução
          print_path( child );
          return;
        }
        queue.add(child);
        nMaxExp++;
      }
    }
  }
  /*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/


  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    menu(in);
  }
}
