package gov.hud.arm;

import gov.hud.arm.service.ARMServiceRunner;
import gov.hud.arm.service.ArmService;
import gov.hud.arm.service.ArmServiceImplService;

import java.io.IOException;
import java.util.Map;

import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;

public class ServiceTestBase {

	private ARMServiceRunner runner = new ARMServiceRunner();
	private ClassPathResource resource = new ClassPathResource("gov/hud/arm/wsdl/arm.wsdl");
	protected ArmService armService;
	protected String username = "armUser";
	protected String password = "armPass";

	@Before
	public void b4() throws Exception {
		armService = new ArmServiceImplService(resource.getURL()).getArmServiceImplPort();
		setupAuthentication(username, password);
		runner.startService(username, password, "8001");
	}

	protected void setupAuthentication(String username, String password) throws Exception {
		BindingProvider provider = (BindingProvider) armService;
		Map<String, Object> requestContext = provider.getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8001/ARM/ARM/");
		requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		SOAPBinding binding = (SOAPBinding) provider.getBinding();
		binding.setMTOMEnabled(true);
	}

	protected ByteArrayDataSource getDataSource(String databag) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(databag);
		String string = IOUtils.toString(classPathResource.getInputStream());
		return new ByteArrayDataSource(string, "xml");
	}

	@After
	public void l8er() throws Exception {
		runner.stopService();
	}
}
