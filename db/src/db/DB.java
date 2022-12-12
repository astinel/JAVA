package db;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	//private static PrintStream out = System.out;			//출력을 위한 클래스(System.out 대체)
	private static final String CN = "org.mariadb.jdbc.Driver";		//DB에 연결에 필요한 클래스 이름
	private static final String URL = "jdbc:mariadb://";			//DB연결에 필요한 기본 주소
	private static final String IP = "localhost";			//기본 로컬 아이피
//	private static final int PORT = 3306;			//기본 포트 값
//	private static final String USER = "root";		//기본 유저 이름
	private static final String PWD = "1234";		//기본 비밀번호	

	private Connection connection;			//연결 클래스
	private Statement statement;			//정적 쿼리 클래스
	
	public static void main(String[] args) {
		DB db = new DB("homework");
		ResultSet res;
		SQL sql = new SQL();
		String query;
		Data[] dat = new Data[4];
		
		dat[0] = new Data(2);
		dat[1] = new Data("샤워");
		dat[2] = new Data(2021);
		dat[3] = new Data(7);
		
		dat[0].name = "NUM";
		dat[1].name = "TITLE";
		dat[2].name = "YEAR";
		dat[3].name = "MONTH";
		
		res = db.executeQuery(sql.select("TB_BOOK"));
		new Table(res).print();
		System.out.println();
		
//		db.execute(query);
//		res = db.executeQuery(sql.select(T));
	}
	
	//기본 생성자
	public DB() {
		connect();			//기본 값으로 데이터베이스 연결
	}
	
	// 데이터베이스 선택 연결
	public DB(String db) {
		connect(db);
	}
	
	//기본 값으로 연결
	public void connect() {
		connect(null);
	}
	
	// 데이터 베이스 선택 연결
	public void connect(String db) {
		connect(IP, 3306, db, "root", PWD);
	}
	
	// 설정 값으로 데이터베이스 연결 
	public void connect(String url, String user, String pwd) {
		if(connection != null) close();
	  
		try {
			Class.forName(CN);
			
			connection = DriverManager.getConnection(url, user, pwd);
			statement = connection.createStatement(); 
		} catch (Exception e) { 
			connection = null;
			System.out.println(e);
		}
	}
	
	//아이피, 포트, 데이터베이스, 이름, 비밀번호를 지정해서 연결
	public void connect(String ip, int port, String database, String user, String pwd) {
		String url = URL + ip + ":" + port + "/" + (database != null ? database : "");
		
		connect(url, user, pwd);
	}
	// 실행 결과 반환
	public ResultSet executeQuery(String sql) {
		ResultSet res = null;
		
		System.out.println(sql);
		try {
			res = statement.executeQuery(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	// 실행
	public void execute(String sql) {
		System.out.println(sql);
		try {
			statement.execute(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	//데이터베이스 존재 확인
//	public boolean isExist(String database) {
//		String sql = SQL.showDatabase();
//		
//		executeQuery(sql);
//		
//		return isContain(database);
//	}
//	
//	//테이블 존재 확인
//	public boolean isTable(String table) {
//		String sql = SQL.showTable();
//		
//		executeQuery(sql);
//		
//		return isContain(table);
//	}
//	
//	//테이블 출력
////	public void show() {
////		String title, year, month, day, hour; 
////		
////		out.println();
////		for(int a = 0; a < table.size(); a++) {
////			row = table.get(a);
////
////			title = row.get(1);
////			year = row.get(2);
////			month = row.get(3);
////			day = row.get(4);
////			hour = row.get(5);
////			
////			out.printf("%d: %4s년 %3s월 %3s일 %2s시 %s\n", a + 1, year, month, day, hour, title);
////		}		
////	}
//	
//	//반환 값 있는 쿼리 실행
//	public Table executeQuery(String sql) {
//		try {
//			resultSet = statement.executeQuery(sql);
//		} catch (SQLException e) {
//			out.println("실패");
//			out.println(e.getMessage());
//			out.println(sql);
//		}
//		return new Table(resultSet);
//	}
//	
//	//반환 값 없는 쿼리 실행
//	public void execute(String sql) {
//		try {
//			statement.execute(sql);
//		} catch (SQLException e) {
//			out.println("실패");
//			out.println(e.getMessage());
//			out.println(sql);
//		}
//	}
//	
//	//테이블 데이터 획득
////	public void select(String title, String year, String month, String day) {		
////		String sql = "";
////		String num, hour;
////		
////		sql += SQL.select("TB_CALENDAR") + "\n";		
////		sql += "WHERE TITLE LIKE '%" + title + "%' AND YEAR LIKE '%" + year + "%' AND MONTH LIKE '%" + month + "%' AND DAY LIKE '%" + day + "%'\n";
////		sql += "ORDER BY YEAR, MONTH, DAY";
////		
////		try {
////			resultSet = statement.executeQuery(sql);
////			table = new ArrayList<List<String>>();
////			
////			while(resultSet.next()) {				
////				num 	= resultSet.getString(1);
////				title 	= resultSet.getString(2);
////				year 	= resultSet.getString(3);
////				month	= resultSet.getString(4);
////				day 	= resultSet.getString(5);
////				hour 	= resultSet.getString(6);
////
////				row	= new ArrayList<String>();
////				row.add(num);
////				row.add(title);
////				row.add(year);
////				row.add(month);
////				row.add(day);
////				row.add(hour);
////				
////				table.add(row);
////			}
////			
////		} catch (SQLException e) {
////			out.print(e.getMessage());
////			out.println(sql);
////		}
////	}
//	
//	//테이블 데이터 전체 조회
////	public void select() {
////		select("", "", "", "");
////	}
//	
//	//테이블 데이터 삽입
//	public void insert(String table, Row row) {
//		String sql = SQL.insert(table, row);
//		
//		execute(sql);
//	}
//	
//	//테이블 데이터 삽입(사용 X)
//	public void insert(String title, String year, String month, String day, String hour) {
//		String sql = "";
//		
//		sql += "INSERT TB_CALENDAR VALUES(NULL,'";
//		sql += title + "', " + year + ", " + month + ", " + day + ", " + hour +")";
//		
//		execute(sql);
//	}
//	
//	//테이블 데이터 수정
//	public void update(String table, Row row) {
//		String sql = "";
//		String num = row.getValue(0);
//		 
//		this.row = this.table. get(Integer.parseInt(num) - 1);
//		num = this.row.get(0);
//		row.remove(0);
//		
//		sql += SQL.update(table, row) + "\n";
//		sql += "WHERE NUM = " + num;
//		
//		execute(sql);		
//	}
//	
//	//테이블 데이서 삭제
//	public void delete(int num) {
//		String sql = "";
//		
//		row = table.get(num - 1);
//		num = Integer.parseInt(row.get(0));
//		
//		sql += "DELETE FROM TB_CALENDAR\n";
//		sql += "WHERE NUM = " + num;
//		
//		execute(sql);
//	}
//	
	//소멸될 때 데이터베이스 연결을 닫음
	public void finalize() {		
		close();
	}

	//값 비교
//	private boolean isContain(String str) {			
//		try {
//			while(resultSet.next()) {
//				if(resultSet.getString(1).equals(str))
//					return true;
//			}
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//		return false;
//	}
	
	//데이터베이스 연결 닫음
	private void close() {
		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
}
