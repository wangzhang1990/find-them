package cn.becto.findthem.pojo;

import java.util.List;

public class ResultData {
	private List<Answer> data;
	private Paging paging;
	public List<Answer> getData() {
		return data;
	}
	public void setData(List<Answer> data) {
		this.data = data;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	
}
