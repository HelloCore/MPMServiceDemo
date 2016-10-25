package io.github.hellocore.MPMService.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.github.hellocore.MPMService.Bean.AuthenticationBean;
import io.github.hellocore.MPMService.Description.MPMCommonDescription;
import io.github.hellocore.MPMService.Description.RequiredSession;
import io.github.hellocore.MPMService.Exception.MPMServiceException;
import io.github.hellocore.MPMService.Exception.MPMServiceExceptionMessage;
import io.github.hellocore.MPMService.Service.AuthenticationService;
import io.github.hellocore.MPMService.Util.BeanUtils;

@Controller
public class AuthenticationController {

	private AuthenticationService authenticationService;		
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RequestMapping("/authen/validate")
	@ResponseBody
	@RequiredSession
	public Map<String,Object> validate(@ModelAttribute AuthenticationBean bean,
			HttpServletRequest request,HttpServletResponse response){
		return authenticationService.validate();	
	}
	
	@RequestMapping("/authen/login")
	@ResponseBody
	public Map<String,Object> login(@ModelAttribute AuthenticationBean bean,
			ModelAndView mv,HttpServletRequest request,HttpServletResponse response){		
		this.validateParameter(bean);
		return authenticationService.login(bean);
	}
	
	@RequestMapping("/authen/logout")
	@RequiredSession
	public void logout(@ModelAttribute AuthenticationBean bean,
			ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		authenticationService.logout();
		request.getSession().removeAttribute(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY_SIGN_FRONT);
		request.getSession().removeAttribute(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY);		
	}
	
	private void validateParameter(AuthenticationBean bean){
		if(BeanUtils.isEmpty(bean.getUsername()) 
				|| BeanUtils.isEmpty(bean.getPassword())){
			throw new MPMServiceException(MPMServiceExceptionMessage.VALIDATE_PARAMETER_FAILED);
		}
	}

	private static Map<String,String> pokemonLocation;
	
	@RequestMapping("/pokemon/save")
	public void savePokemonData(@RequestParam(value="location",required=true)String location,ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		if(pokemonLocation == null){
			pokemonLocation = new HashMap<String, String>();
		}
		String[] fin = location.split(",");
		pokemonLocation.put("lat", fin[0]);
		pokemonLocation.put("lng", fin[1]);
	}

	
	@RequestMapping("/pokemon/get")
	@ResponseBody
	public Map<String,String> getPokemonData(ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		return pokemonLocation;
	}
}
