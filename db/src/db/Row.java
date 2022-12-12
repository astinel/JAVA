package db;

// 행 클래스
public class Row {
	private Data[] cell = new Data[0];		// 칸
	
	// 빈 행 생성
	public Row() {
		
	}
	
	// 지정한 수 만큼 빈 칸 생성
	public Row(int cou) {
		cell = new Data[cou];
	}
	
	// 지정한 칼럼으로 빈 데이터 생성
	public Row(Column...cols) {
		for(Column col : cols)
			add(col);
	}
	
	// 들어온 데이터로 행 초기화
	public Row(Data...dats) {
		for(Data dat : dats)
			add(dat);
	}
	
	// 데이터 전체 반환
	public Data[] getData() {
		return cell;
	}
	
	// 선택한 인덱스의 데이터 객체 반환
	public Data get(int ind) {
		return checkIndex(ind) ? cell[ind] : null;
	}
	
	// 칼럼명 배열 별칭 없이 반환
	public String[] getColumns() {
		return getColumns(false);
	}
	
	// 칼럼 배열 반환
	public String[] getColumns(boolean setAlias) {
		final int C = cellCount();
		String[] col = new String[C];

		for(int a = 0; a < C; a++)
			col[a] = cell[a].id;
		
		if(setAlias)	setAlias(col);
		
		return col;
	}
	
	// 선택한 인덱스의 데이터 설정
	public void set(int ind, Object val) {
		if(!checkIndex(ind))	return;
		
		cell[ind] = new Data(val);
	}
	
	// 빈 데이터 추가
	public void add(Column col) {
		add(new Data(col));
	}
	
	// 행에 데이터 추가
	// 데이터가 없으면 추가 안됨
	public void add(Data dat) {
		final int C = cell.length;
		Data[] cell = new Data[C + 1];
		
		if(dat == null)		return;
		//if(dat.type == null)	return;
		
		for(int a = 0; a < C; a++)
			cell[a] = this.cell[a];
		
		cell[C] = dat;
		
		this.cell = cell;
//		System.gc();
	}
	
	// 데이터 개수 반환
	public int cellCount() {
		return cell.length;
	}
	
	// 행 데이터 출력
	public String toString() {
		String str = "";
		
		for(Data dat: cell)
			str += dat.toString(false) + "\t";
		
		return str;
	}
	
	
	// 별칭 설정
	private void setAlias(String[] ids) {
		final int C = cellCount();
		
		for(int a = 0; a < C; a++)
			ids[a] += " AS " + cell[a].name;
	}
	
	// 인덱스 유효 확인
	private boolean checkIndex(int ind) {
		if(cell == null)	return false;
		if(ind < 0 || ind >= cell.length)	return false;
		
		return true;
	}
}
