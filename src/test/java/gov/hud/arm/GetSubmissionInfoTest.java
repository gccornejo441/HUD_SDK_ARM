package gov.hud.arm;

import static org.junit.Assert.*;
import gov.hud.arm.service.GetSubmissionInfo;
import gov.hud.arm.service.GetSubmissionInfoResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;

public class GetSubmissionInfoTest extends ServiceTestBase {

	@Test
	public void testIt() throws Exception {
		GetSubmissionInfo getSubmissionInfo = new GetSubmissionInfo();
		getSubmissionInfo.setAgcHcsId(1);
		getSubmissionInfo.setSubmissionId(123456789);
		GetSubmissionInfoResponse response = armService.getSubmissionInfo(getSubmissionInfo);
		assertNotNull(response);
		System.out.println("Response is : " + ToStringBuilder.reflectionToString(response, ToStringStyle.MULTI_LINE_STYLE));
	}
}
