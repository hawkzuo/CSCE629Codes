// Bottom Up Implementation of DP Matrix Algorithm
// Top Down Implementation of DP Matrix Algorithm

package dynamicprogramming;

public class MatrixMultiply {
	
	int Num;
	int [] dims;
	//Bottom Up
	int[][] M;	
	int[][] optK;		//short for optimalK
	//Record the result
	int minWork=-1;
	String procedure;
	
	
	//Top Down Version
	int[][] tM;			//short for topdown_M
	int[][] toptK;		//short for topdown_optimalK
	//Record the result
	int tminWork=-1;	//short for topdown_minWork
	String tprocedure;	//short for topdown_procedure

	public void showTableM(){
		String str = "";
		for(int i=0;i<M.length;i++){
			
			
			for(int j=0;j<M[0].length;j++){
				str += M[i][j];
				str += '\t';
			}
			str+='\n';
		}
		System.out.println(str);
	}
	
	
	
	
	
	public MatrixMultiply(Matrix[] ms) {
		// TODO Auto-generated constructor stub
		
		Num = ms.length;
		dims = new int[Num+1];
		dims[0] = ms[0].ldim;
		for(int i=1;i<dims.length;i++){
			dims[i] = ms[i-1].rdim;
		}
		
		//bottom-up
		M=new int[Num+1][Num+1];
		optK=new int[Num+1][Num+1];

		//top-down
		tM=new int[Num+1][Num+1];
		toptK=new int[Num+1][Num+1];
	}

//Top Down Version:
	
	public void gnerateAnsTopDown(){
		//Top Down construct table
		tminWork = get(1,Num);
		System.out.println("Total work is :");
		tprocedure = traceBackTopDown();
		System.out.println(tminWork);
		System.out.println(tprocedure);			
	}
	
	private int get(int row, int col){
		if(row == col){	return 0;}
		else if(tM[row][col] > 0){	return tM[row][col];}
		
		int optk = -1;
		int mincost = Integer.MAX_VALUE;
		
		for(int k = row;k<=(col-1);k++){
			int cur = get(row,k)+get(k+1,col)+dims[row-1]*dims[k]*dims[col];
			if(cur < mincost){
				mincost = cur;
				optk = k;
			}			
		}
		tM[row][col] = mincost;
		toptK[row][col] = optk;		
		
		return tM[row][col];
	}
	
	private String traceBackTopDown(){
		//This function require some tedious work
		if(tminWork == -1){
			return null;
		}
		String title = " The operation order is: \n";		
		String ans = generateParenthesis(1,Num,toptK);
		return title+ans;
	}
	
	
	
	
	
//Bottom Up Version:	
	public void generateAnsBottomUp(){
		//Bottom up construct table
		for(int row=Num;row>=1;row--){
			for(int col = row;col<=Num;col++){
				
				if(row == col){	continue;}
				//Loop over all possible situations
				int optk = -1;
				int mincost = Integer.MAX_VALUE;
				for(int k = row;k<=(col-1);k++){
					int cur = M[row][k]+M[k+1][col]+dims[row-1]*dims[k]*dims[col];
					if(cur < mincost){
						mincost = cur;
						optk = k;
					}
				}				
				M[row][col] = mincost;
				optK[row][col] = optk;												
			}			
		}		
		printBottomUpAns();
	}
	private void printBottomUpAns(){
		minWork = M[1][Num];	
		System.out.println("Total work is :");
		procedure = traceBackBottomUp();
		System.out.println(minWork);
		System.out.println(procedure);		
	}
	private String traceBackBottomUp(){
		//This function require some tedious work
		if(minWork == -1){
			return null;
		}
		String title = " The operation order is: \n";		
		String ans = generateParenthesis(1,Num,optK);
		return title+ans;
	}
	
	

// Shared Parenthesis Generating Function	
	
	private String generateParenthesis(int l, int r,int[][] K){
		String res = "";
		if(r == l ){
/*			res += "(";
			res += l;
			res += ",";
			res += r;
			res += ")";
			return res;*/
			res +="("+"A"+r+")";
			return res;
		}else{
			//Recursively construct string
			int k = K[l][r];			
			String lhalf = generateParenthesis(l,k,K);
			String rhalf = generateParenthesis(k+1,r,K);
			res += "(";
			res += lhalf;
			res += rhalf;
			res += ")";
			return res;
		}
	}

// Supplementary method for updating Matrices	
	
	public void refreshData(Matrix[] newms){
		Num = newms.length;
		dims = new int[Num+1];
		dims[0] = newms[0].ldim;
		for(int i=1;i<dims.length;i++){
			dims[i] = newms[i-1].rdim;
		}
		
		//bottom-up
		M=new int[Num+1][Num+1];
		optK=new int[Num+1][Num+1];
		minWork = -1;
		procedure = "";

		//top-down
		tM=new int[Num+1][Num+1];
		toptK=new int[Num+1][Num+1];
		tminWork = -1;
		tprocedure = "";		
	}	
	
	

	/**
	 * Test Bench
	 * @param args
	 */
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Matrix [] matrices = new Matrix[6];
		matrices[0] = new Matrix(3,8);
		matrices[1] = new Matrix(8,20);
		matrices[2] = new Matrix(20,15);
		matrices[3] = new Matrix(15,11);
		matrices[4] = new Matrix(11,2);
		matrices[5] = new Matrix(2,17);	

		
		MatrixMultiply s = new MatrixMultiply(matrices);
		
		//s.gnerateAnsTopDown();
		//System.out.println();
		s.generateAnsBottomUp();
		System.out.println();
		s.showTableM();
		
		System.out.println();
		System.out.println("fin");
	}

}
