import java.util.*;

public class TS{

  static int nli;
  static int nlf;
  static int custom;
  static int [] custo;
  static int [] emes;

  static int getCusto( String s ){
    return Integer.parseInt(s.split(",")[0]);
  }

  static int getCount( String s ){
    return Integer.parseInt(s.split(",")[1]);
  }

/*  static String setCusto( int n ){
    String [] temp= s.split(",");
    temp[0]= ""+n;
    return temp[0]+","+temp[1];
  }

  static String setCount( int n ){
    String [] temp= s.split(",");
    temp[1]= ""+n;
    return temp[0]+","+temp[1];
  }
  static String addCount( int n){
    String [] temp= s.split(",");
    temp[1]= ""+ ( Integer.parseInt(temp[1])+n);
    return temp[0]+","+temp[1];
  }
*/
  static int[] perfectSol(Map<String,int[]> despesas,  int armazenado, int k){
    int[] values= new int[2];
    String key = ""+k+"."+armazenado;
    if( k == emes.length){
      if( armazenado < nlf){
        values[0] = -1;
        return values;
      }
      values[1] = 1;
      return values;
    }

    if( despesas.containsKey(key) ){
      return despesas.get(key);
    }

    int min=999999999;
    int count=1;

    for( int i = 0; i < custo.length; i++){

      int curArma=(armazenado+i)-emes[k];

      if( curArma<0)continue;
      int[] aux = perfectSol(despesas, curArma, k+1);

      if( aux[0]!=-1 ){

        int tempCusto = aux[0];
        int tempCount = aux[1];

        tempCusto += (curArma*custom) + custo[i];

        if ( tempCusto == min){
          count = (count + tempCount) % 1073741823;
        }
        else if( tempCusto < min){
          min = tempCusto;
          count =tempCount;
        }

      }
    }
    if( min==999999999){
      values[0]=-1;
      return values;
    }
    values[1]=count;
    values[0]=min;
    despesas.put(key,values);
    return despesas.get(key);
  }


  public static void main(String[] args){
    Scanner stdin = new Scanner( System.in );
    int pmes = stdin.nextInt();
    custo = new int [ pmes + 1 ];

    for( int i = 1; i < custo.length; i++){
      custo[ i ] = stdin.nextInt();
    }

    int nmes = stdin.nextInt();
    nli = stdin.nextInt();
    nlf = stdin.nextInt();
    custom = stdin.nextInt();

    emes = new int [ nmes + 1 ];

    for( int i = 1; i < emes.length; i++){
      emes[ i ] = stdin.nextInt();
    }
    //System.out.println(Arrays.toString(emes));
    //System.out.println(Arrays.toString(custo));
    Map<String,int[]> despesas = new HashMap<String,int[]>(nmes*pmes);
    perfectSol(despesas, nli, 1);
    //System.out.println(despesas);
    int temp[] = despesas.get(""+1+"."+nli);

    if ( temp == null ){
      System.out.println("impossivel");
    }
    else{
      System.out.println( "Custo Minimo = " + temp[0] );
      System.out.println( "No.Sols = " + temp[1] );
    }
  }
}
