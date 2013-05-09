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
	private boolean field = false;
	private boolean content = false;
	private boolean syntax = false;
	private String root;
	private String resultField;
	private ArrayList<String> contentList;
	private ArrayList<String> fieldsList;
	private ArrayList<String> syntaxList;
	
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
	
	public AcResult(boolean syntax, String word, String root){
		syntaxList = new ArrayList<>();
		this.syntax = syntax;
		this.root = root;
		syntaxList.add(root+word+"AND");
		syntaxList.add(root+word+"OR");
	}

	public AcResult(boolean field, ArrayList<String> resultField, String root) {
		super();
		this.field = field;
		this.setFieldsList(resultField);
		this.root = root;
		prepareFieldsResult();
	}
	/**
	 * Takes the fields results as string and add ":(" at the end
	 */
	private void prepareFieldsResult() {
		Iterator<String> iter = fieldsList.iterator();
		ArrayList<String> newFieldList = new ArrayList<String>();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			string = root+string+":(";
			newFieldList.add(string);
		}
		fieldsList = newFieldList;
		
	}
	public AcResult(boolean content, String resultField, ArrayList<String> contentList, String root) {
		super();
		this.content = content;
		this.setResultField(resultField);
		this.setContentList(contentList);
		this.root = root;
		fusionResult();
		
	}
	/**
	 * Fusion the field name and the full content in the result of the query
	 */
	private void fusionResult() {
		Iterator<String> iter = contentList.iterator();
		ArrayList<String> newContentList = new ArrayList<String>();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			string = root + resultField + ":("+string+")";
			newContentList.add(string);
		}
		contentList = newContentList;
	}
	public AcResult(boolean content, boolean field, boolean syntax){
		this.content = content;
		this.field = field;
		this.syntax = syntax;
	}
	/**
	 * The result is empty ie: there is no such thing referenced
	 * @return
	 */
	public boolean isEmpty(){
		return !(content || field || syntax);
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
