package io.github.hellocore.MPMService.Entity;

import java.util.Date;

import org.jsoup.nodes.Element;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.github.hellocore.MPMService.Util.MPMDateSerializer;
import io.github.hellocore.MPMService.Util.MPMUtils;

public class Timesheet {
	private String workingDate;
	private String resourceName;
	private String customer;
	private String customerId;
	private String projectCode;
	private String project;
	private String taskName;
	private String taskGroup;	
	private String projectManager;
	private String effort;
	private String progress;
	private String startTime;
	private String stopTime;
	private String memo;
	private String entryDate;
	private String approveStatus;
	private String approveBy;
	private String approveDate;
	
	private String timesheetID; 
	
	private Date startDateTime;
	
	private Date stopDateTime;
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(String workingDate) {
		this.workingDate = workingDate;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
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
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getEffort() {
		return effort;
	}
	public void setEffort(String effort) {
		this.effort = effort;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getApproveBy() {
		return approveBy;
	}
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	@JsonSerialize(using=MPMDateSerializer.class)  
	public Date getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	@JsonSerialize(using=MPMDateSerializer.class)  
	public Date getStopDateTime() {
		return stopDateTime;
	}
	
	public void setStopDateTime(Date stopDateTime) {
		this.stopDateTime = stopDateTime;
	}

//	  <th id="hb1" width="10%">Working Date</th> 
//	  <th id="hb1" width="5%">Resource Name</th> 
//	  <th id="hb1" width="5%">Customer</th> 
//	  <th id="hb1" width="20%">Project Code</th> 
//	  <th id="hb1" width="10%">Task Name</th> 
//	  <th id="hb1" width="10%">Project Manager</th> 
//	  <th id="hb1_ver" width="5%">Effort (hrs)</th> 
//	  <th id="hb1_ver" width="5%">Progress (%)</th> 
//	  <th id="hb1" width="5%">Start Time</th> 
//	  <th id="hb1" width="5%">Stop Time</th> 
//	  <th id="hb1" width="15%">Memo</th> 
//	  <th id="hb1_ver" width="5%">Entry Date</th> 
//	  <th id="hb1_ver" width="5%">Approve Status</th> 
//	  <th id="hb1_ver" width="5%">Approved By</th> 
//	  <th id="hb1_ver" width="5%">Approved Date</th>

	public String getTimesheetID() {
		return timesheetID;
	}
	public void setTimesheetID(String timesheetID) {
		this.timesheetID = timesheetID;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTaskGroup() {
		return taskGroup;
	}
	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}
	public Timesheet(Element tr,Boolean isPM){
//		String tempText = tr.child(0).child(0).text();
		this.workingDate = tr.child(0).child(0).text();
		this.resourceName = tr.child(1).text();
		this.customer = tr.child(2).text();
//		this.customerId = MPMCustomerTaskUtil.findCustomerIdFromName(this.customer);
		this.project = tr.child(3).text();
		this.projectCode = tr.child(3).text();
		this.taskName = tr.child(5).text();
		
//		if(this.customer.length() > 0){
//			this.taskGroup = MPMCustomerTaskUtil.findTaskGroupFromName(this.taskName);
//		}else{
//			this.taskGroup = "None Project";
//		}
		
		this.projectManager = tr.child(4).text();
		this.effort = tr.child(8).text();
//		this.progress = tr.child(8).text();
		this.startTime = tr.child(6).text();
		this.stopTime = tr.child(7).text();
		this.memo = tr.child(9).text();
//		this.entryDate = tr.child(9).text();
		this.approveStatus = tr.child(10).text();
		this.approveBy = tr.child(11).text();
//		this.approveDate = tr.child(15).text();
//		if(isPM){
//			if(BeanUtils.isNotNull(tr.child(0).children().first())){
//				String idString = tr.child(0).children().first().attr("id").toString();
//				this.timesheetID = idString.substring(10, idString.length()-7);
//			}else{
//				this.timesheetID = "";
//			}
//		}else{
//			if(tr.child(0).child(0).){		
				String tempString = tr.child(0).child(0).attr("href").toString();
				this.timesheetID = tempString.substring(16, tempString.length());
//			}else{
//				this.timesheetID = "";				
//			}
//		}
//		
		this.startDateTime = MPMUtils.parseDate(this.workingDate+" "+this.startTime);
		this.stopDateTime = MPMUtils.parseDate(this.workingDate+" "+this.stopTime);		
	}
	
	
	
}
