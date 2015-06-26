package com.broadridge.cmss.service.adapter;

import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:test-servlet-context.xml")
public class PhaseIVAdapterTest {
	
	@Autowired
	PhaseIVAdapter phaseIVAdapter;
	
	private String phaseIVURL = "http://web6test.spsnet.broadridge.com/bpsconn000s/services/PhaseIVSession";

	@Test
	public void testProcessRequestforNull(){
		String result = null;
		try {
			result = phaseIVAdapter.processRequest(null,null);
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
		
		Assert.isNull(result);
	}
	
	@Test
	public void testProcessRequestforBlank(){
		String result = null;
		try {
			result = phaseIVAdapter.processRequest("","");
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
		
		Assert.isNull(result);
	}
	
	@Test
	public void testProcessRequestforNullValues(){
		String result = null;
		try {
			result = phaseIVAdapter.processRequest("null","null");
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
		
		Assert.isNull(result);
	}
	
	
	
	@Test
	public void testProcessRequestforInvalidRequest(){
		String request="<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"> 	<s:Body> 		<processRequest xmlns=\"http://ejbs.com.broadrideg.com\"> 			<xmlStr> <![CDATA[ <ADP_MESSAGE Message_id=\"ISACTIVE\" Client_Number Token=\"0096S02746615011212767289450127\" Version=\"\"> 	<REQUEST> 	</REQUEST> </ADP_MESSAGE> ]]> 			</xmlStr> 		</processRequest> 	</s:Body> </s:Envelope>";
		String result = null;
		try {

			result = phaseIVAdapter.processRequest(request,phaseIVURL);
			if(!result.contains("Error_Response"))
				fail("Failed PhaseIV Processrequest isValid test. ");
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
		
		Assert.notNull(result);
	}
	
	
	@Test
	public void testProcessRequestforInvalidphaseIVURL(){
		String request="<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"> 	<s:Body> 		<processRequest xmlns=\"http://ejbs.com.broadrideg.com\"> 			<xmlStr> <![CDATA[ <ADP_MESSAGE Message_id=\"ISACTIVE\" Client_Number=\"0096\" Token=\"0096S02746615011212767289450127\" Version=\"\"> 	<REQUEST> 	</REQUEST> </ADP_MESSAGE> ]]> 			</xmlStr> 		</processRequest> 	</s:Body> </s:Envelope>";
		String result = null;
		try {
			result = phaseIVAdapter.processRequest(request,"http://web6test.spsnet.broadridge.com/bpsconn000s/services");
			if(!result.contains("Error_Response"))
				fail("Failed PhaseIV Processrequest isValid test. ");
		} catch (Exception e) {
			if(!e.getMessage().contains("Bad response: (404Not Found"))
				fail("Un expected exception thrown from processRequest method.");
		}
		
		
	}
	
	
	@Test
	public void testProcessRequestForPhaseIVIsValid(){
		
		String request="<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"> 	<s:Body> 		<processRequest xmlns=\"http://ejbs.com.broadrideg.com\"> 			<xmlStr> <![CDATA[ <ADP_MESSAGE Message_id=\"ISACTIVE\" Client_Number=\"0096\" Token=\"0096S02746615011212767289450127\" Version=\"\"> 	<REQUEST> 	</REQUEST> </ADP_MESSAGE> ]]> 			</xmlStr> 		</processRequest> 	</s:Body> </s:Envelope>";
		String result = null;
		try {
			result = phaseIVAdapter.processRequest(request,phaseIVURL);
			if(!result.contains("Error_Response"))
				fail("Failed PhaseIV Processrequest isValid test. ");
			
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
		
		Assert.notNull(result);
		
	}
	
}
