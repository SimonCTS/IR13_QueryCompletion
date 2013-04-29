package ir13.achandler;

public class AcRequest {
	public static byte REGULAR = 1;
	public static byte FIELD = 2;
	public static byte SYNTAX = 4;
	
	private String field;
	private String content;
	private byte reqType;
	
	public AcRequest(byte requestType, String field, String content) {
		super();
		reqType = requestType;
		this.field = field;
		this.content = content;
	}
	
	public AcRequest(byte requestType, String field) {
		super();
		reqType = requestType;
		this.field = field;
		this.content = null;
	}
	
	public byte requestType() {
		return reqType;
	}
	
	public boolean isRegularRequest() {
		return (reqType ^ REGULAR) == 0;
	}
	public boolean isFieldRequest() {
		return (reqType ^ FIELD) == 0;
	}
	public boolean isSyntaxRequest() {
		return (reqType ^ SYNTAX) == 0;
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
