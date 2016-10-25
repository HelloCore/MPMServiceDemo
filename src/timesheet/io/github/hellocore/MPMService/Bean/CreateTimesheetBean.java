package io.github.hellocore.MPMService.Bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.hellocore.MPMService.Entity.CreateTimesheet;
import io.github.hellocore.MPMService.Exception.MPMServiceException;
import io.github.hellocore.MPMService.Exception.MPMServiceExceptionMessage;
import io.github.hellocore.MPMService.Util.BeanUtils;

public class CreateTimesheetBean {
	
	private Long customerId;
	private String projectCode;
	private String taskGroup;
	private String taskId;
	private String type;
	private String timesheetData;	
	
	private String listTimesheet;	
		
	public String getListTimesheet() {
		return listTimesheet;
	}

	public void setListTimesheet(String listTimesheet) {
		this.listTimesheet = listTimesheet;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getTimesheetData() {
		return timesheetData;
	}

	public void setTimesheetData(String timesheetData) {
		this.timesheetData = timesheetData;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
//	public List<TimesheetDate> toListTimesheetDate(){
//		List<TimesheetDate> results = null;
//		ObjectMapper mapper = new ObjectMapper();
//		if(BeanUtils.isNotEmpty(this.getTimesheetData())){
//			try{
//				results = mapper.readValue(this.getTimesheetData(),new TypeReference<ArrayList<TimesheetDate>>(){});
//			}catch (Exception e){
//				throw new MPMServiceException(new MPMServiceExceptionMessage(e.toString()));
//			}
//		}		
//		if(BeanUtils.isEmpty(results)){
//			throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
//		}
//		return results;
//	}
	
	public List<CreateTimesheet> toListTimesheet(){
		List<CreateTimesheet> results = null;
		ObjectMapper mapper = new ObjectMapper();
		if(BeanUtils.isNotEmpty(this.getListTimesheet())){
			try{
				results = mapper.readValue(this.getListTimesheet(),new TypeReference<ArrayList<CreateTimesheet>>(){});
			}catch (Exception e){
				throw new MPMServiceException(new MPMServiceExceptionMessage(e.toString()));
			}
		}
		if(BeanUtils.isEmpty(results)){
			throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
		}else{
			for(CreateTimesheet createTimesheet : results){
				if(BeanUtils.isEmpty(createTimesheet.getWorkingDate())
						|| BeanUtils.isEmpty(createTimesheet.getTaskName())
						|| BeanUtils.isEmpty(createTimesheet.getStartTime())
						|| BeanUtils.isEmpty(createTimesheet.getEndTime())){
					throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
				}
			}			
		}
		return results;
	}
	
//	public List<CreateTimesheet> toListTimesheet(){
//		List<CreateTimesheet> results = null;
//		ObjectMapper mapper = new ObjectMapper();
//		if(BeanUtils.isNotEmpty(this.getListTimesheet())){
//			try{
//				results = mapper.readValue(this.getListTimesheet(),new TypeReference<ArrayList<CreateTimesheet>>(){});
//			}catch (Exception e){
//				throw new MPMServiceException(new MPMServiceExceptionMessage(e.toString()));
//			}
//		}
//		if(BeanUtils.isEmpty(results)){
//			throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
//		}else{
//			for(CreateTimesheet createTimesheet : results){
//				if(BeanUtils.isEmpty(createTimesheet.getProjectCode())
//						|| BeanUtils.isEmpty(createTimesheet.getTaskGroup())
//						|| BeanUtils.isEmpty(createTimesheet.getTaskId())
//						|| BeanUtils.isEmpty(createTimesheet.getTimesheetData())){
//					throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
//				}
//			}			
//		}
//		return results;
//	}
	
}
