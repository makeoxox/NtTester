package nt.com.model;

import org.dom4j.Element;

/**
 * xml�ڵ�Ԫ��ģ��
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
