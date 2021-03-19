package gov.hud.arm.service;

import gov.hud.arm.bus.AttachmentValidationInInterceptor;
import gov.hud.arm.bus.AuthorizationInterceptor;
import gov.hud.arm.bus.MTOMFaultInterceptor;
import gov.hud.arm.bus.MTOMInterceptor;
import gov.hud.arm.bus.ServiceValidationInInterceptor;
import gov.hud.arm.bus.service.ServiceRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ARMServiceRunner {
	
	private ServiceRunner serviceRunner = new ServiceRunner();

	public void startService(String username, String password, String port) throws Exception {
		
		serviceRunner.createService(new ArmServiceImpl(), "http://localhost:" + port + "/ARM/ARM/");
		
		AuthorizationInterceptor authInterceptor = new AuthorizationInterceptor();
		HashMap<String, String> users = new HashMap<String, String>();
		users.put(username, password);
		authInterceptor.setUsers(users);
		ServiceValidationInInterceptor validationInInterceptor = new ServiceValidationInInterceptor();
		validationInInterceptor.setXsdResourcePath("/gov/hud/arm/wsdl/arm-internal.xsd");
		AttachmentValidationInInterceptor attachmentValidationInInterceptor = new AttachmentValidationInInterceptor();
		Map<String, String> xsdResourceMap = new HashMap<String, String>();
		xsdResourceMap.put("http://gov.hud.arm/arm_databag_3_1", "/gov/hud/arm/databag/arm_databag_3_1.xsd");
		xsdResourceMap.put("http://gov.hud.arm/arm_databag_3_0", "/gov/hud/arm/databag/arm_databag_3_0.xsd");
		xsdResourceMap.put("http://gov.hud.arm/agency_profile_databag_5_0", "/gov/hud/arm/databag/agency_profile_databag_5_0.xsd");
		xsdResourceMap.put("http://gov.hud.arm/client_profile_databag_5_0", "/gov/hud/arm/databag/client_profile_databag_5_0.xsd");
		xsdResourceMap.put("http://gov.hud.arm/counselor_profile_databag_5_0", "/gov/hud/arm/databag/counselor_profile_databag_5_0.xsd");
		xsdResourceMap.put("http://gov.hud.arm/form_9902_databag_5_0", "/gov/hud/arm/databag/form_9902_databag_5_0.xsd");
		attachmentValidationInInterceptor.setXsdResourceMap(xsdResourceMap);
		
		//In interceptors
		serviceRunner.addInInterceptor(new LoggingInInterceptor());
		serviceRunner.addInInterceptor(validationInInterceptor);
		serviceRunner.addInInterceptor(attachmentValidationInInterceptor);
		serviceRunner.addInInterceptor(authInterceptor);
		
		//Out interceptors
		serviceRunner.addOutInterceptor(new LoggingOutInterceptor());
		serviceRunner.addOutInterceptor(new MTOMInterceptor());
		
		//OutFault interceptors
		serviceRunner.addOutFaultInterceptor(new MTOMFaultInterceptor());
		
		serviceRunner.startService();
	}

	public void stopService() throws Exception {
		serviceRunner.stopService();
	}

	public static void main(String[] args) throws Exception {
		if (args.length >= 3) {
			String username = args[0];
			String password = args[1];
			String port = args[2];

			ARMServiceRunner serviceRunner = new ARMServiceRunner();
			try {
				System.out.println("Starting service... ");
				String[] files = new String[] { "META-INF/cxf/cxf.xml", "META-INF/cxf/cxf-extension-soap.xml", "META-INF/cxf/cxf-extension-http-jetty.xml" };
				new ClassPathXmlApplicationContext(files);
				serviceRunner.startService(username, password, port);
				System.out.println("Service started. Type 'exit' to shutdown the service.");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String line;
				try {
					do {
						Thread.sleep(500);
						line = reader.readLine();
					} while (!"exit".equals(line));
				} finally {
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Shutting down service...");
				serviceRunner.stopService();
				System.out.println("Service shutdown");
				System.exit(0);
			}
		} else {
			System.out.println("Usage: <username> <password> <port>");
		}
	}
}
