package db;

// 데이터 클래스
public class Data extends Column{
	private Object value;
	
	// 타입에 맞는 문자열 반환
	public static String toString(Object obj) {
		Type type = Type.getType(obj);
		
		return type == null ? obj.toString() : toString(type, obj);
	}
	
	// 타입에 맞는 형태로 문자열 반환
	private static String toString(Type type, Object obj) {		
		switch(type) {
			case NULL:
				return "NULL";
			case INT:
				return obj + "";
			case CHAR:
				return "'" + obj + "'";
			default:
				return obj.toString();
		}
	}
	
	// 빈 데이터 생성
	public Data() {
		
	}
	
	// 데이터 초기화
	public Data(Object value) {
		set(value);	
	}
	
	// 칼럼 이름과 값 초기화
	public Data(Object value, String id) {
		super(id);
		set(value);
	}
	
	// 데이터 이름과 값 초기화, 별칭 부여 
	public Data(Object value, String id, String name) {
		this(value, id);
		this.name = name;
	}
	
	public Data(Column col) {
		super(col.id, col.name);		
	}
	
	// 값 얻기
	public Object get() {
		return value;
	}
	
	// 데이터 초기화
	public void set(Object value) {
		type = Type.getType(this.value = value);
	}
	
	// 칼럼 = 값
	public String toStrings() {
		String str = name + " = " + toString();
		
		System.out.println(str);
		
		return str;
	}
	
	// 타입에 맞는 형태로 문자열 반환
	public String toString() {
		return toString(type, value);
//		switch(type) {
//			case NULL:
//				return "NULL";
//			case INT:
//				return value + "";
//			case CHAR:
//				return "'" + value + "'";
//		}
//		
//		return value.toString();
	}
	
	// 원본 반환 결정
	public String toString(boolean origin) {
		return origin ? toString() : value + "";
	}
}
