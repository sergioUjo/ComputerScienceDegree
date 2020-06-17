import java.util.Scanner;
public class Sopa{
  static String resolve(int size, String s1, String s2){
    for(int i=0; i<size;i++){
      if(s1.charAt(i)!=s2.charAt(i)){
        return "" +s1.charAt(i)+s2.charAt(i);
      }
    }
    return "Nenhum";
  }
  public static void main(String[] agrs){
    Scanner stdin=new Scanner(System.in);
    String s1=stdin.nextLine();
    String s2=stdin.nextLine();
    int k=Math.min(s1.length(),s2.length());
    System.out.println(resolve(k,s1,s2));
  }
}
