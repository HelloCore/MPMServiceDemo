package io.github.hellocore.MPMService.Bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import io.github.hellocore.MPMService.Util.MPMUtils;

public class TimesheetBean {
	
	private String timesheetID;
	private String approveStatus;
	private String year;
	private String month;
	private String projectCode;
	private String taskName;
	private String resourceName;
	private String bizUnitID;
	private String approvedByName;
	private String projectStatus;	
	private String page;
	private Boolean isPM;
	
	public Boolean getIsPM() {
		return isPM;
	}
	public void setIsPM(Boolean isPM) {
		this.isPM = isPM;
	}
	public String getTimesheetID() {
		return timesheetID;
	}
	public void setTimesheetID(String timesheetID) {
		this.timesheetID = timesheetID;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getBizUnitID() {
		return bizUnitID;
	}
	public void setBizUnitID(String bizUnitID) {
		this.bizUnitID = bizUnitID;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}	
	
	public List<NameValuePair> toListParams(String commit){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("authenticity_token",MPMUtils.getCSRFToken()));
		params.add(new BasicNameValuePair("timesheet[approve_status_id]",""));
		params.add(new BasicNameValuePair("timesheet[working_year]",""));
		params.add(new BasicNameValuePair("timesheet[working_month]",""));
		params.add(new BasicNameValuePair("timesheet[user]",""));
		params.add(new BasicNameValuePair("timesheet[project_code]",""));
		params.add(new BasicNameValuePair("timesheet[task_name]",""));
		params.add(new BasicNameValuePair("utf8","✓"));
		params.add(new BasicNameValuePair("commit",commit));

		
		return params;		
	}
	
}
