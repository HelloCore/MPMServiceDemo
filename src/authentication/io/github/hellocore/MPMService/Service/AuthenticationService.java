package io.github.hellocore.MPMService.Service;

import java.util.Map;

import io.github.hellocore.MPMService.Bean.AuthenticationBean;


public interface AuthenticationService {
	public Map<String,Object> validate();
	public Map<String,Object> login(AuthenticationBean proto);
	public void logout();
}
