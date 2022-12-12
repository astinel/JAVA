package db;

// 배열 클래스
public class Array {
	private Object[] value = new Object[0];
	
	public Array() {
		
	}
	
	// 값 추가
	public void add(Object val) {
		final int C = count();		
		Object[] obj = new Object[C + 1];
		
		for(int a = 0; a < C; a ++)
			obj[a] = value[a];
		
		value = obj;			
	}
	
	// 개수 반환
	public int count() {
		return value.length;
	}
}
