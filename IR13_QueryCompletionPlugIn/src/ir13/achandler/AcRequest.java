package ir13.achandler;

public class AcRequest {
	private String field;
	private String content;
	
	public AcRequest(String field, String content) {
		super();
		this.field = field;
		this.content = content;
	}
	

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
