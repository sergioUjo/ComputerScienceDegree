import java.util.Scanner;
class SlotMachine{
  int v[] = new int[6];
  int divida;
  int invalidas;
  int transacoes;
  int dado;
  SlotMachine(Scanner in){
    for(int i=0; i<6;i++){
      v[i]=in.nextInt();
    }
    divida=0;
    invalidas=0;
    transacoes=0;
    dado=0;
  }
  public int pagar(int quant1){
    int quant=(dado-quant1);
    while(quant>=200 && v[0]>0){
      quant-=200;
      v[0]-=1;
    }
    while(quant>=100 && v[1]>0){
      quant-=100;
      v[1]-=1;
    }
    while(quant>=50 && v[2]>0){
      quant-=50;
      v[2]-=1;
    }
    while(quant>=20 && v[3]>0){
      quant-=20;
      v[3]-=1;
    }
    while(quant>=10 && v[4]>0){
      quant-=10;
      v[4]-=1;
    }
    while(quant>=5 && v[5]>0){
      quant-=5;
      v[5]-=1;
    }
    divida+=quant;
    if(quant!=0){
      invalidas++;
    }
    transacoes++;
    dado=0;
    return quant;
  }
  public void inserir(int n){
    switch (n){
      case 2: v[0]+=1;dado+=200;
      break;
      case 1: v[1]+=1;dado+=100;
      break;
      case 50: v[2]+=1;dado+=50;
      break;
      case 20: v[3]+=1;dado+=20;
      break;
      case 10: v[4]+=1;dado+=10;
      break;
      case 5: v[5]+=1;dado+=5;
      break;
    }
  }
  public String toString(){
    int eu=0;
    while(divida>=100){
      eu++;
      divida-=100;
    }
    return eu+" " +divida+"\n"+ +invalidas+"/" +transacoes;
  }
}
public class Nldt{
  public static void main(String[] args){
    Scanner in= new Scanner(System.in);
    SlotMachine prime = new SlotMachine(in);
    int eu= in.nextInt();
    int cent= in.nextInt();
    while(eu!=0 || cent !=0){
      int quant= eu*100 +cent;
      int moedas=in.nextInt();
      while(moedas!=0){
        prime.inserir(moedas);
        moedas=in.nextInt();
      }
      prime.pagar(quant);
      eu= in.nextInt();
      cent= in.nextInt();
    }
    System.out.println(prime);
  }
}
