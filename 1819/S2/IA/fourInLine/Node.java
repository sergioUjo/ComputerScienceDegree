import java.util.*;

public class Node {
  private double score;
  private int n;
  private List<Node> succ;
  private ConnectFour tabuleiro;

  //GETTERS
  public List<Node> getSucc() {
    return succ;
  }
  public ConnectFour getNodeTab() {
    return tabuleiro;
  }
  public int getNumVis() {
    return n;
  }
  public double getScore() {
    return score;
  }

  Node(int i, ConnectFour tab) {
    n = 0;
    score = 0;
    tabuleiro = tab.apply(i);
    succ = new ArrayList<Node>();
  }

  Node(ConnectFour tab) {
    n = 0;
    score = 0;
    tabuleiro = tab;
    succ = new ArrayList<Node>();
  }


  public boolean expand() {
    for(Integer action : tabuleiro.suce()) {
      succ.add(new Node(action, tabuleiro));
    }
    return succ.size() != 0;
  }

  public void updateStats(int value) {
    n++;
    score+=value;
  }
  public boolean isLeaf(){
    return succ.size() == 0;
  }
  public void clearNodeTab(){
    tabuleiro = null;
  }
}
