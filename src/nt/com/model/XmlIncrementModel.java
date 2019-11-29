package nt.com.model;
/**
 * xmlµÝÔöÄ£ÐÍ
 * 
 * @author kege
 *
 */
public class XmlIncrementModel {
	
	private boolean disable;
	
	private String type;
	
	private String xmlFilePath;
	
	private String attrname;
	
	private String incrementListName;

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

	public String getIncrementListName() {
		return incrementListName;
	}

	public void setIncrementListName(String incrementListName) {
		this.incrementListName = incrementListName;
	}



}
