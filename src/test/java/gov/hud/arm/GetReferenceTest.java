package gov.hud.arm;

import static org.junit.Assert.assertNotNull;
import gov.hud.arm.service.GetReference;
import gov.hud.arm.service.GetReferenceResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;

public class GetReferenceTest extends ServiceTestBase {

	@Test
	public void testGoodCase() throws Exception {
		GetReference getReference = new GetReference();
		getReference.setReferenceId(1);
		getReference.setAgcHcsId(0);
		GetReferenceResponse response = armService.getReference(getReference);
		assertNotNull(response);
		System.out.println("Response is : " + ToStringBuilder.reflectionToString(response.getReferenceItem(), ToStringStyle.MULTI_LINE_STYLE));
	}
}
