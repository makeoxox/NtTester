package nt.com.model;
/**
 * xml¸³ÖµÄ£ÐÍ
 * 
 * @author kege
 *
 */
public class XmlAssignModel {
	
	private boolean disable;
	
	private String type;
	
	private String xmlFilePath;
	
	private String attrname;
	
	private String valuesFilePath;
	
	private String assignListName;

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public void setXmlFilePath(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
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
