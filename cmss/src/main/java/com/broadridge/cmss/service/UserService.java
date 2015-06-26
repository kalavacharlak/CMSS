package com.broadridge.cmss.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.broadridge.cmss.domain.xml.EntitelmentRequest;
import com.broadridge.cmss.domain.xml.PhaseIVEntitlements;
import com.broadridge.cmss.domain.xml.PhaseIVIsActive;
import com.broadridge.cmss.service.adapter.PhaseIVAdapter;
import com.broadridge.cmss.service.adapter.PhaseIVXmlParser;
import com.broadridge.cmss.utilities.Utils;

@Component
public class UserService {
	
	@Autowired
	private PhaseIVAdapter phaseIVAdapter;
	
	private String phaseIVURL = "http://web6test.spsnet.broadridge.com/bpsconn000s/services/PhaseIVSession";	

	
	public boolean isValidToken(String token, String clientNumber) throws Exception{
		if(Utils.getInstance().isNullString(token)
				|| Utils.getInstance().isNullString(clientNumber))
			return false;
		
		PhaseIVIsActive isActiveRequest = new PhaseIVIsActive();
		isActiveRequest.setClientNumber(clientNumber);
		isActiveRequest.setToken(token);
		
		String xml = Utils.getInstance().createXML(isActiveRequest, PhaseIVIsActive.class);
		
		String isValidTokenRequest  = Utils.getInstance().constructPhaseIVXmlRequest(xml);
		
		String response = phaseIVAdapter.processRequest(isValidTokenRequest, phaseIVURL);
		
		PhaseIVXmlParser xmlParser = new PhaseIVXmlParser();
		
		String xmlResponse = xmlParser.parseXml(response);
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^"+ xmlResponse);
		
		return xmlResponse.contains("Success_Response");
		
	}


	public List<String> getEntitlements(String token, String clientNumber) throws Exception {
		if(Utils.getInstance().isNullString(token)
				|| Utils.getInstance().isNullString(clientNumber))
			return null;
		
		PhaseIVEntitlements entitlementRequest = new PhaseIVEntitlements();
		EntitelmentRequest request = new EntitelmentRequest();
		
		request.setClient(clientNumber.substring(1));
		entitlementRequest.setRequest(request);
		entitlementRequest.setClientNumber(clientNumber);
		entitlementRequest.setToken(token);
		
		String xml = Utils.getInstance().createXML(entitlementRequest, PhaseIVEntitlements.class);
		
		String entitlementRequestXML  = Utils.getInstance().constructPhaseIVXmlRequest(xml);
		
		String response = phaseIVAdapter.processRequest(entitlementRequestXML, phaseIVURL);
		
		PhaseIVXmlParser xmlParser = new PhaseIVXmlParser();
		
		String xmlResponse = xmlParser.parseXml(response);
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^"+ xmlResponse);
		
		String entitlement = (String) Utils.getInstance().getXPathValue(xmlResponse, "/ADP_MESSAGE/RESPONSE/Reply");		
		
		List<String> entitlements = new ArrayList<String>();
        int index=0;
        int len = entitlement.length();
        int i=0;
        if(!Utils.getInstance().isNullString(entitlement)){
            while(true){
                if(entitlement.trim().equalsIgnoreCase("END")){
                    break;
                }
                entitlements.add(entitlement.substring(i,i+10).trim());                
                entitlement = entitlement.substring(i+11);
            }
        }
		
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^"+ entitlements);
		return entitlements;
	}
	
	
	
	
}
