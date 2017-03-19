package oracle.sysman.emaas.platform.dashboards.test.ui;

import oracle.sysman.emaas.platform.dashboards.test.ui.util.DashBoardUtils;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.LoginAndLogout;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.PageId;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardBuilderUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardHomeUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * @version
 * @author
 * @since release specific (what release of product did this appear in)
 */

public class TestDashboard extends LoginAndLogout
{
	private String dbName = "";

	public void initTestCustom(String testName, String Username, String tenantName)
	{
		customlogin(this.getClass().getName() + "." + testName, Username, tenantName);
		DashBoardUtils.loadWebDriver(webd);
		
		//reset the home page
		webd.getLogger().info("Reset all filter options in the home page");
		DashboardHomeUtil.resetFilterOptions(webd);
	}
	
	@AfterClass
	public void RemoveDashboard()
	{
		//Initialize the test
		initTestCustom(Thread.currentThread().getStackTrace()[1].getMethodName(),"emcsadmin","emaastesttenantnoita");
		webd.getLogger().info("Start to remove test data");

		//delete dashboard
		webd.getLogger().info("Switch to grid view");
		DashboardHomeUtil.gridView(webd);

		webd.getLogger().info("Start to remove the test data...");
   	    DashBoardUtils.deleteDashboard(webd, dbName);

		webd.getLogger().info("All test data have been removed");

		LoginAndLogout.logoutMethod();
	}

	@Test(groups = "first run")
	public void testDashboardPerformance1() throws InterruptedException 
	{
		dbName = "selfDb-" + generateTimeStamp();
		
		//initialize the test
		initTestCustom(Thread.currentThread().getStackTrace()[1].getMethodName(),"emcsadmin","emaastesttenantnoita");
		webd.getLogger().info("start to test the performance when creating dashboard with tenant that doesn't have ITA privilege");

		//Create dashboard
		DashboardHomeUtil.createDashboard(webd, dbName, null, DashboardHomeUtil.DASHBOARD);
		Assert.assertTrue(DashboardBuilderUtil.verifyDashboard(webd, dbName, null, true),
				"Create dashboard failed!");
		
		DashboardBuilderUtil.respectGCForEntity(webd);
		
		((JavascriptExecutor) webd.getWebDriver()).executeScript("scroll(0,0)");
		
		//find "All Entities " button and click it
		WebElement entitiesBtn = webd.getWebDriver().findElement(By.xpath(PageId.ENTITYBUTTON));
		Assert.assertTrue(entitiesBtn.isDisplayed(), "'All Entities' button isn't displayed in self dashboard");
		webd.click(PageId.ENTITYBUTTON);		
		
		//find Select button and click it
		WebElement selectButton = webd.getWebDriver().findElement(By.xpath(PageId.SELECTBUTTON));
		Assert.assertTrue(selectButton.isDisplayed(), "Select button isn't displayed in Select Entities dialog");
		webd.click(PageId.SELECTBUTTON);
	}
	

	@Test(groups = "first run", dependsOnMethods = { "testDashboardPerformance1" })
	public void testDashboardPerformance2() throws InterruptedException
	{		
		//initialize the test
		initTestCustom(Thread.currentThread().getStackTrace()[1].getMethodName(),"emcsadmin","emaastesttenantnoita");
		webd.getLogger().info("start to test the performance when creating dashboard with tenant that doesn't have ITA privilege");

		//open the dashboard dbName
		webd.getLogger().info("open the created dashboard");
		DashboardHomeUtil.selectDashboard(webd, dbName);
		
		DashboardBuilderUtil.showEntityFilter(webd, true);
		
		((JavascriptExecutor) webd.getWebDriver()).executeScript("scroll(0,0)");
		
		//find "All Entities" button and click it
		WebElement entitiesBtn = webd.getWebDriver().findElement(By.xpath(PageId.ENTITYBUTTON));
		Assert.assertTrue(entitiesBtn.isDisplayed(), "'All Entities' button isn't displayed in self dashboard");
		webd.click(PageId.ENTITYBUTTON);	
		
		//find Cancel(Select) button and click it  
		WebElement cancelButton = webd.getWebDriver().findElement(By.xpath(PageId.CANCELBUTTON));
		Assert.assertTrue(cancelButton.isDisplayed(), "Cancel/Select button isn't displayed in Select Entities dialog");
		webd.click(PageId.CANCELBUTTON);
	}
	
	private String generateTimeStamp()
	{
		return String.valueOf(System.currentTimeMillis());
	}
}