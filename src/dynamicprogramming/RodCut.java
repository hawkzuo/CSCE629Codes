package dynamicprogramming;
public class RodCut {
	//Reconstruct the prices with index starting at 1
	int [] A;
	//The cost of each rod cutting
	int cutcost;
	//D is dp table, P is parent table
	int [] D;
	int [] P;
	//Len is the input rod's length
	int Len;	
	public RodCut(int[] p, int cost, int rodlen) {
		// TODO Auto-generated constructor stub
		// By definition of the problem, prices table's length is 
		// at least as large as rodlen		
		A = new int[p.length+1];
		for(int i=0;i<p.length;i++){
			A[i+1] = p[i];
		}
		Len = rodlen;
		D = new int[rodlen+1];
		P = new int[rodlen+1];
		cutcost = cost;		
	}
	public void getAns(){
		//Initialize
		D[0] = 0;	D[1] = A[1]; P[1] = 1;
		for(int i=2;i<=Len;i++){			
			int localmax = Integer.MIN_VALUE;
			int p = Integer.MIN_VALUE;
			for(int k=i-1;k>=1;k--){				
				int cur = D[k]+A[i-k]-cutcost;
				if(cur > localmax){
					//Update local
					localmax = cur;
					p = k;
				}				
			}
			if(A[i] >= localmax){
				//Update local
				localmax = A[i];
				p = i;
			}			
			//Update table
			D[i] = localmax;
			P[i] = p;			
		}
	}

	public void printAns(){
		String str1 = "Inner table stored:\n[ ";
		for(int i=1;i<D.length;i++){
			str1 += D[i];
			str1 += " ";
		}
		str1 += "]";
		System.out.println(str1);
		
		System.out.println();

		String str2 = "Parent table stored:\n[ ";
		for(int i=1;i<P.length;i++){
			str2 += P[i];
			str2 += " ";
		}
		str2 += "]";
		System.out.println(str2);
		
		
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[] prices = {1,5,8,9,10,17,17,20,24,30};
		RodCut sol = new RodCut(prices,2,10);
		sol.getAns();
		sol.printAns();
		
	}

}
