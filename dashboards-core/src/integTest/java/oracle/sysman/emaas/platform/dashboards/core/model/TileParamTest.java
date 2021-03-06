package oracle.sysman.emaas.platform.dashboards.core.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

import oracle.sysman.emaas.platform.dashboards.core.exception.functional.CommonFunctionalException;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author guobaochen
 */
public class TileParamTest
{

	private static final Logger LOGGER = LogManager.getLogger(TileParamTest.class);
	@Test(groups = { "s1" })
	public void testGetValue()
	{
		//Logger LOGGER = EMTestLogger.getLogger("testGetValue");

		TileParam tp = new TileParam();
		tp.setType(TileParam.PARAM_TYPE_STRING);
		tp.setStringValue("test");
		Assert.assertEquals("test", tp.getValue(), tp.toString());
		//LOGGER.info("Values inside TileParam is: " + tp.toString());

		tp.setType(null);
		BigDecimal bd = new BigDecimal(1024);
		tp.setNumberValue(bd);
		Assert.assertEquals(bd.toString(), tp.getValue(), tp.toString());

		tp.setType(TileParam.PARAM_TYPE_BOOLEAN);
		tp.setLongValue(33L);
		Assert.assertEquals(tp.getValue(), Boolean.TRUE.toString(), tp.toString());
		tp.setNumberValue(new BigDecimal(-33));
		Assert.assertEquals(tp.getValue(), Boolean.FALSE.toString(), tp.toString());
		tp.setNumberValue(null);
		Assert.assertEquals(tp.getValue(), Boolean.FALSE.toString(), tp.toString());

		tp.setType("Unknown type");
		Assert.assertNull(tp.getValue());
	}

	@Test(groups = { "s1" })
	public void testSetValue() throws CommonFunctionalException
	{
		//Logger LOGGER = EMTestLogger.getLogger("testSetValue");

		TileParam tp = new TileParam();
		tp.setType(TileParam.PARAM_TYPE_NUMBER);
		tp.setValue(null);
		Assert.assertEquals(Long.valueOf(0), tp.getLongValue(), tp.toString());

		tp.setValue("3");
		Assert.assertEquals(Long.valueOf(3), tp.getLongValue(), tp.toString());

		try {
			tp.setValue("abcde");
			Assert.fail("Fail: invalid string for number type");
		}
		catch (CommonFunctionalException e) {
			// expected exception
			LOGGER.info("context",e);
		}

		tp.setType(TileParam.PARAM_TYPE_BOOLEAN);
		tp.setValue("true");
		Assert.assertEquals(tp.getValue(), Boolean.TRUE.toString(), tp.toString());
		tp.setValue("TRUE");
		Assert.assertEquals(tp.getValue(), Boolean.TRUE.toString(), tp.toString());
		tp.setValue(null);
		Assert.assertEquals(tp.getValue(), Boolean.FALSE.toString(), tp.toString());
		tp.setValue("false");
		Assert.assertEquals(tp.getValue(), Boolean.FALSE.toString(), tp.toString());
		tp.setValue("abc");
		Assert.assertEquals(tp.getValue(), Boolean.FALSE.toString(), tp.toString());
	}
}
