package db;

import java.util.Date;

// DB타입
public enum Type {
	NULL,
	INT,
	CHAR,
	DATE;
	
	public static Type getType(Object val) {
		if(val == null)			return NULL;
		if(val instanceof Number)	return INT;		
		if(val instanceof String)	return CHAR;
		if(val instanceof Character)return CHAR;
		if(val instanceof Date)		return DATE;
		
		return null;
	}
}
