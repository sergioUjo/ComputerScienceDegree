import java.util.*;

public class Node{

  private String name;
  private Node childs[];
  private int count; // Numero de ocorrencias da classe OU pos do atributo
  private String[] cName; // Nome do ramo[i] (atributo)

  //Getters and Setters

  public Node[] getChilds(){
    return childs;
  }

  public String[] getcName(){
    return cName;
  }
  public String getName(){
    return name;
  }
  public int getCount(){
    return count;
  }

  public void addCount( int n ){
    count += n;
  }
  // **********************************************

  // Construtores

  Node( String name, int count){ // Contrutor usado para representar uma folha da arvore
    this.count = count; // quantidade de ocorrencias
    this.name = name; // nome da classe
  }

  Node( Atribute A ){ // No que se encontra a meio da arvore
    name = A.getName(); // Como se encontra a meio da arvore é criado atraves de um atributo novo
    childs = new Node[ A.getValues().size() ]; // O numero de filhos é dado pela quantidade de valores associados a um atributo
    cName = A.getValues().toArray( new String[ childs.length ]); // O nome destes é o seu respetivo nome
    count = A.getPos();
  }

  // *****************************************************

  // Funcoes da classe

  public boolean isLeaf(){ // Se o no não tiver filhos retorna TRUE else FALSE.
    return childs == null;
  }

  public void expand( String value, Atribute A, Node subtree){ // liga ao no corrente um novo no mesmo indice que value no atributo
    childs[ A.getValues().indexOf(value) ] = subtree;
  }

  public Node getSucessor( String[] exemplo ){
    for( int i = 0; i<cName.length; i++){
      if(  isInInter( cName[i], exemplo[count] ) ) return childs[i];
      else if( cName[i].equals( exemplo[count] ) ) return childs[i];
    }
    return null;
  }

  public String toString(){ //Representacao do no em string
    if( childs == null) return name + " (" + count + ")"; // class1 (counter1)
    else return "<" + name + ">"; // <attribute2>
  }
  // Funcoes para remover dups
  private boolean isInInter( String inter, String number){
    String min_max[] = inter.split(" - ");
    if( min_max.length != 2 ) return false;
    else if( isNumeric( min_max[0] ) && isNumeric( min_max[1] ) ){
      double n = Double.parseDouble(number);
      return Double.parseDouble(min_max[0]) <= n && n <= Double.parseDouble(min_max[1]);
    }
    else return false;
  }
  private boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
  }

  public void removeDups(){
    if( !isNumeric(cName[0]) ) return;
    int cur = 0;
    int pos = 0;
    String res = "" ;
    while( pos < childs.length ){
      childs[ cur ] = childs[ pos ];

      if( !childs[ pos ].isLeaf() ) {
        cName[ cur++ ] =   cName[ pos++ ];
        continue;
      }
      else{
        int temp = pos;
        res = childs[ pos++ ].getName();

        while(pos < childs.length &&  childs[pos].getName().equals(res) && childs[pos].isLeaf() ){
          childs[ cur ].addCount( childs[ pos++ ].getCount() );
        }
        cName[cur++] = temp==(pos-1) ? cName[temp] : cName[temp] + " - " + cName[pos-1];
      }
    }
    cName = Arrays.copyOf( cName, cur);
    childs = Arrays.copyOf( childs, cur);
  }

}
