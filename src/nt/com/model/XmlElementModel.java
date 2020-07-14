package nt.com.model;

import org.dom4j.Element;

/**
 * xml节点元素模型
 * 
 * @author kege
 *
 */
public class XmlElementModel {

	private Element element;

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	@Override
	public String toString() {
		
		return this.element.getName();
	}
	
	
}
