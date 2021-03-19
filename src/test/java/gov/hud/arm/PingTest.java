package gov.hud.arm;

import static org.junit.Assert.assertNotNull;
import gov.hud.arm.service.Ping;

import org.junit.Test;

public class PingTest extends ServiceTestBase {
	@Test
	public void testGoodCase() throws Exception {
		Ping ping = new Ping();
		ping.setAgcHcsId(123);
		String response = armService.ping(ping);
		assertNotNull(response);
		System.out.println("Response is : " + response);
	}
}
