package com.broadridge.cmss.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import com.broadridge.cmss.service.adapter.PhaseIVAdapter;
import com.broadridge.cmss.service.adapter.PhaseIVXmlParser;
import com.broadridge.cmss.utilities.Utils;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:test-servlet-context.xml")
public class UserServiceTest {
	  @Autowired
	  private UserService userService;

	  @Autowired
	  PhaseIVAdapter phaseIVAdapter;
	  
	  private String phaseIVURL = "http://web6test.spsnet.broadridge.com/bpsconn000s/services/PhaseIVSession";	

	  @Test
	  public void testIsValidTokenNullValues() {
		  try {
			assertFalse(userService.isValidToken(null, null));
		} catch (Exception e) {
			fail("Exception occurred in testIsValidTokenNullValues");
		}
	  }
	  
	  @Test
	  public void testIsValidToken() {
		  try {
			String token = login();
			assertTrue(userService.isValidToken(token, "0096"));
		} catch (Exception e) {
			fail("Exception occurred in testIsValidToken");
		}
	  }

	private String login() {
		String request="<ADP_MESSAGE Client_Number=\"0096\" Message_id=\"LOGON\" Version=\"1.0\" User_id=\"\">   <REQUEST>  <userId>V096P441</userId>  <password>Catherine_12</password>  <RunMessageId>PTPI/PTPI_GETSTATION</RunMessageId>  </REQUEST>  </ADP_MESSAGE>";
		request = Utils.getInstance().constructPhaseIVXmlRequest(request);
		String result = null;
		try {
			
			result = phaseIVAdapter.processRequest(request,phaseIVURL);
			PhaseIVXmlParser xmlParser = new PhaseIVXmlParser();
			String response = xmlParser.parseXml(result);
			String token = response.substring(response.indexOf("<Token>")+7,response.indexOf("</Token>"));
			System.out.println("Token from login - "+ token);
			return token;	
		} catch (Exception e) {
			fail("Exception thrown from processRequest method.");
		}
		
		return null;
	}
	
	@Test
	public void testGetEntitlementsForNullValues(){
		try {
			Assert.isNull(userService.getEntitlements(null,null));
		} catch (Exception e) {
			fail("Exception occurred in getEntitlements");
		}
	}
	
	@Test
	public void testGetEntitlements(){
		try {
			String token = login();
			Assert.notNull(userService.getEntitlements(token,"0096"));
		} catch (Exception e) {
			fail("Exception occurred in getEntitlements");
		}
	}
	  
}
