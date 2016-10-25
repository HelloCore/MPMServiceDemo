package io.github.hellocore.MPMService.Controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.hellocore.MPMService.Bean.CreateTimesheetBean;
import io.github.hellocore.MPMService.Bean.TimesheetBean;
import io.github.hellocore.MPMService.Description.RequiredSession;
import io.github.hellocore.MPMService.Exception.MPMServiceException;
import io.github.hellocore.MPMService.Exception.MPMServiceExceptionMessage;
import io.github.hellocore.MPMService.Service.TimesheetService;
import io.github.hellocore.MPMService.Util.BeanUtils;

@Controller
@RequestMapping("/timesheet")
public class TimesheetController {

	private TimesheetService timesheetService;
	public void setTimesheetService(TimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}


	@RequestMapping("/listPaging")
	@ResponseBody
	@RequiredSession
	public Map<String,Object> listPagingTimesheet(@ModelAttribute TimesheetBean bean,HttpServletRequest request,HttpServletResponse response){		
		return timesheetService.findListTimeSheet(bean);
	}


	@RequestMapping("/creates")
	@RequiredSession
	@ResponseBody
	public String createTimesheets(@ModelAttribute CreateTimesheetBean bean,HttpServletRequest request,HttpServletResponse response){
		timesheetService.createTimesheets(bean);
		return "Success";
	}
	
	@RequestMapping("/delete")
	@RequiredSession
	@ResponseBody
	public String deleteTimesheet(@ModelAttribute TimesheetBean bean,HttpServletRequest request,HttpServletResponse response){
		if(BeanUtils.isEmpty(bean.getTimesheetID())){
			throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
		}else{
			timesheetService.deleteTimesheet(bean.getTimesheetID());
		}
		return "Success";
	}
}
