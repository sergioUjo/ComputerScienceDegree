/*------------------------------------------------------\
|       Bacalhaus congelados                            |
|  Exemplos disponiveis  -- Prova pratica  2017         |
|  A.P. Tomas (Dez 2017)                                |
\------------------------------------------------------*/


import java.util.*;


//============================== Grafo.java 

class Arco {
    int no_final;
    int valor;
    
    Arco(int fim, int v){
	no_final = fim;
	valor = v;
    }

    int extremo_final() {
	return no_final;
    }

    int valor_arco() {
	return valor;
    }

    void novo_valor(int v) {
	valor = v;
    }
}


class No {
    //int label;
    LinkedList<Arco> adjs;

    No() {
	adjs = new LinkedList<Arco>();
    }
}


class Grafo {
    No verts[];
    int nvs, narcos;
			
    public Grafo(int n) {
	nvs = n;
	narcos = 0;
	verts  = new No[n+1];
	for (int i = 0 ; i <= n ; i++)
	    verts[i] = new No();
        // para vertices numerados de 1 a n (posicao 0 nao vai ser usada)
    }
    
    public int num_vertices(){
	return nvs;
    }

    public int num_arcos(){
	return narcos;
    }

    public LinkedList<Arco> adjs_no(int i) {
	return verts[i].adjs;
    }
    
    public void insert_new_arc(int i, int j, int valor_ij){
	verts[i].adjs.addFirst(new Arco(j,valor_ij));
        narcos++;
    }

    public Arco find_arc(int i, int j){
	for (Arco adj: adjs_no(i))
	    if (adj.extremo_final() == j) return adj;
	return null;
    }
}

//============================== Bacalhaus Congelados

class Bacalhaus {

   
    public static Grafo lerGrafo(Scanner stdin){
	int nverts = stdin.nextInt();
 	int nedges = stdin.nextInt();
        int temp, aux, i, j;

	Grafo g = new Grafo(nverts);

	while (nedges-- > 0) {
	    i = stdin.nextInt();
	    j = stdin.nextInt();
	    temp = stdin.nextInt();
	    aux = stdin.nextInt();   // this value is not used 
	    g.insert_new_arc(i,j,temp);
	    g.insert_new_arc(j,i,temp); // undirected graph
	}
	
	return g;
    }

    public static void analisaPercurso(Scanner stdin,int nvs, Grafo g){
	int prev = stdin.nextInt();
 	int curr = stdin.nextInt();
	int mintemp, maxtemp;
	int temp = g.find_arc(prev,curr).valor_arco();
	mintemp = maxtemp = temp;

	nvs = nvs-2;
	while(nvs-- > 0) {
	    prev = curr;
	    curr =  stdin.nextInt();
	    temp = g.find_arc(prev,curr).valor_arco();
	    if (temp < mintemp) mintemp = temp;
	    else if (temp > maxtemp) maxtemp = temp;
	}  

	System.out.println(mintemp+" "+maxtemp);

    }	

    public static void main(String[] args){

	Scanner stdin = new Scanner(System.in);	

	Grafo g = lerGrafo(stdin);

	int numbnodes;
	while ((numbnodes = stdin.nextInt()) != 0)    // ler e testar se diferente de zero
	    analisaPercurso(stdin,numbnodes,g);
	
    }
}

