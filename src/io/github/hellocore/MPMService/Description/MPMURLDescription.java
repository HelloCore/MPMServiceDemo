package io.github.hellocore.MPMService.Description;

public class MPMURLDescription {
//	public static final String BASE_DOMAIN = "www.google.com";
	public static final String BASE_DOMAIN = "ampm.demo.com";
	
	public static final String BASE = "https://" + BASE_DOMAIN;
	public static final String BASE_MPM = BASE + "/mpm/";
	
	public static final String MPM_AUTHEN = BASE_MPM + "authen/";	

	public static final String LOGIN_PAGE = BASE + "/signfront/authen/login";
	public static final String LOGIN_EXE = BASE + "/signfront/authen";
	
	public static final String SIGNFRONT_PAGE = BASE + "/signfront/switcher";
	public static final String LOGOUT = MPM_AUTHEN + "logout";
	
	public static final String VALIDATE_PROFILE = BASE + "/signfront/switcher/app?name=mpm";

	public class URI {
		public static final String LOGIN = "/frontgate/login"; 
		public static final String LIST_TIMESHEET = "/mpm/timesheets/list";
		public static final String PROFILE = "/mpm/emps/edit_myself";
		public static final String KEY_TIMESHEET = "/mpm/timesheet/list";
		public static final String CREATE_TIMESHEET = "/mpm/timesheet/create";
		public static final String DELETE_TIMESHEET =  "/mpm/timesheets/destroy";
		public static final String APPROVE_TIMESHEET =  "/mpm/timesheets/approve_reject_timesheet";
		public static final String REJECT_TIMESHEET =  "/mpm/timesheets/update_reject_reason";
	}

	public class MPM {
		public static final String LIST_TIMESHEET = BASE_MPM + "timesheets/search";
		public static final String FETCH_PROJECT = BASE_MPM + "timesheet_standards/fetch_project";
		public static final String CREATE_TIMESHEET_STANDARD = BASE_MPM + "timesheet_standards";
		public static final String CREATE_TIMESHEET_NONPROJECT = BASE_MPM + "timesheet_nonprojects";
		
		public static final String DELETE_TIMESHEET = BASE_MPM + "timesheets";
		public static final String APPROVE_TIMESHEET = BASE_MPM + "timesheets/approve_reject_timesheet";
		public static final String REJECT_TIMESHEET = BASE_MPM + "timesheets/update_reject_reason";
	}
}
