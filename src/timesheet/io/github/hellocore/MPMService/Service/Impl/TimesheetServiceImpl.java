package io.github.hellocore.MPMService.Service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import io.github.hellocore.MPMService.Bean.CreateTimesheetBean;
import io.github.hellocore.MPMService.Bean.TimesheetBean;
import io.github.hellocore.MPMService.Description.MPMURLDescription;
import io.github.hellocore.MPMService.Entity.CreateTimesheet;
import io.github.hellocore.MPMService.Entity.Timesheet;
import io.github.hellocore.MPMService.Service.BasicHttpService;
import io.github.hellocore.MPMService.Service.TimesheetService;
import io.github.hellocore.MPMService.Util.BeanUtils;
import io.github.hellocore.MPMService.Util.MPMUtils;

@Service
public class TimesheetServiceImpl implements TimesheetService{

	BasicHttpService basicHttpService;	
	public void setBasicHttpService(BasicHttpService basicHttpService) {
		this.basicHttpService = basicHttpService;
	}

	private static final Pattern pageMatch = Pattern.compile("<a href=\\\"/mpm/timesheets/search?page=(\\\\d+)\\\">(\\\\d+)</a>");
	
	public Map<String,Object> findWaitApproveTimesheet(TimesheetBean bean)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String source = basicHttpService.executeHttpPost(MPMURLDescription.MPM.LIST_TIMESHEET, bean.toListParams("Search"),true);
		Matcher pageMatcher = pageMatch.matcher(source);
		Integer pageCount = 1;
		Integer tempPage = 0;
		
		List<Timesheet> listTimeSheet = new ArrayList<Timesheet>();
		Document document;
		Elements tbody;
		
		while(pageMatcher.find()){
			tempPage = Integer.parseInt(pageMatcher.group(0).toString());
			if(pageCount < tempPage){
				pageCount = tempPage;
			}
		}
		
		for(int page=1;page<=pageCount;page++){
			if(BeanUtils.isNull(source)){
				StringBuilder urlBuilder = new StringBuilder(MPMUtils.getURLByAppendSession(MPMURLDescription.MPM.LIST_TIMESHEET));
				urlBuilder.append("&page=").append(page);
				source = basicHttpService.executeHttpPostWithoutSession(urlBuilder.toString(), bean.toListParams("Search"), true);
			}
			document = Parser.htmlParser().parseInput(source, "");
			tbody = document.select("form table tbody").last().children();
			int rowSize = tbody.size();
			
			Boolean isPM = bean.getIsPM();
			if(BeanUtils.isNull(isPM)){
				isPM = false;
			}
			for(int i=1;i<rowSize;i++){
				listTimeSheet.add(new Timesheet(tbody.get(i),isPM));
			}
			source = null;
		}
		
		resultMap.put("listTimesheet", listTimeSheet);
			
		
		return resultMap;
	}
	
	public Map<String,Object> findListTimeSheet(TimesheetBean bean){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String source;
		StringBuilder urlBuilder = new StringBuilder(MPMURLDescription.MPM.LIST_TIMESHEET);
		urlBuilder.append("?sort=working_date_desc");
		if(BeanUtils.isEmpty(bean.getPage())){
			source = basicHttpService.executeHttpPost(urlBuilder.toString(), bean.toListParams("Search"),true);
		}else{
			urlBuilder.append("&page=").append(bean.getPage());
			source = basicHttpService.executeHttpPost(urlBuilder.toString(), bean.toListParams("Search"), true);
		}
		

		Matcher pageMatcher = pageMatch.matcher(source);
		Integer pageCount = 1;
		Integer tempPage = 0;

		while(pageMatcher.find()){
			tempPage = Integer.parseInt(pageMatcher.group(0).toString());
			if(pageCount < tempPage){
				pageCount = tempPage;
			}
		}
		
		Document document = Parser.htmlParser().parseInput(source, "");
		
		List<Timesheet> listTimeSheet = new ArrayList<Timesheet>();
		Elements tr = document.select("table.table tr.tr");
		
		int rowSize = tr.size();
		
		Boolean isPM = bean.getIsPM();
		if(BeanUtils.isNull(isPM)){
			isPM = false;
		}
		for(int i=0;i<rowSize;i++){
			listTimeSheet.add(new Timesheet(tr.get(i),isPM));
		}
		
				
		resultMap.put("listTimesheet", listTimeSheet);
		resultMap.put("totalPage", pageCount);
		if(BeanUtils.isEmpty(bean.getPage())){
			resultMap.put("page", 1);			
		}else{
			resultMap.put("page", BeanUtils.toInteger(bean.getPage()));
		}
		
		source = basicHttpService.executeHttpGet(MPMURLDescription.MPM.CREATE_TIMESHEET_STANDARD, true);
		resultMap.put("percent",  this.findPercentFromSource(source));

		return resultMap;		
	}

	
	public void createTimesheets(CreateTimesheetBean bean)
	{
		List<CreateTimesheet> timesheets = bean.toListTimesheet();
		for(CreateTimesheet timesheet : timesheets){
			if(timesheet.isNonProject()){
				basicHttpService.executeHttpPost(MPMURLDescription.MPM.CREATE_TIMESHEET_NONPROJECT, timesheet.toListParam(),false);
			}else{
				basicHttpService.executeHttpPost(MPMURLDescription.MPM.CREATE_TIMESHEET_STANDARD, timesheet.toListParam(),false);
			}
		}		
	}
	
	public void deleteTimesheet(String timesheetID)
	{
		List<NameValuePair> deleteParams = new ArrayList<NameValuePair>();
		deleteParams.add(new BasicNameValuePair("_method", "delete"));
		deleteParams.add(new BasicNameValuePair("authenticity_token", MPMUtils.getCSRFToken()));
		basicHttpService.executeHttpPost(MPMURLDescription.MPM.DELETE_TIMESHEET + "/" + timesheetID, deleteParams, false);
	}

	private String findPercentFromSource(String source){
		Document document = Parser.htmlParser().parseInput(source, "");
		String result = document.select("table.calendar_tab").first().siblingElements().first().text();		
		return result;
	}

}
