import java.io.*;
import java.util.*;


public class Decision {

  //INICIADORES DE ESTRUTURAS NECESSARIAS PARA A FORMACAO DA arvore

  public static  List<String[]> readexemplos( Scanner in){ // Funcao para ler input, retorna lista

    BufferedReader br = null;
    String line = "";
    List<String[]> exemplos = new LinkedList<String[]>();

    try {
      System.out.println("Indique o path do ficheiro a abrir.");
      String ficheiro = in.next();
      File f = new File(ficheiro);
      while(!f.exists()) {
        clearScreen();
        System.out.println("Verifique o path introduzido...");
        System.out.print("Path: ");
        ficheiro = in.next();
        f = new File(ficheiro);
      }
      br = new BufferedReader(new FileReader(ficheiro));
      while ((line = br.readLine()) != null) {
        String[] object = line.split(",");
        exemplos.add( object );
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return exemplos;
  }

  public static Atribute classe; // Atribute geral representando a Class. EX restaurant (YES or NO)

  public static List<Atribute> get_atributes( List<String[]> exemplos){
    List<Atribute> atri = new LinkedList<Atribute>();

    for( int i = 1; i<exemplos.get(0).length-1; i++){ // Começa em 1 porque em 0 são os IDS dos exmplos
      atri.add( new Atribute( exemplos, i) ); // cria novo atributo
    }
    classe = new Atribute( exemplos, exemplos.get(0).length-1); // cria o representante da class que se situa na ultima posiçao;

    return atri;
  }



  //Funcao principal na formacao da arvore, retirada do pseudo codigo do livro
  public static Node decision_tree( List<String[]> exemplos, List<Atribute> atributes, List<String[]> parent_exemplos){

    if ( exemplos.isEmpty() ) return plurality_value( parent_exemplos, false ); // caso ja nao tenha mais exemplos para avaliar

    else if ( same_class( exemplos ) ) return new Node( exemplos.get(0)[ classe.getPos() ], exemplos.size() ); // se todos os exemplos chegam a mesma conclusao EX YES

    else if ( atributes.isEmpty() ) return plurality_value( exemplos, true ); // caso nao possuia mais atributos

    else{
      Atribute A = max_importance( exemplos, atributes); // recebe o atributo mais relevante na distincao
      //System.out.println(A);
      Node temp = new Node( A ); // cria um no com base no atributo
      List<Atribute> notA = copyRemove( atributes, A);
      for( String vk : A.getValues() ){ // para todos os valores que o atributo possui

        List<String[]> exs = getVks( A.getPos(), vk, exemplos ); // lista com todos os exemplos com o valor de vk no atributo A
        Node subtree = decision_tree( exs, notA, exemplos ); // chamada recursiva
        temp.expand( vk, A, subtree ); // adiciona a chamada recursiva ao no corrente no ramo do valor vk
      }
      return temp;
    }
  }

  public static List<Atribute> copyRemove(List<Atribute> ori, Atribute A){
    List<Atribute> res = new LinkedList<Atribute>();
    for( Atribute temp: ori){
      if( temp != A ){
        res.add( temp );
      }
    }
    return res;
  }

  // Calculo do atributo com mais relevancia
  public static Atribute max_importance( List<String[]> exemplos, List<Atribute> atri ){ // dada uma lista(qualquer) de exemplos e de atributos
    double min = 2; // qualquer valor maior que 1 porque a entropia no maximo atinge 1
    Atribute amin= null; // atributo mais relevante

    for( Atribute atemp : atri){ // percorre a lista de atributos
      double dtemp=0;
      for( String vtemp: atemp.getValues()){
        int ab[] = calc_a_b( vtemp, atemp.getPos(), exemplos);
        dtemp +=  ( sumArray( ab ) / exemplos.size() ) * calcEntrop( ab );
      }
      //System.out.println( atemp + " " + dtemp);
      if( dtemp < min){
        min = dtemp;
        amin = atemp;
      }
      else if( dtemp == min ){
        if( atemp.getValues().size() < amin.getValues().size() ){ // caso o valor seja iguail escolhe o atributo com menos valores
          amin = atemp;
        }
      }
    }
    return amin;
  }

  private static double calcEntrop( int ab[] ){
    double res=0;

    for( int i = 0; i< ab.length; i++ ){
      if( ab[i] != 0 ){
        double temp = (double) ab[i] / sumArray( ab );
        res-= temp * ( Math.log( temp ) / Math.log(2) );
      }
    }

    return res;
  }

  private static double sumArray( int array[] ){
    double res=0;

    for( int i = 0; i < array.length; i++ ) res += array[i];

    return res;
  }

  private  static int[] calc_a_b(String value, int pos, List<String[]> exemplos){

    int [] ab = new int[ classe.getValues().size() ];

    for( String[] temp : exemplos){
      if(  temp[pos].equals(value) ){
        for( int i = 0 ; i < classe.getValues().size(); i++ ){
          if( temp[ classe.getPos() ].equals( classe.getValues().get(i)) ) ab[i]++;
        }
      }
    }
    return ab;
  }


  private static boolean  same_class( List<String[]> exemplos ){ // retorna TRUE se todos os exemplos na lista levarem a mesma class
    String cur = exemplos.get(0)[ classe.getPos() ];
    for (String[] temp : exemplos) {
      if( !cur.equals( temp[ classe.getPos() ] ) ) return false;
    }
    return true;
  }

  private static Node plurality_value( List<String[]> list, boolean people ){ // retorna o falor que ocorre com mais frequencia nos exemplos
    //people representa como chegamos aqui, true atraves da falta de atributos, ou seja, ainda existe exemplos. False falta de exemplos;
    int ab[] = new int[ classe.getValues().size() ];
    int max = 0;
    int maxS = 0;

    for (String[] exam : list) {
      ListIterator<String> li = classe.getValues().listIterator();
      int i =0;
      while (li.hasNext()) { // enquanto ainda houver lista
        String temp = li.next(); // recebe uma linha dos exemplos, e avanca uma posicao na lista
        if( exam[ classe.getPos() ].equals( temp ) ) ab[i]++;
        i++;
      }
    }

    for( int i = 0; i < ab.length; i++){
      if( ab[i] > max ){
        max = ab[i];
        maxS = i;
      }
    }

    return new Node( classe.getValues().get(maxS), people ? list.size() : 0 );
  }

   private static List<String[]> getVks( int pos, String value, List<String[]> exemplos){
     List<String[]> selected = new LinkedList<String[]>(); // nova lista para retornas os exemplos selecionados

     for (String[] temp : exemplos) {
       if( temp[ pos ].equals( value ) ) selected.add( temp); // se exemplo tiver o mesmo valor do que o atributo dado pela pos, e adicionado a lista
     }
     return selected;
   }


  public static void printSol(Node root, String space){ // imprime
    System.out.println( root );
    if( root.isLeaf() ) return;

    Node[] childs = root.getChilds();

    for ( int i = 0; i < childs.length ; i++) {
      System.out.print(space + root.getcName()[i]+ ": ");
      printSol( childs[i], space+ "\t");
    }
  }

  private static void classify( List<String[]> testes, Node root){
    System.out.println("\n\n");
    String [] classifi = new String[ testes.size() ];

    int i=0;
    for( String[] cur: testes ){
      Node tempN = root;
      while( !tempN.isLeaf() ){
        tempN = tempN.getSucessor( cur );
      }
      classifi[ i++ ] = tempN.getName();
    }

    for (i = 0; i < classifi.length; i++) {
      System.out.println("Amostra " + (i+1) + " obteve: " + classifi[i]);
    }
    System.out.println("\n");
  }

  public static void concat(Node root){
    if( root.isLeaf() ) return;
    root.removeDups();
    Node[] childs = root.getChilds();

    for ( int i = 0; i < childs.length ; i++) {
      concat( childs[i] );
    }
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
}

  private static void menu(Scanner in) {
    System.out.println("***Arvores de Decisao***");
    System.out.println("***Bem Vindo***\n\n\n");
    Node root = null;
    while(true) {
      System.out.println("1) Construir arvore\n");
      System.out.println("2) Imprimir arvore\n");
      System.out.println("3) Testar arvore\n");
      System.out.println("4) Sair\n");
      System.out.print(">>>");
      switch(in.nextInt()) {
        case 1:
        clearScreen();
        List<String[]> exemplos = readexemplos(in); // le o ficheiro e converte para uma lista, cada linha é uma entrada do tipo []
        List<Atribute> atributes = get_atributes(exemplos); // cria uma lista de atributos e respetivos valores associados, para cada entrada de atributo no ficheiro
        exemplos.remove(0); // remove a primerira linha  porque são apenas os nomes dos atributos
        root = decision_tree( exemplos, atributes, null); // retorna root da arvore de Decisao
        concat( root );
        clearScreen();
        break;
        case 2:
        clearScreen();
        if(root != null) printSol( root , "\t"); // impressão
        else System.out.println("\n\nNao existe arvore para impressao!\nConstrua-a uma...\n");
        break;
        case 3:
        clearScreen();
        if(root != null) {
        List<String[]> testes = readexemplos(in);
        classify(testes, root); }
        else System.out.println("\n\nNao existe arvore para testar!\nConstrua-a uma...\n");
        break;
        case 4:
        System.out.println("\nAte a proxima!\n"); return;
        default:
        System.out.println("\nEscolha uma das opcoes...\n\n");
        break;
      }
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner( System.in );
    menu(in);
  }
}
