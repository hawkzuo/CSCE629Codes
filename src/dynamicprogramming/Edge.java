package dynamicprogramming;

public class Edge<T> {
	
	T ch;
	int left;
	int right;	
	public Edge(T item,int from, int to) {
		// TODO Auto-generated constructor stub
		this.ch = item;
		this.left = from;
		this.right = to;
	}
	public String toString(){
		String str = "";
		str +='(';
		str +=left;
		str +=',';
		str +=right;
		str += ',';
		str +=(String)ch;
		str +=')';
		return str;
	}
	
	public void Change(T newitem){
		ch = newitem;
	}
	
	
	
}
