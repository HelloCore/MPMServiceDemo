package io.github.hellocore.MPMService.Service.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import io.github.hellocore.MPMService.Description.MPMURLDescription;
import io.github.hellocore.MPMService.Entity.Customer;
import io.github.hellocore.MPMService.Entity.Project;
import io.github.hellocore.MPMService.Service.BasicHttpService;
import io.github.hellocore.MPMService.Service.CustomerProjectService;
import io.github.hellocore.MPMService.Util.BeanUtils;
import io.github.hellocore.MPMService.Util.MPMUtils;

@Service
public class CustomerProjectServiceImpl implements CustomerProjectService {
	
	private BasicHttpService basicHttpService;	
	public void setBasicHttpService(BasicHttpService basicHttpService) {
		this.basicHttpService = basicHttpService;
	}
	
	private static final Pattern startOfCustomer = Pattern.compile(">Customer<\\\\/th><th class=\\\\\\\"th\\\\\\\">PM<\\\\/th>");
	private static final Pattern endOfCustomer = Pattern.compile("<\\\\/table>\\t\\t<\\\\/fieldset>\\\\n");	
	private static final Pattern customerMatch = Pattern.compile("<tr class=\\\"tr\\\">\\n\\t\\t\\t\\t<td class=\\\"td \\\"><a data-remote=\\\"true\\\" href=\\\"/mpm/timesheet_standards/fetch_task_group\\?date=.*&amp;last_id=\\d*\\&amp;project_id=(\\d+)\\\">(.*)</a></td>\\n\\t\\t\\t\\t<td class=\\\"td \\\">(.*)</td>\\n\\t\\t\\t\\t<td class=\\\"td \\\">(.*)</td>\\n\\t\\t\\t\\t<td class=\\\"td \\\">(.*)</td>\\n\\t\\t\\t\\t<td class=\\\"td \\\">(.*)</td>\\n\\n</tr>");
	
	@Override
	public Map<String,Object> getCustomerTask(){	
		return null;
	}

	@Override
	public Map<String,Customer> getCustomer(){	
		String result = basicHttpService.executeHttpGet(MPMURLDescription.MPM.FETCH_PROJECT+"?date=2015-12-04&last_id=1184356",true);
		String customerSource = MPMUtils.subStringFromSource(result, startOfCustomer, endOfCustomer);
		customerSource = StringEscapeUtils.unescapeJava(customerSource);

		Map<String,Customer> customerMap = new HashMap<String, Customer>();
		Matcher customerMatcher = customerMatch.matcher(customerSource);
		String customerName;
		while(customerMatcher.find()){
			customerName = BeanUtils.getDefaultValueIfNull(customerMatcher.group(5),"").toString();
			if(!customerMap.containsKey(customerName)){
				customerMap.put(customerName, new Customer(customerName));
			}
			Customer customer = customerMap.get(customerName);			
			if(BeanUtils.isNotNull(customer)){
				customer.addProject(new Project(BeanUtils.getDefaultValueIfNull(customerMatcher.group(1),"").toString(),
												BeanUtils.getDefaultValueIfNull(customerMatcher.group(2),"").toString(), 
												BeanUtils.getDefaultValueIfNull(customerMatcher.group(3),"").toString(), 
												BeanUtils.getDefaultValueIfNull(customerMatcher.group(4),"").toString(), 
												BeanUtils.getDefaultValueIfNull(customerMatcher.group(6),"").toString()));
			}
		}
		return customerMap;
	        
	}	
}
