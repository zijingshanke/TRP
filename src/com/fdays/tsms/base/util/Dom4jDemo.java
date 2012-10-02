package com.fdays.tsms.base.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jDemo {
	private Element tempElement;
	/**
	 * 将String型xml信息构建成XML的document
	 * @param url
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Document createXml(String xml){
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * 获取指定元素的指定子元素名称的值
	 * @param parent
	 * @param childName
	 * @return
	 */
	public String getElementText(Element parent,String childName){
		String value = parent.element(childName).getText();
		return value;
	}
	
	/**
	 * 递归遍历xml的值
	 * @param element
	 */
	@SuppressWarnings("unused")
	private void readXmlValue(Element rootEelement){
		if(rootEelement.elements().size()<1){
			System.out.println(rootEelement.getName()+":"+rootEelement.getText());
		}else{
			for(int i=0;i<rootEelement.elements().size();i++){
				tempElement = (Element) rootEelement.elements().get(i);
				this.readXmlValue(tempElement);							//递归
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {


	}

}
