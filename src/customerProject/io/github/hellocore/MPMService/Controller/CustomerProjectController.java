package io.github.hellocore.MPMService.Controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.hellocore.MPMService.Bean.CustomerProjectBean;
import io.github.hellocore.MPMService.Description.RequiredSession;
import io.github.hellocore.MPMService.Entity.Customer;
import io.github.hellocore.MPMService.Service.CustomerProjectService;

@Controller
@RequestMapping("/customerProject")
public class CustomerProjectController {

	private CustomerProjectService customerProjectService; 
	public void setCustomerProjectService(CustomerProjectService customerProjectService) {
		this.customerProjectService = customerProjectService;
	}


	@RequestMapping("/listCustomer")
	@ResponseBody
	@RequiredSession
	public Collection<Customer> getListCustomer(@ModelAttribute CustomerProjectBean bean,
			HttpServletRequest request,HttpServletResponse response){
		return customerProjectService.getCustomer().values();
	}
	
}
