package dynamicprogramming;

public class CheckSwap {

	public CheckSwap() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Edge<String> e1 = new Edge("a",1,2);
		Edge<String> e2 = new Edge("b",2,3);
		
		System.out.println(e1);
		
		Edge<String> t = e1;
		e1 = e2;
		e2 = t;

		System.out.println(e1);
		System.out.print(e2);
		
	}
	public static Edge<String> swap(Edge<String> a1, Edge<String> a2){
		Edge<String> t = a1;
		a1 = a2;
		a2 = t;
		
		a1.Change("c");
		return a1;
	}

}
