package com.broadridge.cmss.utilities;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class Utils {
	
	private static final Utils instance = new Utils();
	
	private Utils(){
		
	}
	
	public static Utils getInstance(){
		return instance;
	}
	
	
	public boolean isNullString(String str){
		return (str == null || str.trim().length() == 0 || str.equalsIgnoreCase("null"));
	}
	
	
	public String createXML(Object obj, Class cls) throws Exception{
		// create JAXB context and initializing Marshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// for getting nice formatted output
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);

		jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(obj, sw);
		String xml = sw.toString();
		
		System.out.println("Returning XML from createXML =="+ xml);
		
		return xml;
	}
	
	public String constructPhaseIVXmlRequest(String xmlMessage){
		StringBuffer sbf = new StringBuffer();
		sbf.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		sbf.append("	<s:Body>");
		sbf.append("	<processRequest xmlns=\"http://ejbs.com.broadrideg.com\">");
		sbf.append("	<xmlStr>");
		sbf.append("<![CDATA[");
		sbf.append(xmlMessage);
		sbf.append("]]>");
		sbf.append("</xmlStr>");
		sbf.append("</processRequest>");
		sbf.append("</s:Body>");
		sbf.append("</s:Envelope>");
		
		System.out.println("Returning PhaseIVXML from request =="+ sbf.toString());

		
		return sbf.toString();
	}
	
	
	public Object getXPathValue(String message, String expression) throws Exception{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder =  builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(message);
        XPath xPath =  XPathFactory.newInstance().newXPath();
        return xPath.compile(expression).evaluate(xmlDocument);
	}
}
