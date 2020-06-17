import java.util.Scanner;

public class SoS{

  public static void main(String[] args){
    Scanner stdin=new Scanner(System.in);
    int ntipo = stdin.nextInt();
    int cad[][]= new int[ntipo][2];

    for(int i=0; i<ntipo; i++){
      cad[i][0]=stdin.nextInt();
      cad[i][1]=stdin.nextInt();
    }

    int nhab= stdin.nextInt();
    //System.out.println(nhab);
    int depe=0;
    for(int i=0; i<nhab; i++){
      int npref=stdin.nextInt();
      boolean sentado=false;
      for(int j=0; j<npref; j++){
        int pref=stdin.nextInt();
        for(int k=0;k<ntipo;k++){
          if(pref==cad[k][0] && cad[k][1]!=0 && !sentado){
            cad[k][1]--;
            sentado=true;
          }
        }
      }
      if(!sentado){depe++;}
    }
    System.out.println(depe);
    int count=0;
    for(int i=0; i<ntipo; i++){
      count+=cad[i][1];
    }
    System.out.println(count);
  }
}
