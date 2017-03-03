package closestpairs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Solution {

	
	class NullInputException extends Exception{

		/**
		 * This exception occurs if the input array is NULL
		 */
		private static final long serialVersionUID = 1L;
		public NullInputException(){
			System.out.println("Input array is not valid");
		}
	}
	class TimePair{
		private int size;
		private long brutetime;
		private long ourtime;
		public TimePair(int size,long brute, long our){
			this.size=size;
			this.brutetime=brute;
			this.ourtime=our;
		}
		public String toString(){
			String s = "";
			s += "[ "+size+": "+brutetime+", "+ourtime+" ]";
			return s;
		}
	}
	
	// stored data

	private double [][] dbX;
	private double [][] dbY;
	private double[][] minPts;
	//# of points
	int Num;
	//global MIN 
	double gmin;	
	//for brute use
	double Mind;
	private double[][] mindPts;
	/**
	 * Get a deep copy of the given array	
	 * @param src -the source array
	 * @return	The new copied array
	 */	
	public static double[][] cloneArray(double[][] src) {
	    int length = src.length;
	    double[][] target = new double[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}	
	/**
	 * Merge two sorted lists into one List
	 * @param l	-one input list
	 * @param r -the other input lists
	 * @return	The merged sorted list
	 */
	private static List<double[]> merge(List<double[]> l, List<double[]> r){
		List<double[]> res = new ArrayList<double[]>();
		int lsize = l.size();	int rsize = r.size();
		int i=0; 	int j=0;
		while(i<lsize && j<rsize){			
			if(l.get(i)[1] < r.get(j)[1]){
				res.add(l.get(i));	i++;
			}else if(l.get(i)[1] > r.get(j)[1]){
				res.add(r.get(j));	j++;
			}else{
				res.add(l.get(i));	i++;
				res.add(r.get(j));	j++;
			}	
		}
		if(i<lsize){
			for(int k=i;k<lsize;k++	){
				res.add(l.get(k));
			}		
		}else if(j<rsize){
			for(int k=j;k<rsize;k++	){
				res.add(r.get(k));
			}			
		}
		return res;		
	}
	/**
	 * Update the problem with the given points.
	 * @param data
	 * @throws NullInputException
	 */
	
	public void refreshData(double [][] data) throws NullInputException{
		if(data == null){
			throw new NullInputException();
		}
		Num = data.length;
		if(Num == 0){
			throw new NullInputException();
		}		
		Arrays.sort(data, new Comparator<double[]>(){
		     public int compare(double[] o1, double[] o2)
		     {
		    	 return Double.valueOf(o1[0]).compareTo(o2[0]);		               
		     }				
		});
		dbX=cloneArray(data);
		Arrays.sort(data, new Comparator<double[]>(){
		     public int compare(double[] o1, double[] o2)
		     {
		    	 return Double.valueOf(o1[1]).compareTo(o2[1]);		               
		     }				
		});		
		dbY=cloneArray(data);
		
		//For Algorithm use
		minPts = new double[2][2];
		gmin=Double.MAX_VALUE;
		
		//For Brute use
		Mind=Double.MAX_VALUE;
		mindPts = new double[2][2];
	}
	
	
	
	
	/**
	 * Create the problem with the given points.
	 * @param data -the input points
	 * @throws NullInputException 
	 */
	public Solution(double [][] data) throws NullInputException {
		// TODO Auto-generated constructor stub
		if(data == null){
			throw new NullInputException();
		}
		Num = data.length;
		if(Num == 0){
			throw new NullInputException();
		}
		
		//Pre-sort the points
		Arrays.sort(data, new Comparator<double[]>(){
		     public int compare(double[] o1, double[] o2)
		     {
		    	 return Double.valueOf(o1[0]).compareTo(o2[0]);		               
		     }				
		});
		dbX=cloneArray(data);
		Arrays.sort(data, new Comparator<double[]>(){
		     public int compare(double[] o1, double[] o2)
		     {
		    	 return Double.valueOf(o1[1]).compareTo(o2[1]);		               
		     }				
		});		
		dbY=cloneArray(data);
		
		//For Algorithm use
		minPts = new double[2][2];
		gmin=Double.MAX_VALUE;
		
		//For Brute use
		Mind=Double.MAX_VALUE;
		mindPts = new double[2][2];
	}
	/**
	 * Get the answer of this given data set.
	 */
	public TimePair getAnswer(){
		if(Num == 1){
			System.out.println("Not sufficient points");
			return null;
		}else{
			long startTime = System.currentTimeMillis();
			System.out.println("By O(nlgn) algorithm: ");
			System.out.println(Math.sqrt(Ans()));
			String str = "The closest pairs are ("+minPts[0][0]+","+minPts[0][1]+") and ("+minPts[1][0]+","+minPts[1][1]+")."+'\n';		
			System.out.println(str);
			long endTime = System.currentTimeMillis();
			long linterval = endTime-startTime;

			
			startTime = System.currentTimeMillis();
			System.out.println("By Brute algorithm: ");
			System.out.println(Math.sqrt(bruteForce(dbX)));
			String str2 = "The closest pairs are ("+mindPts[0][0]+","+mindPts[0][1]+") and ("+mindPts[1][0]+","+mindPts[1][1]+")."+'\n';		
			System.out.println(str2);	
			endTime = System.currentTimeMillis();
			long binterval = endTime-startTime;
			return new TimePair(Num,binterval,linterval);
		}
	}
	
	
	
	/**
	 * The Brute Force Method of calculating the square of the closest distance.
	 * @param d -the input array
	 * @return	The minimum distance
	 */
	private double bruteForce(double[][] d){
		
		int N=d.length;
		for(int i=0;i<N;i++){
			for(int j=i+1;j<N;j++){
				bruteDist(d[i][0],d[i][1],d[j][0],d[j][1]);
			}
		}
		return Mind;
		
	}
	
	private double bruteDist(double x1, double y1,double x2,double y2){
		double cur_d=(x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);

		//Update minPts
		if(cur_d < Mind){
			mindPts[0][0]=x1;	mindPts[0][1]=y1;
			mindPts[1][0]=x2;	mindPts[1][1]=y2;
			Mind = Math.min(Mind, cur_d);
		}

		return cur_d;
	}	

	/**
	 * Get the minimum distance by our algorithm.
	 * @return Minimum distance
	 */
	
	public double Ans(){
		if(!checkUnique(dbX)){
			gmin=0;
		}else{	
			getMin(dbY,0,Num);
		}
		return gmin;
	}
	
	/**
	 * Check if the given points are unique
	 * @param data -the input points
	 * @return True/False.
	 */
	
	private boolean checkUnique(double[][] data){
		
		int len=data.length;
		double prevx=data[0][0];	double prevy=data[0][1];
		for(int i=1;i<len;i++){
			if(prevx == data[i][0] && prevy == data[i][1]){
				minPts[0][0]=prevx;	minPts[0][1]=prevy;
				minPts[1][0]=prevx;	minPts[1][1]=prevy;				
				return false;
			}
			prevx = data[i][0];  prevy = data[i][1];
		}
		return true;		
	}
	
	/**
	 * Calculate the distance between (x1,y1) and (x2,y2), record the global smallest distance in gmin
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return Distance between (x1,y1) and (x2,y2).
	 */
		
	private double getDist(double x1, double y1,double x2,double y2){
		double cur_d=(x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);

		//Update minPts
		if(cur_d < gmin){
			minPts[0][0]=x1;	minPts[0][1]=y1;
			minPts[1][0]=x2;	minPts[1][1]=y2;
			gmin=Math.min(gmin, cur_d);
		}

		return cur_d;
	}
	
	
	/**
	 * Main recursive method for Divide and Conquer
	 * @param Y -the array of the input sorted by Y coordinate 
	 * @param start -the start index of dbX
	 * @param end -the end index of dbX
	 * @return	the minimum distance of all the input parts
	 */
	private double getMin(double[][] Y, int start, int end){	
		//Coordinate format: [start,end)
		if(end-start <= 3 ){			
			double minD = Double.MAX_VALUE;
			if(end-start <=1){
			}
			else if(end-start == 2){
				minD = getDist(dbX[start][0],dbX[start][1],dbX[start+1][0],dbX[start+1][1]);
			}else{
				minD = Math.min(Math.min(getDist(dbX[start][0],dbX[start][1],dbX[start+1][0],dbX[start+1][1]), 
						getDist(dbX[start+2][0],dbX[start+2][1],dbX[start+1][0],dbX[start+1][1])), 
						getDist(dbX[start][0],dbX[start][1],dbX[start+2][0],dbX[start+2][1]));										
			}
			return minD;
		}
				
		int mid = (start+end)/2;		
		double Xmid = dbX[mid][0];		
		//Construct YL and YR and keep them sorted
		double [][] Yl = new double[mid-start+1][2];
		double [][] Yr = new double[end-mid-1][2];
		//Insertion Pointer for Yl and Yr
		int pl=0;	int pr=0;
		for(int i=0;i<Y.length;i++){
			//check (xi,yi) in wich group
			double xi=Y[i][0];	double yi=Y[i][1];
			if(xi < Xmid){
				Yl[pl][0]=xi; Yl[pl][1]=yi;	pl++;
			}else if (xi > Xmid){
				Yr[pr][0]=xi; Yr[pr][1]=yi;	pr++;
			}else{
				//Check if in the left half
				int z=mid;
				boolean isLeft = false;
				while(z>=0 && dbX[z][0] == Xmid){
					if(dbX[z][1] == yi){
						isLeft = true;
						Yl[pl][0]=xi; Yl[pl][1]=yi;	pl++;
						break;
					}
					z--;
				}
				if(!isLeft){
					Yr[pr][0]=xi; Yr[pr][1]=yi;	pr++;
				}				
			}	
		}
	
		
		double lmin = getMin(Yl,start,mid+1);
		double rmin = getMin(Yr,mid+1,end);
		
		double delta = Math.min(lmin, rmin);
		//gmin = Math.min(gmin, delta);
		
		double newd=Double.MAX_VALUE;

		//Now construct Ypm by Yl and Yr:
		List<double[]> Ypl = new ArrayList<double[]>();
		List<double[]> Ypr = new ArrayList<double[]>();
		
		for(int i=0;i<mid-start+1;i++){
			if(Math.abs(Yl[i][0]-Xmid) < delta){
				Ypl.add(Yl[i]);
			}
		}
		for(int i=0;i<end-mid-1;i++){
			if(Math.abs(Yr[i][0]-Xmid) < delta){
				Ypr.add(Yr[i]);
			}
		}		
		//Merge back to Y'
		List<double[]> Yp = merge(Ypl,Ypr);				
		
		for (int i = 0; i < Yp.size(); i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < Yp.size()) && (Yp.get(j)[1]-Yp.get(i)[1] < delta); j++) {
                double distance = getDist(Yp.get(i)[0],Yp.get(i)[1],Yp.get(j)[0],Yp.get(j)[1]);
                if (distance < newd) {
                    newd = distance;
                }
            }
        }				
		newd = Math.min(newd, delta);
		return newd;
	}

	/**
	 * Test Bench
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		List<TimePair> timelist = new ArrayList<TimePair>();

		double [][] input1 =null;
		Solution s1;
		try {
			s1 = new Solution(input1);
			s1.getAnswer();
		} catch (NullInputException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}	
		System.out.println("-------------------------------------------------------------");
		
		double [][] input2 ={};
		Solution s2;
		try {
			s2 = new Solution(input2);
			s2.getAnswer();
		} catch (NullInputException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		
		System.out.println("-------------------------------------------------------------");
		
		double [][] input6 ={{1,1}};
		Solution s6;
		try {
			s6 = new Solution(input6);
			s6.getAnswer();
		} catch (NullInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-----------------------General Cases-------------------------");
		
		double [][] input3 ={{0,0},{0,1},{100,45}};
		Solution s3;
		try {
			s3 = new Solution(input3);
			timelist.add(s3.getAnswer());
		} catch (NullInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Random r = new Random();		
		double [][] input4 ={{0,0},{0,1},{100,45},{2,3},{9,9},{9,9}};
		double [][] input5 ={{1,4},{2,3},{2,2},{1,1},{2,1},{4,3},{5,2},{4,1},{5,1},{4,0.9},{21,23},{27,16},{45,45.0001},{45,45},{55,28},{13,31},
				{14,16},{12.23,11.46}};	
		
		double [][] input8 =new double[1000][2];
		for(int i=0;i<1000;i++){
			input8[i][0] = 10000 * r.nextDouble();
			input8[i][1] = 10000 * r.nextDouble();
		}
		double [][] input81 =new double[2000][2];
		for(int i=0;i<2000;i++){
			input81[i][0] = 10000 * r.nextDouble();
			input81[i][1] = 10000 * r.nextDouble();
		}
		double [][] input82 =new double[4000][2];
		for(int i=0;i<4000;i++){
			input82[i][0] = 10000 * r.nextDouble();
			input82[i][1] = 10000 * r.nextDouble();
		}		
		double [][] input83 =new double[8000][2];
		for(int i=0;i<8000;i++){
			input83[i][0] = 10000 * r.nextDouble();
			input83[i][1] = 10000 * r.nextDouble();
		}	
		
		double [][] input7 =new double[10000][2];
		for(int i=0;i<10000;i++){
			input7[i][0] = 10000 * r.nextDouble();
			input7[i][1] = 10000 * r.nextDouble();
		}	
		double [][] input71 =new double[20000][2];
		for(int i=0;i<20000;i++){
			input71[i][0] = 10000 * r.nextDouble();
			input71[i][1] = 10000 * r.nextDouble();
		}		
		double [][] input72 =new double[40000][2];
		for(int i=0;i<40000;i++){
			input72[i][0] = 10000 * r.nextDouble();
			input72[i][1] = 10000 * r.nextDouble();
		}		
		double [][] input73 =new double[80000][2];
		for(int i=0;i<80000;i++){
			input73[i][0] = 10000 * r.nextDouble();
			input73[i][1] = 10000 * r.nextDouble();
		}		
		
		double [][] input9 =new double[100000][2];
		for(int i=0;i<100000;i++){
			input9[i][0] = 10000 * r.nextDouble();
			input9[i][1] = 10000 * r.nextDouble();
		}		
		double [][] input10 =new double[200000][2];
		for(int i=0;i<200000;i++){
			input10[i][0] = 10000 * r.nextDouble();
			input10[i][1] = 10000 * r.nextDouble();
		}		
		//The following only test proper cases
		Solution sol;		
		try {
			sol = new Solution(input4);
			timelist.add(sol.getAnswer());
			sol.refreshData(input5);
			timelist.add(sol.getAnswer());
			sol.refreshData(input8);
			timelist.add(sol.getAnswer());
			sol.refreshData(input81);
			timelist.add(sol.getAnswer());
			sol.refreshData(input82);
			timelist.add(sol.getAnswer());			
			sol.refreshData(input83);
			timelist.add(sol.getAnswer());			
			sol.refreshData(input7);
			timelist.add(sol.getAnswer());
			sol.refreshData(input71);
			timelist.add(sol.getAnswer());
			sol.refreshData(input72);
			timelist.add(sol.getAnswer());
			sol.refreshData(input73);
			timelist.add(sol.getAnswer());
			sol.refreshData(input9);
			timelist.add(sol.getAnswer());
			sol.refreshData(input10);
			timelist.add(sol.getAnswer());
		} catch (NullInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(TimePair tp: timelist){
			System.out.println(tp);
			
		}		
		
		
		
		
		
		
		System.out.println("xxx");
		
	}

}
