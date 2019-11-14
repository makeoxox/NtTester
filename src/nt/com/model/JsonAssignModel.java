package nt.com.model;
/**
 * json¸³ÖµÄ£ÐÍ
 * 
 * @author kege
 *
 */
public class JsonAssignModel {
	
	private boolean disable;
	
	private String jsonFilePath;
	
	private String valuesFilePath;
	
	private String assignListName;

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public void setJsonFilePath(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}

	public String getValuesFilePath() {
		return valuesFilePath;
	}

	public void setValuesFilePath(String valuesFilePath) {
		this.valuesFilePath = valuesFilePath;
	}

	public String getAssignListName() {
		return assignListName;
	}

	public void setAssignListName(String assignListName) {
		this.assignListName = assignListName;
	}

	
	
	

}
