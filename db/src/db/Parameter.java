package db;

// 파라미터 문자열 클래스
public class Parameter {
	private final static String E = "";
	private final static String S = " ";
	
	public String separation;
	public String start, end;
	
	private String param = E;
	
	// 기본 파라미터
	public Parameter() {
		this(S, S, S);
	}
	
	// 구분자 초기화
	public Parameter(char separation) {
		this(separation + E);
	}
	
	// 구분자 초기화
	public Parameter(String separation) {
		this(S, separation, S);
	}
	
	// 시작 문자, 구분자, 끝 문자 초기화
	public Parameter(char start, char separation, char end) {
		this(start + E, separation + E, end + E);
	}
	
	// 문자열로 파라미터 초기화
	public Parameter(String start, String separation, String end) {
		this.start = start;
		this.end = end;
		this.separation = separation;
	}
	
	// 파라미터 반환
	public String get() {
		return start + subString() + end;
	}
	
	// 파라미터 한개 추가
	public void add(String param) {
		this.param += param + separation;
	}
	
	// 파라미터 여러개 추가
	public void add(String...params) {
		for(String param : params)
			add(param);
	}
	
	public void add(Object...params) {
		for(Object param : params)
			add(param.toString());
	}
	
	// 최종 파라미터 보정
	private String subString() {
		return param.isBlank() ? param : param.substring(0, param.length() - separation.length());
	}
}
