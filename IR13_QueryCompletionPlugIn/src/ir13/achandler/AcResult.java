package ir13.achandler;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for the gestion of results inside the new plugin
 * @author fingolfin
 *
 */
public class AcResult {
	
	/**
	 * Field and content are controls,
	 * ContentList and fieldList are used for storing the results
	 */
	private boolean field;
	private boolean content;
	private String resultField;
	private ArrayList<String> contentList;
	private ArrayList<String> fieldsList;
	
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

	public AcResult(boolean field, ArrayList<String> resultField) {
		super();
		this.field = field;
		this.setFieldsList(resultField);
		prepareFieldsResult();
	}
	/**
	 * Takes the fields results as string and add ":(" at the end
	 */
	private void prepareFieldsResult() {
		Iterator<String> iter = fieldsList.iterator();
		ArrayList<String> newFieldList = new ArrayList<>();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			string = string+":(";
			newFieldList.add(string);
		}
		fieldsList = newFieldList;
		
	}
	public AcResult(boolean content, String resultField, ArrayList<String> contentList) {
		super();
		this.content = content;
		this.setResultField(resultField);
		this.setContentList(contentList);
		fusionResult();
	}
	/**
	 * Fusion the field name and the full content in the result of the query
	 */
	private void fusionResult() {
		Iterator<String> iter = contentList.iterator();
		ArrayList<String> newContentList = new ArrayList<>();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			string = resultField + ":("+string+")";
			newContentList.add(string);
		}
		contentList = newContentList;
	}
	public AcResult(boolean content, boolean field){
		this.content = content;
		this.field = field;
	}
	/**
	 * The result is empty ie: there is no such thing referenced
	 * @return
	 */
	public boolean isEmpty(){
		return !(content || field);
	}
	
	public String getResultField() {
		return resultField;
	}
	public void setResultField(String resultField) {
		this.resultField = resultField;
	}
	public ArrayList<String> getContentList() {
		return contentList;
	}
	public void setContentList(ArrayList<String> contentList) {
		this.contentList = contentList;
	}
	public ArrayList<String> getFieldsList() {
		return fieldsList;
	}
	public void setFieldsList(ArrayList<String> fieldsList) {
		this.fieldsList = fieldsList;
	}
	

}
