//Exercise 15.4-5

package dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public class LongestMonotonicallyIncreasingSubsequence {

	int[] D;
	int[] P;
	int[] A;
	int N;
	int globalMax;
	int globalIndex;
	
	int[] lD;
	
	
	
	public LongestMonotonicallyIncreasingSubsequence(int[] input) {
		// TODO Auto-generated constructor stub
		N = input.length;
		D = new int[N+1];
		P = new int[N+1];
		A = input;
		lD = new int[N+1];
	}
	
	public void generateAnsLog(){		
		//Store lD[i] as (if exists) path's length == i's smallest ending element
		//Use an array of Lists to store the PATH
		
		//Initialize
		for(int i=1;i<=N;i++){
			lD[i] = Integer.MAX_VALUE;
		}		
		int finalLen = 1;		
		ArrayList<Integer>[] lists = new ArrayList[N+1];
		for(int i=0;i<=N;i++){
			lists[i] = new ArrayList<Integer>();
		}				
		//Only Scan the Array Once
		for(int i=1;i<=N;i++){
			//A[i-1] is the i-th element in the array
			if(A[i-1] < lD[1]){
				//update d[1]
				lD[1] = A[i-1];
				lists[1].clear();
				lists[1].add(lD[1]);
			}else{
				//find j* such that lD[j*] <= A[i-1] and update lD[j*+1]
				//Takes O(log n) time find j*
				int starJ = binarySearch(lD,A[i-1]);
				lD[starJ+1]=A[i-1];
				listCopy(lists[starJ],lists[starJ+1]);
				lists[starJ+1].add(A[i-1]);
				if(starJ+1>finalLen){
					finalLen+=1;
				}
			}			
		}		
		System.out.println(lists[finalLen]);		
	}
	
	public static int binarySearch(int[] arr, int target){
		//return directly the index in arr[]
		for(int i=0;i<arr.length;i++){
			if(arr[i] <= target){
				continue;
			}else{
				return i-1;
			}
		}
		return -1;
	}
	
	
	
	public static void listCopy(List<Integer> from, List<Integer> to){
		to.clear();
		for(int i=0;i<from.size();i++){
			to.add(from.get(i));
		}
	}
	
	
	
	
	
	
	
	
	public void generateAns(){
		D[1] = 1; P[1] = 1;	
		globalMax = 1;	globalIndex = 1;
		for(int i=2;i<=N;i++){
			int max = 1;
			int parent = i;			
			for(int k=i-1;k>=1;k--){
				if(A[k-1] <= A[i-1]){
					if(max < D[k]+1){
						max = D[k]+1;
						parent = k;						
					}					
				}
			}
			D[i] = max;
			P[i] = parent;
			if(max > globalMax){
				globalMax =  max;
				globalIndex = i;
			}			
		}		
	}

	public static void printTable(int[] D){
		String str1 = "[ ";
		for(int i=0;i<D.length;i++){
			str1 += D[i];
			str1 += " ";
		}
		str1 += "]";
		System.out.println(str1);
	}

	public String getOrder(){
		List<Integer> order = new ArrayList<Integer>();
		List<Integer> indices = new ArrayList<Integer>();
		order.add(0,A[globalIndex -1]);
		indices.add(0,globalIndex);
		
		int newindex = P[globalIndex];
		while(newindex != P[newindex]){
			order.add(0,A[newindex-1]);
			indices.add(0,newindex);
			
			newindex = P[newindex];
		}
		order.add(0,A[newindex-1]);
		indices.add(0,newindex);
		
		return "Chosen indices are:\n" + indices.toString()+"\n"+"Corresponding values are:\n"+order.toString();
		
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int [] input = {1,4,1,5,9,2,6,7,8,0,0,1,4,5,3,1,3,1,1,1,1,1};
		
		
		LongestMonotonicallyIncreasingSubsequence s = 
				new LongestMonotonicallyIncreasingSubsequence(input);
		s.generateAns();
		System.out.println("Input table is:");
		printTable(input);
		System.out.println("DP table is:");
		printTable(s.D);
		System.out.println("Parent table is:");
		printTable(s.P);
		System.out.println("");
		
		System.out.println("Longest Subsequence's Length is :\n"+s.globalMax);
		
		System.out.println("One possible sequence(by N^2) :");
		System.out.println(s.getOrder());
		System.out.println("");
		
		System.out.println("One possible sequence(by NlgN) :");
		s.generateAnsLog();
		
		
		System.out.println("fin");
		
	}

}
