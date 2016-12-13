/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.test.ui;

import oracle.sysman.emaas.platform.dashboards.test.ui.util.DashBoardUtils;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.LoginAndLogout;
import oracle.sysman.emaas.platform.dashboards.tests.ui.BrandingBarUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardBuilderUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardHomeUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.WelcomeUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.WaitUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author shangwan
 */
public class TestDashboardSet_OtherFeatures extends LoginAndLogout
{
	private String dbsetName = "";
	private String dbsetName_setHome = "";
	private String dbsetName_Favorite = "";
	private String dbsetName_Share = "";
	private String dbsetName_ITA = "";
	private String dbsetName_LA = "";
	private String dbset_testMaximizeRestore = "";
	private String dbName_indbSet = "";

	private static String OOBAddToSet = "Database Operations";

	@BeforeClass
	public void createTestData()
	{
		dbsetName = "DashboardSet_FeatureTest-" + generateTimeStamp();
		String dbsetDesc = "Test the dashboard set";

		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: createTestData");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboardset
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbsetName, dbsetDesc, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboardset
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName), "Dashboard set NOT found!");

		LoginAndLogout.logoutMethod();
	}

	public void initTest(String testName)
	{
		login(this.getClass().getName() + "." + testName);
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
		DashBoardUtils.deleteDashboard(webd, dbsetName_setHome);
		DashBoardUtils.deleteDashboard(webd, dbsetName_Favorite);
		DashBoardUtils.deleteDashboard(webd, dbsetName);
		DashBoardUtils.deleteDashboard(webd, dbsetName_ITA);
		DashBoardUtils.deleteDashboard(webd, dbsetName_LA);
		DashBoardUtils.deleteDashboard(webd, dbsetName_Share);
		DashBoardUtils.deleteDashboard(webd, dbset_testMaximizeRestore);
		DashBoardUtils.deleteDashboard(webd, dbName_indbSet);

		webd.getLogger().info("All test data have been removed");

		LoginAndLogout.logoutMethod();
	}

	@Test(groups = "first run")
	public void testFavorite()
	{
		dbsetName_Favorite = "DashboardSet_Favorite-" + generateTimeStamp();
		String dbsetDesc = "set the dashboard set as favorite";

		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testFavorite");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboardset
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbsetName_Favorite, dbsetDesc, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboardset
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_Favorite), "Dashboard set NOT found!");

		//set the dashboard set as favorite
		webd.getLogger().info("Set the dashboard as favorite");
		Assert.assertTrue(DashboardBuilderUtil.favoriteOptionDashboardSet(webd), "Set the dashboard set as favorite failed!");

		//verify the dashboard set if set as favorite
		webd.getLogger().info("Click my favorite link in the branding bar");
		BrandingBarUtil.visitMyFavorites(webd);
		WaitUtil.waitForPageFullyLoaded(webd);
		webd.getLogger().info("Verify if the dashboard set is favorite");
		Assert.assertTrue(DashboardHomeUtil.isFilterOptionSelected(webd, "favorites"), "My Favorites options is NOT checked!");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbsetName_Favorite), "NOT find the dashboard set");

		//open the dashboard set and remove the favorite
		webd.getLogger().info("Open the dashboard set in builder page");
		DashboardHomeUtil.selectDashboard(webd, dbsetName_Favorite);
		webd.getLogger().info("Set the dashboard set as not favorite");
		Assert.assertFalse(DashboardBuilderUtil.favoriteOptionDashboardSet(webd), "Set the dashboard set as NOT favorite failed!");

		//verify the dashboard set if not as favorite
		webd.getLogger().info("Click my favorite link in the branding bar");
		BrandingBarUtil.visitMyFavorites(webd);
		WaitUtil.waitForPageFullyLoaded(webd);
		webd.getLogger().info("Verify if the dashboard set is not favorite");
		Assert.assertTrue(DashboardHomeUtil.isFilterOptionSelected(webd, "favorites"), "My Favorites options is NOT checked!");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, dbsetName_Favorite), "dashboard set is still favorite");
	}

	@Test(groups = "first run")
	public void testFilterITADashboardSet()
	{
		dbsetName_ITA = "DashboardSet-ITA-" + generateTimeStamp();

		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testFilterITADashboardSet");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboardset
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbsetName_ITA, null, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboardset
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_ITA), "Dashboard set NOT found!");

		//add the ITA dashboard into the dashboard set
		webd.getLogger().info("Add a ITA oob dashboard into the set");
		DashboardBuilderUtil.addNewDashboardToSet(webd, "Categorical");

		//back to the home page
		webd.getLogger().info("Back to dashboard home page");
		BrandingBarUtil.visitDashboardHome(webd);

		//set filter option, cloud services="IT Analytics" created by ME
		webd.getLogger().info("set filter option, cloud services='IT Analytics' and Created by ME");
		DashboardHomeUtil.filterOptions(webd, "ita");
		DashboardHomeUtil.filterOptions(webd, "me");
		webd.getLogger().info("Verify the created dashboard exists");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbsetName_ITA), "The dashboard NOT exists");

		//reset filter options
		webd.getLogger().info("Reset filter options");

	}

	@Test(groups = "first run")
	public void testFilterLADashboardSet()
	{
		dbsetName_LA = "DashboardSet-LA-" + generateTimeStamp();

		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testFilterLADashboardSet");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboardset
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbsetName_LA, null, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboardset
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_LA), "Dashboard set NOT found!");

		//add the ITA dashboard into the dashboard set
		webd.getLogger().info("Add a ITA oob dashboard into the set");
		DashboardBuilderUtil.addNewDashboardToSet(webd, "Database Operations");

		//back to the home page
		webd.getLogger().info("Back to dashboard home page");
		BrandingBarUtil.visitDashboardHome(webd);

		//set filter option, cloud services="IT Analytics" created by ME
		webd.getLogger().info("set filter option, cloud services='Log Analytics' and Created by ME");
		DashboardHomeUtil.filterOptions(webd, "la");
		DashboardHomeUtil.filterOptions(webd, "me");
		webd.getLogger().info("Verify the created dashboard exists");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbsetName_LA), "The dashboard NOT exists");

		//reset filter options
		webd.getLogger().info("Reset filter options");
	}

	//test maxmize/restore widget in OOB Dashboard Set
	@Test(groups = "second run", dependsOnGroups = { "first run" })
	public void testMaximizeRestoreWidget_OOB_DbSet()
	{
		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testMaximize/RestoreWidget");

		//open the OOB Dashboard Set, eg: Exadata Health in the dashboard home page
		webd.getLogger().info("open the dashboard set");
		DashboardHomeUtil.selectDashboard(webd, "Exadata Health");

		//widget operation
		webd.getLogger().info("maximize/restore the widget");
		DashboardBuilderUtil.selectDashboardInsideSet(webd, "Overview");
		DashboardBuilderUtil.maximizeWidget(webd, "Entities by Database Machine", 0);
		DashboardBuilderUtil.restoreWidget(webd, "Entities by Database Machine", 0);

		//verify the edit/add button not displayed in the page
		WebElement addButton = webd.getWebDriver().findElement(By.xpath("//button[@title='Add Content']"));
		Assert.assertFalse(addButton.isDisplayed(), "Add button be displayed in system dashboard set");

		WebElement editButton = webd.getWebDriver().findElement(By.xpath("//button[@title='Edit Settings']"));
		Assert.assertFalse(editButton.isDisplayed(), "Edit button be displayed in system dashboard set");
	}

	//test maxmize/restore widget in self created dashboard set, and self creating a dashboard to test add/edit button
	@Test(groups = "second run", dependsOnMethods = { "testMRWidgetSelfDbSet_sysBb" })
	public void testMRWidgetSelfDbSet_selfBb()
	{
		dbName_indbSet = "selfDb-" + generateTimeStamp();

		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testMaximize/RestoreWidget in selt create db set");

		//open the created dashboard set
		webd.getLogger().info("select and open the dashboard set");
		DashboardHomeUtil.selectDashboard(webd, dbset_testMaximizeRestore);

		//add self created dashboard to the dashboard set
		webd.getLogger().info("Add another dashboard to set: create a new dashboard to this set");
		DashboardBuilderUtil.createDashboardInsideSet(webd, dbName_indbSet, "");

		webd.getLogger().info("Verify if the dashboard existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardInsideSet(webd, dbName_indbSet),
				"Creat dashboard in dashboard set failed!");

		//Add widget
		webd.getLogger().info("Start to add Widget into the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, "Access Log Error Status Codes");
		DashboardBuilderUtil.addWidgetToDashboard(webd, "All Logs Trend");
		webd.getLogger().info("Add widget finished");

		//verify if the widget added successfully
		Assert.assertTrue(DashboardBuilderUtil.verifyWidget(webd, "Access Log Error Status Codes"),
				"Widget 'Access Log Error Status Codes' not found");
		Assert.assertTrue(DashboardBuilderUtil.verifyWidget(webd, "All Logs Trend"), "Widget 'All Logs Trend' not found");

		//save dashboard
		webd.getLogger().info("save the dashboard");
		DashboardBuilderUtil.saveDashboard(webd);

		//widget operation
		webd.getLogger().info("maximize/restore the widget");
		DashboardBuilderUtil.maximizeWidget(webd, "Access Log Error Status Codes", 0);
		Assert.assertFalse(webd.isDisplayed("css=" + ".dbd-widget[data-tile-name=\"All Logs Trend\"]"),
				"Widget 'All Logs Trend' is still displayed");

		DashboardBuilderUtil.restoreWidget(webd, "Access Log Error Status Codes", 0);
		Assert.assertTrue(webd.isDisplayed("css=" + ".dbd-widget[data-tile-name=\"All Logs Trend\"]"),
				"Widget 'All Logs Trend' is not displayed");

		//verify the edit/add button displayed in the page
		WebElement addButton2 = webd.getWebDriver().findElement(By.xpath("//button[@title='Add Content']"));
		Assert.assertTrue(addButton2.isDisplayed(), "Add button isn't displayed in self dashboard which in self dashboard set");

		WebElement editButton2 = webd.getWebDriver().findElement(By.xpath("//button[@title='Edit Settings']"));
		Assert.assertTrue(editButton2.isDisplayed(), "Edit button isn't displayed in self dashboard which in self dashboard set");
	}

	//test maxmize/restore widget in self created dashboard set, and select a system dashboard to test edit button
	@Test(groups = "second run", dependsOnGroups = { "first run" })
	public void testMRWidgetSelfDbSet_sysBb()
	{
		dbset_testMaximizeRestore = "selfDbSet-" + generateTimeStamp();

		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testMaximize/RestoreWidget in selt create db set");

		//Create dashboard set
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbset_testMaximizeRestore, null, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboard set
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbset_testMaximizeRestore),
				"Create dashboard set failed!");

		//Select a dashboard and open it
		webd.getLogger().info("select and open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, "Application Servers");

		//widget operation
		webd.getLogger().info("maximize/restore the widget");
		DashboardBuilderUtil.maximizeWidget(webd, "Application Server Status", 0);
		DashboardBuilderUtil.restoreWidget(webd, "Application Server Status", 0);

		//verify the add button not displayed in the page
		WebElement addButton1 = webd.getWebDriver().findElement(By.xpath("//button[@title='Add Content']"));
		Assert.assertFalse(addButton1.isDisplayed(), "Add button be displayed in system dashboard set");

		//verify the edit button displayed in the page
		WebElement editButton1 = webd.getWebDriver().findElement(By.xpath("//button[@title='Edit Settings']"));
		Assert.assertTrue(editButton1.isDisplayed(), "Edit button isn't displayed in self dashboard set");
	}

	@Test(groups = "first run")
	public void testPrint()
	{
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testPrint");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//open the dashboardset
		webd.getLogger().info("Open the dashboard set in builder page");
		DashboardHomeUtil.selectDashboard(webd, dbsetName);

		//print the dashboard set
		webd.getLogger().info("Print the dashboard set");
		DashboardBuilderUtil.printDashboardSet(webd);
	}

	@Test(groups = "first run")
	public void testRefresh()
	{
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testRefresh");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//open the dashboardset
		webd.getLogger().info("Open the dashboard in the builder page");
		DashboardHomeUtil.selectDashboard(webd, dbsetName);

		//verify the refresh status
		webd.getLogger().info("Verify the defaul refresh setting for the dashboard is 5 mins");
		Assert.assertTrue(DashboardBuilderUtil.isRefreshSettingCheckedForDashbaordSet(webd,
				DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_5MIN), "the default setting is not ON(5 mins)");

		//set the refresh setting to 5 min
		webd.getLogger().info("Set the refresh setting to OFF");
		DashboardBuilderUtil.refreshDashboardSet(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_OFF);
		webd.getLogger().info("Verify the refresh setting for the dashboard is OFF");
		Assert.assertTrue(DashboardBuilderUtil.isRefreshSettingCheckedForDashbaordSet(webd,
				DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_OFF), "the setting is not OFF");

		//set the refresh setting to OFF
		webd.getLogger().info("Set the refresh setting to 5 mins");
		DashboardBuilderUtil.refreshDashboardSet(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_5MIN);
		webd.getLogger().info("Verify the refresh setting for the dashboard is 5 mins");
		Assert.assertTrue(DashboardBuilderUtil.isRefreshSettingCheckedForDashbaordSet(webd,
				DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_5MIN), "the setting is not ON(5 mins)");
	}

	@Test(groups = "last run", dependsOnMethods = { "testSetHome" })
	public void testRemoveHome()
	{
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testRemoveHome");

		//verify is in the builder page
		webd.getLogger().info("Start the test case: testSetHome");
		String url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("current url = " + url);
		if (!url.substring(url.indexOf("emsaasui") + 9).contains("builder.html?dashboardId=")) {
			Assert.fail("not open the builder page");
		}
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_setHome), "Not the correct home page");

		//remove the home page
		DashboardBuilderUtil.toggleHomeDashboardSet(webd);
		//verify the home page
		webd.getLogger().info("Go to home page and verify if the dashboard set is home page");
		BrandingBarUtil.visitMyHome(webd);
		Assert.assertTrue(WelcomeUtil.isServiceExistedInWelcome(webd, WelcomeUtil.SERVICE_NAME_DASHBOARDS),
				"It is NOT the home page!");

	}

	@Test(groups = "last run", dependsOnGroups = { "second run" })
	public void testSetHome()
	{
		dbsetName_setHome = "DashboardSet_TestSetHome-" + generateTimeStamp();
		String dbsetDesc = "Test the dashboardset set home";

		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testSetHome");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboardset
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbsetName_setHome, dbsetDesc, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboardset
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_setHome), "Dashboard set NOT found!");

		//set the dashboard as home
		webd.getLogger().info("Set the created dashborad set as Home");
		DashboardBuilderUtil.toggleHomeDashboardSet(webd);

		//verify the home page
		webd.getLogger().info("Go to home page and verify if the dashboard set is home page");
		BrandingBarUtil.visitMyHome(webd);
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_setHome), "DashboarSet is NOT set to home page");
	}

	@Test(groups = "first run")
	public void testShare()
	{
		dbsetName_Share = "DashboardSet-Share-" + generateTimeStamp();
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testShare");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboardset
		webd.getLogger().info("Create a new dashboard set");
		DashboardHomeUtil.createDashboard(webd, dbsetName_Share, null, DashboardHomeUtil.DASHBOARDSET);

		//verify the dashboardset
		webd.getLogger().info("Verify if the dashboard set existed in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboardSet(webd, dbsetName_Share), "Dashboard set NOT found!");

		//add the ITA dashboard into the dashboard set
		webd.getLogger().info("Add a ITA oob dashboard into the set");
		DashboardBuilderUtil.addNewDashboardToSet(webd, "Categorical");

		//back to the home page
		webd.getLogger().info("Back to dashboard home page");
		BrandingBarUtil.visitDashboardHome(webd);

		//open the dashboardset
		webd.getLogger().info("Open the dashboard in the builder page");
		DashboardHomeUtil.selectDashboard(webd, dbsetName_Share);

		//share the dashboardset
		webd.getLogger().info("Share the dashboard");
		Assert.assertTrue(DashboardBuilderUtil.toggleShareDashboardset(webd), "Share the dashboard set failed!");

	}

	@Test(groups = "first run")
	public void testShareWithoutDashboardInSet()
	{
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testShareWithoutDashboardInSet");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//open the dashboardset
		webd.getLogger().info("Open the dashboard in the builder page");
		DashboardHomeUtil.selectDashboard(webd, dbsetName);

		webd.getLogger().info("Set the refresh setting to OFF");
		DashboardBuilderUtil.refreshDashboardSet(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_OFF);

		WaitUtil.waitForPageFullyLoaded(webd);

		//verify the share options are diabled
		Assert.assertTrue(DashBoardUtils.verfiyShareOptionDisabled(), "The options are enabled!");
	}

	@Test(groups = "first run", dependsOnMethods = { "testShare" })
	public void testStopSharing()
	{
		//init the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testStopSharing");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to grid view
		webd.getLogger().info("Switch to the grid view");
		DashboardHomeUtil.gridView(webd);

		//open the dashboardset
		webd.getLogger().info("Open the dashboard in the builder page");
		DashboardHomeUtil.selectDashboard(webd, dbsetName_Share);

		//share the dashboardset
		webd.getLogger().info("Share the dashboard");
		Assert.assertFalse(DashboardBuilderUtil.toggleShareDashboardset(webd), "Stop sharing the dashboard set failed!");

	}

	private String generateTimeStamp()
	{
		return String.valueOf(System.currentTimeMillis());
	}
}
