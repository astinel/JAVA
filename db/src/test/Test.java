package test;

import db.Data;
import db.Parameter;
import db.SQL;

public class Test {
	public static void  main(String[] args) {
		final String T = "TB_CALENDAR";
		SQL sql = new SQL();
		Data[] dat = new Data[2];
		Parameter parameter = new Parameter('(', ',' ,')');
		
		dat[0] = new Data("운동");
		//dat[0].name = "TITLE";
		
		dat[1] = new Data(2022);
		
		parameter.add("1", "2");
		System.out.println(parameter.get());
	}
}
