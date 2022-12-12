package db;

import java.sql.ResultSet;

// 결과 클래스
public class Result {
	public Table table;
	
	private Exception exception;
	private boolean success;
	
	// 결과 생성
	Result(ResultSet res){
		if(res == null) {
			exception = new NullPointerException();
			success = false;
		}
	}
	
	// 성공 여부 반환
	public boolean success() {
		return success;
	}
	
	// 오류 반환
	public String getError() {
		return exception == null ? null : exception.getMessage();
	}
}
