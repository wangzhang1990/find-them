package cn.becto.findthem.pojo;

public class Answer {
	private String content;
	private long created_time;
	private long updated_time;
	private Author author;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreated_time() {
		return created_time;
	}
	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}
	public long getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(long updated_time) {
		this.updated_time = updated_time;
	}
	
}
