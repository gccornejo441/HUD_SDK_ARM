package gov.hud.arm;

import static org.junit.Assert.*;
import gov.hud.arm.service.PostCounselorData;
import gov.hud.arm.service.PostSubmissionResponse;
import gov.hud.arm.service.SubmissionHeader50;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;

public class PostCounselorDataTest extends ServiceTestBase {

	@Test
	public void testMissingAgencyName() throws Exception {
		PostCounselorData parameters = new PostCounselorData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testCounselorProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postCounselorData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("agcName}' is expected"));
		}
	}

	@Test
	public void testMissingPassword() throws Exception {
		PostCounselorData parameters = new PostCounselorData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testCounselorProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postCounselorData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("cmsPassword}' is expected"));
		}
	}

	@Test
	public void testBadNamespace() throws Exception {
		PostCounselorData parameters = new PostCounselorData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData/>", "application/octet-stream");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postCounselorData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	} 
	
	@Test
	public void testBadCase() throws Exception {
		PostCounselorData parameters = new PostCounselorData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData xmlns=\"bogusNamespaceCowboy\"/>", "xml");
		
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postCounselorData(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	}
		
	@Test(timeout=600000)
	public void testGoodCase() throws Exception {
		PostCounselorData parameters = new PostCounselorData();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testCounselorProfileData.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		PostSubmissionResponse response = armService.postCounselorData(parameters);
		assertNotNull(response.getSubmissionId());
	}
}
