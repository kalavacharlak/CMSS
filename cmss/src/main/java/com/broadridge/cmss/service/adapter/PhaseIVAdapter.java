package com.broadridge.cmss.service.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.broadridge.cmss.utilities.Utils;

@Component
public class PhaseIVAdapter {

	private static Logger logger = Logger.getLogger(PhaseIVAdapter.class);

	/**
	 * processRequest processes PhaseIV Web Service calls. It creates a soap
	 * message from input string xmlRequest and invokes Phase IV Web Service
	 * processRequest method.
	 * 
	 * @param PhaseIV
	 *            xmlRequest
	 * @return PhaseIV xmlResponse
	 * @throws Exception
	 */
	public String processRequest(String xmlRequest, String clientURL) throws Exception{
		try {
			if(Utils.getInstance().isNullString(xmlRequest) || Utils.getInstance().isNullString(clientURL))
			{
				logger.debug("Recieved xmlRequest or clientURL as null");
				return null;
			}
			logger.debug("processRequest--> xmlRequest :" + xmlRequest);
			ByteArrayInputStream inStream = new ByteArrayInputStream(
					xmlRequest.getBytes());
			StreamSource source = new StreamSource(inStream);
			MessageFactory msgFactory = MessageFactory.newInstance();
			SOAPMessage sMsg = msgFactory.createMessage();
			SOAPPart sPart = sMsg.getSOAPPart();
			sPart.setContent(source);
			MimeHeaders mimeHeader = sMsg.getMimeHeaders();
			mimeHeader.setHeader("SOAPAction",
					"http://ejbs.phaseiv.bsg.adp.com");
			logger.debug("processRequest-->in PhaseIVClient before call");
			SOAPMessage respMsg = executeCall(sMsg, clientURL);
			logger.debug("processRequest-->in PhaseIVClient after call");
			// SOAPFault faultCode =
			// respMsg.getSOAPPart().getEnvelope().getBody().getFault();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			respMsg.writeTo(outStream);
			logger.debug("processRequest xmlResponse:" + outStream.toString());
			return outStream.toString();
		} catch (Exception exp) {
			logger.error("processRequest --> Error while processing request : "
					+ exp.getMessage());
			logger.error("processRequest --> error xmlRequest:" + xmlRequest);
			exp.printStackTrace();
			throw exp;
		}
	}

	/**
	 * executeCall method intakes SOAP Message, establishes connection to
	 * PhaseIV Web Service to invoke processRequest method and returns response
	 * SOAMPmessage.
	 * 
	 * @param request
	 *            Input SOAPMessage with xmlRequest
	 * @return SOAPMessage with xmlResponse.
	 * @throws Exception
	 */
	private SOAPMessage executeCall(SOAPMessage request, String clientURL)
			throws Exception {

		SOAPMessage respMsg = null;
		SOAPConnection conn = null;

		try {
			conn = SOAPConnectionFactory.newInstance().createConnection();
			logger.debug("clientURL:" + clientURL);
			respMsg = conn.call(request, clientURL);
			logger.debug("executeCall .... completed" + respMsg);
		} catch (Exception exp) {
			logger.error("executeCall -->error while invoking PhaseIV web service:"
					+ exp.getMessage());
			throw exp;
		} finally {
			if(conn != null)
				conn.close();
		}

		return respMsg;
	}
}
