package com.broadridge.cmss.service.adapter;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:test-servlet-context.xml")
public class PhaseIVXmlParserTest {
	
	@Autowired
	PhaseIVAdapter phaseIVAdapter;
	
	private String phaseIVURL = "http://web6test.spsnet.broadridge.com/bpsconn000s/services/PhaseIVSession";
	
	@Test
	public void testParseNullXML(){
		PhaseIVXmlParser xmlParser = new PhaseIVXmlParser();
		String result = xmlParser.parseXml(null);
		Assert.isNull(result);
	}
	
	@Test
	public void testParseXMLResponseForInvalidToken(){
		String request="<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"> 	<s:Body> 		<processRequest xmlns=\"http://ejbs.com.broadrideg.com\"> 			<xmlStr> <![CDATA[ <ADP_MESSAGE Message_id=\"ISACTIVE\" Client_Number=\"0096\" Token=\"0096S02746615011212767289450127\" Version=\"\"> 	<REQUEST> 	</REQUEST> </ADP_MESSAGE> ]]> 			</xmlStr> 		</processRequest> 	</s:Body> </s:Envelope>";
		String result = null;
		try {
			result = phaseIVAdapter.processRequest(request,phaseIVURL);
			PhaseIVXmlParser xmlParser = new PhaseIVXmlParser();
			String response = xmlParser.parseXml(result);	
			if(!response.contains("Error_Response"))
				fail("Failed PhaseIV Processrequest isValid test. ");	
			
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
	}
	
}
