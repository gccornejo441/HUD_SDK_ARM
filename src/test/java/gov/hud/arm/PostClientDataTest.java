package gov.hud.arm;

import static org.junit.Assert.*;
import gov.hud.arm.service.PostClientData;
import gov.hud.arm.service.PostSubmissionResponse;
import gov.hud.arm.service.SubmissionHeader50;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;

public class PostClientDataTest extends ServiceTestBase {

	@Test
	public void testMissingAgencyName() throws Exception {
		PostClientData parameters = new PostClientData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testClientProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postClientData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("agcName}' is expected"));
		}
	}

	@Test
	public void testMissingPassword() throws Exception {
		PostClientData parameters = new PostClientData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testClientProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postClientData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("One of '{\"http://service.arm.hud.gov/\":cmsPassword}' is expected"));
		}
	}

	@Test
	public void testBadNamespace() throws Exception {
		PostClientData parameters = new PostClientData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
 		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData/>", "application/octet-stream");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postClientData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	} 
	
	@Test
	public void testBadCase() throws Exception {
		PostClientData parameters = new PostClientData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData xmlns=\"bogusNamespaceCowboy\"/>", "xml");
		
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postClientData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	}
	
	@Test
	public void testGoodCase() throws Exception {
		PostClientData parameters = new PostClientData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testClientProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		PostSubmissionResponse response = armService.postClientData(parameters);
		assertNotNull(response.getSubmissionId());
	}
}
