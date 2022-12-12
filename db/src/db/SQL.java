package db;

// 쿼리문 생성
public class SQL {	
	private final static String SEP = ", ";		// 공통 구분자
	private String sql;

//	SELECT SCHEMA_NAME AS 'DATABASE' FROM SCHEMATA ORDER BY SCHEMA_NAME DESC
	public static void main(String[] args) {
		DB db = new DB();
		final String T = "TB_CALENDAR";
		SQL sql = new SQL();
		Data[] dat = new Data[3];
		Row row;
		String query;
		
		dat[0] = new Data(0, "NUM", "ID");
		dat[1] = new Data("title", "TITLE", "NAME");
		dat[2] = new Data(2000, "YEAR");
		
		query = sql.select(T) + sql.limit(1, 5);
		
		db.select(db.executeQuery(query));
	}
	
	// 전체 조회
	// SELECT * FROM TABLE
	public String select(String tab) {
		return select(tab, "*");
	}
	
	// 선택 조회
	// SELECT COL1, ... FROM TABLE
	public String select(String tab, String...col) {
		return set("SELECT", col) + from(tab);
	}
	
	// 선택 칼럼 별칭으로 조회
	// SELECT COL1 AS NM1, COL2 AS NM2, ... FROM TABLE
	public String select(String tab, Column...col) {
		return select(tab, new Row(col).getColumns(true));
	}
	
	// 행 데이터와 똑같은 칼럼 형식으로 조회
	// SELECT COL1 AS NM1, COL2 AS NM2, ... FROM TABLE
	public String select(String tab, Row row) {		
		return select(tab, row.getColumns(true));
	}
	
	// 전체 칼럼 값 추가(VALUES문 별도 필요)
	// INSERT TABLE VALUES(VAL1,...)
	public String insert(String tab, Object...val) {
		return insert(tab) + values(val);
	}
		
	// 선택 삽입(VALUES문 별도 필요)
	// INSERT TABLE(COL1,...)
	public String insert(String tab, String...col) {
		return insert(tab) + param(col);
	}

	// 선택 삽입(완성문) 
	// INSERT TABLE(COL1,...) VALUES(VAL1,...)
	public String insert(String tab, Data...dat) {
		sql = insert(tab) + param(Data.getIds(dat));
		sql += values((Object[])dat);

		return sql;
	}
	
	// 새로운 행 데이터 추가
	// INSERT TABLE(COL1,...) VALUES(VAL1,...)
	public String insert(String tab, Row row) {
		return insert(tab, row.getData());
	}
	
	// 값 1개 수정
	// UPDATE TABLE SET COL = VAL
	public String update(String tab, String col, Object val) {
		sql = update(tab);		
		sql += " SET " + set(col, val);
		
		return sql;
	}
	
	// 선택 값 수정
	// UPDATE TABLE SET COL1 = VAL1, ...
	public String update(String tab, Data...dat) {
		Parameter param = new Parameter(SEP);
		String str;
		
		sql = update(tab) + " SET";		
		for(Data d : dat) {
			str = set(d);
			param.add(str);
		}		
		return sql + param.get();
	}
	
	// 선택 값 수정
	// UPDATE TABLE SET COL1 = VAL1, ...
	public String update(String tab, Row row) {				
		return update(tab, row.getData());
	}
	
	// 테이블 데이터 삭제
	// DELETE TABLE
	public String delete(String tab) {
		return "DELETE " + tab;
	}
	
	// 테이블 데이터 선택 삭제
	// DELETE TABLE WHERE COL1 = VAL1 AND COL2 = VAL2
	public String delete(String tab, Data...con) {
		return delete(tab) + where(con);
	}

	// 테이블 데이터 선택 삭제
	// DELETE TABLE WHERE COL1 = VAL1 AND COL2 = VAL2
	public String delete(String tab, Row row) {
		return delete(tab, row.getData());
	}
	
	// 조건
	// WHERE COL = VAL
	public String where(String col, Object val) {
		return " WHERE " + set(col, val);
	}
	
	// 조건 추가
	// WHERE COL1 = VAL1 AND COL2 = VAL2,...
	public String where(Data...con) {
		Parameter param = new Parameter(" WHERE ", " AND ", " ");
		
		for(Data c : con)
			param.add(set(c));
		
		return param.get();
	}
	
	// 조건 추가
	// WHERE COL1 = VAL1 AND COL2 = VAL2,...
	public String where(Row row) {
		return where(row.getData());
	}
	
	// 추가 조건
	// AND COL = VAL
	public String and(String col, Object val) {
		return " AND " + set(col, val);
	}
	
	// 추가 조건
	// AND COL = VAL
	public String and(Data con) {
		return " AND " + set(con);
	}
	
	// 칼럼 기준으로 묶음
	// GROUP BY COL1, COL2,...
	public String group(String...col) {
		return " GROUP BY" + set(col);
	}
	
	// 칼럼 기준으로 묶음
	// GROUP BY COL1, COL2,...
	public String group(Column...col) {
		Parameter param = new Parameter(SEP);
		
		param.add((Object[])col);
		
		return " GROUP BY " + param.get();
	}
	
	// 칼럼 기준으로 선택 정렬
	// ORDER BY COL (ASC OR DESC)
	public String order(String col, boolean asc) {
		return " ORDER BY " + col + (asc ?  " ASC" : " DESC");
	}
	
	// 칼럼 기준으로 정렬
	public String order(String...col) {
		return " ORDER BY " + set(col);
	}
	
	// 묶은 데이터에 조건
	// HAVING COL1 = VAL1
	public String having(Data...dat) {
		Parameter param = new Parameter("HAVING " , " AND ", "");
		
		for(Data d : dat)
			param.add(set(d));
		
		return param.get();
	}
	
	// 묶은 데이터에 조건
	// HAVING COL1 = VAL1
	public String having(Row row) {
		return having(row.getData());
	}
	
	// 개수 제한
	// LIMIT COUNT
	public String limit(int cou) {
		return limit(0, cou); 
	}
	
	// 시작 번호 부터 개수 제한
	public String limit(int sta, int cou) {
		return " LIMIT " + sta + ", " + cou;
	}
	
	// 데이터 베이스 생성
	// CREATE DATABASES DB_NAME
	public String createDatabase(String name) {
		return create("DATABASES", name);
	}
	
	// 테이블 생성
	// CREATE TABLE TABLE_NAME(COLUMN_NAME COLUMN_TYPE, ...)
	public String createTable(String name, Column...col) {
		sql = create("TABLE", name);
		sql += param(Column.desc(col));		
		
		return sql;
	}

	// 테이블 생성
	// CREATE TABLE TABLE_NAME(COLUMN_NAME COLUMN_TYPE, ...)
	public String create(String name, Row row) {
		return createTable(name, row.getData());
	}

	// 사용 데이터베이스 변경
	public String use(String db) {
		return "USE " + db;
	}
	
	// 데이터베이스 목록 조회
	public String showDatabase() {
		return show("DATABASES");
	}
	
	// 테이블 목록 조회
	public String showTable() {
		return show("TABLES");
	}
	
	// 테이블 정보 조회
	public String desc(String tab) {
		return "DESC " + tab;
	}
	
	// 데이터베이스 삭제
	// DROP DATABASE DB_NAME
	public String dropDatabase(String name) {
		return drop("DATABASES", name);
	}
	
	// 테이블 삭제
	// DROP TABLE TABLE_NAME
	public String drop(String name) {
		return drop("TABLE", name);
	}
	
	// 값 나열
	public String values(Object...val) {
		final int C = val.length;
		Parameter param = new Parameter('(', ',', ')');
		String sql = " VALUES";
		
		param.separation = SEP;
		
		for(int a = 0; a < C; a++)
			param.add(value(val[a]));
		
		return sql + param.get();
	}
	
	
	// 타입에 맞는 값 반환
	private String value(Object val) {
		return Data.toString(val);
	}
	
	// 칼럼 나열
	private String param(String...col) {		
		return "(" + set(col).trim() + ")";
	}
	
	// 칼럼 나열
	private String set(String...col) {
		Parameter param = new Parameter(SEP);
		
		param.add(col);
		
		return param.get();
	}
	
	// 칼럼 = 값 나열
	private String set(String col, Object val) {
		return col + " = " + value(val);
	}
	
	// 쿼리 + 파라미터
	private String set(String que, String...col) {
		return que + set(col);
	}
	
	// 칼럼 = 값
	// COL = VAL
	private String set(Data dat) {		
		return dat != null ? set(dat.name, dat.get()) : null;
	}
	
	// 칼럼 = 값,...
	// COL1 = VAL1, COL2 = VAL2
	private String set(Data...dat) {
		Parameter param = new Parameter(SEP);
		String s;
		
		for(Data d : dat) {
			s = set(d);
			if(s != null)		param.add(s);
		}
		
		return param.get();
	}
	
	// INSERT TABLE
	private String insert(String tab) {
		return "INSERT " + tab;
	}
	
	// UPDATE TABLE
	private String update(String tab) {
		return "UPDATE " + tab;
	}
	
	// FROM TABLE
	private String from(String tab) {
		return "FROM " + tab;
	}
	
	// CREATE (DATABASE OR TABLE)
	private String create(String type, String name) {
		return "CREATE " + type + " " + name;
	}
	
	private String drop(String type, String name) {
		return "DROP " + type + " " + name;
	}
	
	// 데이터베이스 또는 테이블 조회
	// SHOW (DATABASES OR TABLES)
	private String show(String type) {
		return "SHOW " + type;
	}
	
	// 쿼리 설정
	private String query(String...word) {
		Parameter param = new Parameter("", " ", " ");
		
		param.add(word);
		
		return param.get();
	}
}
