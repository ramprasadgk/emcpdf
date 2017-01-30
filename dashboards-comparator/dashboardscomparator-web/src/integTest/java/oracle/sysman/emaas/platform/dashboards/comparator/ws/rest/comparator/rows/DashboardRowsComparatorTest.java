package oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows;

import java.io.IOException;
import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

import mockit.Deencapsulation;
import oracle.sysman.emaas.platform.dashboards.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.DashboardRowEntity;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.DashboardSetRowEntity;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.DashboardTileParamsRowEntity;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.DashboardTileRowEntity;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.DashboardUserOptionsRowEntity;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.PreferenceRowEntity;
import oracle.sysman.emaas.platform.dashboards.comparator.ws.rest.comparator.rows.entities.TableRowsEntity;
@Test(groups = { "s1" })
public class DashboardRowsComparatorTest
{
	private static final String DASHBOARD1_NAME = "D1";
	private static final String DASHBOARD1_OPTIONS = "options1";
	private static final String CREATION_DATE = "2016-07-21 07:37:48.060864";
	private static final String LAST_MODIFICATION_DATE = "2016-07-25 07:37:48.060864";

	// @formatter:off
	private static final String JSON_RESPONSE_DATA_TABLE="{"
			+ "\"EMS_DASHBOARD\": [{"
			+ 		"\"DASHBOARD_ID\":1,"
			+ 		"\"NAME\":\"" + DASHBOARD1_NAME + "\","
			+ 		"\"TYPE\":1,"
			+ 		"\"DESCRIPTION\":\"desc1\","
			+ 		"\"OWNER\":\"emcsadmin\","
			+ 		"\"IS_SYSTEM\":1,"
			+ 		"\"APPLICATION_TYPE\":1,"
			+ 		"\"ENABLE_TIME_RANGE\":1,"
			+ 		"\"DELETED\":1,"
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"ENABLE_REFRESH\":1,"
			+ 		"\"SHARE_PUBLIC\":0,"
			+ 		"\"ENABLE_ENTITY_FILTER\":0,"
			+ 		"\"ENABLE_DESCRIPTION\":1,"
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"EXTENDED_OPTIONS\":\"" + DASHBOARD1_OPTIONS + "\""
			+ 	"}],"
			+ "\"EMS_DASHBOARD_SET\": [{"
			+ 		"\"DASHBOARD_SET_ID\": 12, "
			+ 		"\"TENANT_ID\":1, "
			+ 		"\"SUB_DASHBOARD_ID\":1,"
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"POSITION\": 0"
			+ "}],"
			+ "\"EMS_DASHBOARD_TILE\": [{"
			+ 		"\"TILE_ID\": 12, "
			+ 		"\"DASHBOARD_ID\":1,"
			+ 		"\"OWNER\":\"emcsadmin\","
			+ 		"\"TITLE\":\"tile1 title\","
			+ 		"\"HEIGHT\":2,"
			+ 		"\"WIDTH\":6,"
			+ 		"\"IS_MAXIMIZED\":1,"
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"WIDGET_LINKED_DASHBOARD\": 1021,"
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"POSITION\": 0"
			+ "}],"
			+ "\"EMS_DASHBOARD_TILE_PARAMS\": [{"
			+ 		"\"TILE_ID\":12,"
			+ 		"\"PARAM_NAME\":\"Test name\","
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"IS_SYSTEM\":1,"
			+ 		"\"PARAM_TYPE\":1,"
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"PARAM_VALUE_STR\":\"test value\"}],"
			+ "\"EMS_DASHBOARD_USER_OPTIONS\": [{"
			+ 		"\"USER_NAME\":\"emcsadmin\","
			+ 		"\"TENANT_ID\":1,"
			+ 		"\"DASHBOARD_ID\":1,"
			+ 		"\"AUTO_REFRESH_INTERVAL\":1,"
			+ 		"\"IS_FAVORITE\":1,"
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"EXTENDED_OPTIONS\":\"options1\"}],"
			+ "\"EMS_PREFERENCE\": [{"
			+ 		"\"USER_NAME\":\"emcsadmin1\","
			+ 		"\"PREF_KEY\":\"Dashboards.showWelcomeDialog\","
			+ 		"\"PREF_VALUE\":\"false\","
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"TENANT_ID\":1"
			+ 	"},{"
			+ 		"\"USER_NAME\":\"emcsadmin2\","
			+ 		"\"PREF_KEY\":\"Dashboards.showWelcomeDialog\","
			+ 		"\"PREF_VALUE\":\"true\","
			+ 		"\"CREATION_DATE\":\"" + CREATION_DATE + "\","
			+ 		"\"LAST_MODIFICATION_DATE\":\"" + LAST_MODIFICATION_DATE + "\","
			+ 		"\"TENANT_ID\":1"
			+ 	"}]"
			+ "}";
	// @formatter:on

	@Test
	public void testCompareInstancesData() throws IOException
	{
		DashboardRowsComparator drc = new DashboardRowsComparator();
		TableRowsEntity tre1 = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance",
				JSON_RESPONSE_DATA_TABLE);
		TableRowsEntity tre2 = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance",
				JSON_RESPONSE_DATA_TABLE);
		InstancesComparedData<TableRowsEntity> cd = Deencapsulation.invoke(drc, "compareInstancesData",
				new InstanceData<TableRowsEntity>(null, tre1), new InstanceData<TableRowsEntity>(null, tre2));
		// the 2 instances have the same data, so there is no difference from the compared result
		TableRowsEntity result1 = cd.getInstance1().getData();
		TableRowsEntity result2 = cd.getInstance1().getData();
		Assert.assertNull(result1.getEmsDashboard());
		
		Assert.assertNull(result1.getEmsDashboardSet());
		Assert.assertNull(result1.getEmsDashboardTile());
		Assert.assertNull(result1.getEmsDashboardTileParams());
		Assert.assertNull(result1.getEmsDashboardUserOptions());
		Assert.assertNull(result1.getEmsPreference());
		Assert.assertNull(result2.getEmsDashboard());
		Assert.assertNull(result2.getEmsDashboardSet());
		Assert.assertNull(result2.getEmsDashboardTile());
		Assert.assertNull(result2.getEmsDashboardTileParams());
		Assert.assertNull(result2.getEmsDashboardUserOptions());
		Assert.assertNull(result2.getEmsPreference());

		// let's introduce more 'differece' to instance 1
		tre1 = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance", JSON_RESPONSE_DATA_TABLE);
		tre2 = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance", JSON_RESPONSE_DATA_TABLE);
		DashboardRowEntity dre1 = new DashboardRowEntity();
		dre1.setApplicationType(1);
		dre1.setDashboardId(new BigInteger("1001"));
		dre1.setDeleted(new BigInteger("1"));
		dre1.setIsSystem(1);
		tre1.getEmsDashboard().add(dre1);
		
		DashboardSetRowEntity dsre1 = new DashboardSetRowEntity();
		dsre1.setDashboardSetId(new BigInteger("1002"));
		dsre1.setPosition(0L);
		dsre1.setSubDashboardId(1001L);
		dsre1.setTenantId(1L);
		tre1.getEmsDashboardSet().add(dsre1);
		DashboardTileParamsRowEntity dtpre1 = new DashboardTileParamsRowEntity();
		dtpre1.setIsSystem(1);
		dtpre1.setParamName("param1");
		dtpre1.setParamType(1L);
		dtpre1.setParamValueStr("para1 value");
		dtpre1.setTenantId(1L);
		dtpre1.setTileId(new BigInteger("2023"));
		tre1.getEmsDashboardTileParams().add(dtpre1);
		DashboardTileRowEntity dtre1 = new DashboardTileRowEntity();
		dtre1.setDashboardId(new BigInteger("1001"));
		dtre1.setHeight(3L);
		dtre1.setIsMaximized(1);
		dtre1.setOwner("emcsadmin");
		dtre1.setPosition(2L);
		dtre1.setProviderAssetRoot("assert root");
		dtre1.setProviderName("provider 1");
		dtre1.setProviderVersion("1.0");
		dtre1.setTenantId(1L);
		dtre1.setTileId(new BigInteger("123456"));
		dtre1.setTileRow(1L);
		tre1.getEmsDashboardTile().add(dtre1);
		DashboardUserOptionsRowEntity duore1 = new DashboardUserOptionsRowEntity();
		duore1.setAutoRefreshInterval(1L);
		duore1.setDashboardId(new BigInteger("1001"));
		duore1.setExtendedOptions("options");
		duore1.setIsFavorite(1);
		duore1.setTenantId(1L);
		duore1.setUserName("emcsadmin");
		tre1.getEmsDashboardUserOptions().add(duore1);
		PreferenceRowEntity pre1 = new PreferenceRowEntity();
		pre1.setPrefKey("key1");
		pre1.setPrefValue("value1");
		pre1.setTenantId(1L);
		pre1.setUserName("emcsadmin");
		tre1.getEmsPreference().add(pre1);


		// let's introduce the same records for both the instances
		PreferenceRowEntity preboth = new PreferenceRowEntity();
		preboth.setPrefKey("same key");
		preboth.setPrefValue("same wvalue");
		preboth.setTenantId(1L);
		preboth.setUserName("same user");
		tre1.getEmsPreference().add(preboth);
		tre2.getEmsPreference().add(preboth);

		// serialize&de-serialize, to avoid any side effects from above operations on objects
		tre1 = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance",
				JsonUtil.buildNormalMapper().toJson(tre1));
		tre2 = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance",
				JsonUtil.buildNormalMapper().toJson(tre2));
		cd = Deencapsulation.invoke(drc, "compareInstancesData", new InstanceData<TableRowsEntity>(null, tre1),
				new InstanceData<TableRowsEntity>(null, tre2));
		result1 = cd.getInstance1().getData();
		result2 = cd.getInstance2().getData();
		Assert.assertEquals(result1.getEmsDashboard().get(0), dre1);
		
		Assert.assertEquals(result1.getEmsDashboardSet().get(0), dsre1);
		Assert.assertEquals(result1.getEmsDashboardTileParams().get(0), dtpre1);
		Assert.assertEquals(result1.getEmsDashboardTile().get(0), dtre1);
		Assert.assertEquals(result1.getEmsDashboardUserOptions().get(0), duore1);
		Assert.assertEquals(result1.getEmsPreference().get(0), pre1);
		Assert.assertEquals(result1.getEmsPreference().size(), 1);// preference appear in both side won't appear here
		Assert.assertNull(result2.getEmsDashboard());
		
		Assert.assertNull(result2.getEmsDashboardSet());
		Assert.assertNull(result2.getEmsDashboardTile());
		Assert.assertNull(result2.getEmsDashboardTileParams());
		Assert.assertNull(result2.getEmsDashboardUserOptions());
		Assert.assertNull(result2.getEmsPreference());// preference appear in both side won't appear here
	}

	@Test
	public void testRetrieveRowsEntityFromJsonForSingleInstance() throws IOException
	{
		DashboardRowsComparator drc = new DashboardRowsComparator();
		TableRowsEntity tre = Deencapsulation.invoke(drc, "retrieveRowsEntityFromJsonForSingleInstance",
				JSON_RESPONSE_DATA_TABLE);
		Assert.assertNotNull(tre);
		Assert.assertEquals(tre.getEmsDashboard().get(0).getName(), DASHBOARD1_NAME);
		Assert.assertEquals(tre.getEmsDashboard().get(0).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsDashboard().get(0).getLastModificationDate(), LAST_MODIFICATION_DATE);
		
		Assert.assertEquals(tre.getEmsDashboardSet().size(), 1);
		Assert.assertEquals(tre.getEmsDashboardSet().get(0).getDashboardSetId().longValue(), 12);
		Assert.assertEquals(tre.getEmsDashboardSet().get(0).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardSet().get(0).getLastModificationDate(), LAST_MODIFICATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardTile().size(), 1);
		Assert.assertEquals(tre.getEmsDashboardTile().get(0).getTileId().longValue(), 12);
		Assert.assertEquals(tre.getEmsDashboardTile().get(0).getHeight().intValue(), 2);
		Assert.assertEquals(tre.getEmsDashboardTile().get(0).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardTile().get(0).getLastModificationDate(), LAST_MODIFICATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardTileParams().size(), 1);
		Assert.assertEquals(tre.getEmsDashboardTileParams().get(0).getTileId().longValue(), 12);
		Assert.assertEquals(tre.getEmsDashboardTileParams().get(0).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardTileParams().get(0).getLastModificationDate(), LAST_MODIFICATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardTileParams().size(), 1);
		Assert.assertEquals(tre.getEmsDashboardUserOptions().get(0).getIsFavorite().intValue(), 1);
		Assert.assertEquals(tre.getEmsDashboardUserOptions().get(0).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsDashboardUserOptions().get(0).getLastModificationDate(), LAST_MODIFICATION_DATE);
		Assert.assertEquals(tre.getEmsPreference().size(), 2);
		Assert.assertEquals(tre.getEmsPreference().get(0).getPrefValue(), "false");
		Assert.assertEquals(tre.getEmsPreference().get(0).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsPreference().get(0).getLastModificationDate(), LAST_MODIFICATION_DATE);
		Assert.assertEquals(tre.getEmsPreference().get(1).getPrefValue(), "true");
		Assert.assertEquals(tre.getEmsPreference().get(1).getCreationDate(), CREATION_DATE);
		Assert.assertEquals(tre.getEmsPreference().get(1).getLastModificationDate(), LAST_MODIFICATION_DATE);
	}
}