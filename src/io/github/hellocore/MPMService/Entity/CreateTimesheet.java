package io.github.hellocore.MPMService.Entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import io.github.hellocore.MPMService.Util.BeanUtils;
import io.github.hellocore.MPMService.Util.MPMUtils;

public class CreateTimesheet {
//	"workingDate":"25-12-2015","startTime":"09:00","endTime":"18:00","projectCode":"","taskName":"Stand by for job","memo":""}]
	private String workingDate;
	private String startTime;
	private String endTime;
	private String projectCode;
	private String taskName;
	private String memo;

	public String getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(String workingDate) {
		this.workingDate = workingDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Boolean isNonProject(){
		return BeanUtils.isEmpty(this.getProjectCode());
	}

	public  List<NameValuePair> toListParam(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("utf8", "✓"));
		params.add(new BasicNameValuePair("authenticity_token", MPMUtils.getCSRFToken()));
		params.add(new BasicNameValuePair("commit", "OK"));

		if(this.isNonProject()){
			params.add(new BasicNameValuePair("timesheet_nonproject[working_date]", this.getWorkingDate()));
			params.add(new BasicNameValuePair("timesheet_nonproject[start_time]", this.getStartTime()));
			params.add(new BasicNameValuePair("timesheet_nonproject[end_time]", this.getEndTime()));
			params.add(new BasicNameValuePair("timesheet_nonproject[task_name]", this.getTaskName()));
			params.add(new BasicNameValuePair("timesheet_nonproject[memo]", this.getMemo()));			
		}else{
			params.add(new BasicNameValuePair("timesheet_standard[working_date]", this.getWorkingDate()));
			params.add(new BasicNameValuePair("timesheet_standard[start_time]", this.getStartTime()));
			params.add(new BasicNameValuePair("timesheet_standard[end_time]", this.getEndTime()));
			params.add(new BasicNameValuePair("timesheet_standard[project_code]", this.getProjectCode()));
			params.add(new BasicNameValuePair("timesheet_standard[task_name]", this.getTaskName()));
			params.add(new BasicNameValuePair("timesheet_standard[memo]", this.getMemo()));
		}
		return params;
	}
	
}
