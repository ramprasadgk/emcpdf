package oracle.sysman.emaas.platform.dashboards.test.ui;

import oracle.sysman.emaas.platform.dashboards.test.ui.util.DashBoardUtils;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.LoginAndLogout;
import oracle.sysman.emaas.platform.dashboards.test.ui.util.VerifyOOBUtil;
import oracle.sysman.emaas.platform.dashboards.tests.ui.DashboardHomeUtil;

import org.testng.annotations.Test;

public class VerifyOOB_V1 extends LoginAndLogout
{

	public void initTest(String testName)
	{
		login(this.getClass().getName() + "." + testName);
		DashBoardUtils.loadWebDriver(webd);

		//reset all the checkboxes
		DashboardHomeUtil.resetFilterOptions(webd);
	}

	@Test(alwaysRun = true)
	public void verifyDatabaseOperations_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseOperations_GridView");

		//lick on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Database Operations
		webd.getLogger().info("Open the OOB dashboard---Database Operations");
		DashboardHomeUtil.selectDashboard(webd, "Database Operations");

		//verify Database Operations
		VerifyOOBUtil.verifyDatabaseOperations(webd);
		VerifyOOBUtil.verifyDatabaseOperations_Details(webd);
	}

	@Test(alwaysRun = true)
	public void verifyDatabaseOperations_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseOperations_ListView");

		//lick on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Database Operations
		webd.getLogger().info("Open the OOB dashboard---Database Operations");
		DashboardHomeUtil.selectDashboard(webd, "Database Operations");

		//verify Database Operations
		VerifyOOBUtil.verifyDatabaseOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyDatabaseOperations_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseOperations_WithFilter_GridView");

		//click Filter-Oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//lick on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Database Operations
		webd.getLogger().info("Open the OOB dashboard---Database Operations");
		DashboardHomeUtil.selectDashboard(webd, "Database Operations");

		//verify Database Operations
		VerifyOOBUtil.verifyDatabaseOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyDatabaseOperations_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseOperations_WithFilter_ListView");

		//click Filter-Oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//lick on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Database Operations
		webd.getLogger().info("Open the OOB dashboard---Database Operations");
		DashboardHomeUtil.selectDashboard(webd, "Database Operations");

		//verify Database Operations
		VerifyOOBUtil.verifyDatabaseOperations(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDatabaseSecurity_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseSecurity_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Oracle Database Security
		webd.getLogger().info("Open the OOB dashboard---Oracle Database Security");
		DashboardHomeUtil.selectDashboard(webd, "Oracle Database Security");

		//verify Database Security
		VerifyOOBUtil.verifyDatabaseSecurity(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDatabaseSecurity_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseSecurity_ListView");

		//click on Grid View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Oracle Database Security
		webd.getLogger().info("Open the OOB dashboard---Oracle Database Security");
		DashboardHomeUtil.selectDashboard(webd, "Oracle Database Security");

		//verify Database Security
		VerifyOOBUtil.verifyDatabaseSecurity(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDatabaseSecurity_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseSecurity_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Oracle Database Security
		webd.getLogger().info("Open the OOB dashboard---Oracle Database Security");
		DashboardHomeUtil.selectDashboard(webd, "Oracle Database Security");

		//verify Database Security
		VerifyOOBUtil.verifyDatabaseSecurity(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDatabaseSecurity_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDatabaseSecurity_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Oracle Database Security
		webd.getLogger().info("Open the OOB dashboard---Oracle Database Security");
		DashboardHomeUtil.selectDashboard(webd, "Oracle Database Security");

		//verify Database Security
		VerifyOOBUtil.verifyDatabaseSecurity(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDNS_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDNS_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---DNS");
		DashboardHomeUtil.selectDashboard(webd, "DNS");

		//verify DNS
		VerifyOOBUtil.verifyDNS(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDNS_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDNS_ListView");

		//click on Grid View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---DNS");
		DashboardHomeUtil.selectDashboard(webd, "DNS");

		//verify DNS
		VerifyOOBUtil.verifyDNS(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDNS_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDNS_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---DNS");
		DashboardHomeUtil.selectDashboard(webd, "DNS");

		//verify DNS
		VerifyOOBUtil.verifyDNS(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyDNS_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyDNS_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---DNS");
		DashboardHomeUtil.selectDashboard(webd, "DNS");

		//verify DNS
		VerifyOOBUtil.verifyDNS(webd);
	}

	@Test(alwaysRun = true)
	public void verifyEnterpriseHealth_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyEnterpriseHealth_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Enterprise Health
		webd.getLogger().info("Open the OOB dashboard---Enterprise Health");
		DashboardHomeUtil.selectDashboard(webd, "Enterprise Health");

		//verify Enterprise Health
		VerifyOOBUtil.verifyEnterpriseHealth(webd);
		VerifyOOBUtil.verifyEnterpriseHealth_Details(webd);
	}

	@Test(alwaysRun = true)
	public void verifyEnterpriseHealth_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyEnterpriseHealth_ListView");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Enterprise Health
		webd.getLogger().info("Open the OOB dashboard---Enterprise Health");
		DashboardHomeUtil.selectDashboard(webd, "Enterprise Health");

		//verify Enterprise Health
		VerifyOOBUtil.verifyEnterpriseHealth(webd);
	}

	@Test(alwaysRun = true)
	public void verifyEnterpriseHealth_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyEnterpriseHealth_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Enterprise Health
		webd.getLogger().info("Open the OOB dashboard---Enterprise Health");
		DashboardHomeUtil.selectDashboard(webd, "Enterprise Health");

		//verify Enterprise Health
		VerifyOOBUtil.verifyEnterpriseHealth(webd);
	}

	@Test(alwaysRun = true)
	public void verifyEnterpriseHealth_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyEnterpriseHealth_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Enterprise Health
		webd.getLogger().info("Open the OOB dashboard---Enterprise Health");
		DashboardHomeUtil.selectDashboard(webd, "Enterprise Health");

		//verify Enterprise Health
		VerifyOOBUtil.verifyEnterpriseHealth(webd);
	}

	@Test(alwaysRun = true)
	public void verifyExadataHealth_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyExadataHealth_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Exadata Health
		webd.getLogger().info("Open the OOB dashboard---Exadata Health");
		DashboardHomeUtil.selectDashboard(webd, "Exadata Health");

		//verify Exadata Health
		VerifyOOBUtil.verifyExadataHealth(webd);
		VerifyOOBUtil.verifyExadataHealth_Details(webd);
	}

	@Test(alwaysRun = true)
	public void verifyExadataHealth_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyExadataHealth_ListView");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Exadata Health
		webd.getLogger().info("Open the OOB dashboard---Exadata Health");
		DashboardHomeUtil.selectDashboard(webd, "Exadata Health");

		//verify Exadata Health
		VerifyOOBUtil.verifyExadataHealth(webd);
	}

	@Test(alwaysRun = true)
	public void verifyExadataHealth_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyExadataHealth_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Exadata Health
		webd.getLogger().info("Open the OOB dashboard---Exadata Health");
		DashboardHomeUtil.selectDashboard(webd, "Exadata Health");

		//verify Exadata Health
		VerifyOOBUtil.verifyExadataHealth(webd);
	}

	@Test(alwaysRun = true)
	public void verifyExadataHealth_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyExadataHealth_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Exadata Health
		webd.getLogger().info("Open the OOB dashboard---Exadata Health");
		DashboardHomeUtil.selectDashboard(webd, "Exadata Health");

		//verify Exadata Health
		VerifyOOBUtil.verifyExadataHealth(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyFirewall_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyFirewall_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---Firewall");
		DashboardHomeUtil.selectDashboard(webd, "Firewall");

		//verify Firewall
		VerifyOOBUtil.verifyFirewall(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyFirewall_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyFirewall_ListView");

		//click on Grid View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---Firewall");
		DashboardHomeUtil.selectDashboard(webd, "Firewall");

		//verify Firewall
		VerifyOOBUtil.verifyFirewall(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyFirewall_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyFirewall_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---Firewall");
		DashboardHomeUtil.selectDashboard(webd, "Firewall");

		//verify Firewall
		VerifyOOBUtil.verifyFirewall(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyFirewall_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyFirewall_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Resource Analytics: Host
		webd.getLogger().info("Open the OOB dashboard---Firewall");
		DashboardHomeUtil.selectDashboard(webd, "Firewall");

		//verify Firewall
		VerifyOOBUtil.verifyFirewall(webd);
	}

	@Test(alwaysRun = true)
	public void verifyHostOperations_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyHostOperations_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Host Operations
		webd.getLogger().info("Open the OOB dashboard---Host Operations");
		DashboardHomeUtil.selectDashboard(webd, "Host Operations");

		//verify Host Operations
		VerifyOOBUtil.verifyHostOperations(webd);
		VerifyOOBUtil.verifyHostOperations_Details(webd);
	}

	@Test(alwaysRun = true)
	public void verifyHostOperations_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyHostOperations_ListView");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Host Operations
		webd.getLogger().info("Open the OOB dashboard---Host Operations");
		DashboardHomeUtil.selectDashboard(webd, "Host Operations");

		//verify Host Operations
		VerifyOOBUtil.verifyHostOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyHostOperations_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyHostOperations_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//open Host Operations
		webd.getLogger().info("Open the OOB dashboard---Host Operations");
		DashboardHomeUtil.selectDashboard(webd, "Host Operations");

		//verify Host Operations
		VerifyOOBUtil.verifyHostOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyHostOperations_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("start to test in verifyHostOperations_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//open Host Operations
		webd.getLogger().info("Open the OOB dashboard---Host Operations");
		DashboardHomeUtil.selectDashboard(webd, "Host Operations");

		//verify Host Operations
		VerifyOOBUtil.verifyHostOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyMiddlewareOperations_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyMiddlewareOperations_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Middleware Operations
		webd.getLogger().info("Open the OOB dashboard---Middleware Operations");
		DashboardHomeUtil.selectDashboard(webd, "Middleware Operations");

		//verify Middleware Operations
		VerifyOOBUtil.verifyMiddlewareOperations(webd);
		VerifyOOBUtil.verifyMiddlewareOperations_Details(webd);
	}

	@Test(alwaysRun = true)
	public void verifyMiddlewareOperations_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyMiddlewareOperations_ListView");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Middleware Operations
		webd.getLogger().info("Open the OOB dashboard---Middleware Operations");
		DashboardHomeUtil.selectDashboard(webd, "Middleware Operations");

		//verify Middleware Operations
		VerifyOOBUtil.verifyMiddlewareOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyMiddlewareOperations_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyMiddlewareOperations_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Middleware Operations
		webd.getLogger().info("Open the OOB dashboard---Middleware Operations");
		DashboardHomeUtil.selectDashboard(webd, "Middleware Operations");

		//verify Middleware Operations
		VerifyOOBUtil.verifyMiddlewareOperations(webd);
	}

	@Test(alwaysRun = true)
	public void verifyMiddlewareOperations_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyMiddlewareOperations_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Middleware Operations
		webd.getLogger().info("Open the OOB dashboard---Middleware Operations");
		DashboardHomeUtil.selectDashboard(webd, "Middleware Operations");

		//verify Middleware Operations
		VerifyOOBUtil.verifyMiddlewareOperations(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyOrchestration_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyOrchestration_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Orchestration Workflows
		webd.getLogger().info("Open the OOB dashboard---Orchestration Workflows");
		DashboardHomeUtil.selectDashboard(webd, "Orchestration Workflows");

		//verify Orchestration Workflows
		VerifyOOBUtil.verifyOrchestration(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyOrchestration_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyOrchestration_ListView");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Orchestration Workflows
		webd.getLogger().info("Open the OOB dashboard---Orchestration Workflows");
		DashboardHomeUtil.selectDashboard(webd, "Orchestration Workflows");

		//verify Orchestration Workflows
		VerifyOOBUtil.verifyOrchestration(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyOrchestration_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyOrchestration_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---Orchestration Workflows
		webd.getLogger().info("Open the OOB dashboard---Orchestration Workflows");
		DashboardHomeUtil.selectDashboard(webd, "Orchestration Workflows");

		//verify Orchestration Workflows
		VerifyOOBUtil.verifyOrchestration(webd);
	}

	//@Test(alwaysRun = true)
	public void verifyOrchestration_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyOrchestration_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---Orchestration Workflows
		webd.getLogger().info("Open the OOB dashboard---Orchestration Workflows");
		DashboardHomeUtil.selectDashboard(webd, "Orchestration Workflows");

		//verify Orchestration Workflows
		VerifyOOBUtil.verifyOrchestration(webd);
	}

	@Test(alwaysRun = true)
	public void verifyUIGallery_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyUIGallery_GridView");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---UI Gallery
		webd.getLogger().info("Open the OOB dashboard---UI Gallery");
		DashboardHomeUtil.selectDashboard(webd, "UI Gallery");

		//verify UI Gallery
		VerifyOOBUtil.verifyUIGallery(webd);
		VerifyOOBUtil.verifyUIGallery_Details(webd);
	}

	@Test(alwaysRun = true)
	public void verifyUIGallery_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyUIGallery_ListView");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---UI Gallery
		webd.getLogger().info("Open the OOB dashboard---UI Gallery");
		DashboardHomeUtil.selectDashboard(webd, "UI Gallery");

		//verify UI Gallery
		VerifyOOBUtil.verifyUIGallery(webd);
	}

	@Test(alwaysRun = true)
	public void verifyUIGallery_WithFilter_GridView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyUIGallery_WithFilter_GridView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on Grid View
		webd.getLogger().info("Click on Grid View icon");
		DashboardHomeUtil.gridView(webd);

		//Open the OOB dashboard---UI Gallery
		webd.getLogger().info("Open the OOB dashboard---UI Gallery");
		DashboardHomeUtil.selectDashboard(webd, "UI Gallery");

		//verify UI Gallery
		VerifyOOBUtil.verifyUIGallery(webd);
	}

	@Test(alwaysRun = true)
	public void verifyUIGallery_WithFilter_ListView()
	{
		//initTest
		initTest(Thread.currentThread().getStackTrace()[1].getMethodName());
		webd.getLogger().info("Start the test in verifyUIGallery_WithFilter_ListView");

		//click Filter-oracle
		webd.getLogger().info("Filter by Oracle");
		DashboardHomeUtil.filterOptions(webd, "oracle");

		//click on List View
		webd.getLogger().info("Click on List View icon");
		DashboardHomeUtil.listView(webd);

		//Open the OOB dashboard---UI Gallery
		webd.getLogger().info("Open the OOB dashboard---UI Gallery");
		DashboardHomeUtil.selectDashboard(webd, "UI Gallery");

		//verify UI Gallery
		VerifyOOBUtil.verifyUIGallery(webd);
	}
}
