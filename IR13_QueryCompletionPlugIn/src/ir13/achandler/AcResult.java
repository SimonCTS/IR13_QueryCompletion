package ir13.achandler;

public class AcResult {
	
	private boolean field;
	private boolean content;
	
	private String resultField;
	private String resultContent;
	
	public boolean isField() {
		return field;
	}
	public void setField(boolean field) {
		this.field = field;
	}
	public boolean isContent() {
		return content;
	}
	public void setContent(boolean content) {
		this.content = content;
	}
	public String getResultField() {
		return resultField;
	}
	public void setResultField(String resultField) {
		this.resultField = resultField;
	}
	public String getResultContent() {
		return resultContent;
	}
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}
	public AcResult(boolean field, String resultField) {
		super();
		this.field = field;
		this.resultField = resultField;
	}
	public AcResult(boolean content, String resultField, String resultContent) {
		super();
		this.content = content;
		this.resultField = resultField;
		this.resultContent = resultContent;
	}
	
	

}
