import java.util.*;


public class Atribute{

  private String name; // nome do atributo
  private List<String> values; // lista com os valores possiveis
  private int pos; // posicao no array de exemplos

  //Getters and Setters

  public List<String> getValues(){
    return values;
  }

  public int getPos(){
    return pos;
  }

  public String getName(){
    return name;
  }
  //*************************************


  Atribute( List<String[]> exemplos, int pos){
    this.pos = pos; // atualiza posicao

    values = new LinkedList<String>();

    name = exemplos.get(0)[pos]; // atualiza o nome

    final ListIterator<String[]> li = exemplos.listIterator(1); // Iterador para a lista a começar na posicao 1 porque na 0 estao os nomes dos atributos

    while (li.hasNext()) { // enquanto ainda houver lista
      String[] temp = li.next(); // recebe uma linha dos exemplos, e avanca uma posicao na lista
      if( !values.contains( temp[pos] )) values.add( temp[pos] ); // Se o valor ainda não estiver na lista adiciona ( comportamento de set)
    }

    Collections.sort(values);
  }

  // Representacao do atributo em string
  public String toString(){
    return name + " " + values.toString(); // NAME [valor1, valor2, valor3, ...]
  }
}
