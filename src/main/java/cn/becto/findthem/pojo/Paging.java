package cn.becto.findthem.pojo;

public class Paging {
	private boolean is_end;
	private boolean is_start;
	private int totals;
	private Integer page;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public boolean isIs_end() {
		return is_end;
	}
	public void setIs_end(boolean is_end) {
		this.is_end = is_end;
	}
	public boolean isIs_start() {
		return is_start;
	}
	public void setIs_start(boolean is_start) {
		this.is_start = is_start;
	}
	public int getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
	}
	
}
