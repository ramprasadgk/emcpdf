package oracle.sysman.emaas.platform.dashboards.tests.ui;

import java.util.List;

import oracle.sysman.emaas.platform.dashboards.tests.ui.util.DashBoardPageId;
import oracle.sysman.qatool.uifwk.webdriver.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WelcomeUtil
{

	private static WebDriver driver;

	public static void dataExplorers(String selection) throws Exception
	{
		String eleXpath = null;
		driver.getLogger().info("Visiting Data Explorer-" + selection + " from Welcome Page...");
		driver.waitForElementPresent("id=oj-select-choice-" + DashBoardPageId.Welcome_DataExp_SelectID);
		driver.click("id=oj-select-choice-" + DashBoardPageId.Welcome_DataExp_SelectID);
		driver.takeScreenShot();
		switch (selection) {
			case "log":
				eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_DataExp_SelectID,
						DashBoardPageId.Welcome_DataExp_Log);
				break;
			case "analyze":
				eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_DataExp_SelectID,
						DashBoardPageId.Welcome_DataExp_Analyze);
				break;
			case "search":
				eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_DataExp_SelectID,
						DashBoardPageId.Welcome_DataExp_Search);
				break;
		}
		driver.getWebDriver().findElement(By.xpath(eleXpath)).click();
		driver.takeScreenShot();
	}

	public static String getOptionXpath(String selectId, String optionId) throws Exception
	{
		String optionXpath;
		WebElement li = driver.getWebDriver().findElement(By.id(optionId));
		List<WebElement> list = driver.getWebDriver().findElements(By.xpath("//select[@id='" + selectId + "']/option"));
		int index = list.indexOf(li);
		optionXpath = "//ul[@id='oj-listbox-results-" + selectId + "']/li[" + (index + 1) + "]/div";
		return optionXpath;
	}

	public static void learnMoreHow() throws Exception
	{
		driver.getLogger().info("Visiting 'Learn More-How to get started' from Welcome Page...");
		driver.waitForElementPresent("id="+DashBoardPageId.Welcome_LearnMore_getStarted);
		driver.click("id="+DashBoardPageId.Welcome_LearnMore_getStarted);
		driver.takeScreenShot();
	}

	public static void learnMoreServiceOffering() throws Exception
	{
		driver.getLogger().info("Visiting 'Learn More-Service Offerings' from Welcome Page...");
		driver.waitForElementPresent("id="+DashBoardPageId.Welcome_LearnMore_ServiceOffering);
		driver.click("id="+DashBoardPageId.Welcome_LearnMore_ServiceOffering);
		driver.takeScreenShot();
	}

	public static void learnMoreVideo() throws Exception
	{
		driver.getLogger().info("Visiting 'Learn More-Videos' from Welcome Page...");
		driver.waitForElementPresent("id="+DashBoardPageId.Welcome_LearnMore_Videos);
		driver.click("id="+DashBoardPageId.Welcome_LearnMore_Videos);
		driver.takeScreenShot();
	}

	public static void loadWebDriverOnly(WebDriver webDriver) throws Exception
	{
		driver = webDriver;
	}

	public static void visitAPM() throws Exception
	{
		driver.getLogger().info("Visit APM from Welcome Page...");
		driver.waitForElementPresent("id="+DashBoardPageId.Welcome_APMLinkCSS);
		driver.click("id="+DashBoardPageId.Welcome_APMLinkCSS);
		driver.takeScreenShot();
	}

	public static void visitDashboards() throws Exception
	{
		driver.getLogger().info("Visit Dashboards from Welcome Page...");
		driver.waitForElementPresent("id="+DashBoardPageId.Welcome_DashboardsLinkID);
		driver.click("id="+DashBoardPageId.Welcome_DashboardsLinkID);
		driver.takeScreenShot();
	}

	public static void visitITA(String selection) throws Exception
	{
		driver.getLogger().info("Visiting ITA-" + selection + " from Welcome Page...");

		if (selection.equals("default")) {
			driver.waitForElementPresent("id="+DashBoardPageId.Welcome_ITALinkID);
			driver.click("id="+DashBoardPageId.Welcome_ITALinkID);
			driver.takeScreenShot();
		}
		else {
			String eleXpath = null;
			driver.waitForElementPresent("id=oj-select-choice-" + DashBoardPageId.Welcome_ITA_SelectID);
			driver.click("id=oj-select-choice-" + DashBoardPageId.Welcome_ITA_SelectID);
			driver.takeScreenShot();
			switch (selection) {
				case "performanceAnalyticsDatabase":
					eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_ITA_SelectID,
							DashBoardPageId.Welcome_ITA_PADatabase);
					break;
				case "performanceAnalyticsMiddleware":
					eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_ITA_SelectID,
							DashBoardPageId.Welcome_ITA_PAMiddleware);
					break;
				case "resourceAnalyticsDatabase":
					eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_ITA_SelectID,
							DashBoardPageId.Welcome_ITA_RADatabase);
					break;
				case "resourceAnalyticsMiddleware":
					eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_ITA_SelectID,
							DashBoardPageId.Welcome_ITA_RAMiddleware);
					break;
				case "dataExplorerAnalyze":
					eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_ITA_SelectID,
							DashBoardPageId.Welcome_ITA_DEAnalyze);
					break;
				case "dataExplorer":
					eleXpath = WelcomeUtil.getOptionXpath(DashBoardPageId.Welcome_ITA_SelectID, DashBoardPageId.Welcome_ITA_DE);
					break;
			}
			driver.click(eleXpath);
			driver.takeScreenShot();
		}
	}

	public static void visitLA() throws Exception
	{
		driver.getLogger().info("Visiting LA from Welcome Page...");
		driver.waitForElementPresent("id="+DashBoardPageId.Welcome_LALinkCSS);
		driver.click("id="+DashBoardPageId.Welcome_LALinkCSS);
		driver.takeScreenShot();
	}
}
