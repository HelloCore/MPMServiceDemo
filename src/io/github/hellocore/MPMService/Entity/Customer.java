package io.github.hellocore.MPMService.Entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.util.HtmlUtils;

import io.github.hellocore.MPMService.Util.BeanUtils;

public class Customer {
	private String name;
	private List<Project> listProject;
	public List<Project> getListProject() {
		return listProject;
	}
	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}
	public void addProject(Project project){
		if(BeanUtils.isNull(this.listProject)){
			this.listProject = new ArrayList<Project>();			
		}
		this.listProject.add(project);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Customer(String name){
		this.name = HtmlUtils.htmlUnescape(name);
		this.listProject = new ArrayList<Project>();
	}
}
