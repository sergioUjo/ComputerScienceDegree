import java.util.*;

public class LA{
  static boolean valid_cage( int[] dimen, int[] minmax){
    if( ( dimen[0] >= minmax[0]) ){
      if( (dimen[1] >= minmax[2]) ){
        if( dimen[2] >= minmax[4] ) return true;
      }
    }
    return false;
  }
  public static void main( String[] args ){
    Scanner stdin = new Scanner( System.in );

  }
}
