package oracle.sysman.emaas.platform.dashboards.test.DPdashboard;


import org.testng.Assert;

import org.testng.annotations.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import oracle.sysman.qatool.uifwk.webdriver.WebDriver;

import oracle.sysman.emaas.platform.dashboards.test.util.*;
import oracle.sysman.emaas.platform.dashboards.tests.ui.*;

import java.util.Set;
import org.testng.Assert;




/**
 * @version
 * @author siakula
 * @since release specific (what release of product did this appear in)
 */

public class TestDashBoard extends LoginAndLogout
{
       private String dbName_noWidgetGrid = "";
       

	public void initTest(String testName) throws Exception
	{
		login(this.getClass().getName() + "." + testName);
		DashBoardUtils.loadWebDriver(webd);
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
			DashboardBuilderUtil.editDashboard(webd, dbName_noWidgetGrid + "-modify",
					"Test_Dashboard_no_Widget_GridView desc modify");
	
			//Verify the dashboard edited successfully
			webd.getLogger().info("Verify the dashboard edited successfully");
			Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName_noWidgetGrid + "-modify",
					"Test_Dashboard_no_Widget_GridView desc modify", true), "Dashboard edit failed!");
	
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
		DashboardBuilderUtil.duplicateDashboard(webd, dbName, dbDesc,"duplicate_noSubMenu");
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName, dbDesc, true), "Duplicate failed!");

	}


        
         private String generateTimeStamp()
	{
		return String.valueOf(System.currentTimeMillis());
	}

}

