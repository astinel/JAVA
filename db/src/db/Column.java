package db;

// 칼럼 클래스
public class Column {
	public String name = "";			// 테이블 칼럼 이름
	
	protected String id = "";			// 데이터베이스 칼럼 이름
	protected Type type = Type.NULL;	// 칼럼 타입
	
	public static String[] desc(Column...col) {
		final int C = col.length;
		String[] param = new String[C];
		
		for(int a = 0; a < C; a++)
			param[a] = col[a].toParam();
		
		return param;
	}
	
	// 데이터들의 칼럼명과 별칭 반환
	public static String[] getIds(Column...col) {
		final int C = col.length;
		String[] ids = new String[C];
		
		for(int a = 0; a < C; a++)
			ids[a] = col[a].id;
		
		return ids;
	}
	
	// 빈 칼럼 생성
	public Column() {
		
	}
	
	// 칼럼명 초기화
	public Column(String name) {
		this.id = this.name = name;
	}
	
	// 
	public Column(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// 칼럼명과 타입 초기화
	public Column(String name, Type type) {
		this(name);
		this.type = type;
	}
	
	// 타입 획득
	public Type getType() {
		return type;
	}
	
	public String toParam() {
		return id + " " + type;
	}
	
	public void print() {		
		System.out.println(type != Type.NULL ? "(" + type + ")" + id : type);
	}
	
	public String toString() {
		return id;
	}
	
	public void as(String name) {
		this.name = name;
	}
}
