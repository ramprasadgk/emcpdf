package oracle.sysman.emaas.platform.dashboards.test.ui;

import oracle.sysman.emaas.platform.dashboards.test.ui.util.DashBoardUtils;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.LoginAndLogout;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.PageId;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.DashBoardPageId;
import oracle.sysman.emaas.platform.dashboards.tests.ui.BrandingBarUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardBuilderUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardHomeUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.TimeSelectorUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.WelcomeUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.WaitUtil;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * @version
 * @author
 * @since release specific (what release of product did this appear in)
 */

public class BugVerification extends LoginAndLogout
{

	public void initTest(String testName)
	{
		login(this.getClass().getName() + "." + testName);
		DashBoardUtils.loadWebDriver(webd);
	}

	public void initTestCustom(String testName, String Username, String tenantName)
	{
		customlogin(this.getClass().getName() + "." + testName, Username, tenantName);
		DashBoardUtils.loadWebDriver(webd);

	}

	@AfterClass
	public void removeTestData()
	{
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: removeTestData");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to the grid view
		webd.getLogger().info("Swtich to the grid view");
		DashboardHomeUtil.gridView(webd);

		//remove the test data
		webd.getLogger().info("Start to remove the test data...");

		DashBoardUtils.deleteDashboard(webd, "Dashboard_EMCPDF2040");
		DashBoardUtils.deleteDashboard(webd, "Dashboard_EMCPDF2856");
		DashBoardUtils.deleteDashboard(webd, "DashboardSet_3660");
		DashBoardUtils.deleteDashboard(webd, "Dashboard_3660");
		DashBoardUtils.deleteDashboard(webd, "test EMCPDF_2975 with / character"); 

		webd.getLogger().info("All test data have been removed");

		LoginAndLogout.logoutMethod();
	}

	@Test(alwaysRun = true)
	public void testEMCPDF_2040()
	{
		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testEMCPDF_2040");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("Create a dashboard: no description, with time refresh");
		DashboardHomeUtil.createDashboard(webd, "Dashboard_EMCPDF2040", null, DashboardHomeUtil.DASHBOARD);
		webd.getLogger().info("verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, "Dashboard_EMCPDF2040", null, true),
				"Create dashboard failed!");

		//set the timepicker as Custom
		webd.getLogger().info("Set the custom date time");
		Assert.assertNotNull(TimeSelectorUtil.setCustomTime(webd, "04/14/2016 12:00 AM", "04/14/2016 12:30 PM"),
				"The restun date time is null");

	}

	@Test
	public void testEMCPDF_2425()
	{
		//login the dashboard with user emaastesttenant1_la_admin1
		initTestCustom(Thread.currentThread().getStackTrace()[1].getMethodName(), "emaastesttenant1_la_admin1", "emaastesttenant1");
		webd.getLogger().info("start to test in testEMCPDF_2425");
		WaitUtil.waitForPageFullyLoaded(webd);

		//reset all filter options
		webd.getLogger().info("Reset all filter options");
		DashboardHomeUtil.resetFilterOptions(webd);

		//create a dashboard set
		webd.getLogger().info("Create a dashboard set");
		DashboardHomeUtil.createDashboardSet(webd, "DashboardSet_2425", "test for sharing");

		//add a OOB dashboard to the set
		webd.getLogger().info("Add an OOB dashboard to the set");
		DashboardBuilderUtil.addNewDashboardToSet(webd, "Databases");

		//share the dashboard set
		webd.getLogger().info("Share the dashboard set");
		DashboardBuilderUtil.toggleShareDashboardset(webd);
	}

	@Test(dependsOnMethods = { "testEMCPDF_2425" })
	public void testEMCPDF_2425_1()
	{
		//login the dashboard with user emaastesttenant1_la_admin1
		initTestCustom(Thread.currentThread().getStackTrace()[1].getMethodName(), "emaastesttenant1_ita_admin1", "emaastesttenant1");
		webd.getLogger().info("start to test in testEMCPDF_2425_1");
		WaitUtil.waitForPageFullyLoaded(webd);

		//open the shared dashboard set
		webd.getLogger().info("Open the shared dashboard set");
		DashboardHomeUtil.selectDashboard(webd, "DashboardSet_2425");

		//verify the set
		webd.getLogger().info("Verify the dashboard set");
		DashboardBuilderUtil.verifyDashboardSet(webd, "DashboardSet_2425");
		DashboardBuilderUtil.verifyDashboardInsideSet(webd, "Databases");

	}

	/*@Test
	public void testEMCPDF_2855()
	{
		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testEMCPDF_2855");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//visit LA page
		BrandingBarUtil.visitApplicationCloudService(webd, BrandingBarUtil.NAV_LINK_TEXT_CS_LA);
                WaitUtil.waitForPageFullyLoaded(webd);

		String laCtx_url = webd.getWebDriver().getCurrentUrl();
		Assert.assertTrue(laCtx_url.contains("log-analytics-search"), "Failed to open the LA page");
		webd.getLogger().info("Start to test opening LA page...");

		//verify omcCtx exist in the LA url
		webd.getLogger().info("start to verify omcCtx exist in the LA page url");
		Assert.assertTrue(laCtx_url.contains("omcCtx="), "The global context infomation in URL is lost");

		//find notification button and click it to open notification page
		WebElement ntButton = webd.getWebDriver().findElement(By.xpath(PageId.NOTIFICATIONBUTTON_LA));
		Assert.assertTrue(ntButton.isDisplayed(), "Notiification button isn't displayed in the page.");
		webd.click(PageId.NOTIFICATIONBUTTON_LA);

		WaitUtil.waitForPageFullyLoaded(webd);

		//verify omcCtx exist in the Notification page url
		webd.switchToWindow();
		String lantCtx_url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("start to verify omcCtx exist in the Notification page url: " + lantCtx_url);
		Assert.assertTrue(lantCtx_url.contains("omcCtx="), "The global context infomation in URL is lost");

	}

	@Test
	public void testEMCPDF_2856()
	{
		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testEMCPDF_2856");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//create dashboard
		BrandingBarUtil.visitDashboardHome(webd);
		DashboardHomeUtil.gridView(webd);
		DashboardHomeUtil.createDashboard(webd, "Dashboard_EMCPDF2856", null);
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, "Dashboard_EMCPDF2856", null, true),
				"Create dashboard failed!");

		//set it as home
		webd.getLogger().info("Set home page");
		Assert.assertTrue(DashboardBuilderUtil.toggleHome(webd), "Set the Dashboard_EMCPDF2856 as Home failed!");

		//check home page
		webd.getLogger().info("Access to the home page");
		BrandingBarUtil.visitMyHome(webd);
		webd.getLogger().info("Verfiy the home page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, "Dashboard_EMCPDF2856", null, true),
				"It is NOT the home page!");

		//logout and login
		LoginAndLogout.logoutMethod();
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Logout and login");

		//visit welcome page
		webd.getLogger().info("Visit Welcome Page");
		BrandingBarUtil.visitWelcome(webd);
		Assert.assertFalse(GlobalContextUtil.isGlobalContextExisted(webd), "The global context exists in Welcome Page");

		//verify omcCtx exist in the Welcome page url
		String wCtx_url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("start to verify omcCtx exist in the welcome page url");
		Assert.assertTrue(wCtx_url.contains("omcCtx="), "The global context infomation in URL is lost");

		//visit home page
		webd.getLogger().info("Access to the home page");
		BrandingBarUtil.visitMyHome(webd);

		//set it not "home"
		webd.getLogger().info("Set Dashboard_EMCPDF2856 not home page");
		Assert.assertFalse(DashboardBuilderUtil.toggleHome(webd), "Remove the dasbhoard Dashboard_EMCPDF2856 as Home failed!");

		//check home page
		webd.getLogger().info("Access to the home page");
		BrandingBarUtil.visitMyHome(webd);
		webd.getLogger().info("Verfiy the home page");
		Assert.assertTrue(WelcomeUtil.isServiceExistedInWelcome(webd, WelcomeUtil.SERVICE_NAME_DASHBOARDS),
				"It is NOT the home page!");
	}*/

	@Test(alwaysRun = true)
	public void testEMPCDF_2970()
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testEMPCDF_2970");

		DashboardHomeUtil.createDashboard(webd, "~!@#$%^&*()-+", null);

		webd.takeScreenShot();

		DashboardBuilderUtil.verifyDashboard(webd, "~!@#$%^&*()-+", null, true);
		DashboardBuilderUtil.saveDashboard(webd);
		webd.takeScreenShot();
		BrandingBarUtil.visitDashboardHome(webd);
		webd.takeScreenShot();
		DashboardHomeUtil.gridView(webd);
		DashboardHomeUtil.deleteDashboard(webd, "~!@#$%^&*()-+", "dashboards_grid_view");
		webd.takeScreenShot();
		webd.getLogger().info("complete testing in testEMPCDF_2970");
	}

	@Test(alwaysRun = true)
	public void testEMPCDF_812_1()
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testEMPCDF_812");

		//reset all filter options
		DashboardHomeUtil.resetFilterOptions(webd);

		//check ita box
		DashboardHomeUtil.filterOptions(webd, "ita");

		//check la box
		DashboardHomeUtil.filterOptions(webd, "la");

		//signout menu
		webd.click(PageId.MENUBTNID);
		webd.click(PageId.SIGNOUTID);

		login(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testEMPCDF_812");

		//check ita box
		Assert.assertTrue(DashboardHomeUtil.isFilterOptionSelected(webd, "ita"));

		//check la box
		Assert.assertTrue(DashboardHomeUtil.isFilterOptionSelected(webd, "la"));

		//check ita box
		DashboardHomeUtil.filterOptions(webd, "ita");

		//check la box
		DashboardHomeUtil.filterOptions(webd, "la");

	}

	@Test(alwaysRun = true)
	public void testEMPCDF_832_1()
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testEMPCDF_832");

		String url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("current url = " + url);

		webd.getWebDriver().navigate()
				.to(url.substring(0, url.indexOf("emsaasui")) + "emsaasui/emcpdfui/error.html?msg=DBS_ERROR_PAGE_NOT_FOUND_MSG");
		webd.waitForElementPresent("css=" + PageId.ERRORPAGESINGOUTBTNCSS);
		webd.takeScreenShot();

		webd.click("css=" + PageId.ERRORPAGESINGOUTBTNCSS);
		webd.getLogger().info("Sing out button is clicked");
		webd.takeScreenShot();

		//initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		login(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), "welcome");
		webd.getLogger().info("welcome page is being loaded, going to to verify...");

		//DashboardHomeUtil.gridView(webd);
		//Assert.assertEquals(DashBoardUtils.getText(DashBoardPageId.WelcomeID),"Welcome to Oracle Management Cloud");

		WelcomeUtil.isServiceExistedInWelcome(webd, WelcomeUtil.SERVICE_NAME_DASHBOARDS);
		webd.getLogger().info("welcome page is verified successfully");
		webd.getLogger().info("complete testing in testEMPCDF_832");
	}

	@Test(alwaysRun = true)
    public void testEMCPDF_3120()
    {
		//Initialize the test
		//login the dashboard with emaastesttenantnoita and onboard OCS Service only
		initTestCustom(Thread.currentThread().getStackTrace()[1].getMethodName(), "emcsadmin", "emaastesttenantnoita");
		WaitUtil.waitForPageFullyLoaded(webd);
		webd.getLogger().info("Start the test case: testEMCPDF_3120");
                
		//verify the Explore Data menu is disabled
		webd.getLogger().info("Verify the Explore Data menu is not diplayed in the page");
		Assert.assertFalse(webd.isDisplayed("id=" + DashBoardPageId.EXPLOREDATABTNID), "Explore Data menu is displayed in dashboard");
	}   

	@Test(alwaysRun = true)
	public void testEMCPDF_3660() throws Exception
	{
		int newdsb_idx = 1;
 		int OOB_idx = 2;
		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testEMCPDF_3660");
		WaitUtil.waitForPageFullyLoaded(webd);

		//reset all filter options
		webd.getLogger().info("Reset all filter options");
		DashboardHomeUtil.resetFilterOptions(webd);

		//create a dashboard set
		webd.getLogger().info("Create a dashboard set");
		DashboardHomeUtil.createDashboardSet(webd, "DashboardSet_3660", "test for set custom time range for dashboard in the set");
		WaitUtil.waitForPageFullyLoaded(webd);

		//add a OOB dashboard to the set
		webd.getLogger().info("Add an OOB dashboard to the set");
		DashboardBuilderUtil.addNewDashboardToSet(webd, "Databases");
		WaitUtil.waitForPageFullyLoaded(webd);

		//create one new dashboard in the same set
		webd.getLogger().info("Create a dashboard inside dashboard set");
		webd.getLogger().info("Click Add Dashboard Icon");
		webd.click("css=" + PageId.DASHBOARDSETADDDASHBOARDICON_CSS);
		DashboardBuilderUtil.createDashboardInsideSet(webd, "Dashboard_3660", null);
		WaitUtil.waitForPageFullyLoaded(webd);

		//verify the dashboard set and dashborad are created successfully
		webd.getLogger().info("Verify the dashboard set is created successfully");
		DashboardBuilderUtil.verifyDashboardSet(webd, "DashboardSet_3660");

		//verify the dashboard is created successfully and in the set
		DashboardBuilderUtil.verifyDashboardInsideSet(webd, "Databases");
		DashboardBuilderUtil.verifyDashboardInsideSet(webd, "Dashboard_3660");

		//verify the new created dashboard is not displayed on home page
		webd.getLogger().info("Navigate to the dashboard home page and verify the new created dashboard is not displayed");
		BrandingBarUtil.visitDashboardHome(webd);
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, "Dashboard_3660"), "Expected dashboard Dashboard_3660 is found in dashboard home page");

		//select the new created dashboardset and disable auto refresh
		webd.getLogger().info("Open the new created dashboard set");
		BrandingBarUtil.visitDashboardHome(webd);
		DashboardHomeUtil.selectDashboard(webd, "DashboardSet_3660");
		WaitUtil.waitForPageFullyLoaded(webd);

		webd.getLogger().info("Set the refresh setting to OFF");
		DashboardBuilderUtil.refreshDashboardSet(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_OFF);

		//set & verify custom time range for added OOB database dashboard
		webd.getLogger().info("Select the added OOB databaase dashboard in set");
		DashboardBuilderUtil.selectDashboardInsideSet(webd, "Databases");
		WaitUtil.waitForPageFullyLoaded(webd);
		Assert.assertNotNull(TimeSelectorUtil.setCustomTime(webd, OOB_idx, "04/07/2016 12:00 AM", "04/14/2016 12:30 PM"), "The return date time is null");
		Assert.assertEquals(TimeSelectorUtil.getTimeRangeLabel(webd, OOB_idx).contains("Custom"), true);

		//set & verify custom time range for new added dashboard
		webd.getLogger().info("Select the new created dashboard in set");
		DashboardBuilderUtil.selectDashboardInsideSet(webd, "Dashboard_3660");
		WaitUtil.waitForPageFullyLoaded(webd);
		Assert.assertNotNull(TimeSelectorUtil.setCustomTime(webd, newdsb_idx, "05/08/2016 12:00 AM", "05/15/2016 13:30 PM"), "The return date time is null");
		Assert.assertEquals(TimeSelectorUtil.getTimeRangeLabel(webd, newdsb_idx).contains("Custom"), true);

	}
    
    @Test(alwaysRun = true)
    public void testEMCPDF_4039()
    {
    	//Initialize the test
    	initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
    	webd.getLogger().info("Start the test case: testEMCPDF_4039");
    		
    	//reset the home page
    	webd.getLogger().info("Reset all filter options in the home page");
    	DashboardHomeUtil.resetFilterOptions(webd);

    	//switch to Grid View
    	webd.getLogger().info("Switch to grid view");
    	DashboardHomeUtil.gridView(webd);
    		
    	//check if hamburger menu enabled
    	if(DashBoardUtils.isHamburgerMenuEnabled(webd))
    	{
    		//expand sub menu for Administrator
    		webd.getLogger().info("Expand submenu of Admin");
    		BrandingBarUtil.expandSubMenu(webd, BrandingBarUtil.ROOT_MENU_ADMIN);
    		String currenMenuHeader = BrandingBarUtil.getCurrentMenuHeader(webd);
    		Assert.assertEquals(currenMenuHeader.trim(), "Administration");
    			
    		//below to verify the fix for EMCPDF-4039
    		webd.getLogger().info("Hide the hamburger menu");
    		Assert.assertFalse(BrandingBarUtil.toggleHamburgerMenu(webd) , "Hamburger menu should be hidden");
    		webd.getLogger().info("Display the hamburger menu");
    		Assert.assertTrue(BrandingBarUtil.toggleHamburgerMenu(webd) , "Hamburger menu should be displayed");
    		webd.getLogger().info("Check the current hamburger menu");
    		currenMenuHeader = BrandingBarUtil.getCurrentMenuHeader(webd);
    		Assert.assertEquals(currenMenuHeader.trim(), BrandingBarUtil.ROOT_MENU_TITLE);
    			
    		//back to the dashboard home page
    		webd.getLogger().info("Navigate to Dashboard Home page");
    		BrandingBarUtil.clickMenuItem(webd, BrandingBarUtil.ROOT_MENU_DASHBOARDS);
    			
    		//open APM oob dashboard
    		webd.getLogger().info("Open APM oob dashboard");
    		DashboardHomeUtil.selectDashboard(webd, "Application Performance Monitoring");

			if(DashBoardUtils.isHamburgerMenuEnabled(webd))
			{
				webd.getLogger().info("Verify in APM page");    			
		    		//below to verify the fix for EMCPDF-4039
				if(BrandingBarUtil.isHamburgerMenuDisplayed(webd))
				{
		    		webd.getLogger().info("Hide the hamburger menu");
		    		Assert.assertFalse(BrandingBarUtil.toggleHamburgerMenu(webd) , "Hamburger menu should be hidden");
				}
		    	webd.getLogger().info("Display the hamburger menu");
		    	Assert.assertTrue(BrandingBarUtil.toggleHamburgerMenu(webd) , "Hamburger menu should be displayed");
		    	webd.getLogger().info("Check the current hamburger menu");
		    	currenMenuHeader = BrandingBarUtil.getCurrentMenuHeader(webd);
		    	Assert.assertEquals(currenMenuHeader.trim(), "APM");
			}
			else
			{
				webd.getLogger().info("Hamburger menu is not enabled in APM, do need to verify the fix for EMCPDF-4039");
			}		
    	}
    	else
    	{
    		webd.getLogger().info("Hamburger menu is not enabled, do need to verify the fix for EMCPDF-4039");
    	}
    }
    
    @Test(alwaysRun = true)
    public void testEMCPDF_2975()
    {
    	String dbName = "test EMCPDF_2975 with / character";
		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testEMCPDF_2975");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("Create a dashboard: no description, with time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName, null, DashboardHomeUtil.DASHBOARD);
		webd.getLogger().info("verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName, null, true),
				"Create dashboard failed!");
		
		webd.getLogger().info("Add Widget to the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, "Donut");
		
		webd.getLogger().info("Save the dashboard");
		DashboardBuilderUtil.saveDashboard(webd);
		
		webd.getLogger().info("Open the Widget");
		DashboardBuilderUtil.openWidget(webd, "Donut");
		
		webd.getLogger().info("Back to Dashboard builder page");
		webd.click("css=#linkHeader>a>span");
		
		webd.getLogger().info("Verify the dashboard");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName, null, true),
				"Verify dashboard failed!");
    	
    }
}
