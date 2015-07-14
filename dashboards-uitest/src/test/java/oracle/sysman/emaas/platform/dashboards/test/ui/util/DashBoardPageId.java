package oracle.sysman.emaas.platform.dashboards.test.ui.util;

import oracle.sysman.qatool.uifwk.webdriver.*;

public class DashBoardPageId {
	/*
	 * 
	 *The following variable define the all elements id of Dashboard page, including the home page, create page
	 *
	 *
	 *
	 * */
	public static final String CreateDSButtonID="cbtn";
	public static final String CreateDSOfDialogID="createDsb";
	public static final String DashBoardNameBoxID = "dbsHNameIn";
	public static final String DashBoardDescBoxID = "dbsHDpIn";
	public static final String DashOKButtonID = "createDsb";
	public static final String WidgetAddButtonID = "add-widget-button";
	public static final String DashBoardSaveID = "dashboard-screenshot";
	public static final String WidgetAddButton = "//div[@id='ojDialogWrapper-widgetDetailsDialog']/div[5]/button";////div[18]/div[5]/button/span[text()='Add']";
	public static final String WidgetDialogCloseButtonID = "div[15]/div[3]/div/span";
	public static final String DashBoardID = "//div[@class='oj-panel dbs-summary-container']";// and @aria-dashboard='1127']";
	public static final String ElementID = "//*[@id=\"dtabhomesc\"]/div[1]";
	public static final String DeleteBtnID = "//*[@id=\"dtabhomesc\"]/div[1]/div[1]/div[2]/button";//div[@id='dtabhomesc']/div/div/div[2]/button";///html/body/div[2]/div/div/div/div/div[3]/div/div/div[2]/button";//"//button[@class='oj-button-half-chrome oj-sm-float-end oj-button oj-component oj-enabled oj-button-icon-only oj-component-initnode oj-default']/span[@class='oj-button-icon oj-start icon-delete-ena-16 oj-fwk-icon']";
	public static final String DeleteBtnID_Dialog = "//div[@id='ojDialogWrapper-dbs_cfmDialog']/div[5]/button";
	public static final String OverviewCloseID = "overviewClose";
	public static final String DashBoardName = "//span[@id='builder-dbd-name-display-hover-area']";
	public static final String AddBtn = "widget-selector-okbtn";
	public static final String closeBtnID = "//div[contains(@id,'ojDialogWrapper-ui-id') and @class='oj-dialog oj-component oj-draggable']/div[3]/div/span";
	public static final String LinkID = "linksButton";
	public static final String DashBoardLinkID = "link=All Dashboards";
	public static final String Application_Performance_Monitoring_ID = "//div[@aria-dashboard='1']";
	public static final String Database_Performance_Analytics_ID = "//div[@aria-dashboard='2']";
	public static final String Database_Resource_Planning_ID = "//div[@aria-dashboard='3']";
	public static final String Garbage_Collection_Overhead_ID = "//div[@aria-dashboard='4']";
	public static final String Host_Inventory_By_Platform_ID = "//div[@aria-dashboard='5']";
	public static final String Database_Configuration_and_Storage_By_Version_ID = "//div[@aria-dashboard='6']";
	public static final String WebLogic_Servers_by_JDK_Version_ID = "//div[@aria-dashboard='7']";
	public static final String Top_25_Databases_by_Resource_Consumption_ID = "//div[@aria-dashboard='8']";
	public static final String Top_25_WebLogic_Servers_by_Heap_Usage_ID = "//div[@aria-dashboard='9']";
	public static final String Top_25_WebLogic_Servers_by_Load_ID = "//div[@aria-dashboard='10']";
	public static final String Database_Health_Summary_ID = "//div[@aria-dashboard='11']";
	public static final String WebLogic_Health_Summary_ID = "//div[@aria-dashboard='12']";
	public static final String Host_Health_Summary_ID = "//div[@aria-dashboard='13']";
	
	public static final String SortDropListID = "//*[@id='ojChoiceId_sortcb_selected']";//ojChoiceId_sortcb";//ojChoiceId_sortcb_selected";//sortcb";
	public static final String Access_Date_ID = "/html/body/div/div/div/ul/li[2]/div";
	
	
	//search
	public static final String SearchDSBoxID = "sinput";
	
	//link/html/body/div[3]/header/div/div/div[3]/div/div/div/div[2]/div[2]/a
	public static final String HomeLinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div/div[2]/div[2]/a";
	//IT Analytics link
	public static final String ITALinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[2]/div[2]/div[2]/a";
	//Log Analytics link/html/body/div[3]/header/div/div/div[3]/div/div/div[2]/div[2]/div[2]/a /html/body/div[3]/header/div/div/div[3]/div/div/div[2]/div[2]/div[2]/a
	public static final String LALinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[2]/div[2]/div[3]/a";
	//APM link /html/body/div[3]/header/div/div/div[3]/div/div/div[2]/div[2]/div[3]/a
	public static final String APMLinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[2]/div[2]/div[4]/a";
	//Log link
	public static final String LOGLinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[3]/div[2]/div[2]/a";
	//AWR Analytics link
	public static final String AWRALinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[3]/div[2]/div[3]/a";
	//Flex link
	public static final String FlexLinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[3]/div[2]/div[4]/a";
	//Target link
	public static final String TargetLinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[3]/div[2]/div[5]/a";
	//Customer Software link
	public static final String CustomLinkID = "/html/body/div[*]/header/div/div/div[3]/div/div/div[4]/div[2]/div[2]/a";
	//IT Analytics Administration link
	public static final String ITA_Admin_LinkID ="/html/body/div[*]/header/div/div/div[3]/div/div/div[4]/div[2]/div[3]/a";
	
	//check box
	public static final String ITA_Check_BoxID = "itaopt";
	public static final String LA_BoxID = "laopt";
	public static final String APM_BoxID = "apmopt";
	public static final String Oracle_BoxID = "oracleopt";
	public static final String Other_BoxID = "otheropt";
	
	//id of dashboard name /html/body/div[2]/div/div/div/div/div/span/h1 /html/body/div[2]/div/div/div/div[2]/div/span/span,/html/body/div[3]/div/div/div/div/div/span/h1;/html/body/div[2]/div/div/div/div[2]/div/span/span
	public static final String DashboardNameID = "/html/body/div[*]/div/div/div/div/div/span/h1";
	public static final String MDashboardNameID = "/html/body/div[3]/div/div/div/div/div/span/h1";
	//id of dashboard desc
	public static final String DashboardDescID = "/html/body/div[*]/div/div/div/div[2]/div/span/span";
	public static final String MDashboardDescID = "/html/body/div[3]/div/div/div/div[2]/div/span/span";
	
	//remove dashboard/html/body/div[3]/div/div/div/div/div[2]/div[3]/div/div[2]/div[2]/button
	public static final String InfoBtnID = "/html/body/div[*]/div/div/div/div/div[2]/div[3]/div/div[2]/div[2]/button";
	public static final String RmBtnID = "/html/body/div/div/div/div/div/div/div[2]/button";
	
}
