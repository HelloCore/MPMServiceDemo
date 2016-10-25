package io.github.hellocore.MPMService.Service;

import java.util.Map;

import io.github.hellocore.MPMService.Entity.Customer;

public interface CustomerProjectService {
	public Map<String,Object> getCustomerTask();
	public Map<String,Customer> getCustomer();
}
