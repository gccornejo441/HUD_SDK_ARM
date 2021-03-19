package gov.hud.arm;

import static org.junit.Assert.*;
import gov.hud.arm.service.PostForm9902Data;
import gov.hud.arm.service.PostSubmissionResponse;
import gov.hud.arm.service.SubmissionHeader50;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;

public class PostFom9902DataTest extends ServiceTestBase {

	@Test
	public void testMissingAgencyName() throws Exception {
		PostForm9902Data parameters = new PostForm9902Data();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testForm9902Data.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postForm9902Data(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("agcName}' is expected"));
		}
	}

	@Test
	public void testMissingPassword() throws Exception {
		PostForm9902Data parameters = new PostForm9902Data();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testForm9902Data.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postForm9902Data(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("cmsPassword}' is expected."));
		}
	}

	@Test
	public void testBadNamespace() throws Exception {
		PostForm9902Data parameters = new PostForm9902Data();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData/>", "application/octet-stream");
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postForm9902Data(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	} 
	
	@Test
	public void testBadCase() throws Exception {
		PostForm9902Data parameters = new PostForm9902Data();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = new ByteArrayDataSource("<bogusData xmlns=\"bogusNamespaceCowboy\"/>", "xml");
		
		parameters.setSubmissionData(new DataHandler(dataSource));
		try {
			armService.postForm9902Data(parameters);
			fail();
		} catch (SOAPFaultException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("Bad databag namespace:"));
		}
	}
	
	@Test
	public void testGoodCase() throws Exception {
		PostForm9902Data parameters = new PostForm9902Data();
		SubmissionHeader50 header = new SubmissionHeader50();
		header.setAgcName("Name1");
		header.setCmsPassword("123456");
		parameters.setSubmissionHeader50(header);
		ByteArrayDataSource dataSource = getDataSource("/gov/hud/arm/databag/testForm9902Data.xml");
		parameters.setSubmissionData(new DataHandler(dataSource));
		PostSubmissionResponse response = armService.postForm9902Data(parameters);
		assertNotNull(response.getSubmissionId());
	}
}
