/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.tests.ui.impl;

import oracle.sysman.emaas.platform.dashboards.tests.ui.util.TimeSelectorUIControls;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.Validator;
import oracle.sysman.qatool.uifwk.webdriver.WebDriver;

public class TimeSelectorUtil_1170 extends TimeSelectorUtil_1160
{
	@Override
	public String setTimeRange(WebDriver webd, int Index, TimeRange rangeoption)
	{
		clickTimePicker(webd, Index);
		switch (rangeoption) {
			case Last15Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_15Min);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_15Min);

				break;
			case Last30Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_30Min);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_30Min);

				break;
			case Last60Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_60Min);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_60Min);

				break;
			case Last2Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_2Hour);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_2Hour);

				break;
			case Last4Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_4Hour);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_4Hour);

				break;
			case Last6Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_6Hour);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_6Hour);

				break;
			case Last1Day:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_1Day);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_1Day);

				break;
			case Last7Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_7Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_7Days);

				break;
			case Last14Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_14Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_14Days);

				break;
			case Last30Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_30Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_30Days);

				break;
			case Last90Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_90Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_90Days);

				break;
			case Last1Year:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_1Year);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_1Year);

				break;
			case Last24Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_24Hours);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_24Hours);

				break;
			case Last12Months:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_12Months);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_12Months);

				break;
			case Last8Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_8Hours);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_8Hours);

				break;
			case NewLast60Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_New60Mins);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_New60Mins);

				break;
			case NewLast7Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_New7Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_New7Days);

				break;

			case Latest:
				if (webd.isDisplayed("css=" + TimeSelectorUIControls.sTimeRange_Latest)) {
					webd.click("css=" + TimeSelectorUIControls.sTimeRange_Latest);

				}
				else {
					webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
					webd.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

					webd.waitForElementPresent("css=" + TimeSelectorUIControls.sLatestRadio);
					webd.click("css=" + TimeSelectorUIControls.sLatestRadio);

					clickApplyButton(webd);
				}

				return webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + Index + "]");
			case Custom:
				try {
					throw new Exception("Please use setCustomTime API to set Custom Range");
				}
				catch (Exception e) {
					webd.getLogger().info(e.getLocalizedMessage());
				}
			default:
				break;

		}
		String returnTimeRange = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + Index + "]");

		if (returnTimeRange.startsWith(rangeoption.getRangeOption() + ":")) {
			return dateConvert(webd, returnTimeRange, rangeoption, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a", Index);
		}
		else {	
			String returnStartDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" + TimeSelectorUIControls.sStartDateInput_XPATH + "@value")
					+ " "
					+ webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" + TimeSelectorUIControls.sStartTimeInput_XPATH + "@value");
			String returnEndDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" + TimeSelectorUIControls.sEndDateInput_XPATH + "@value")
					+ " "
					+ webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" + TimeSelectorUIControls.sEndTimeInput_XPATH + "@value");


			returnStartDate = timeFormatChange(webd, returnStartDate, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a");
			returnEndDate = timeFormatChange(webd, returnEndDate, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a");

			String returnDate = returnTimeRange + ": " + returnStartDate + " - " + returnEndDate;
			return dateConvert(webd, returnDate, rangeoption, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a", Index);
		}

	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.dashboards.tests.ui.util.ITimeSelectorUtil#setTimeRange(oracle.sysman.qatool.uifwk.webdriver.WebDriver, oracle.sysman.emaas.platform.dashboards.tests.ui.util.ITimeSelectorUtil.TimeRange)
	 */
	@Override
	public String setTimeRange(WebDriver webd, TimeRange rangeoption)
	{
		return setTimeRange(webd, 1, rangeoption);
	}

	@Override
	public String setTimeRangeWithDateOnly(WebDriver webd, int index, TimeRange rangeOption)
	{
		webd.getLogger().info(
				"Start to set time range with date only for #" + index + " time picker. Range options is " + rangeOption);

		Validator.fromValidValues("timeRangeOption", rangeOption, TimeRange.Last1Day, TimeRange.Last7Days, TimeRange.Last14Days,
				TimeRange.Last30Days, TimeRange.Last90Days, TimeRange.Last1Year, TimeRange.Latest, TimeRange.Last24Hours,
				TimeRange.Last12Months, TimeRange.Last8Hours, TimeRange.NewLast60Mins, TimeRange.NewLast7Days);

		clickTimePicker(webd, index);
		switch (rangeOption) {
			case Last1Day:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_1Day);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_1Day);

				break;
			case Last7Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_7Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_7Days);
				break;
			case Last14Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_14Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_14Days);
				break;
			case Last30Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_30Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_30Days);
				break;
			case Last90Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_90Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_90Days);
				break;
			case Last1Year:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_1Year);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_1Year);
				break;
			case Last24Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_24Hours);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_24Hours);
				break;
			case Last12Months:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_12Months);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_12Months);

				break;
			case Last8Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_8Hours);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_8Hours);

				break;
			case NewLast60Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_New60Mins);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_New60Mins);

				break;
			case NewLast7Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_New7Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_New7Days);

				break;
			case Latest:
				if (webd.isDisplayed("css=" + TimeSelectorUIControls.sTimeRange_Latest)) {
					webd.click("css=" + TimeSelectorUIControls.sTimeRange_Latest);

				}
				else {
					webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
					webd.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

					webd.waitForElementPresent("css=" + TimeSelectorUIControls.sLatestRadio);
					webd.click("css=" + TimeSelectorUIControls.sLatestRadio);

					clickApplyButton(webd);
				}
				return webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");
			case Custom:
				try {
					throw new Exception("Please use setCustomTime API to set Custom Range");
				}
				catch (Exception e) {
					webd.getLogger().info(e.getLocalizedMessage());
				}
			default:
				break;

		}
		String returnTimeRange = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");

		if (returnTimeRange.startsWith(rangeOption.getRangeOption() + ":")) {
			//dateConvert with date only
			return dateConvert(webd, returnTimeRange, rangeOption, "MM/dd/yyyy", "MMM d, yyyy", index);
		}
		else {
			String returnStartDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sStartDateInput_XPATH + "@value");
			String returnEndDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sEndDateInput_XPATH + "@value");
			
			returnStartDate = timeFormatChange(webd, returnStartDate, "MM/dd/yyyy", "MMM d, yyyy");
			returnEndDate = timeFormatChange(webd, returnEndDate, "MM/dd/yyyy", "MMM d, yyyy");

			String returnDate = returnTimeRange + ": " + returnStartDate + " - " + returnEndDate;

			return dateConvert(webd, returnDate, rangeOption, "MM/dd/yyyy", "MMM d, yyyy", index);
		}
	}

	@Override
	public String setTimeRangeWithDateOnly(WebDriver webd, TimeRange rangeOption)
	{
		return setTimeRangeWithDateOnly(webd, 1, rangeOption);
	}

	@Override
	public String setTimeRangeWithMillisecond(WebDriver webd, int index, TimeRange rangeOption)
	{
		clickTimePicker(webd, index);
		switch (rangeOption) {
			case Last15Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_15Min);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_15Min);

				break;
			case Last30Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_30Min);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_30Min);

				break;
			case Last60Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_60Min);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_60Min);

				break;
			case Last2Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_2Hour);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_2Hour);

				break;
			case Last4Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_4Hour);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_4Hour);

				break;
			case Last6Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_6Hour);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_6Hour);

				break;
			case Last1Day:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_1Day);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_1Day);

				break;
			case Last7Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_7Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_7Days);

				break;
			case Last14Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_14Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_14Days);

				break;
			case Last30Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_30Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_30Days);

				break;
			case Last90Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_90Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_90Days);

				break;
			case Last1Year:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_1Year);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_1Year);

				break;
			case Last24Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_24Hours);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_24Hours);

				break;
			case Last12Months:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_12Months);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_12Months);

				break;
			case Last8Hours:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_8Hours);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_8Hours);

				break;
			case NewLast60Mins:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_New60Mins);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_New60Mins);

				break;
			case NewLast7Days:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_New7Days);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_New7Days);

				break;

			case Latest:
				if (webd.isDisplayed("css=" + TimeSelectorUIControls.sTimeRange_Latest)) {
					webd.click("css=" + TimeSelectorUIControls.sTimeRange_Latest);

				}
				else {
					webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
					webd.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

					webd.waitForElementPresent("css=" + TimeSelectorUIControls.sLatestRadio);
					webd.click("css=" + TimeSelectorUIControls.sLatestRadio);

					clickApplyButton(webd);
				}
				return webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");
			case Custom:
				try {
					throw new Exception("Please use setCustomTime API to set Custom Range");
				}
				catch (Exception e) {
					webd.getLogger().info(e.getLocalizedMessage());
				}
			default:
				break;

		}

		String returnTimeRange = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");

		if (returnTimeRange.startsWith(rangeOption.getRangeOption() + ":")) {
			return dateConvert(webd, returnTimeRange, rangeOption, "MM/dd/yyyy hh:mm:ss:SSS a", "MMM d, yyyy hh:mm:ss:SSS a",
					index);
		}
		else {
			String returnStartDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sStartDateInput_XPATH + "@value")
					+ " "
					+ webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sStartTimeInput_XPATH + "@value");
			String returnEndDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sEndDateInput_XPATH + "@value")
					+ " "
					+ webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sEndTimeInput_XPATH + "@value");
			returnStartDate = timeFormatChange(webd, returnStartDate, "MM/dd/yyyy hh:mm:ss:SSS a", "MMM d, yyyy hh:mm:ss:SSS a");
			returnEndDate = timeFormatChange(webd, returnEndDate, "MM/dd/yyyy hh:mm:ss:SSS a", "MMM d, yyyy hh:mm:ss:SSS a");

			String returnDate = returnTimeRange + ": " + returnStartDate + " - " + returnEndDate;
			return dateConvert(webd, returnDate, rangeOption, "MM/dd/yyyy hh:mm:ss:SSS a", "MMM d, yyyy hh:mm:ss:SSS a", index);
		}
	}

	@Override
	public String setTimeRangeWithMillisecond(WebDriver webd, TimeRange rangeOption)
	{
		return setTimeRangeWithMillisecond(webd, 1, rangeOption);
	}
}