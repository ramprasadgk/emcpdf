package oracle.sysman.emaas.platform.dashboards.test.dashboard;

import java.util.List;

import oracle.sysman.emaas.platform.dashboards.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class DashboardCRUD
{
	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String tenantid;
	static String remoteuser;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
		tenantid = ct.getTenantid();
		remoteuser = ct.getRemoteUser();
	}

	@Test
	public void dashboard_create_emptyPara()
	{
		try {
			System.out.println("------------------------------------------");
			String jsonString1 = "{ \"name\":\"\"}";
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res1.jsonPath().get("errorMessage"),
					"Invalid dashboard name. Name can not be empty and must be less than 64 characters");
			System.out.println("											");

			String jsonString2 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"\"}]}";
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString2).when().post("/dashboards");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res2.jsonPath().get("errorMessage"), "Title for tile is required");

			String jsonString3 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"\"}]}";
			Response res3 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString3).when().post("/dashboards");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 400);
			Assert.assertEquals(res3.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res3.jsonPath().get("errorMessage"), "\"WIDGET_NAME\" is required for tile(s) of the dashboard");

			String jsonString4 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"\"}]}";
			Response res4 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString4).when().post("/dashboards");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 400);
			Assert.assertEquals(res4.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res4.jsonPath().get("errorMessage"),
					"\"WIDGET_UNIQUE_ID\" is required for tile(s) of the dashboard");

			String jsonString5 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"\"}]}";
			Response res5 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString5).when().post("/dashboards");
			System.out.println("Status code is: " + res5.getStatusCode());
			Assert.assertTrue(res5.getStatusCode() == 400);
			Assert.assertEquals(res5.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res5.jsonPath().get("errorMessage"), "\"WIDGET_OWNER\" is required for tile(s) of the dashboard");

			String jsonString6 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"\"}]}";
			Response res6 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString6).when().post("/dashboards");
			System.out.println("Status code is: " + res6.getStatusCode());
			Assert.assertTrue(res6.getStatusCode() == 400);
			Assert.assertEquals(res6.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res6.jsonPath().get("errorMessage"),
					"\"WIDGET_CREATION_TIME\" is required for tile(s) of the dashboard");

			String jsonString7 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": \"\"}]}";
			Response res7 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString7).when().post("/dashboards");
			System.out.println("Status code is: " + res7.getStatusCode());
			Assert.assertTrue(res7.getStatusCode() == 400);
			Assert.assertEquals(res7.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res7.jsonPath().get("errorMessage"), "\"WIDGET_SOURCE\" is required for tile(s) of the dashboard");

			String jsonString8 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"\"}]}";
			Response res8 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString8).when().post("/dashboards");
			System.out.println("Status code is: " + res8.getStatusCode());
			Assert.assertTrue(res8.getStatusCode() == 400);
			Assert.assertEquals(res8.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res8.jsonPath().get("errorMessage"),
					"\"WIDGET_KOC_NAME\" is required for tile(s) of the dashboard");

			String jsonString9 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\":\"\"}]}";
			Response res9 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString9).when().post("/dashboards");
			System.out.println("Status code is: " + res9.getStatusCode());
			Assert.assertTrue(res9.getStatusCode() == 400);
			Assert.assertEquals(res9.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res9.jsonPath().get("errorMessage"),
					"\"WIDGET_VIEWMODEL\" is required for tile(s) of the dashboard");

			String jsonString10 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\":\"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\":\"\"}]}";
			Response res10 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString10).when().post("/dashboards");
			System.out.println("Status code is: " + res10.getStatusCode());
			Assert.assertTrue(res10.getStatusCode() == 400);
			Assert.assertEquals(res10.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res10.jsonPath().get("errorMessage"),
					"\"WIDGET_TEMPLATE\" is required for tile(s) of the dashboard");

			String jsonString11 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\":\"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\":\"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"\"}]}";
			Response res11 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString11).when().post("/dashboards");
			System.out.println("Status code is: " + res11.getStatusCode());
			Assert.assertTrue(res11.getStatusCode() == 400);
			Assert.assertEquals(res11.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res11.jsonPath().get("errorMessage"),
					"\"PROVIDER_NAME\" is required for tile(s) of the dashboard");

			String jsonString12 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\":\"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\":\"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"X Analytics\",\"PROVIDER_VERSION\": \"\"}]}";
			Response res12 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString12).when().post("/dashboards");
			System.out.println("Status code is: " + res12.getStatusCode());
			Assert.assertTrue(res12.getStatusCode() == 400);
			Assert.assertEquals(res12.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res12.jsonPath().get("errorMessage"),
					"\"PROVIDER_VERSION\" is required for tile(s) of the dashboard");

			String jsonString13 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\":\"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\":\"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"X Analytics\",\"PROVIDER_VERSION\": \"0.1\",\"PROVIDER_ASSET_ROOT\": \"\"}]}";
			Response res13 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString13).when().post("/dashboards");
			System.out.println("Status code is: " + res13.getStatusCode());
			Assert.assertTrue(res13.getStatusCode() == 400);
			Assert.assertEquals(res13.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res13.jsonPath().get("errorMessage"),
					"\"PROVIDER_ASSET_ROOT\" is required for tile(s) of the dashboard");

			String jsonString14 = "{ \"name\":\"test_emptyPara\", \"tiles\":[{\"title\":\"test1\", \"WIDGET_NAME\": \"test_widget\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\":\"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\":\"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"X Analytics\",\"PROVIDER_VERSION\": \"0.1\",\"PROVIDER_ASSET_ROOT\": \"assetRoot\",\"tileParameters\": [{\"name\": \"\"}]}]}";
			Response res14 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString14).when().post("/dashboards");
			System.out.println("Status code is: " + res14.getStatusCode());
			Assert.assertTrue(res14.getStatusCode() == 400);
			Assert.assertEquals(res14.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res14.jsonPath().get("errorMessage"), "\"Name\" is required for tile parameter(s) of the tile");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void dashboard_create_simple()
	{

		/* create a dashboard with required field
		 *
		 */
		String dashboard_id = "";
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new dashboard");

			String jsonString1 = "{ \"name\":\"Test_Dashboard\"}";
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("Verfify whether the timestamp of create&modify is same or not");
			//Assert.assertEquals(res1.jsonPath().get("createdOn"), res1.jsonPath().get("lastModifiedOn"));
			System.out.println("											");

			dashboard_id = res1.jsonPath().getString("id");

			System.out.println("Verify that the created dashboard will be queried...");
			Response res4 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().get("/dashboards/" + dashboard_id);
			System.out.println(res4.asString());
			System.out.println("Status code is:  " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 200);
			Assert.assertEquals(res4.jsonPath().getString("name"), "Test_Dashboard");
			Assert.assertEquals(res4.jsonPath().getString("type"), "NORMAL");
			Assert.assertEquals(res4.jsonPath().getString("id"), dashboard_id);
			Assert.assertEquals(res4.jsonPath().getBoolean("systemDashboard"), false);
			Assert.assertEquals(res4.jsonPath().getString("tiles"), "[]");
			System.out.println("											");

			System.out.println("Verify that creating dashbaord with existed Id won't be successful");
			String jsonString2 = "{ \"id\":" + dashboard_id + ",\"name\": \"Dashboard_with_existed_ID\"}";
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString2).when().post("/dashboards");
			System.out.println(res2.asString());
			System.out.println("Status code is:  " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "10000");
			Assert.assertEquals(res2.jsonPath().getString("errorMessage"),
					"Failed to create a dashboard by specifying an ID exists already");
			System.out.println("											");

			System.out.println("Verify that creating dashbaord with existed name won't be successful");
			String jsonString3 = "{ \"name\":\"Test_Dashboard\"}";
			Response res3 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString3).when().post("/dashboards");
			System.out.println(res3.asString());
			System.out.println("Status code is:  " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 400);
			Assert.assertEquals(res3.jsonPath().getString("errorCode"), "10001");
			Assert.assertEquals(res3.jsonPath().getString("errorMessage"), "Dashboard with the same name exists already");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			if (!dashboard_id.equals("")) {
				System.out.println("cleaning up the dashboard that is created above using DELETE method");
				Response res5 = RestAssured
						.given()
						.contentType(ContentType.JSON)
						.log()
						.everything()
						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
				System.out.println(res5.asString());
				System.out.println("Status code is: " + res5.getStatusCode());
				Assert.assertTrue(res5.getStatusCode() == 204);
			}
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
	}

	@Test
	public void dashboard_create_withTile()
	{
		String dashboard_id = "";
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new dashboard");

			String jsonString1 = "{\"name\": \"test_dashboard_tile\",\"tiles\":[{\"title\":\"A sample tile for search\",\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_NAME\": \"iFrame\",\"WIDGET_DESCRIPTION\": \"A widget which can show another page inside a iFrame\",\"WIDGET_ICON\": \"dependencies/built-in/iFrame/images/icon.png\",\"WIDGET_HISTOGRAM\": \"dependencies/built-in/iFrame/images/histogram.png\",\"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\", \"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\": \"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\": \"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\": \"dependencies/built-in/iFrame/iFrame.html\", \"PROVIDER_NAME\": \"X Analytics\", \"PROVIDER_VERSION\": \"0.1\",\"PROVIDER_ASSET_ROOT\": \"asset\"}]}";
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);

			dashboard_id = res1.jsonPath().getString("id");
			System.out.println("											");

			System.out.println("Get the details of the created dashboard...");
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().get("/dashboards/" + dashboard_id);
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 200);
			Assert.assertEquals(res2.jsonPath().get("tiles.title[0]"), "A sample tile for search");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			if (!dashboard_id.equals("")) {
				System.out.println("cleaning up the dashboard that is created above using DELETE method");
				Response res5 = RestAssured
						.given()
						.contentType(ContentType.JSON)
						.log()
						.everything()
						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
				System.out.println(res5.asString());
				System.out.println("Status code is: " + res5.getStatusCode());
				Assert.assertTrue(res5.getStatusCode() == 204);
			}
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
	}

	@Test
	public void dashboard_delete_invalidId()
	{
		try {
			System.out.println("------------------------------------------");
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().delete("/dashboards/99999999");
			System.out.println("Status code:" + res1.getStatusCode());
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.jsonPath().getString("errorCode"), "20001");
			Assert.assertEquals(res1.jsonPath().getString("errorMessage"), "Specified dashboard is not found");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	//	@Test
	//	public void dashboard_delete_sytemDashboard()
	//	{
	//		try {
	//			System.out.println("------------------------------------------");
	//			Response res1 = RestAssured
	//					.given()
	//					.contentType(ContentType.JSON)
	//					.log()
	//					.everything()
	//					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
	//							"Authorization", authToken).when().delete("/dashboards/1");
	//			System.out.println("Status code:" + res1.getStatusCode());
	//			System.out.println(res1.asString());
	//			Assert.assertTrue(res1.getStatusCode() == 403);
	//			Assert.assertEquals(res1.jsonPath().getString("errorCode"), "30001");
	//			Assert.assertEquals(res1.jsonPath().getString("errorMessage"), "Not support to delete system dashboard");
	//			System.out.println("											");
	//			System.out.println("------------------------------------------");
	//			System.out.println("											");
	//		}
	//		catch (Exception e) {
	//			Assert.fail(e.getLocalizedMessage());
	//		}
	//
	//	}

	@Test
	public void dashboard_query_multi()
	{
		int totalResults = 0;
		int count = 0;
		int limit = 0;
		String dashboard_id = "";
		try {
			//get all dashboards, default is to display 20
			System.out.println("List all dashboard...");
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().get("/dashboards/");
			Assert.assertTrue(res1.getStatusCode() == 200);
			List<String> list_dashboards = res1.jsonPath().get("dashboards.name");
			totalResults = res1.jsonPath().getInt("totalResults");
			limit = res1.jsonPath().getInt("limit");
			count = res1.jsonPath().getInt("count");
			Assert.assertEquals(count, list_dashboards.size());

			String jsonString1 = "{ \"name\":\"Test_Dashboard_QueryAll\"}";
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 201);

			dashboard_id = res2.jsonPath().getString("id");

			System.out.println("List the dashboards with ...");
			Response res3 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when()
					.get("/dashboards?queryString=Test_Dashboard_QueryAll&offset=0&limit=5");
			Assert.assertTrue(res3.getStatusCode() == 200);
			list_dashboards = res3.jsonPath().get("dashboards.name");
			totalResults = res3.jsonPath().getInt("totalResults");
			limit = res3.jsonPath().getInt("limit");
			count = res3.jsonPath().getInt("count");
			Assert.assertEquals(count, list_dashboards.size());
			Assert.assertEquals(count, 1);
			Assert.assertEquals(totalResults, 1);
			Assert.assertEquals(limit, 5);
			Assert.assertEquals(res3.jsonPath().getInt("offset"), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			if (!dashboard_id.equals("")) {
				System.out.println("cleaning up the dashboard that is created above using DELETE method");
				Response res5 = RestAssured
						.given()
						.contentType(ContentType.JSON)
						.log()
						.everything()
						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
				System.out.println(res5.asString());
				System.out.println("Status code is: " + res5.getStatusCode());
				Assert.assertTrue(res5.getStatusCode() == 204);
			}
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
	}

	@Test
	public void dashboard_query_multi_invalid_para()
	{
		try {
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().get("/dashboards?limit=0");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res1.jsonPath().get("errorMessage"),
					"Specified limit for the dashboard query is invalid: numeric number larger than zero is expected");

			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().get("/dashboards?limit=-1");
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res2.jsonPath().get("errorMessage"),
					"Specified limit for the dashboard query is invalid: numeric number larger than zero is expected");

			Response res3 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).when().get("/dashboards?offset=-1");
			Assert.assertTrue(res3.getStatusCode() == 400);
			Assert.assertEquals(res3.jsonPath().get("errorCode"), 10000);
			Assert.assertEquals(res3.jsonPath().get("errorMessage"),
					"Specified offset for the dashboard query is invalid: numeric number larger than or equal to zero is expected");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void dashboard_update()
	{
		String dashboard_id = "";
		try {
			//create a new dashboard
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new dashboard");

			String jsonString = "{ \"name\":\"Test_Update_Dashboard\"}";
			Response res = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString).when().post("/dashboards");
			System.out.println(res.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 201);

			dashboard_id = res.jsonPath().getString("id");

			//update the simple dashboard
			System.out.println("											");
			System.out.println("Update the created dashboard...");

			String jsonString1 = "{\"name\": \"Test_Update_Dashboard_Edit\"}";
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().put("/dashboards/" + dashboard_id);
			System.out.println(res1.asString());
			System.out.println("==PUT operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 200);
			Assert.assertEquals(res1.jsonPath().get("name"), "Test_Update_Dashboard_Edit");
			Assert.assertNotEquals(res1.jsonPath().get("createOn"), res1.jsonPath().get("lastModifiedOn"));

			//update the dashboard with tile
			System.out.println("											");
			System.out.println("Update the dashboard with tile...");
			String jsonString2 = "{\"name\":\"Test_Update_Dashboard_Edit_NewTile\",\"tiles\":[{\"title\":\"Another tile for search\", \"isMaximized\": false,\"width\": 2,\"height\": 220,\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_NAME\": \"iFrame\",\"WIDGET_DESCRIPTION\": \"A widget which can show another page inside a iFrame\",\"WIDGET_GROUP_NAME\": \"Built-in Widgets\",\"WIDGET_ICON\": \"dependencies/built-in/iFrame/images/icon.png\",\"WIDGET_HISTOGRAM\": \"dependencies/built-in/iFrame/images/histogram.png\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\": \"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\": \"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\": \"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"X Analytics\",\"PROVIDER_VERSION\": \"0.1\",\"PROVIDER_ASSET_ROOT\": \"asset\"}]}";
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString2).when().put("/dashboards/" + dashboard_id);
			System.out.println(res2.asString());
			System.out.println("==PUT operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 200);
			String tile_id = res2.jsonPath().get("tiles.tileId[0]").toString();
			Assert.assertEquals(res2.jsonPath().get("name"), "Test_Update_Dashboard_Edit_NewTile");
			Assert.assertNotEquals(res2.jsonPath().get("createOn"), res2.jsonPath().get("lastModifiedOn"));
			Assert.assertEquals(res2.jsonPath().get("tiles.title[0]"), "Another tile for search");

			//update the existed tile in dashboard
			System.out.println("											");
			System.out.println("Update the exsited tile in dashboard");
			String jsonString3 = "{\"name\":\"Test_Update_Dashboard_Edit_ExistTile\",\"tiles\":[{\"tileId\": "
					+ tile_id
					+ ",\"title\":\"Another tile for search_edit\", \"isMaximized\": false,\"width\": 2,\"height\": 220,\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_NAME\": \"iFrame\",\"WIDGET_DESCRIPTION\": \"A widget which can show another page inside a iFrame\",\"WIDGET_GROUP_NAME\": \"Built-in Widgets\",\"WIDGET_ICON\": \"dependencies/built-in/iFrame/images/icon.png\",\"WIDGET_HISTOGRAM\": \"dependencies/built-in/iFrame/images/histogram.png\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\": \"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\": \"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\": \"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"X Analytics\",\"PROVIDER_VERSION\": \"0.1\",\"PROVIDER_ASSET_ROOT\": \"asset\",\"tileParameters\": [{\"name\": \"WIDGET_CREATED_BY\",\"systemParameter\":false, \"type\": \"STRING\",\"value\":\"SYSMAN\"}]}]}";
			Response res3 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString3).when().put("/dashboards/" + dashboard_id);
			System.out.println(res3.asString());
			System.out.println("==PUT operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 200);
			Assert.assertEquals(res3.jsonPath().get("name"), "Test_Update_Dashboard_Edit_ExistTile");
			Assert.assertNotEquals(res3.jsonPath().get("createOn"), res3.jsonPath().get("lastModifiedOn"));
			Assert.assertEquals(res3.jsonPath().get("tiles.title[0]"), "Another tile for search_edit");
			Assert.assertEquals(res3.jsonPath().get("tiles.tileParameters[0].name[0]"), "WIDGET_CREATED_BY");
			Assert.assertEquals(res3.jsonPath().get("tiles.tileParameters[0].type[0]"), "STRING");
			Assert.assertEquals(res3.jsonPath().get("tiles.tileParameters[0].systemParameter[0]"), false);

			//update the dashboard with new tile
			System.out.println("											");
			System.out.println("Add a new tile in dashboard");
			String jsonString4 = "{\"name\":\"Test_Update_Dashboard_Edit_NewTile\",\"tiles\":[{\"title\":\"A sample tile for search\", \"isMaximized\": false,\"width\": 2,\"height\": 220,\"WIDGET_UNIQUE_ID\": \"1001\", \"WIDGET_NAME\": \"iFrame\",\"WIDGET_DESCRIPTION\": \"A widget which can show another page inside a iFrame\",\"WIDGET_GROUP_NAME\": \"Built-in Widgets\",\"WIDGET_ICON\": \"dependencies/built-in/iFrame/images/icon.png\",\"WIDGET_HISTOGRAM\": \"dependencies/built-in/iFrame/images/histogram.png\", \"WIDGET_OWNER\": \"SYSMAN\",\"WIDGET_CREATION_TIME\": \"2014-11-11T19:20:30.45Z\",\"WIDGET_SOURCE\": 0,\"WIDGET_KOC_NAME\": \"DF_V1_WIDGET_IFRAME\",\"WIDGET_VIEWMODEL\": \"dependencies/built-in/iFrame/js/iFrameViewModel.js\",\"WIDGET_TEMPLATE\": \"dependencies/built-in/iFrame/iFrame.html\",\"PROVIDER_NAME\": \"X Analytics\",\"PROVIDER_VERSION\": \"0.1\",\"PROVIDER_ASSET_ROOT\": \"asset\",\"tileParameters\": [{\"name\": \"WIDGET_CREATED_BY\",\"systemParameter\":false, \"type\":\"STRING\",\"value\":\"SYSMAN\"}]}]}";
			Response res4 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString4).when().put("/dashboards/" + dashboard_id);
			System.out.println(res4.asString());
			System.out.println("==PUT operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 200);
			String tile_newid = res4.jsonPath().get("tiles.tileId[0]").toString();
			System.out.println(tile_newid);
			Assert.assertEquals(res4.jsonPath().get("name"), "Test_Update_Dashboard_Edit_NewTile");
			Assert.assertNotEquals(res4.jsonPath().get("createOn"), res4.jsonPath().get("lastModifiedOn"));
			Assert.assertEquals(res4.jsonPath().get("tiles.title[0]"), "A sample tile for search");
			Assert.assertNotEquals(tile_newid, tile_id);
			Assert.assertEquals(res4.jsonPath().get("tiles.tileParameters[0].name[0]"), "WIDGET_CREATED_BY");
			Assert.assertEquals(res4.jsonPath().get("tiles.tileParameters[0].type[0]"), "STRING");
			Assert.assertEquals(res4.jsonPath().get("tiles.tileParameters[0].systemParameter[0]"), false);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			if (!dashboard_id.equals("")) {
				System.out.println("cleaning up the dashboard that is created above using DELETE method");
				Response res5 = RestAssured
						.given()
						.contentType(ContentType.JSON)
						.log()
						.everything()
						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
				System.out.println(res5.asString());
				System.out.println("Status code is: " + res5.getStatusCode());
				Assert.assertTrue(res5.getStatusCode() == 204);
			}
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}

	}

	@Test
	public void dashboard_update_invalidId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to validate update a non-existed dashboard");
			String jsonString = "{ \"name\":\"Custom_DashboardEdit\" }";
			Response res = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString).when().put("/dashboards/999999999");

			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println(res.asString());

			Assert.assertTrue(res.getStatusCode() == 404);

			Assert.assertEquals(res.jsonPath().getString("errorCode"), "20001");
			Assert.assertEquals(res.jsonPath().getString("errorMessage"), "Specified dashboard is not found");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void dashboard_update_systemDashboard()
	{
		String dashboard_id = "";
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to validate update a system dashboard");
			String jsonString = "{ \"name\":\"Custom_Dashboard_Edit\" }";
			Response res = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString).when().put("/dashboards/1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.jsonPath().getString("errorCode"), "20000");
			Assert.assertEquals(res.jsonPath().getString("errorMessage"), "Not allow to update \"systemDashboard\"");
			System.out.println("											");

			System.out.println("This test is to validate update a custom dashboard to the system dashboard");
			System.out.println("Create a new dashboard...");
			String jsonString1 = "{ \"name\":\"Test_Custom_Dashboard\"}";
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			Assert.assertTrue(res1.getStatusCode() == 201);
			dashboard_id = res1.jsonPath().getString("id");
			System.out.println("Update the dashboard to system dashboard...");
			String jsonString2 = "{ \"name\":\"Custom_Dashboard_Edit\", \"systemDashboard\":true }";
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString2).when().put("/dashboards/" + dashboard_id);
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 404);
			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "20000");
			Assert.assertEquals(res2.jsonPath().getString("errorMessage"), "Not allow to update \"systemDashboard\"");

			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			if (!dashboard_id.equals("")) {
				System.out.println("cleaning up the dashboard that is created above using DELETE method");
				Response res5 = RestAssured
						.given()
						.contentType(ContentType.JSON)
						.log()
						.everything()
						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
				System.out.println(res5.asString());
				System.out.println("Status code is: " + res5.getStatusCode());
				Assert.assertTrue(res5.getStatusCode() == 204);
			}
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}

	}

	@Test
	public void multitenant_headerCheck()
	{
		try {
			System.out.println("------------------------------------------");
			String jsonString1 = "{ \"name\":\"test_header\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 403);
			Assert.assertEquals(res1.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res1.jsonPath().get("errorMessage"),
					"\"X-USER-IDENTITY-DOMAIN-NAME\" is missing in request header");
			System.out.println("											");

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).body(jsonString1).when().put("/dashboards/1");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 403);
			Assert.assertEquals(res2.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res2.jsonPath().get("errorMessage"),
					"\"X-USER-IDENTITY-DOMAIN-NAME\" is missing in request header");
			System.out.println("											");

			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).when().get("/dashboards/1");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 403);
			Assert.assertEquals(res3.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res3.jsonPath().get("errorMessage"),
					"\"X-USER-IDENTITY-DOMAIN-NAME\" is missing in request header");
			System.out.println("											");

			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).when().delete("/dashboards/1");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 403);
			Assert.assertEquals(res4.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res4.jsonPath().get("errorMessage"),
					"\"X-USER-IDENTITY-DOMAIN-NAME\" is missing in request header");
			System.out.println("											");

			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).when().get("/dashboards");
			System.out.println("Status code is: " + res5.getStatusCode());
			Assert.assertTrue(res5.getStatusCode() == 403);
			Assert.assertEquals(res5.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res5.jsonPath().get("errorMessage"),
					"\"X-USER-IDENTITY-DOMAIN-NAME\" is missing in request header");
			System.out.println("											");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void multitenant_queryupdatedelete()
	{
		String dashboard_id = "";
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new dashboard");

			String jsonString1 = "{ \"name\":\"Test_Dashboard_multitenant\"}";
			Response res1 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");

			dashboard_id = res1.jsonPath().getString("id");

			System.out.println("Verify that the created dashboard can't be queried by other tenant...");
			Response res2 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", "errortenant", "X-REMOTE-USER", tenantid + ".userA", "Authorization",
							authToken).when().get("/dashboards/" + dashboard_id);
			System.out.println(res2.asString());
			System.out.println("Status code is:  " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 403);
			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "30000");
			Assert.assertEquals(res2.jsonPath().getString("errorMessage"), "Tenant Name is not recognized: errortenant");
			System.out.println("											");

			System.out.println("Verify that the created dashboard can't be update by other tenant...");
			String jsonString2 = "{ \"name\":\"Test_Dashboard_multitenant\"}";
			Response res3 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", "errortenant", "X-REMOTE-USER", tenantid + ".userA", "Authorization",
							authToken).body(jsonString2).when().put("/dashboards/" + dashboard_id);
			System.out.println(res3.asString());
			System.out.println("Status code is:  " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 403);
			Assert.assertEquals(res3.jsonPath().getString("errorCode"), "30000");
			Assert.assertEquals(res3.jsonPath().getString("errorMessage"), "Tenant Name is not recognized: errortenant");
			System.out.println("											");

			System.out.println("Verify that the created dashboard can't be deleted by other tenant...");
			Response res4 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.log()
					.everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", "errortenant", "X-REMOTE-USER", tenantid + ".userA", "Authorization",
							authToken).when().delete("/dashboards/" + dashboard_id);
			System.out.println(res4.asString());
			System.out.println("Status code is:  " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 403);
			Assert.assertEquals(res4.jsonPath().getString("errorCode"), "30000");
			Assert.assertEquals(res4.jsonPath().getString("errorMessage"), "Tenant Name is not recognized: errortenant");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			if (!dashboard_id.equals("")) {
				System.out.println("cleaning up the dashboard that is created above using DELETE method");
				Response res5 = RestAssured
						.given()
						.contentType(ContentType.JSON)
						.log()
						.everything()
						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
				System.out.println(res5.asString());
				System.out.println("Status code is: " + res5.getStatusCode());
				Assert.assertTrue(res5.getStatusCode() == 204);
			}
		}
	}

	@Test
	public void remoteuser_headerCheck()
	{
		try {
			System.out.println("------------------------------------------");
			String jsonString1 = "{ \"name\":\"test_header\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "Authorization", authToken).body(jsonString1).when()
					.post("/dashboards");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 403);
			Assert.assertEquals(res1.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res1.jsonPath().get("errorMessage"),
					"Valid header \"X-REMOTE-USER\" in format of <tenant_name>.<user_name> is required");
			System.out.println("											");

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "Authorization", authToken).body(jsonString1).when()
					.put("/dashboards/1");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 403);
			Assert.assertEquals(res2.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res2.jsonPath().get("errorMessage"),
					"Valid header \"X-REMOTE-USER\" in format of <tenant_name>.<user_name> is required");
			System.out.println("											");

			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "Authorization", authToken).when().get("/dashboards/1");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 403);
			Assert.assertEquals(res3.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res3.jsonPath().get("errorMessage"),
					"Valid header \"X-REMOTE-USER\" in format of <tenant_name>.<user_name> is required");
			System.out.println("											");

			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "Authorization", authToken).when().delete("/dashboards/1");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 403);
			Assert.assertEquals(res4.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res4.jsonPath().get("errorMessage"),
					"Valid header \"X-REMOTE-USER\" in format of <tenant_name>.<user_name> is required");
			System.out.println("											");

			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "Authorization", authToken).when().get("/dashboards");
			System.out.println("Status code is: " + res5.getStatusCode());
			Assert.assertTrue(res5.getStatusCode() == 403);
			Assert.assertEquals(res5.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res5.jsonPath().get("errorMessage"),
					"Valid header \"X-REMOTE-USER\" in format of <tenant_name>.<user_name> is required");
			System.out.println("											");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	//	@Test
	//	public void remoteuser_queryupdatedelete()
	//	{
	//		String dashboard_id = "";
	//		try {
	//			System.out.println("------------------------------------------");
	//			System.out.println("POST method is in-progress to create a new dashboard");
	//
	//			String jsonString1 = "{ \"name\":\"Test_Dashboard_remoteuser\"}";
	//			Response res1 = RestAssured
	//					.given()
	//					.contentType(ContentType.JSON)
	//					.log()
	//					.everything()
	//					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
	//							"Authorization", authToken).body(jsonString1).when().post("/dashboards");
	//			System.out.println(res1.asString());
	//			System.out.println("==POST operation is done");
	//			System.out.println("											");
	//			System.out.println("Status code is: " + res1.getStatusCode());
	//			Assert.assertTrue(res1.getStatusCode() == 201);
	//			System.out.println("											");
	//
	//			dashboard_id = res1.jsonPath().getString("id");
	//
	//			System.out.println("Verify that the created dashboard can't be queried by other user...");
	//			Response res2 = RestAssured
	//					.given()
	//					.contentType(ContentType.JSON)
	//					.log()
	//					.everything()
	//					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + ".userA", "Authorization",
	//							authToken).when().get("/dashboards/" + dashboard_id);
	//			System.out.println(res2.asString());
	//			System.out.println("Status code is:  " + res2.getStatusCode());
	//			Assert.assertTrue(res2.getStatusCode() == 404);
	//			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "20001");
	//			Assert.assertEquals(res2.jsonPath().getString("errorMessage"), "Specified dashboard is not found");
	//			System.out.println("											");
	//
	//			System.out.println("Verify that the created dashboard can't be update by other user...");
	//			String jsonString2 = "{ \"name\":\"Test_Dashboard_multitenant\"}";
	//			Response res3 = RestAssured
	//					.given()
	//					.contentType(ContentType.JSON)
	//					.log()
	//					.everything()
	//					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + ".userA", "Authorization",
	//							authToken).body(jsonString2).when().put("/dashboards/" + dashboard_id);
	//			System.out.println(res3.asString());
	//			System.out.println("Status code is:  " + res3.getStatusCode());
	//			Assert.assertTrue(res3.getStatusCode() == 404);
	//			Assert.assertEquals(res3.jsonPath().getString("errorCode"), "20001");
	//			Assert.assertEquals(res3.jsonPath().getString("errorMessage"), "Specified dashboard is not found");
	//			System.out.println("											");
	//
	//			System.out.println("Verify that the created dashboard can't be deleted by other user...");
	//			Response res4 = RestAssured
	//					.given()
	//					.contentType(ContentType.JSON)
	//					.log()
	//					.everything()
	//					.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + ".userA", "Authorization",
	//							authToken).when().delete("/dashboards/" + dashboard_id);
	//			System.out.println(res4.asString());
	//			System.out.println("Status code is:  " + res4.getStatusCode());
	//			Assert.assertTrue(res4.getStatusCode() == 404);
	//			Assert.assertEquals(res4.jsonPath().getString("errorCode"), "20001");
	//			Assert.assertEquals(res4.jsonPath().getString("errorMessage"), "Specified dashboard is not found");
	//			System.out.println("											");
	//		}
	//		catch (Exception e) {
	//			Assert.fail(e.getLocalizedMessage());
	//		}
	//		finally {
	//			if (!dashboard_id.equals("")) {
	//				System.out.println("cleaning up the dashboard that is created above using DELETE method");
	//				Response res5 = RestAssured
	//						.given()
	//						.contentType(ContentType.JSON)
	//						.log()
	//						.everything()
	//						.headers("X-USER-IDENTITY-DOMAIN-NAME", tenantid, "X-REMOTE-USER", tenantid + "." + remoteuser,
	//								"Authorization", authToken).when().delete("/dashboards/" + dashboard_id);
	//				System.out.println(res5.asString());
	//				System.out.println("Status code is: " + res5.getStatusCode());
	//				Assert.assertTrue(res5.getStatusCode() == 204);
	//			}
	//		}
	//	}
}