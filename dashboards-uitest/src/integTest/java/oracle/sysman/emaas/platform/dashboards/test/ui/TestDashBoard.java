package oracle.sysman.emaas.platform.dashboards.test.ui;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.sysman.emaas.platform.dashboards.test.ui.util.DashBoardUtils;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.LoginAndLogout;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.PageId;
import oracle.sysman.emaas.platform.dashboards.tests.ui.BrandingBarUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardBuilderUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardHomeUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.TimeSelectorUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.TimeSelectorUtil.TimeRange;
import oracle.sysman.emaas.platform.dashboards.tests.ui.WelcomeUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.WidgetSelectorUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.DashBoardPageId;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.WaitUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * @version
 * @author charles.c.chen
 * @since release specific (what release of product did this appear in)
 */

public class TestDashBoard extends LoginAndLogout
{
	private String dbName_noDesc = "";
	private String dbName_noWidgetGrid = "";
	private String dbName_noWidgetList = "";
	private String dbName_withWidgetGrid = "";
	private String dbName_setHome = "";
	private String dbName_favorite = "";
	private String dbName_timepicker = "";

	public void initTest(String testName) throws Exception
	{
		login(this.getClass().getName() + "." + testName);
		DashBoardUtils.loadWebDriver(webd);
	}

	@AfterClass
	public void RemoveDashboard() throws Exception
	{
		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to remove test data");

		//delete dashboard
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		webd.getLogger().info("Delete dashboard: " + dbName_setHome);
		DashboardHomeUtil.search(webd, dbName_setHome);
		if (DashboardHomeUtil.isDashboardExisted(webd, dbName_setHome)) {
			DashboardHomeUtil.deleteDashboard(webd, dbName_setHome, DashboardHomeUtil.DASHBOARDS_GRID_VIEW);
		}
		webd.getLogger().info("verify if the dashboard has been deleted");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, dbName_setHome), "Dashboard NOT removed");

		webd.getLogger().info("Delete dashboard: " + dbName_timepicker);
		DashboardHomeUtil.search(webd, dbName_timepicker);
		if (DashboardHomeUtil.isDashboardExisted(webd, dbName_timepicker)) {
			DashboardHomeUtil.deleteDashboard(webd, dbName_timepicker, DashboardHomeUtil.DASHBOARDS_GRID_VIEW);
		}
		webd.getLogger().info("verify if the dashboard has been deleted");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, dbName_timepicker), "Dashboard NOT removed");

		webd.getLogger().info("Delete dashboard: TestSaveConfirmation");
		DashboardHomeUtil.search(webd, "TestSaveConfirmation");
		if (DashboardHomeUtil.isDashboardExisted(webd, "TestSaveConfirmation")) {
			DashboardHomeUtil.deleteDashboard(webd, "TestSaveConfirmation", DashboardHomeUtil.DASHBOARDS_GRID_VIEW);
		}
		webd.getLogger().info("verify if the dashboard has been deleted");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, "TestSaveConfirmation"), "Dashboard NOT removed");

		webd.getLogger().info("Delete dashboard: Test_Dashboard_duplicate");
		DashboardHomeUtil.search(webd, "Test_Dashboard_duplicate");
		if (DashboardHomeUtil.isDashboardExisted(webd, "Test_Dashboard_duplicate")) {
			DashboardHomeUtil.deleteDashboard(webd, "Test_Dashboard_duplicate", DashboardHomeUtil.DASHBOARDS_GRID_VIEW);
		}
		webd.getLogger().info("verify if the dashboard has been deleted");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, "Test_Dashboard_duplicate"), "Dashboard NOT removed");

		webd.getLogger().info("all dashboards have been deleted");
	}

	@Test
	public void testCreateDashboad_noDesc_GridView() throws Exception
	{
		dbName_noDesc = "NoDesc-" + generateTimeStamp();

		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test case: testCreateDashboad_noDesc_GridView");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("Create a dashboard: no description, with time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName_noDesc, null, DashboardHomeUtil.DASHBOARD);
		webd.getLogger().info("verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_noDesc, null, true), "Create dashboard failed!");

		//verify the refresh option
		webd.getLogger().info("Verify the default refersh option is 5 mins");
		Assert.assertTrue(
				DashboardBuilderUtil.isRefreshSettingChecked(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_5MIN),
				"Refresh option is NOT set to 5 mins!");

		webd.getLogger().info("Turn off the refresh option and check the option is checked");
		DashboardBuilderUtil.refreshDashboard(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_OFF);
		Assert.assertTrue(
				DashboardBuilderUtil.isRefreshSettingChecked(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_OFF),
				"Refresh option is NOT set to OFF!");

		webd.getLogger().info("Turn on the refresh option to 5 mins and check the option is checked");
		DashboardBuilderUtil.refreshDashboard(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_5MIN);
		Assert.assertTrue(
				DashboardBuilderUtil.isRefreshSettingChecked(webd, DashboardBuilderUtil.REFRESH_DASHBOARD_SETTINGS_5MIN),
				"Refresh option is NOT set to 5 mins!");

		//delete the dashboard
		webd.getLogger().info("start to delete dashboard in builder page");
		DashboardBuilderUtil.deleteDashboard(webd);
	}

	@Test
	public void testCreateDashboad_noWidget_GridView() throws Exception
	{
		dbName_noWidgetGrid = "NoWidgetGridView-" + generateTimeStamp();
		String dbDesc = "Test Dashboard no Widget description";

		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testCreateDashboad_noWidget_GridView");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("Create a dashboard: with description, time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName_noWidgetGrid, dbDesc, DashboardHomeUtil.DASHBOARD);
		webd.getLogger().info("verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_noWidgetGrid, dbDesc, true),
				"Create dashboard failed!");
	}

	@Test
	public void testCreateDashboard_noWidget_ListView() throws Exception
	{
		dbName_noWidgetList = "noWidgetListView-" + generateTimeStamp();
		String dbDesc = "Test Dashboard no Widget description";

		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testCreateDashboard_noWidget_ListView");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to list view");
		DashboardHomeUtil.listView(webd);

		//create dashboard
		webd.getLogger().info("create a dashboard: with description, time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName_noWidgetList, dbDesc, DashboardHomeUtil.DASHBOARD);
		webd.getLogger().info("verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_noWidgetList, dbDesc, true),
				"Create dashboard failed!");

	}

	@Test
	public void testCreateDashboard_withWidget_GridView() throws Exception
	{
		dbName_withWidgetGrid = "withWidget-" + generateTimeStamp();
		String dbDesc = "AAA_testDashBoard desc";

		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testCreateDashBoard");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//create dashboard
		webd.getLogger().info("Start to create dashboard in grid view");
		DashboardHomeUtil.gridView(webd);
		DashboardHomeUtil.createDashboard(webd, dbName_withWidgetGrid, dbDesc, DashboardHomeUtil.DASHBOARD);

		//verify dashboard in builder page
		webd.getLogger().info("Verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_withWidgetGrid, dbDesc, true),
				"Create dashboard failed!");

		//add widget
		webd.getLogger().info("Start to add Widget into the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, "Database Errors Trend");
		webd.getLogger().info("Add widget finished");

		//verify if the widget added successfully
		Assert.assertTrue(DashboardBuilderUtil.verifyWidget(webd, "Database Errors Trend"),
				"Widget 'Database Errors Trend' not found");

		//save dashboard
		webd.getLogger().info("save the dashboard");
		DashboardBuilderUtil.saveDashboard(webd);

		//open the widget
		webd.getLogger().info("open the widget");
		DashboardBuilderUtil.openWidget(webd, "Database Errors Trend");
		webd.switchToWindow();

		String url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("url = " + url);
		if (!url.substring(url.indexOf("emsaasui") + 9).contains(
				"emlacore/html/log-analytics-search.html?widgetId=2013&dashboardId")) {
			Assert.fail("NOT open the correct widget");
		}
	}

	@Test
	public void testDeleteOOB() throws Exception
	{
		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testUserMenu");

		//reset all filter options
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);
		//switch to grid view
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//check OOB delete protection
		webd.getLogger().info("Verfiy if the OOB dashboard can be deleted");
		DashboardHomeUtil.search(webd, "Application Performance");
		webd.click(DashBoardPageId.InfoBtnID);
		WebElement removeButton = webd.getWebDriver().findElement(By.xpath(DashBoardPageId.RmBtnID));
		Assert.assertFalse(removeButton.isEnabled(), "delete is enabled for OOB dashboard");
	}

	@Test(dependsOnMethods = { "testCreateDashboad_noWidget_GridView", "testModifyDashboard_namedesc" })
	public void testDuplicateDashboard() throws Exception
	{

		String dbName = "Test_Dashboard_duplicate";
		String dbDesc = "Test_Dashboard_duplicate_desc";

		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testDuplicateDashboard");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//search the dashboard and open it in builder page
		webd.getLogger().info("search the dashboard");
		DashboardHomeUtil.search(webd, dbName_noWidgetGrid + "-modify");
		webd.getLogger().info("verify the dashboard is existed");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid + "-modify"), "Dashboard NOT found");

		//open the dashboard
		webd.getLogger().info("open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName_noWidgetGrid + "-modify");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_noWidgetGrid + "-modify",
				"Test_Dashboard_no_Widget_GridView desc modify", true), "Dashboard NOT found");

		//Duplicate dashbaord
		DashboardBuilderUtil.duplicateDashboard(webd, dbName, dbDesc);
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName, dbDesc, true), "Duplicate failed!");

	}

	/*@Test
	public void testErrorPage() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testErrorPage");

		//ErrorPage link
		//BrandingBarUtil.visitApplicationCloudService(webd, BrandingBarUtil.NAV_LINK_TEXT_Erpge);
		String url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("url = " + url);
	           	webd.getWebDriver().navigate()
	                             .to(url.substring(0, url.indexOf("emsaasui")) + "emcpdfui/error.html?invalidUrl=https%3A%2F%2Fden00bve.us.oracle.com%3A4443%2Femsaasui%2Femcpdfui%2Fbuilder.html%3FdashboardId%3D80");

		//Assert.assertEquals(url.substring(url.indexOf("emsaasui") + 9), "emcpdfui/builder.html?dashboardId=80");
		WaitUtil.waitForPageFullyLoaded(webd);
		//validating Error page
		ErrorPageUtil.signOut(webd);
	}*/

	@Test
	public void testFavorite() throws Exception
	{
		dbName_favorite = "favoriteDashboard-" + generateTimeStamp();
		String dbDesc = "favorite_testDashboard desc";

		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testFavorite");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("Create a dashboard: with description, time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName_favorite, dbDesc, DashboardHomeUtil.DASHBOARD);

		//verify dashboard in builder page
		webd.getLogger().info("Verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_favorite, dbDesc, true), "Create dashboard failed!");

		//set it to favorite
		webd.getLogger().info("Set the dashboard to favorite");
		Assert.assertTrue(DashboardBuilderUtil.favoriteOption(webd));

		//verify the dashboard is favorite
		webd.getLogger().info("Visit my favorite page");
		BrandingBarUtil.visitMyFavorites(webd);

		webd.getLogger().info("Verfiy the favortie checkbox is checked");
		Assert.assertTrue(DashboardHomeUtil.isFilterOptionSelected(webd, "favorites"), "My Favorites option is NOT checked");
		//		WebElement el = webd.getWebDriver().findElement(By.id(DashBoardPageId.Favorite_BoxID));
		//		Assert.assertTrue(el.isSelected());

		webd.getLogger().info("Verfiy the dashboard is favorite");
		//DashboardHomeUtil.search(webd, dbName_favorite);
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_favorite), "Can not find the dashboard");

		webd.getLogger().info("Open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName_favorite);
		webd.getLogger().info("Verify the dashboard in builder page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_favorite, dbDesc, true), "Create dashboard failed!");

		//set it to not favorite
		webd.getLogger().info("set the dashboard to not favorite");
		Assert.assertFalse(DashboardBuilderUtil.favoriteOption(webd), "Set to not my favorite dashboard failed!");

		//verify the dashboard is not favoite
		webd.getLogger().info("visit my favorite page");
		BrandingBarUtil.visitMyFavorites(webd);
		webd.getLogger().info("Verfiy the favortie checkbox is checked");
		Assert.assertTrue(DashboardHomeUtil.isFilterOptionSelected(webd, "favorites"), "My Favorites option is NOT checked");
		//		el = webd.getWebDriver().findElement(By.id(DashBoardPageId.Favorite_BoxID));
		//		Assert.assertTrue(el.isSelected());
		webd.getLogger().info("Verfiy the dashboard is not favorite");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, dbName_favorite),
				"The dashboard is still my favorite dashboard");
		//		DashboardHomeUtil.search(webd, dbName_favorite);
		//		Assert.assertEquals(webd.getAttribute(DashBoardPageId.DashboardSerachResult_panelID + "@childElementCount"), "0");
		//		webd.getLogger().info("no favorite dashboard");

		//delete the dashboard
		webd.getLogger().info("start to delete the dashboard");

		WebElement el = webd.getWebDriver().findElement(By.id(DashBoardPageId.Favorite_BoxID));
		if (el.isSelected()) {
			el.click();
		}
		DashboardHomeUtil.deleteDashboard(webd, dbName_favorite, DashboardHomeUtil.DASHBOARDS_GRID_VIEW);
		webd.getLogger().info("the dashboard has been deleted");
	}

	@Test(dependsOnMethods = { "testCreateDashboad_noWidget_GridView" })
	public void testModifyDashboard_namedesc() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testModifyDashBoard");

		//edit dashboard
		webd.getLogger().info("Start to edit dashboard in grid view");
		DashboardHomeUtil.gridView(webd);
		webd.getLogger().info("Search dashboard");
		DashboardHomeUtil.search(webd, dbName_noWidgetGrid);
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid), "Dashboard NOT found!");

		//Open the dashboard
		webd.getLogger().info("Open the dashboard to edit name and description");
		DashboardHomeUtil.selectDashboard(webd, dbName_noWidgetGrid);
		DashboardBuilderUtil
		.editDashboard(webd, dbName_noWidgetGrid + "-modify", "Test_Dashboard_no_Widget_GridView desc modify");

		//Verify the dashboard edited successfully
		webd.getLogger().info("Verify the dashboard edited successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_noWidgetGrid + "-modify",
				"Test_Dashboard_no_Widget_GridView desc modify", true), "Dashboard edit failed!");

	}

	@Test(dependsOnMethods = { "testCreateDashboard_withWidget_GridView" })
	public void testModifyDashboard_widget() throws Exception
	{
		String WidgetName_1 = "Top Hosts by Log Entries";
		String WidgetName_2 = "Top 10 Listeners by Load";

		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testModifyDashboard_widget");
		DashboardHomeUtil.gridView(webd);

		//search the dashboard want to modify
		webd.getLogger().info("Search dashboard");
		DashboardHomeUtil.search(webd, dbName_withWidgetGrid);
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_withWidgetGrid), "Dashboard NOT found!");

		//open the dashboard in the builder page
		webd.getLogger().info("Open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName_withWidgetGrid);

		//add the widget into the dashboard
		webd.getLogger().info("Start to add Widget into the dashboard");

		webd.getLogger().info("Add Widget 'Top Hosts by Log Entries' into the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, WidgetName_1);
		Assert.assertTrue(DashboardBuilderUtil.verifyWidget(webd, WidgetName_1), "Widget '" + WidgetName_1 + "' not found");

		webd.getLogger().info("Add Widget 'Top 10 Listeners by Load' into the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, WidgetName_2);
		Assert.assertTrue(DashboardBuilderUtil.verifyWidget(webd, WidgetName_2), "Widget '" + WidgetName_2 + "' not found");

		webd.getLogger().info("Add Widget 'Top Hosts by Log Entries' into the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, WidgetName_1);
		Assert.assertTrue(DashboardBuilderUtil.verifyWidget(webd, WidgetName_1, 1), "The second Widget '" + WidgetName_1
				+ "' not found");

		webd.getLogger().info("Add widget finished");

		//save dashboard
		webd.getLogger().info("Save the dashboard");
		DashboardBuilderUtil.saveDashboard(webd);
	}

	@Test(dependsOnMethods = { "testCreateDashboad_noWidget_GridView", "testModifyDashboard_namedesc", "testShareDashboard",
			"testStopShareDashboard", "testDuplicateDashboard" })
	public void testRemoveDashboard_GridView() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testRemoveDashboard_GridView");

		//search dashboard
		webd.getLogger().info("Search the dashboard want to delete in grid view");
		DashboardHomeUtil.gridView(webd);
		webd.getLogger().info("Search dashboard");
		DashboardHomeUtil.search(webd, dbName_noWidgetGrid + "-modify");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid + "-modify"), "Dashboard NOT found!");

		//delte dashboard
		webd.getLogger().info("Start to delete dashboard in grid view");
		DashboardHomeUtil.deleteDashboard(webd, dbName_noWidgetGrid + "-modify", DashboardHomeUtil.DASHBOARDS_GRID_VIEW);

		//verify the dashboard deleted
		webd.getLogger().info("Verify the dashboard is deleted");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid + "-modify"), "Delete failed!");

	}

	@Test(dependsOnMethods = { "testCreateDashboard_noWidget_ListView" })
	public void testRemoveDashboard_ListView() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testRemoveDashboard_ListView");

		//search dashboard
		webd.getLogger().info("Search the dashboard want to delete in grid view");
		DashboardHomeUtil.listView(webd);
		webd.getLogger().info("Search dashboard");
		DashboardHomeUtil.search(webd, dbName_noWidgetList);
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetList), "Dashboard NOT found!");

		//delte dashboard
		webd.getLogger().info("Start to delete dashboard in grid view");
		DashboardHomeUtil.deleteDashboard(webd, dbName_noWidgetList, DashboardHomeUtil.DASHBOARDS_LIST_VIEW);

		//verify the dashboard deleted
		webd.getLogger().info("Verify the dashboard is deleted");
		Assert.assertFalse(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid + "-modify"), "Delete failed!");

	}

	@Test(dependsOnMethods = { "testCreateDashboard_withWidget_GridView", "testModifyDashboard_widget", "testWidgetConfiguration" })
	public void testRemoveDashboardInBuilderPage() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testRemoveDashboardInBuilderPage");

		//search dashboard
		webd.getLogger().info("Search the dashboard want to delete in grid view");
		DashboardHomeUtil.gridView(webd);
		webd.getLogger().info("Search dashboard");
		DashboardHomeUtil.search(webd, dbName_withWidgetGrid);
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_withWidgetGrid), "Dashboard NOT found!");

		//open the dashboard in builder page
		DashboardHomeUtil.selectDashboard(webd, dbName_withWidgetGrid);
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_withWidgetGrid, "AAA_testDashBoard desc", true),
				"Dashboard NOT found!");

		//delte the dashboard in builder page
		webd.getLogger().info("start to delete dashboard in builder page");
		DashboardBuilderUtil.deleteDashboard(webd);

		//verify if in the home page
		webd.getLogger().info("verify delete successfully and back to the home page");
		WebDriverWait wait1 = new WebDriverWait(webd.getWebDriver(), 900L);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(PageId.DashboardDisplayPanelCss)));
	}

	@Test
	public void testSaveConfirmation() throws Exception
	{
		String dbName = "TestSaveConfirmation";

		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testSaveConfimation");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create a dashboard
		webd.getLogger().info("create a dashboard: with description, time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName, null, DashboardHomeUtil.DASHBOARD);
		//verify dashboard in builder page
		webd.getLogger().info("Verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName, null, true), "Create dashboard failed!");

		//edit the dashboard
		webd.getLogger().info("add a widget to the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, "Database Errors Trend");

		//leave the builder page
		webd.getLogger().info("return to dashboard home page");
		DashBoardUtils.clickDashboardLinkInBrandingBar(webd);

		webd.getLogger().info("the warning dialog pop up");
		DashBoardUtils.handleAlert(webd);

		//verify if in the home page
		webd.getLogger().info("Verify if in the home page");
		WebDriverWait wait1 = new WebDriverWait(webd.getWebDriver(), 900L);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(PageId.DashboardDisplayPanelCss)));
	}

	@Test
	public void testSetHome() throws Exception
	{
		dbName_setHome = "setHomeDashboard-" + generateTimeStamp();
		String dbDesc = "SetHome_testDashboard desc";

		//Initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testCreateDashboad_noWidget_GridView");
		WaitUtil.waitForPageFullyLoaded(webd);
		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("create a dashboard: with description, time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName_setHome, dbDesc, DashboardHomeUtil.DASHBOARD);

		//verify dashboard in builder page
		webd.getLogger().info("Verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_setHome, dbDesc, true), "Create dashboard failed!");

		//set it as home
		webd.getLogger().info("Set home page");
		Assert.assertTrue(DashboardBuilderUtil.toggleHome(webd), "Set the dasbhoard as Home failed!");

		//check home page
		webd.getLogger().info("Access to the home page");
		BrandingBarUtil.visitMyHome(webd);
		webd.getLogger().info("Verfiy the home page");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_setHome, dbDesc, true), "It is NOT the home page!");

		//set it not home
		webd.getLogger().info("Set not home page");
		Assert.assertFalse(DashboardBuilderUtil.toggleHome(webd), "Remove the dasbhoard as Home failed!");

		//check home page
		webd.getLogger().info("Access to the home page");
		BrandingBarUtil.visitMyHome(webd);
		webd.getLogger().info("Verfiy the home page");
		Assert.assertTrue(WelcomeUtil.isServiceExistedInWelcome(webd, WelcomeUtil.SERVICE_NAME_DASHBOARDS),
				"It is NOT the home page!");
	}

	@Test(dependsOnMethods = { "testCreateDashboad_noWidget_GridView", "testModifyDashboard_namedesc" })
	public void testShareDashboard() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testshareddashboard");

		DashboardHomeUtil.gridView(webd);

		//search the dashboard and open it in builder page
		webd.getLogger().info("search the dashboard");
		DashboardHomeUtil.search(webd, dbName_noWidgetGrid + "-modify");
		webd.getLogger().info("verify the dashboard is existed");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid + "-modify"), "Dashboard NOT found!");
		webd.getLogger().info("open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName_noWidgetGrid + "-modify");

		//sharing dashbaord
		webd.getLogger().info("Share the dashboard");
		Assert.assertTrue(DashboardBuilderUtil.toggleShareDashboard(webd), "Share dashboard failed!");

	}

	@Test(dependsOnMethods = { "testShareDashboard" })
	public void testStopShareDashboard() throws Exception
	{
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testshareddashboard");

		DashboardHomeUtil.gridView(webd);

		//search the dashboard and open it in builder page
		webd.getLogger().info("search the dashboard");
		DashboardHomeUtil.search(webd, dbName_noWidgetGrid + "-modify");
		webd.getLogger().info("verify the dashboard is existed");
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_noWidgetGrid + "-modify"), "Dashboard NOT found!");
		webd.getLogger().info("open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName_noWidgetGrid + "-modify");

		//stop sharing dashbaord
		webd.getLogger().info("Stop share the dashboard");
		Assert.assertFalse(DashboardBuilderUtil.toggleShareDashboard(webd), "Stop sharing the dashboard failed!");
	}

	@Test
	public void testTimePicker() throws Exception
	{
		dbName_timepicker = "TestDashboardTimeselector-" + generateTimeStamp();
		String dbDesc = "Test Dashboard timeselector description";

		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start to test in testTimePicker");

		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);

		//switch to Grid View
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		//create dashboard
		webd.getLogger().info("Create a dashboard: with description, time refresh");
		DashboardHomeUtil.createDashboard(webd, dbName_timepicker, dbDesc, DashboardHomeUtil.DASHBOARD);

		//verify dashboard in builder page
		webd.getLogger().info("Verify the dashboard created Successfully");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_timepicker, dbDesc, true), "Create dashboard failed!");

		//Add the widget to the dashboard
		webd.getLogger().info("Start to add Widget into the dashboard");
		DashboardBuilderUtil.addWidgetToDashboard(webd, "Database Errors Trend");
		webd.getLogger().info("Add widget finished");

		//save dashboard
		webd.getLogger().info("save the dashboard");
		DashboardBuilderUtil.saveDashboard(webd);

		//set time range for time picker
		webd.getLogger().info("Set the time range");
		TimeSelectorUtil.setTimeRange(webd, TimeRange.Last15Mins);
		String returnTimeRange = TimeSelectorUtil.setCustomTime(webd, "04/06/2015 07:22 PM", "04/07/2016 09:22 PM");

		//convert date to timestamp
		SimpleDateFormat fmt = new SimpleDateFormat("MMM d, yyyy h:mm a");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date dTmpStart = new Date();
		Date dTmpEnd = new Date();

		String tmpReturnDate = returnTimeRange.substring(7);

		String[] tmpDate = tmpReturnDate.split("-");

		String tmpStartDate = tmpDate[0].trim();
		String tmpEndDate = tmpDate[1].trim();

		dTmpStart = fmt.parse(tmpStartDate);
		dTmpEnd = fmt.parse(tmpEndDate);

		String tmpStartDateNew = df.format(dTmpStart);
		String tmpEndDateNew = df.format(dTmpEnd);
		webd.getLogger().info(tmpStartDateNew);
		webd.getLogger().info(tmpEndDateNew);

		long StartTimeStamp = Timestamp.valueOf(tmpStartDateNew).getTime();
		long EndTimeStamp = Timestamp.valueOf(tmpEndDateNew).getTime();

		webd.getLogger().info(String.valueOf(StartTimeStamp));
		webd.getLogger().info(String.valueOf(EndTimeStamp));

		//open the widget
		webd.getLogger().info("Open the widget");
		DashboardBuilderUtil.openWidget(webd, "Database Errors Trend");

		//verify the url
		webd.switchToWindow();

		String url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("url = " + url);
		if (!url.substring(url.indexOf("emsaasui") + 9).contains(
				"startTime=" + String.valueOf(StartTimeStamp) + "&endTime=" + String.valueOf(EndTimeStamp))) {
			Assert.fail("not open the correct widget");
		}

	}

	//Testcase for validating Error page

	@Test(dependsOnMethods = { "testCreateDashboard_withWidget_GridView", "testModifyDashboard_widget" })
	public void testWidgetConfiguration() throws Exception
	{
		String WidgetName_1 = "Database Errors Trend";
		String WidgetName_2 = "Top 10 Listeners by Load";

		//initialize the test
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in testWidgetConfiguration");
		//search the dashboard want to modify
		webd.getLogger().info("search dashboard");
		DashboardHomeUtil.search(webd, dbName_withWidgetGrid);
		Assert.assertTrue(DashboardHomeUtil.isDashboardExisted(webd, dbName_withWidgetGrid), "Dashboard NOT found!");

		//open the dashboard in the builder page
		webd.getLogger().info("open the dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName_withWidgetGrid);

		//widget operation
		webd.getLogger().info("hide/show the widget title");
		DashboardBuilderUtil.showWidgetTitle(webd, WidgetName_1, false);
		DashboardBuilderUtil.showWidgetTitle(webd, WidgetName_2, true);
		webd.getLogger().info("resize the widget title");
		DashboardBuilderUtil.resizeWidget(webd, WidgetName_1, DashboardBuilderUtil.TILE_WIDER);
		DashboardBuilderUtil.resizeWidget(webd, WidgetName_1, DashboardBuilderUtil.TILE_NARROWER);
		DashboardBuilderUtil.resizeWidget(webd, WidgetName_2, DashboardBuilderUtil.TILE_TALLER);
		DashboardBuilderUtil.resizeWidget(webd, WidgetName_2, DashboardBuilderUtil.TILE_SHORTER);
		webd.getLogger().info("remove the widget title");
		DashboardBuilderUtil.removeWidget(webd, WidgetName_1);
		DashboardBuilderUtil.removeWidget(webd, WidgetName_2);

		//save the dashboard
		webd.getLogger().info("save the dashboard");
		DashboardBuilderUtil.saveDashboard(webd);
	}

	//Testcase for adding widget using widgetselector

	@Test
	public void testWidgetSelector() throws Exception
	{
		String WidgetName_1 = "Database Errors Trend";

		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in WidgetSelectorPage");

		//ErrorPage link
		//BrandingBarUtil.visitApplicationCloudService(webd, BrandingBarUtil.NAV_LINK_TEXT_WidgetSelector);
		String url = webd.getWebDriver().getCurrentUrl();
		webd.getLogger().info("url = " + url);
		String testUrl = url.substring(0, url.indexOf("emsaasui")) + "emsaasui/uifwk/test.html";
		webd.getLogger().info("test page url is " + testUrl);
		webd.getWebDriver().navigate().to(testUrl);

		//Assert.assertEquals(url.substring(url.indexOf("emsaasui") + 9), "uifwk/test.html");
		// let's try to wait until page is loaded and jquery loaded before calling waitForPageFullyLoaded
		WebDriverWait wait = new WebDriverWait(webd.getWebDriver(), WaitUtil.WAIT_TIMEOUT);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(DashBoardPageId.WidgetSelector_AddButtonId)));
		WaitUtil.waitForPageFullyLoaded(webd);
		//click on Add button
		webd.click("id=" + DashBoardPageId.WidgetSelector_AddButtonId);
		webd.takeScreenShot();
		//Adding widgets using widgetSElector diagoue
		webd.getLogger().info("satrt widget selector dialogue box opens");
		WidgetSelectorUtil.addWidget(webd, WidgetName_1);

	}

	private String generateTimeStamp()
	{
		return String.valueOf(System.currentTimeMillis());
	}

}
