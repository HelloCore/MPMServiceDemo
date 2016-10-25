package io.github.hellocore.MPMService.Service;

import java.util.Map;

import io.github.hellocore.MPMService.Bean.CreateTimesheetBean;
import io.github.hellocore.MPMService.Bean.TimesheetBean;

public interface TimesheetService {
	public Map<String,Object> findListTimeSheet(TimesheetBean bean);
		
	public void createTimesheets(CreateTimesheetBean bean);
	public void deleteTimesheet(String timesheetID);

}
