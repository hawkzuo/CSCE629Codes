//hw4-Q(B)


package dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public class GraphStringPath {
	
	int Dim;	
	Map<Integer,String>[] Adjs;
	int[][] A;
	String target;
	
	int[][] tA;
	
	//Store the path
	String[][] paths;
	
	public void showTable(){
		String str = "The internal table stored is: \n";
		for(int i=0;i<A.length;i++){		
			for(int j=0;j<A[0].length;j++){
				str += A[i][j];
				str += '\t';
			}
			str+='\n';
		}
		System.out.println(str);
	}
	public void showPath(){
/*		String str = "";
		for(int i=0;i<paths.length;i++){		
			for(int j=0;j<paths[0].length;j++){
				str += paths[i][j];
				str += '\t';
				str += '\t';
			}
			str+='\n';
		}*/
		
		for(int i=0;i<Dim;i++){
			if(paths[target.length()][i].length() != 0){
				System.out.println("One of the possible paths is shown as belows:\n");
				System.out.println(paths[target.length()][i]);	
			}
		}
			
	}
	
	
	public GraphStringPath(Edge<Character> [] es, int vertices, String refer) {
		// TODO Auto-generated constructor stub
		Dim = vertices;
		Adjs = new Map[Dim];
		
		for(int i=0;i<Dim;i++){
			Adjs[i] = new HashMap<Integer,String>();
		}
		
		for(Edge<Character> edge : es){
			String c= "";
			c+=edge.ch;
			int lnode = edge.left;
			int rnode = edge.right;
			Adjs[lnode].put(rnode, c);
		}
		
		target = refer;		
		A = new int[target.length()+1][Dim];	
		tA = new int[target.length()+1][Dim];
		paths = new String[target.length()+1][Dim];
		for(int r=0;r<=target.length();r++){
			for(int c=0;c<Dim;c++){
				paths[r][c] = "";
			}
		}
	}

	public boolean getAns(){		
		//Fill in the base case first
		boolean hasAns;		
		Map<Integer,String> adj0 = Adjs[0];		
		for(int rightnode: adj0.keySet()){
			if(adj0.get(rightnode).charAt(0) == target.charAt(0)){
				A[1][rightnode] = 1;
				//Must use double quotes
				paths[1][rightnode] += "("+0+","+rightnode+","+target.charAt(0)+")";
			}		
		}
		
		//Bottom Up DP
		//Loop over the cols
		for(int i=2;i<=target.length();i++){
			
			//Fill in the rows
			hasAns = false;
			for(int j=0;j<Dim;j++){
				
				for(int k=0;k<Dim;k++){
					
					if(A[i-1][k] != 1){
						continue;
					}
					//now check edge (k,j)
					Map<Integer,String> adjK = Adjs[k];
					if(!adjK.containsKey(j)){
						continue;
					}else{
						if(adjK.get(j).charAt(0) == target.charAt(i-1)){
							//Found path
							A[i][j] = 1;
							hasAns = true;
							
							paths[i][j] += paths[i-1][k] +"->"+ '('+k+','+j+','+target.charAt(i-1)+')';
							break;
						}
					}					
				}				
			}
			
			if(!hasAns){	return false;}			
		}		
		return true;
	}

	
	public boolean getAnsTopDown(){
		
		int res = 0;	
		Map<Integer,String> adj0 = Adjs[0];	
		for(int k=0;k<Dim;k++){
			tA[1][k] = -1;
		}
		
		for(int rightnode: adj0.keySet()){
			if(adj0.get(rightnode).charAt(0) == target.charAt(0)){
				tA[1][rightnode] = 1;
			}else{
				tA[1][rightnode] = -1;
			}
		}		
		
		for(int k=0;k<Dim;k++){
			if(get(target.length(),k) == 1){
				res = 1;
			}
		}
		
		if(res == 1){		
			return true;
		}else{
			return false;
		}
	}
	
	private int get(int i, int j){
		if(tA[i][j] != 0){
			return tA[i][j];
		}
		
		
		for(int k=0;k<Dim;k++){
			
			if(get(i-1,k) != 1){
				continue;
			}
			//now check edge (k,j)
			Map<Integer,String> adjK = Adjs[k];
			if(!adjK.containsKey(j)){
				continue;
			}else{
				if(adjK.get(j).charAt(0) == target.charAt(i-1)){
					//Found path
					tA[i][j] = 1;
					return 1;
				}
			}					
		}		
		tA[i][j] = -1;
		return -1;
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Edge<Character> [] e = new Edge[6];
		
		 e[0] = new Edge<Character>('a',0,1);
		 e[1] = new Edge<Character>('a',1,3);
		 e[2] = new Edge<Character>('b',3,4);
		 e[3] = new Edge<Character>('c',4,1);
		 e[4] = new Edge<Character>('b',0,2);
		 e[5] = new Edge<Character>('c',2,4);
		
		String refer = "aabcabcabcabca";
		 
		GraphStringPath sol = new GraphStringPath(e,5,refer);
		
		//System.out.println(sol.getAnsTopDown());
		System.out.println(sol.getAns());
		sol.showTable();
		sol.showPath();
	}

}
