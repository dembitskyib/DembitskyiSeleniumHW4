package com.epam.lab.parsers;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLParser {
	private DocumentBuilder documentBuilder;
	private Document document;

	public XMLParser(String XMLPath) {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(XMLPath);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String tagName) {
		return document.getElementsByTagName(tagName).item(0).getTextContent();
	}
}
