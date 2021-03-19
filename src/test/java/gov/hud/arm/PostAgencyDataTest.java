package gov.hud.arm;

import static org.junit.Assert.*;
import gov.hud.arm.service.PostSubmissionResponse;
import gov.hud.arm.service.PostAgencyData;
import gov.hud.arm.service.SubmissionHeader50;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;

public class PostAgencyDataTest extends ServiceTestBase {

	@Test
	public void testMissingAgencyName() throws Exception {
		PostAgencyData parameters = new PostAgencyData();
		SubmissionHeader50 header = new SubmissionHeader50();
		parameters.setSubmissionHeader50(header);
		header.setCmsPassword("123456");
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testAgencyProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postAgencyData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("agcName}' is expected"));
		}
	}

	@Test
	public void testBadPassword() throws Exception {
		PostAgencyData parameters = new PostAgencyData();
		SubmissionHeader50 header = new SubmissionHeader50();
		parameters.setSubmissionHeader50(header);
		header.setAgcName("Name1");
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testAgencyProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postAgencyData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("cmsPassword}' is expected"));
		}
	}

	@Test
	public void testBadNamespace() throws Exception {
		PostAgencyData parameters = new PostAgencyData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		parameters.setSubmissionHeader50(header);
		header.setCmsPassword("123456");
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData/>", "application/octet-stream");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postAgencyData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	} 
	
	@Test
	public void testBadCase() throws Exception {
		PostAgencyData parameters = new PostAgencyData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData xmlns=\"bogusNamespaceCowboy\"/>", "xml");
		
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postAgencyData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	}
	
	@Test
	public void testGoodCase() throws Exception {
		PostAgencyData parameters = new PostAgencyData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		parameters.setSubmissionHeader50(header);
		header.setCmsPassword("123456");
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testAgencyProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		PostSubmissionResponse response = armService.postAgencyData(parameters);
		assertNotNull(response.getSubmissionId());
	}
}
