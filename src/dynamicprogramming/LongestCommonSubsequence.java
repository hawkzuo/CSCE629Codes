package dynamicprogramming;

public class LongestCommonSubsequence {

	
	int m;
	int n;
	String x;
	String y;
	
	int[][] C;
	String [][] Lcs;
	
	public LongestCommonSubsequence(String input1,String input2) {
		// TODO Auto-generated constructor stub
		m = input1.length();
		n = input2.length();
		x = input1;
		y = input2;
		C = new int[m+1][n+1];
		Lcs = new String[m+1][n+1];
		for(int i=0;i<=m;i++){
			for(int j=0;j<=n;j++){
				Lcs[i][j] = "";
			}
		}		
	}

	public void getAns(){
		for(int i=0;i<=m;i++){
			C[i][0]=0;
		}
		for(int i=0;i<=n;i++){
			C[0][i] = 0;
		}

		
		for(int i=1;i<=m;i++){
			for(int j=1;j<=n;j++){
				
				if(x.charAt(i-1) == y.charAt(j-1)){
					C[i][j] = C[i-1][j-1]+1;
					Lcs[i][j]+=Lcs[i-1][j-1]+x.charAt(i-1);
				}else{
					C[i][j] = Math.max(C[i][j-1], C[i-1][j]);
					if(C[i][j-1] >= C[i-1][j]){
						Lcs[i][j]+=Lcs[i][j-1];
					}else{
						Lcs[i][j]+=Lcs[i-1][j];
					}
				}				
				
			}
						
		}
		System.out.println(C[m][n]);
		System.out.println(Lcs[m][n]);
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String x = "ABCBDAB";
		String y = "BDCABA";
		
		
		LongestCommonSubsequence s = new LongestCommonSubsequence(x,y);
		
		
		s.getAns();
	}

}
 