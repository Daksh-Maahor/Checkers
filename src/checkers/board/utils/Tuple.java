package checkers.board.utils;

public class Tuple<A, B> {
	
	private A obj1;
	private B obj2;

	public Tuple(A obj1, B obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	public A get1() {
		return obj1;
	}
	
	public B get2() {
		return obj2;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tuple)) {
			return false;
		}
		
		@SuppressWarnings("unchecked")
		Tuple<A, B> obj = (Tuple<A, B>) o;
		
		if (obj.obj1 == obj1 && obj.obj2 == obj2) {
			return true;
		}
		
		return false;
	}

}
