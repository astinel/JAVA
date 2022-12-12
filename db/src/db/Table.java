package db;

import java.sql.ResultSet;
import java.sql.SQLException;

// 테이블 클래스
public class Table {
	public Column[] column = new Column[0];		// 테이블 칼럼
	public Row[] row = new Row[0];			// 테이블 행
	public String name;			// 테이블 이름
	
	// 빈 테이블 생성
	public Table() {
		
	}
	
	// 테이블 이름 초기화
	public Table(String name) {
		this.name = name;
	}
	
	// 테이블 칼럼 초기화
	public Table(Column...column) {
		this.column = column;
	}
	
	// 테이블 행 초기화
	public Table(Row...rows) {
		for(Row row : rows)
			add(row);
	}
	
	// TODO 칼럼출력하기
	// DB 결과로 초기화
	public Table(ResultSet resultSet) {
		Row row = null;
		Data dat;		
				
		try {
			while(resultSet.next()) {				
				try {
					row = new Row();
					for(int a = 1; true; a++)
					{
						dat = new Data(resultSet.getString(a));
						row.add(dat);
					}
				}catch (Exception e) {
					add(row);					
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 테이블 행 추가
	public void add(Row row) {
		final int C = rowCount();
		Row[] rows = new Row[C + 1];
		
		if(row == null)		return;
		
		for(int a = 0; a < C; a++)
			rows[a] = this.row[a];
		
		rows[C] = row;
		
		this.row = rows;
	}
	
	// 행 개수 반환
	public int rowCount() {
		return row.length;
	}
	
	// 데이터 출력
	public void print() {
		for(Row r : row)
			System.out.println(r);
	}
}
