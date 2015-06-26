package com.broadridge.cmss.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ADP_MESSAGE")
public class PhaseIVEntitlements {
	
	
	private String clientNumber;
	
	@XmlAttribute(name = "Message_id") 
	private String messageId = "WSECL";
	
	
	private String token;
	
	@XmlAttribute(name="Version")
	private String version="";
	
	
	private EntitelmentRequest request;

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

	public void setRequest(EntitelmentRequest request) {
		this.request = request;
	}
	
	@XmlElement(name="REQUEST")
	public EntitelmentRequest getRequest() {
		return request;
	}
}

