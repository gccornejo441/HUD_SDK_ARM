ARM-SDK version 1.50


TABLE OF CONTENTS

1. INSTALLATION REQUIREMENTS
2. NOTES
3. CONTENTS
4. INSTALLATION
5. RUNNING THE TESTS
6. RUNNING THE "MOCK" ARM SERVICE IN STANDALONE MODE
7. TESTING WITH SOAP-UI
8. USING .NET WITH THE ARM SERVICE
    8a. USING .NET WITH THE ARM SERVICE (Visual Studio 2005)
    8b. USING .NET WITH THE ARM SERVICE (Visual Studio 2008)


1. INSTALLATION REQUIREMENTS: 

	This initial distribution demonstrates connectivity to ARM for Java and .NET clients.
	For .NET clients, please see the section 9 on using .NET with the ARM service.

	For Java clients, the installation instruction assume you are using the Eclipse IDE,
	but can be imported into any development environment.
	
	Tested with Java 1.5.


2. NOTES:

	ARM is implemented using the Apache CXF Services Framework. CXF is an easy to use
	framework for developing web services and clients of web services. More 
	information can be found at http://cxf.apache.org/.

	Each Test Case starts up a "mock" ARM Service that behaves like the real ARM
	service up to the point of invoking the actual service method. This means that
	all validation errors that you may experience with the service or the ARM
	Databag are handled by this "mock" service. Stricter validation errors may be 
	enforced with subsequent releases of the XSD, but changes to the ARM databag XML 
	structure will be minimal.

	Any suggestions on how to make this SDK better are greatly appreciated. Please
	try to follow the examples and feel free to copy the source code.


3. CONTENTS:

->	CHANGES.txt		- SDK version change list.
->	README.txt      	- This file.
-> 	src/main/java
	-> gov.hud.arm.service 	- Code used to run the "mock" ARM service.

->  	src/test/java
	-> gov.hud.arm		- Test code used to invoke the ARM service.
	
-> 	src/main/resources
	-> referenceData.txt	- Sample reference data to be returned by the real ARM service
	-> referenceGroupNames.txt	- Sample reference group names to be returned by the real ARM service
	-> gov.hud.arm.databag	- XSDs for the ARM databag
	-> gov.hud.arm.wsdl	- The ARM WSDL files
	-> gov.hud.arm.xsd	- SOAP 1.1 and 1.2 XSD files
	
->	lib			- All the dependency jars needed to run the example code.

-> 	arm.jar			- The jar file used to run the standalone "mock" ARM service.


4. INSTALLATION:
	
	Tested with Eclipse Ganymede (version 3.4.0).

	1) Unpack the distribution ZIP into your eclipse workspace. For example,
	   if your eclipse workspace is C:/sb, then you would unpack the SDK into
	   C:/sb/arm.
	2) In Eclipse, select "File" -> "New" -> "Java Project".
	3) Enter "arm" as the Project Name and click Finish. For example, after 
	   typing in "arm", the directory listed should be C:/sb/arm.
	4) Right-click on the "arm" project and select "properties" at the bottom.
	5) Click on the "Java Build Path" and click the "Source" tab.
	6) Click the "Add Folder" button and choose the src/main/resources folder.
	   If not already added as a source folder, also add the src/main/java 
	   folder.
	7) Click on the "Libraries" tab, select the arm.jar and click "Remove".
	8) Click "OK". You should have three source folders in the eclipse project: 
	   src/main/java, src/main/resources, and src/test/java.


5. RUNNING THE TESTS:

	Right click on the gov.hud.arm package in src/test/java and select
	"Run As -> JUnit Test"

	If all is working well, the test bar should turn green. The console will show 
	various output from both the client and server, some of which includes the actual
	SOAP messages being exchanged.
	
	If the tests are failing to run, make sure you followed step 6 in section 4 of 
	this README.
 

6. RUNNING THE "MOCK" ARM SERVICE IN STANDALONE MODE:

	Run the arm.jar file using the following command:

		java -jar arm.jar <user> <password> <port>
	
	Parameters:

		<user> 		- the username your client will use to connect to the "mock" service
		<password> 	- the password your client will use to connect to the "mock" service
		<port> 		- the port on which the "mock" service will listen
	

7. TESTING WITH SOAP-UI

	SOAP-UI is a tool used to test webservices independent of any code written in
	Java, .NET, or other languages. This is a good way to test that
	the real or "mock" ARM Service is responding to requests. Downloads and
	documentation are available at:

	http://www.soapui.org/

	To get started, follow these steps.

  1.	From the left-hand menu, right-click "Projects" and select "New soapUI Project".
  2.	In the "New soapUI Project" dialog box, enter the following:
      	a. Project Name: Provide your own meaningful name
      	b. Initial WSDL/WADL: enter either
          i) 	The URL to the WSDL from the real or "mock" ARM Service (For instructions
              on starting the "mock" ARM Service, please see section 7.) For example,
              if the "mock" service was started on port 8081, the URL to the service
              would be http://localhost:8081/ARM/ARM?WSDL.
          ii) The location on disk that the wsdl is located at in the unpacked distribution.
              For example, if the distribution was unpacked into the c:/temp directory, the
              URL would be c:\temp\arm\src\main\resources\gov\hud\arm\wsdl\arm.wsdl.
      	c. Click "Ok"
  3.  	A new project with the given name will appear in the left-hand menu.
  4.	Double-click the XXXServiceSoapBinding to open its definition where "XXX" is given project name.
  5.	Select the “Service Endpoints” from top menu.
  6.	Define an endpoint for your web service. The endpoint value should be the URL for the real or "mock" ARM service
      	specified in step 2 without the "?WSDL", or specifically http://<hostname>:<portNumber>/ARM/ARM
  7.  	If using real ARM service, provide Username and Password values (as provided to you by ARM Development Team).
  8.	Select “Assign” to assign this endpoint to all requests. From “Assign Endpoint” dialog box, select “- All Requests -“. Click “Ok”.
  9.	Select "+" next to the method you would like to invoke. For this example, we will use "ping".
  10.	Double-click the “Request 1” tree item.
  11.	In the Request 1 window that opens on the right, replace the "?" with the Agency HCS id (a 5-digit value starting with an 8 or 9 issued by HUD).
  12.	Select the  Green arrow on the top-left of the “Request 1” Panel and this should send your request to the web service. 
  13. 	The results will be displayed on the right side of this panel.
 	
	For postXXXXData (where XXXX = Agency, Counselor, Client or Form9902) request, you need to attach your databag to the request. 
	This is acheived as follows:
  
  1.	Select "+" next to "postXXXXData"
  2.	Double-click "request1" and the right-hand panel should display outgoing SOAP message for you to fill in.
  3.	Provide data for the submissionHeader element in the SOAP body.
  4.	Down below, select the “Attachments(0)” tab.
  5.	Select the “+” icon at the top of this tab.
  6.	Navigate to a relevant test XML File provided in the SDK. Select it.
  7.	Select the drop-down box underneath the “Part” tab. Select the 12 digit number which will insert the file as a SOAP attachment.
  8.	Select the  Green arrow on the top-left of the “Request 1” Panel and this should send your request to the web service. 
  
	Please note that you will need to turn on preemptive authentication. This is done as follows:
	
	1. Select File/Preferences.
	2. In the soapUI Preferences dialog box, select the "Authenticate Preemptively" checkbox with the description 
     	   "Adds authentication information to outgoing request".
  	3. Select OK.

8. USING .NET WITH THE ARM SERVICE

	Be sure to use the correct set of instructions. The first set is for Visual Studio 2005 which is incomplete as it 
	does not show how to invoke getSubmissionInfo. The second set is newly added and is for Visual Studio 2008 and does 
	show how to invoke getSubmissionInfo. 

	NOTE:	To solve a network timeout issue when submitting large files (> 5 MB), it is necessary to pre-authenticate with the
		server. This is acheived by setting an HTTP header to Preemptively Authenticate. Read and follow the instructions here:
		http://www.tino.nl/index.php/2009/11/20/implementing-a-wcf-soap-client-that-uses-basic-authentication-at-first-request/

8a. USING .NET WITH THE ARM SERVICE (Visual Studio 2005)
	The following documentation uses Microsoft Visual Studio 2005 with the
	Web Services Enhancement (WSE) 3.0 for Microsoft .NET. WSE 3.0 can be obtained
	from:

	http://www.microsoft.com/downloads/details.aspx?FamilyID=018a09fd-3a74-43c5-8ec1-8d789091255d&displaylang=en

	This serves as a sample program used to connect to the real or "mock" ARM Service and assumes you are sending the submission
	as TEXT/XML. The postSubmission method also accepts ZIP'ed and GZIP'ed submission files.
	In this particular example, we are using C# for our sample application.

	1. Unpack the distribution ZIP into a directory.
	2. Open Visual Studio 2005
	3. Click on the File menu at the top and select New -> Project
	4. Select Visual C# -> Windows
	5. Select Console Application from the Visual Studio installed templates on the right.
	6. Enter SampleArmClient as the name of the project
	7. After Visual Studio opens the newly created project, right-click on SampleArmClient in the 
		Solution Explorer and select "WSE Settings 3.0"
	8. On the General tab, select the checkbox marked "Enable this project for Web Services
		Enhancements" and click OK.
	9. In the "WSE Settings 3.0" menu, select the "Messaging" tab and turn on MTOM for the 
		"Client Mode"
	10. Right-click the References folder underneath SampleArmClient in the Solution Explorer and
		select "Add Web Reference"
	11. In the box labeled URL enter either:
		a) 	The URL to the WSDL from the real or "mock" ARM Service (For instructions
			on starting the "mock" ARM Service, please see section 7.) For example,
			if the "mock" service was started on port 8081, the URL to the service
			would be http://localhost:8081/ARM/ARM?WSDL. Please note that the URL to be used from within 
			your code is http://<hostname>:<portNumber>/ARM/ARM. It does not contain the "?WSDL"- that 
			suffix is strictly for discovering the web service.
		b) 	The location on disk that the wsdl is located at in the unpacked distribution.
			For example, if the distribution was unpacked into the c:/temp directory, the
			URL would be c:\temp\arm\src\main\resources\gov\hud\arm\wsdl\arm-inlined.wsdl.
			(This is the version of WSDL with the schema included in it so that .NET can
			resolve the references without an external schema.)
	12. Click the "Show All Files" button at the top of the Solution Explorer. Open the 
		reference.cs file and change the "Microsoft.Web.Services3.WebServicesClientProtocol" to
		"System.Web.Services.Protocols.SoapHttpClientProtocol". This is a workaround for a bug
		in .NET where it cannot handle SoapFault responses correctly.
	13. Replace the contents of the Program.cs class with the code listed below.
		This example is set to run against the local "mock" ARM service on port 8081,
		with the username set to "dotnet" and password set to "user", but could be
		altered to run against the real service by altering these parameters.
	14. The call to "postAgencyData" will fail because the contents of the databag is not valid.
		Once you provide the databag contents that validates against agency_profile_databag_5_0.xsd, 
		then the service will not report an error. The other three methods (postCounselorData, postClientData and postForm9902Data)
		expect the databag contents to validate against counselor_profile_databag_5_0.xsd, client_profile_databag_5_0.xsd and 
		form_9902_databag_5_0.xsd, respectively.
	
------------------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Text;
using SampleArmClient.Arm;
using System.Net;
using System.IO;

namespace SampleArmClient
{
    class Program
    {
        static void Main(string[] args)
        {
            ArmServiceServiceWse armService = new ArmServiceServiceWse();
            armService.Url = "http://localhost:8081/ARM/ARM/";

            NetworkCredential credentials = new NetworkCredential();
            credentials.UserName = "dotnet";
            credentials.Password = "user";
            armService.Credentials = credentials;

            doPing(armService);
            doGetReference(armService);
            doPostSubmission(armService);            
        }

        private static void doGetReference(ArmServiceServiceWse armService)
        {
            getReference getReference = new getReference();
            getReference.agcHcsId = 1;
            getReference.referenceId = 0;
            getReferenceResponseReferenceItem[] referenceItems = armService.getReference(getReference);
            foreach (getReferenceResponseReferenceItem referenceItem in referenceItems)
            {
                Console.WriteLine(referenceItem.id);
                Console.WriteLine(referenceItem.name);
                Console.WriteLine(referenceItem.longDesc);
                Console.WriteLine(referenceItem.shortDesc);
            }
        }

        private static void doPing(ArmServiceServiceWse armService)
        {
            ping pingIn = new ping();
            pingIn.agcHcsId = 99;
            string pingString = armService.ping(pingIn);
            Console.WriteLine(pingString);
        }

        private static void doPostAgencyData(ArmServiceServiceWse armService)
        {
            postAgencyData postAgencyData = new postAgencyData();
            submissionHeader50 header = new submissionHeader50();
            header.agcHcsId = 80000;
            header.agcName = "test";
            header.systemName = 8;
            header.subFlag = 0;
            postSubmission.submissionHeader50 = header;

            FileStream file = new FileStream("testAgencyProfileData.xml", FileMode.Open, FileAccess.Read);
            StreamReader sr = new StreamReader(file);
            string str = sr.ReadToEnd();

            System.Text.ASCIIEncoding encoding = new System.Text.ASCIIEncoding();
            postAgencyData.submissionData = encoding.GetBytes(str);
            
            armService.postAgencyData(postAgencyData);
            Console.WriteLine("submitted Agency data");
        } 
------------------------------------------------------------------------------------

8b. USING .NET WITH THE ARM SERVICE (Visual Studio 2008)

	The following documentation uses Microsoft Visual Studio 2008.
	 
  	NOTE: To solve a network timeout issue when submitting large files (> 5 MB), it is necessary to pre-authenticate with the
		server. This is acheived by setting an HTTP header to Preemptively Authenticate. Read and follow the instructions here:
		http://www.tino.nl/index.php/2009/11/20/implementing-a-wcf-soap-client-that-uses-basic-authentication-at-first-request/

	This serves as a sample program used to connect to the real or "mock" ARM Service and assumes you are sending the submission
	as TEXT/XML. The postSubmission method also accepts ZIP'ed and GZIP'ed submission files.
	In this particular example, we are using C# for our sample application.

	1. Unpack the distribution ZIP into a directory.
	2. Open Visual Studio 2008
	3. Click on the File menu at the top and select New -> Project
	4. Select Visual C# -> Windows
	5. Select Console Application from the Visual Studio installed templates on the right.
	6. Enter SampleArmClient as the name of the project
	7. After Visual Studio opens the newly created project, right-click on SampleArmClient in the 
      	   Solution Explorer and select "Add Service Reference"
	8. In the "Add Service Reference" dialog box, in the box labeled URL enter either:
		a) 	The URL to the WSDL from the real or "mock" ARM Service (For instructions
        		on starting the "mock" ARM Service, please see section 7.) For example,
        		if the "mock" service was started on port 8081, the URL to the service
        		would be http://localhost:8081/ARM/ARM?WSDL.Please note that the URL to be used from within 
			your code is http://<hostname>:<portNumber>/ARM/ARM. It does not contain the "?WSDL" - that 
			suffix is strictly for discovering the web service.
		b) 	The location on disk that the wsdl is located at in the unpacked distribution.
			For example, if the distribution was unpacked into the c:/temp directory, the
			URL would be c:\temp\arm\src\main\resources\gov\hud\arm\wsdl\arm-inlined.wsdl.
			(This is the version of WSDL with the schema included in it so that .NET can
			resolve the references without an external schema.)
	9. Select "Go" to discover the web service definition.
	10. Replace the contents of the Program.cs class with the code listed below.
		This example is set to run against the local "mock" ARM service on port 8081,
		with the username set to "dotnet" and password set to "user", but could be
		altered to run against the real service by altering these parameters.
	11. The call to "postAgencyData" will fail because the contents of the databag is not valid.
		Once you provide the databag contents that validates against agency_profile_databag_5_0.xsd, 
		then the service will not report an error. The other three data submission methods (postCounselorData, 
		postClientData and postForm9902Data) expect the databag contents to validate against 
		counselor_profile_databag_5_0.xsd, client_profile_databag_5_0.xsd and form_9902_databag_5_0.xsd, respectively.
	
------------------------------------------------------------------------------------


using System;
using System.Collections.Generic;
using System.Text;
using SampleARMClient.ARM;
using System.Net;
using System.IO;
using System.Threading;

namespace SampleARMClient
{
    class Program
    {
        static void Main(string[] args)
        {
            ArmServiceImplService armService = new ArmServiceImplService();
            // TODO: Replace with live server URL when ready
            armService.Url = "http://localhost:8081/ARM/ARM/";

            NetworkCredential credentials = new NetworkCredential();
            // TODO: Replace with userid/password issued by HUD for HCS system when ready
            credentials.UserName = "dotnet";
            credentials.Password = "user";
            armService.Credentials = credentials;

            doPing(armService);
            doGetReference(armService);
            postSubmissionResponse submissionId = doPostAgencyData(armService);
            while (doGetSubmissionInfo(armService, submissionId) != true)
            {
                // sleep for 60 seconds before checking agin.
                Thread.Sleep(60000);
            }
        }

        private static void doGetReference(ArmServiceImplService armService)
        {
            getReference getReference = new getReference();
            // TODO: Replace 80000 with your agency HCS id.
            getReference.agcHcsId = 80000;
            getReference.referenceId = 0;
            referenceItem[] referenceItems = armService.getReference(getReference);
            foreach (referenceItem referenceItem in referenceItems)
            {
                Console.WriteLine(referenceItem.id);
                Console.WriteLine(referenceItem.name);
                Console.WriteLine(referenceItem.longDesc);
                Console.WriteLine(referenceItem.shortDesc);
            }
        }

        private static void doPing(ArmServiceImplService armService)
        {
            ping pingIn = new ping();
            // TODO: Replace 80000 with your agency HCS id.
            pingIn.agcHcsId = 80000;
            string pingString = armService.ping(pingIn);
            Console.WriteLine(pingString);
        }

        private static postSubmissionResponse doPostAgencyData(ArmServiceImplService armService)
        {
            postAgencyData postAgencyData = new postAgencyData();
            submissionHeader50 header = new submissionHeader50();
            // TODO: Replace 80000 with your agency HCS id.
            header.agcHcsId = 80000;
            header.agcName = "TEST AGENCY";
            // TODO: Replace 8 with your CMS vendor id issued to you by ARM Development Team.
            header.systemName = 8;
            header.subFlag = 0;
            postAgencyData.submissionHeader50 = header;

            // TODO: Replace the test databag with a valid databag of your own
            FileStream file = new FileStream("c:\sb\arm\src\main\resources\gov\hud\arm\databag\testAgencyProfileData.xml", FileMode.Open, FileAccess.Read);
            StreamReader sr = new StreamReader(file);
            string str = sr.ReadToEnd();

            System.Text.ASCIIEncoding encoding = new System.Text.ASCIIEncoding();
            postAgencyData.submissionData = encoding.GetBytes(str);

            postSubmissionResponse submissionId = armService.postAgencyData(postAgencyData);
            Console.WriteLine("Submitted Data returned id : " + submissionId);
            return submissionId;
        }

        private static Boolean doGetSubmissionInfo(ArmServiceImplService armService,
            postSubmissionResponse postSubmissionResponse)
        {
            getSubmissionInfo getSubmissionInfo = new getSubmissionInfo();
            // TODO: Replace 80000 with your agency HCS id.
            getSubmissionInfo.agcHcsId = 80000;
            getSubmissionInfo.submissionId = postSubmissionResponse.submissionId;
            getSubmissionInfoResponse response = armService.getSubmissionInfo(getSubmissionInfo);
            Console.WriteLine("SubmissionInfo Status Date = " + response.statusDate);
            Console.WriteLine("SubmissionInfo Status Message = " + response.statusMessage);
            // if Done or Error returned, then return true to stop polling server.
            if (response.statusMessage.Equals("DONE") || response.statusMessage.Contains("ERROR"))
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
    }
}

------------------------------------------------------------------------------------