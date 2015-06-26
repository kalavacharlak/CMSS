package com.broadridge.cmss.service.adapter;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PhaseIVXmlParser extends DefaultHandler{
	
	private CharArrayWriter contents = new CharArrayWriter();
	private String returnContent;
	private static Logger logger = Logger.getLogger(PhaseIVXmlParser.class);
	
	public static final String RESPONSE_TAG_NAME = "p829:processRequestReturn";

	
	/**
	 * 
	 */
	public PhaseIVXmlParser() {
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		contents.reset();
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equalsIgnoreCase(RESPONSE_TAG_NAME)){
			returnContent = contents.toString();
		}
		contents.reset();
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
			contents.write(ch,start,length);
	}

	public String parseXml(String xmlRes){
		try{
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			sp.parse(new InputSource(new ByteArrayInputStream(xmlRes.getBytes("UTF-8"))),this);
			
		}catch(Exception exp){
			logger.error("Error while parsing p829:processRequestReturn : " + exp.getMessage());
			exp.printStackTrace();
		}
		return returnContent;
	}
}
