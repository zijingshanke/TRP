package com.fdays.tsms.base.util;

import java.io.StringReader;
import java.util.Iterator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	public static Document readResult(StringBuffer paramStringBuffer) {
		Document localDocument = null;
		StringReader localStringReader = new StringReader(paramStringBuffer
				.toString());
		SAXReader localSAXReader = new SAXReader();
		try {
			localDocument = localSAXReader.read(localStringReader);
		} catch (DocumentException localDocumentException) {
			localDocumentException.printStackTrace();
			System.out.println(localDocumentException.getMessage());
		}
		return localDocument;
	}

	public static String getTextByNode(Document paramDocument, String paramString) {
		String text="";
		Node localNode = paramDocument.selectSingleNode(paramString);
		if(localNode!=null){
			text = localNode.getText();
		}
		return text;
	}

	public static void searchAttribute(Element paramElement) {
		Iterator localIterator = paramElement.attributeIterator();
		while (localIterator.hasNext()) {
			Attribute localAttribute = (Attribute) localIterator.next();
			System.out.println(localAttribute.getName() + " "
					+ localAttribute.getValue());
		}
	}

	public static void searchElement(Element paramElement) {
		int i = 0;
		int j = paramElement.nodeCount();
		while (i < j) {
			Node localNode = paramElement.node(i);
			if (localNode instanceof Element) {
				Element localElement = (Element) localNode;
				searchAttribute(localElement);
				searchElement((Element) localNode);
			}
			++i;
		}
	}

}
