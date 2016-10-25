package io.github.hellocore.MPMService.Bean;

import io.github.hellocore.MPMService.Util.BeanUtils;

public class MPMSession {
	
	private String mpmSessionId;
	private String signFrontSessionId;
	
	public String getMpmSessionId() {
		return mpmSessionId;
	}
	public void setMpmSessionId(String mpmSessionId) {
		this.mpmSessionId = mpmSessionId;
	}
	public String getSignFrontSessionId() {
		return signFrontSessionId;
	}
	public void setSignFrontSessionId(String signFrontSessionId) {
		this.signFrontSessionId = signFrontSessionId;
	}
	
	public Boolean isEmptySession(){
		if(BeanUtils.isEmpty(this.mpmSessionId) || BeanUtils.isEmpty(this.signFrontSessionId)){
			return true;
		}
		return false;
	}
}
