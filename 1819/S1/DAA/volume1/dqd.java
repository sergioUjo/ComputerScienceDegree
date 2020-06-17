import java.util.Scanner;
class dqd{

  public static void print_group(int p[], int id){
    int start = id;
    System.out.print(start + " ");
    while (p[id]!=start){
      if(p[p[id]]==start){
        System.out.print(p[id]);
      }
      else{
        System.out.print(p[id] + " ");
      }
      id=p[id];
    }
  }

  public static Grupo find_group(int p[], int id, boolean visited[]){
    Grupo g = new Grupo();
    g.size=0;
    g.max=id;
    while (!visited[id]){
      visited[id]=true;
      if(id>g.max) g.max = id;
      g.size++;
      id=p[id];
    }
    return g;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int [] p = new int[101];
    boolean [] visited = new boolean[101];
    for (int i = 1; i<=n ;i++ ) {
      p[i]=sc.nextInt();
      visited [i] = false;
    }
    int ng=0;
    Grupo g = new Grupo();
    for (int i=1; i<=n; i++){
      g=find_group(p, i, visited);
      if(g.size>=3){
        System.out.print(g.size + " ");
        print_group(p, g.max);
        System.out.print("\n");
      } else ng += g.size;
    }
    System.out.println(ng);
  }
}

class Grupo {
  int size;
  int max;
}
