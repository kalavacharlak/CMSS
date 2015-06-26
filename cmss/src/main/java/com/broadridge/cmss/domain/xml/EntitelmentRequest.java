package com.broadridge.cmss.domain.xml;

import javax.xml.bind.annotation.XmlElement;


public class EntitelmentRequest {
	
	private String action="3";
	private String type="FUNC";
	private String item="";
	private String client;
	private String tableCount="99";
	public String getAction() {
		return action;
	}
	
	@XmlElement(name="Action")
	public void setAction(String action) {
		this.action = action;
	}
	public String getType() {
		return type;
	}
	
	@XmlElement(name="Type")
	public void setType(String type) {
		this.type = type;
	}
	public String getItem() {
		return item;
	}
	@XmlElement(name="Item")
	public void setItem(String item) {
		this.item = item;
	}
	public String getClient() {
		return client;
	}
	
	
	@XmlElement(name="Client")
	public void setClient(String client) {
		this.client = client;
	}
	public String getTableCount() {
		return tableCount;
	}
	
	@XmlElement(name="TableCount")
	public void setTableCount(String tableCount) {
		this.tableCount = tableCount;
	}

}
