package gov.hud.arm.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import gov.hud.arm.utils.*;

import javax.xml.ws.soap.MTOM;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@MTOM
public class ArmServiceImpl implements ArmService{

	public String ping(Ping ping) {
		System.out.println("Invoking 'ping'");
		System.out.println("Invoked 'ping'");
		return "ARM Service Version 14.16.5.0 (Latest Databag Version 5.0) - " + ping.getAgcHcsId();
	}

	public GetReferenceResponse getReference(GetReference getReference) {
		System.out.println("Invoking 'getReference'");
		System.out.println("Request is: " + ToStringBuilder.reflectionToString(getReference, ToStringStyle.MULTI_LINE_STYLE));
		GetReferenceResponse response = new GetReferenceResponse();
		ReferenceItem value = new ReferenceItem();
		value.setId(1);
		value.setLongDesc("ARM SDK Test Reference Item");
		value.setName("Test Reference Item 1");
		value.setShortDesc("Reference Item");
		List<ReferenceItem> referenceItems = response.getReferenceItem();
		referenceItems.add(value);
		value.setId(2);
		value.setName("Test Reference Item 2");
		referenceItems.add(value);
		System.out.println("Invoked 'getReference'");
		return response;
	}

	public GetAllReferenceItemsResponse getAllReferenceItems(GetAllReferenceItems getReference) {
		System.out.println("Invoking 'getAllReferenceItems'");
		System.out.println("Request is: " + ToStringBuilder.reflectionToString(getReference, ToStringStyle.MULTI_LINE_STYLE));
		GetAllReferenceItemsResponse response = new GetAllReferenceItemsResponse();
		List<ReferenceItemGroup> referenceItemGroups = response.getReferenceItemGroup();

		ReferenceItemGroup value = new ReferenceItemGroup();
		value.setGroupId(30);
		
		List<ReferenceItem> refItems = value.getReferenceItem();
		ReferenceItem refItem = new ReferenceItem();
		refItem.setId(1);
		refItem.setLongDesc("ARM SDK Test Reference Item");
		refItem.setName("Test Reference Item 1");
		refItem.setShortDesc("Reference Item");
		refItems.add(refItem);

		refItem = new ReferenceItem();
		refItem.setId(2);
		refItem.setLongDesc("ARM SDK Test Reference Item");
		refItem.setName("Test Reference Item 2");
		refItem.setShortDesc("Reference Item");
		refItems.add(refItem);

		referenceItemGroups.add(value);
		
		System.out.println("Invoked 'getAllReferenceItems'");
		return response;
	}

	public GetReferenceGroupNamesResponse getReferenceGroupNames(GetReferenceGroupNames request) {
		System.out.println("Invoking 'getReferenceGroupNames'");
		GetReferenceGroupNamesResponse response = new GetReferenceGroupNamesResponse();
		List<ReferenceGroup> groups = response.getReferenceGroup();
		ReferenceGroup group = new ReferenceGroup();
		group.setId(0);
		group.setName("States");
		groups.add(group);
		return response;
	}	

	public PostSubmissionResponse postSubmission(PostSubmission postSubmission) {
		System.out.println("Invoking 'postSubmission'");
		try {
			InputStream is = postSubmission.getSubmissionData().getDataSource().getInputStream();
			try {
				String data = IOUtils.toString(is);
				System.out.println("Data on request is: " + data);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PostSubmissionResponse response = new PostSubmissionResponse();
		response.setSubmissionId(12345678);
		System.out.println("Invoked 'postSubmission'");
		return response;
	}

	public GetSubmissionInfoResponse getSubmissionInfo(GetSubmissionInfo getSubmissionInfo) {
		System.out.println("Invoking 'getSubmissionInfo'");
		System.out.println("Request is: " + ToStringBuilder.reflectionToString(getSubmissionInfo, ToStringStyle.MULTI_LINE_STYLE));
		GetSubmissionInfoResponse response = new GetSubmissionInfoResponse();
		response.setStatusDate(new Date());
		response.setStatusMessage("An error message");
		System.out.println("Invoked 'getSubmissionInfo'");
		return response;
	}

	public GetDetailedErrorMessageResponse getDetailedErrorMessage(
			GetDetailedErrorMessage msg) {
		System.out.println("Invoking 'getDetailedErrorMessage'");
		System.out.println("Request is: " + ToStringBuilder.reflectionToString(msg, 
				ToStringStyle.MULTI_LINE_STYLE));
		GetDetailedErrorMessageResponse response = new GetDetailedErrorMessageResponse();
		response.setStatusDate(new Date());
		response.setMessage("An error message");
		System.out.println("Invoked 'getDetailedErrorMessage'");
		return response;
	}

	public void refreshReferenceItemCache(RefreshReferenceItemCache msg) {
		System.out.println("Invoking 'refreshReferenceItemCache' for reference Item = " + msg.getReferenceId());
	}

	public void refreshAllReferenceItemsInCache(RefreshAllReferenceItemsInCache msg) {
		System.out.println("Invoking 'refreshAllReferenceItemsInCache'");
	}

	public PostSubmissionResponse postAgencyData(PostAgencyData agencyData) {
		System.out.println("Invoking 'postAgencyData'");
		try {
			InputStream is = agencyData.getSubmissionData().getDataSource().getInputStream();
			try {
				String data = IOUtils.toString(is);
				System.out.println("Data on request is: " + data);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PostSubmissionResponse response = new PostSubmissionResponse();
		response.setSubmissionId(12345678);
		System.out.println("Invoked 'postAgencyData'");
		return response;
	}

	public PostSubmissionResponse postClientData(PostClientData postClientData) {
		System.out.println("Invoking 'postClientData'");
		try {
			InputStream is = postClientData.getSubmissionData().getDataSource().getInputStream();
			try {
				String data = IOUtils.toString(is);
				System.out.println("Data on request is: " + data);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PostSubmissionResponse response = new PostSubmissionResponse();
		response.setSubmissionId(12345678);
		System.out.println("Invoked 'postClientData'");
		return response;
	}

	public PostSubmissionResponse postCounselorData(
			PostCounselorData postCounselorData) {
		System.out.println("Invoking 'postCounselorData'");
		try {
			InputStream is = postCounselorData.getSubmissionData().getDataSource().getInputStream();
			try {
				String data = IOUtils.toString(is);
				System.out.println("Data on request is: " + data);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PostSubmissionResponse response = new PostSubmissionResponse();
		response.setSubmissionId(12345678);
		System.out.println("Invoked 'postCounselorData'");
		return response;
	}

	public PostSubmissionResponse postForm9902Data(
			PostForm9902Data postForm9902Data) {
		System.out.println("Invoking 'postForm9902Data'");
		try {
			InputStream is = postForm9902Data.getSubmissionData().getDataSource().getInputStream();
			try {
				String data = IOUtils.toString(is);
				System.out.println("Data on request is: " + data);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PostSubmissionResponse response = new PostSubmissionResponse();
		response.setSubmissionId(12345678);
		System.out.println("Invoked 'postForm9902Data'");
		return response;
	}
}
