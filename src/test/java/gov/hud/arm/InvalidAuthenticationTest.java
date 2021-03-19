package gov.hud.arm;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.hud.arm.service.Ping;

import javax.xml.ws.WebServiceException;

import org.junit.Test;

public class InvalidAuthenticationTest extends ServiceTestBase {
	@Test
	public void testBadUsername() throws Exception {
		Ping ping = new Ping();
		ping.setAgcHcsId(123);
		setupAuthentication(username + "Bad", password);
		try {
			armService.ping(ping);
			fail();
		} catch (WebServiceException e) {
			assertTrue(e.getCause().getCause().getMessage().contains("Server returned HTTP response code: 403"));
		}
	}
	
	@Test
	public void testBadPassword() throws Exception {
		Ping ping = new Ping();
		ping.setAgcHcsId(123);
		setupAuthentication(username, password  + "Bad");
		try {
			armService.ping(ping);
			fail();
		} catch (WebServiceException e) {
			assertTrue(e.getCause().getCause().getMessage().contains("Server returned HTTP response code: 403"));
		}
	}
}
