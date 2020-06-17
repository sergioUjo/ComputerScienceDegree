import java.util.Scanner;
import java.util.Arrays;

public class Pdf{
  static int tmes(int d, int m){
    int res=0;
    switch (m){
      case 6:res+=d;
      break;
      case 7:res+=31+d-1;
      break;
      case 8:res+=62+d-1;
      break;
      case 9:res+=93+d-1;
      break;
    }
    return res;
  }
  static int week(int[] cal){
    int d=4;
    int max=0;
    int maxd=0;
    while(d<=99){
      int maxSegun=0;
      int maxSaba=0;
      for(int i=d; i<d+7;i++){
        if(cal[i]==-1){
          maxSaba=-1;
        }
        else if(maxSaba!=-1){
          maxSaba+=cal[i];
        }
        if(cal[i+2]==-1){
          maxSegun=-1;
        }
        else if(maxSegun!=-1){
          maxSegun+=cal[i+2];
        }
      }
      if(max<maxSaba || maxSaba==0 && max==0 && maxd==0){
        max=maxSaba;
        maxd=d;
      }
      if(max<maxSegun || maxSegun==0 && max==0 && maxd==0){
        max=maxSegun;
        maxd=d+2;
      }
      //System.out.println(maxSaba+" "+maxSegun+" "+max+" "+maxd+" "+d);
      d+=7;
    }
    return maxd;
  }
  static String print(int n){
    //System.out.println(n);
    if(n<=0){
      return "inconsistente";
    }
    else{
      if(n>92){
        return (n-92) +" de Setembro";
      }
      else if(n>61){
        return (n-61) +" de Agosto";
      }
      else if(n>30){
        return (n-30) +" de Julho";
      }
      else{
        return (n)+" de Junho";
      }
    }
  }
  static void setdays(int inicio, int fim,int pref, int[] cal){
    for(int k=inicio; k<=fim; k++){
      if(pref==-1){
        cal[k]=pref;
      }
      else if(cal[k]!=-1){
        cal[k]+=pref;
      }
    }
  }
  public static void main(String[] args){
    int[] cal= new int [106];
    Scanner stdin= new Scanner(System.in);
    int npes= stdin.nextInt();
    for(int i=0; i<npes; i++){
      int npref=stdin.nextInt();
      for(int j=0; j<npref; j++){
        int pd=stdin.nextInt();
        int pm=stdin.nextInt();
        int sd=stdin.nextInt();
        int sm=stdin.nextInt();
        int pref=stdin.nextInt();
        setdays(tmes(pd,pm),tmes(sd,sm),pref,cal);
        //System.out.println(pm+ " "+sm);
      }//fim de ler preferencia
    }// numero de pessoas

    System.out.println(print(week(cal)));
  //  System.out.println(Arrays.toString(cal));
  }//fim de main
}
