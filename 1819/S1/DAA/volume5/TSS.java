import java.util.*;

public class TSS{

  static int nli;
  static int nlf;
  static int custom;
  static int nmes;
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
  static String perfectSol(Map<String,String> despesas,  int armazenado, int k){

    String key = ""+k+"."+armazenado;
    if( k == nmes+1){
      if( armazenado < nlf) return "-1,0";
      return "0,1";
    }

    if( despesas.containsKey(key) ){
      return despesas.get(key);
    }

    int min=999999999;
    int count=1;
    for( int i = 0; i < custo.length; i++){
        int curArma=armazenado-emes[k]+i;
      if( curArma<0) continue;

      String aux = perfectSol(despesas, curArma, k+1);
      int tempCusto =  getCusto(aux);

      if( tempCusto!=-1 ){
        int tempCount = getCount(aux);

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
      despesas.put(key,"-1"+","+count);
    }
    else{
      despesas.put(key,""+min+","+count);
    }
    return despesas.get(key);
  }


  public static void main(String[] args){
    Scanner stdin = new Scanner( System.in );
    int pmes = stdin.nextInt();
    custo = new int [ pmes + 1 ];

    for( int i = 1; i < custo.length; i++){
      custo[ i ] = stdin.nextInt();
    }

    nmes = stdin.nextInt();
    nli = stdin.nextInt();
    nlf = stdin.nextInt();
    custom = stdin.nextInt();

    emes = new int [ nmes + 1 ];

    for( int i = 1; i < emes.length; i++){
      emes[ i ] = stdin.nextInt();
    }
    //System.out.println(Arrays.toString(emes));
    //System.out.println(Arrays.toString(custo));
    Map<String,String> despesas = new HashMap<String,String>(nmes*pmes);
    perfectSol(despesas, nli, 1);
    //System.out.println(despesas);
    String temp = despesas.get(""+1+"."+nli);

    if ( getCusto( temp )==-1 ){
      System.out.println("impossivel");
    }
    else{
      System.out.println( "Custo Minimo = " + getCusto( temp ) );
      System.out.println( "No.Sols = " + getCount( temp ) );
    }
  }
}
