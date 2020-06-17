import java.util.*;

public class CM{
  static String converter(int n){
    return String.format("%.2f", n / 100.0).replaceAll(",",".");
  }

  static void caixore_morango( int mer, int caixa, int[][] precos ){
    int[] z= new int[caixa+1];
    int count[] = new int[caixa+1];
    z[0]=0;
    count[0]=1;
    for(int k = 1; k <= caixa; k++ ){
      z[ k ] = precos[ k ] [ 1 ];
      count[ k ] = 1;
    }

    for( int i = 2; i <= mer; i++){
      for( int k = caixa; k > 0; k--){
        for( int t = 0; t < k; t++ ){
          if(precos[k - t][ i ] + z[t]==z[k]){
            count[k] = (count [k] + count[t])  % 1073741823;
          }
          else if( precos[k - t][ i ] + z[t] > z[k] ){
            z[k] = precos[k - t][ i ] + z[t];
            count[k]=count[t];
          }
        }
      }
    }

    //System.out.println(Arrays.toString(count));
    System.out.println("Lucro Maximo = "+converter(z[caixa]));
    System.out.println("No.Sols = " + count[caixa] );
  }
  static void add_prices(String[] s , int[][] precos, int mer){
    for( int i = 1; i < precos.length; i++ ){
      precos[i][mer]= Integer.parseInt(s[i-1]);
    }
  }
  public static void main(String[] args){
    Scanner stdin = new Scanner( System.in );
    int mer = stdin.nextInt();
    int caixa = stdin.nextInt();
    int[][] precos = new int[caixa+1][mer+1];
    stdin.nextLine();
    for( int i = 1; i <= mer; i++){
      String[] s = stdin.nextLine().replaceAll("\\.","").split(" ");
      add_prices(s, precos,i);
    }
    caixore_morango(mer,caixa,precos);

  }
}
