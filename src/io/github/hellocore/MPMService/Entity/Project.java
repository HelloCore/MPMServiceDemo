package io.github.hellocore.MPMService.Entity;

import org.springframework.web.util.HtmlUtils;

public class Project {
	
	private String id;
	private String code;
	private String name;
	private String erpCode;
	private String pmName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public Project(String id,String code,String erpCode,String name,String pmName){
		this.id = HtmlUtils.htmlUnescape(id);
		this.code = HtmlUtils.htmlUnescape(code);
		this.erpCode = HtmlUtils.htmlUnescape(erpCode);
		this.name = HtmlUtils.htmlUnescape(name);
		this.pmName = HtmlUtils.htmlUnescape(pmName);
	}
	
	public String getPmName() {
		return pmName;
	}
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
