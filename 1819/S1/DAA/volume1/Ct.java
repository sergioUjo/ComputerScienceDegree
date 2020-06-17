import java.util.Scanner;
public class Ct{
    static int insere(int v[], int corr,int n){
	for(int i=0; i<corr; i++){
	    if(v[i]==n){
		return i+1;
	    }
	}
	v[corr]=n;
	return corr+1;
    }
    public static void main(String[] args){
	Scanner in= new Scanner(System.in);
	int temp=in.nextInt();
	int v[]=new int[30];
	v[0]=temp;
	int corr=1;
	temp=in.nextInt();
	while(temp!=0){
	    corr=insere(v,corr,temp);
	    temp=in.nextInt();
	    //System.out.println(v[corr]+" " + corr+ " " + temp);
	}
	for(int i=0; i<corr; i++){
	    System.out.println(v[i]);
	}
    }
}
