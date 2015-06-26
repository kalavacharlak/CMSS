package com.broadridge.cmss.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ADP_MESSAGE")
public class PhaseIVIsActive {
	
	
	private String clientNumber;
	
	@XmlAttribute(name = "Message_id") 
	private String messageId = "ISACTIVE";
	
	
	private String token;
	
	@XmlAttribute(name="Version")
	private String version="";
	
	@XmlElement(name="REQUEST")
	private String request;

	public String getClientNumber() {
		return clientNumber;
	}

	@XmlAttribute(name="Client_Number")
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getToken() {
		return token;
	}

	@XmlAttribute(name="Token")
	public void setToken(String token) {
		this.token = token;
	}

		
}

