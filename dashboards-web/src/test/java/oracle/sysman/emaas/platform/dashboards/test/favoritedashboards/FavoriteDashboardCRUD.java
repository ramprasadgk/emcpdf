package oracle.sysman.emaas.platform.dashboards.test.favoritedashboards;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emaas.platform.dashboards.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class FavoriteDashboardCRUD
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

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
		tenantid = ct.getTenantid();
	}

	@Test
	public void favorite_create_invalidId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Verify if the invalid dashboard would be added as favorite dashboard...");
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().post("/dashboards/favorites/0");
			System.out.println("Stauts code is :" + res1.getStatusCode());
			System.out.println("Output is :" + res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 404);
			//Assert.assertEquals(res1.asString(), "Specified dashboard is not found");

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().post("/dashboards/favorites/9999999999");
			System.out.println("Stauts code is :" + res2.getStatusCode());
			System.out.println("Output is :" + res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 404);
			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "20001");
			Assert.assertEquals(res2.jsonPath().getString("errorMessage"), "Specified dashboard is not found");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void favorite_CRUD()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new dashboard");

			String jsonString = "{ \"name\":\"Test_Favorite_Dashboard\"}";
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).body(jsonString).when().post("/dashboards");
			System.out.println(res.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 201);

			String dashboard_id = res.jsonPath().getString("id");
			System.out.println("											");

			System.out.println("Add the newly created dashboard as favorite dashborad...");
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().post("/dashboards/favorites/" + dashboard_id);
			System.out.println("Stauts code is :" + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 204);
			System.out.println("											");

			System.out.println("Verify if the dashboard has been added as favorite dashborad...");
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().get("/dashboards/favorites/" + dashboard_id);
			System.out.println("Stauts code is :" + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 200);
			Assert.assertEquals(res2.jsonPath().getBoolean("isFavorite"), true);
			System.out.println("											");

			System.out.println("Delete the favorite dashborad...");
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().delete("/dashboards/favorites/" + dashboard_id);
			System.out.println("Stauts code is :" + res3.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 204);
			System.out.println("											");

			System.out.println("Verify if the favorite dashboard has been removed...");
			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().get("/dashboards/favorites/" + dashboard_id);
			System.out.println("Stauts code is :" + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 200);
			Assert.assertEquals(res4.jsonPath().getBoolean("isFavorite"), false);
			System.out.println("											");

			System.out.println("cleaning up the dashboard that is created above using DELETE method");
			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().delete("/dashboards/" + dashboard_id);
			System.out.println(res5.asString());
			System.out.println("Status code is: " + res5.getStatusCode());
			Assert.assertTrue(res5.getStatusCode() == 204);

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void favorite_delete_invalidId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Verify if the invalid favorite dashboard would be removed...");
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().delete("/dashboards/favorites/0");
			System.out.println("Stauts code is :" + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 404);
			//Assert.assertEquals(res1.asString(), "Specified dashboard is not found");

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().delete("/dashboards/favorites/9999999999");
			System.out.println("Stauts code is :" + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 404);
			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "20001");
			Assert.assertEquals(res2.jsonPath().getString("errorMessage"), "Specified dashboard is not found");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void favorite_query_all()
	{
		try {
			System.out.println("------------------------------------------");

			System.out.println("Check if there is favorite dashboard...");
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().get("/dashboards/favorites");
			System.out.println("Stauts code is :" + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			List<Integer> origin_id = new ArrayList<Integer>();
			if (res.jsonPath().getString("id") != null && !"".equals(res.jsonPath().getString("id"))) {
				origin_id = res.jsonPath().get("id");
			}

			System.out.println("POST method is in-progress to create a new dashboard");

			String jsonString = "{ \"name\":\"Test_Favorite_Dashboard\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).body(jsonString).when().post("/dashboards");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);

			String dashboard_id = res1.jsonPath().getString("id");
			System.out.println("											");

			System.out.println("Add the newly created dashboard as favorite dashborad...");
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().post("/dashboards/favorites/" + dashboard_id);
			System.out.println("Stauts code is :" + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);
			System.out.println("											");

			System.out.println("Verify if the dashboard has been added as favorite dashborad...");
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().get("/dashboards/favorites");
			System.out.println("Stauts code is :" + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 200);
			List<String> name = new ArrayList<String>();
			name = res3.jsonPath().get("name");
			List<Integer> id = new ArrayList<Integer>();
			id = res3.jsonPath().get("id");

			if (id.size() - origin_id.size() == 1) {
				for (int i = 0; i < id.size(); i++) {
					if (name.get(i).equals("Test_Favorite_Dashboard") && id.get(i).toString().equals(dashboard_id)) {
						System.out.println("cleaning up the dashboard that is created above using DELETE method");
						Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()
								.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().delete("/dashboards/" + dashboard_id);
						System.out.println("Status code is: " + res5.getStatusCode());
						Assert.assertTrue(res5.getStatusCode() == 204);
					}
					else {
						Assert.fail("Get a list of all favorite dashboards failed");
					}
				}
			}
			else {
				Assert.fail("Get a list of all favorite dashboards failed");

			}

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void favorite_query_invalidId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Verify if the invalid favorite dashboard would be queried...");
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().get("/dashboards/favorites/0");
			System.out.println("Stauts code is :" + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 404);
			//Assert.assertEquals(res1.asString(), "Specified dashboard is not found");

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", tenantid).when().get("/dashboards/favorites/9999999999");
			System.out.println("Stauts code is :" + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 404);
			Assert.assertEquals(res2.jsonPath().getString("errorCode"), "20001");
			Assert.assertEquals(res2.jsonPath().getString("errorMessage"), "Specified dashboard is not found");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void headerCheck()
	{
		try {
			System.out.println("------------------------------------------");
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().when()
					.post("/dashboards/favorites/1");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 403);
			Assert.assertEquals(res1.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res1.jsonPath().get("errorMessage"), "'X-USER-IDENTITY-DOMAIN-NAME' is missing in request header");
			System.out.println("											");

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().when()
					.delete("/dashboards/favorites/1");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 403);
			Assert.assertEquals(res2.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res2.jsonPath().get("errorMessage"), "'X-USER-IDENTITY-DOMAIN-NAME' is missing in request header");
			System.out.println("											");

			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything().when()
					.get("/dashboards/favorites/1");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 403);
			Assert.assertEquals(res3.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res3.jsonPath().get("errorMessage"), "'X-USER-IDENTITY-DOMAIN-NAME' is missing in request header");
			System.out.println("											");

			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything().when()
					.post("/dashboards/favorites");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 403);
			Assert.assertEquals(res4.jsonPath().get("errorCode"), 30000);
			Assert.assertEquals(res4.jsonPath().get("errorMessage"), "'X-USER-IDENTITY-DOMAIN-NAME' is missing in request header");
			System.out.println("											");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
