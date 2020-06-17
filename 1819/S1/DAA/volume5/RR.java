import java.util.*;
public class Bolo implements Comparable<Bolo>{
  int [] carac= new int[4];
  double rend;
  int produzido;

  Bolo( Scanner stdin ){
    for( int i = 0; i < 4; i++ ){
      carac [ i ] = stdin.nextInt();
    }
    rend = Math.min( carac[ 1 ] / carac[ 0 ], ) ;
    int produzido = 0;
  }
  //GET
  public int getMin(){
    return carac[ 3 ];
  }

  public int getMax(){
    return carac[ 2 ];
  }
  //--------------------------

  public int productMin ( int min ){
    while( min > carac[ 1 ] || carac[ 3 ] != 0 ){
      carac[ 3 ]--;
      min-=carac[ 1 ];
    }
    if( carac[ 3 ] != 0 ){
      return -1;
    }
    return min;
  }

  public int compareTo( Bolo outro){
    return outro.rend - this.rend;
  }

}
public class RR{
  public static void main( String[] args ){
    Scanner stdin = new Scanner( System.in );

    int nhoras = stdin.nextInt();
    int ntipos = stdin.nextInt();

    int Bolos[] = new int[ ntipos ];

    for( int i = 0; i < ntipos; i++){
      Bolos[ i ] = new Bolo(stdin);
    }

    for( int i = 0; i < ntipos; i++){
      System.out.println(Arrays.toString( produtos[ i ] ) );
    }
  }
}
