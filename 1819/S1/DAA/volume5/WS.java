import java.util.*;
import java.lang.*;

public class WS{

  static int[] cabecas;

  static boolean overlay( int o1, int f1, int o2, int f2 ){
    int sentido1= (f1-o1); // falso <=== <====
    int sentido2= (f2-o2); // falso <=== <====
    if( sentido2==0 ) return false;
    if( (sentido1>0 && sentido2>0) || (sentido1<0 && sentido2<0) ) {
      if(o1 == o2)return true;
    }
    else{
      if(o2 == f1) return true;
    }
    return false;
  }
  static int countSigns(Map<String,Integer> sign,  int origem, int cur, String S){

    if( cur == cabecas.length -1 ){
      return 1;
    }
    int dest = cabecas[cur];
    String key="" + origem + "." + dest +S;
    if( sign.containsKey( key ) ){
      return sign.get( key );
    }
    //System.out.println(key);
    if( origem - dest < 0){
      S="R";
    }
    else{S="L";}
    int count = 0;
    if( overlay( origem, dest, origem, cabecas[cur+1] ) ){
      count= (count + countSigns(sign, origem, cur+1,S) ) % 2147483647;
    }
    if( overlay( origem, dest, dest, cabecas[cur+1] ) ){
      count= ( count +countSigns(sign, dest, cur+1,S) ) % 2147483647;
    }

    sign.put( key, count);

    return sign.get(key);
  }


  public static void main(String[] args){
    Scanner stdin = new Scanner( System.in );
    int nsinais = stdin.nextInt();
    int inicio = stdin.nextInt();

    cabecas = new int[ nsinais + 1 ];
    for( int i = 1; i <= nsinais; i++){
      cabecas[i] = stdin.nextInt();
    }
    Map<String, Integer > sinais = new HashMap<String,Integer>(1999000);

    System.out.println( countSigns(sinais, inicio,1,"R") );
    //System.out.println( factorial(10) );
  }
}
