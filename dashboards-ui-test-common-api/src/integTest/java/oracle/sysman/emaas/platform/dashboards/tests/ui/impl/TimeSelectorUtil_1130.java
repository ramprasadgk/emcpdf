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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.sysman.emaas.platform.dashboards.tests.ui.util.TimeSelectorUIControls;
import oracle.sysman.emaas.platform.dashboards.tests.ui.util.Validator;
import oracle.sysman.qatool.uifwk.webdriver.WebDriver;

public class TimeSelectorUtil_1130 extends TimeSelectorUtil_175
{
	private static final String Index = null;

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.dashboards.tests.ui.util.ITimeSelectorUtil#getTimeRangeLabel(oracle.sysman.qatool.uifwk.webdriver.WebDriver)
	 */
	@Override
	public String getTimeRangeLabel(WebDriver webd)
	{
		return getTimeRangeLabel(webd, 1);
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.dashboards.tests.ui.util.ITimeSelectorUtil#getTimeRangeLabel(oracle.sysman.qatool.uifwk.webdriver.WebDriver, int)
	 */
	//index: start from 1
	@Override
	public String getTimeRangeLabel(WebDriver webd, int index)
	{
		//verify the index, it should equal or larger than 1
		Validator.equalOrLargerThan("index", index, 1);

		//locate the datetimepicker component
		webd.waitForElementPresent("css=" + TimeSelectorUIControls.sTimeRangeBtn);
		webd.takeScreenShot();
		webd.savePageToFile();
		String str_timerangelable = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + Index + "]");

		return str_timerangelable;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.dashboards.tests.ui.util.ITimeSelectorUtil#setCustomTime(oracle.sysman.qatool.uifwk.webdriver.WebDriver, int, java.lang.String, java.lang.String)
	 */
	@Override
	public String setCustomTime(WebDriver webd, int index, String startDateTime, String endDateTime)
	{

		String start = null;
		try {
			start = timeFormatChange(webd, startDateTime, "MM/dd/yy hh:mm a", "MM/dd/yyyy hh:mm a");
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		String end = null;
		try {
			end = timeFormatChange(webd, endDateTime, "MM/dd/yy hh:mm a", "MM/dd/yyyy hh:mm a");
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		webd.getLogger().info("the start time in dashboard is:" + start + ",the end time in dashboard is:" + end);
		webd.getLogger().info("we are going to set the custom time in dashboard page");

		try {
			clickTimePicker(webd, index);
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
		webd.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

		webd.waitForElementPresent("css=" + TimeSelectorUIControls.sRangeRadio);
		webd.click("css=" + TimeSelectorUIControls.sRangeRadio);

		String regex = "(.*?)\\s+(.*)";
		Pattern p = Pattern.compile(regex);
		Matcher mStart = p.matcher(start);
		Matcher mEnd = p.matcher(end);
		String startDate = null;
		String startTime = null;
		String endDate = null;
		String endTime = null;

		if (mStart.find()) {
			startDate = mStart.group(1);
			startTime = mStart.group(2);
			webd.getLogger().info("start date is:" + startDate + ",start time is:" + startTime);

		}
		if (mEnd.find()) {
			endDate = mEnd.group(1);
			endTime = mEnd.group(2);
			webd.getLogger().info("end date is:" + endDate + ",end time is:" + endTime);
		}

		//set start date time and end date time
		webd.getLogger().info("Verify if custom panpel displayed...");		
		webd.waitForElementVisible("css=" + TimeSelectorUIControls.sPickPanel);
		webd.takeScreenShot();
		webd.savePageToFile();
		webd.getLogger().info("Input the start date time and end date time...");
		webd.click("css=" + TimeSelectorUIControls.sStartDateInput);
		webd.clear("css=" + TimeSelectorUIControls.sStartDateInput);
		webd.sendKeys("css=" + TimeSelectorUIControls.sStartDateInput, startDate);
		webd.click("css=" + TimeSelectorUIControls.sStartTimeInput);
		webd.clear("css=" + TimeSelectorUIControls.sStartTimeInput);
		webd.sendKeys("css=" + TimeSelectorUIControls.sStartTimeInput, startTime);
		webd.click("css=" + TimeSelectorUIControls.sEndDateInput);
		webd.clear("css=" + TimeSelectorUIControls.sEndDateInput);
		webd.sendKeys("css=" + TimeSelectorUIControls.sEndDateInput, endDate);
		webd.click("css=" + TimeSelectorUIControls.sEndTimeInput);
		webd.clear("css=" + TimeSelectorUIControls.sEndTimeInput);
		webd.sendKeys("css=" + TimeSelectorUIControls.sEndTimeInput, endTime);
		webd.takeScreenShot();
		webd.savePageToFile();

		try {
			clickApplyButton(webd);
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		String returnTimeRange = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");
		return dateConvert(webd, returnTimeRange, TimeRange.Custom, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a", index);		
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emaas.platform.dashboards.tests.ui.util.ITimeSelectorUtil#setCustomTime(oracle.sysman.qatool.uifwk.webdriver.WebDriver, java.lang.String, java.lang.String)
	 */
	//Date MM/dd/yyyy
	//Time hh:mm a
	@Override
	public String setCustomTime(WebDriver webd, String startDateTime, String endDateTime)
	{
		return setCustomTime(webd, 1, startDateTime, endDateTime);
	}

	@Override
	public String setCustomTimeWithDateOnly(WebDriver webd, int index, String startDate, String endDate)
	{
		try {
			startDate = timeFormatChange(webd, startDate, "MM/dd/yy", "MM/dd/yyyy");
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		try {
			endDate = timeFormatChange(webd, endDate, "MM/dd/yy", "MM/dd/yyyy");
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		webd.getLogger().info("the start date in dashboard is:" + startDate + ",the end date in dashboard is:" + endDate);
		webd.getLogger().info("we are going to set the custom time in dashboard page");

		try {
			clickTimePicker(webd, index);
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
		webd.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

		webd.waitForElementVisible("css=" + TimeSelectorUIControls.sRangeRadio); 
		//webd.waitForElementPresent("css=" + TimeSelectorUIControls.sRangeRadio);   
		webd.click("css=" + TimeSelectorUIControls.sRangeRadio);
		
		//set start date time and end date time
		webd.getLogger().info("Verify if custom panpel displayed...");
		webd.waitForElementVisible("css=" + TimeSelectorUIControls.sPickPanel);

		webd.takeScreenShot();
		webd.savePageToFile();
		webd.getLogger().info("Input the start date and end date...");
		webd.click("css=" + TimeSelectorUIControls.sStartDateInput);
		webd.clear("css=" + TimeSelectorUIControls.sStartDateInput);
		webd.sendKeys("css=" + TimeSelectorUIControls.sStartDateInput, startDate);
		webd.click("css=" + TimeSelectorUIControls.sEndDateInput);
		webd.clear("css=" + TimeSelectorUIControls.sEndDateInput);
		webd.sendKeys("css=" + TimeSelectorUIControls.sEndDateInput, endDate);
		webd.click("css=" + TimeSelectorUIControls.sEndDateInput);

		try {
			clickApplyButton(webd);
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		String returnTimeRange = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");
		return dateConvert(webd, returnTimeRange, TimeRange.Custom, "MM/dd/yyyy", "MMM d, yyyy", index);
	}

	@Override
	public String setCustomTimeWithDateOnly(WebDriver webd, String startDate, String endDate)
	{
		return setCustomTimeWithDateOnly(webd, 1, startDate, endDate);
	}

	@Override
	public String setFlexibleRelativeTimeRange(WebDriver driver, int index, int relTimeVal, TimeUnit relTimeUnit)
	{
		//open time selector
		clickTimePicker(driver, index);

		//click "Custom" option to open panel
		driver.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
		driver.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

		driver.waitForElementPresent("css=" + TimeSelectorUIControls.sLastRadio);
		driver.click("css=" + TimeSelectorUIControls.sLastRadio);

		driver.waitForElementPresent("css=" + TimeSelectorUIControls.sFlexRelTimeVal);
		driver.getElement("css=" + TimeSelectorUIControls.sFlexRelTimeVal).clear();
		driver.click("css=" + TimeSelectorUIControls.sFlexRelTimeVal);
		driver.sendKeys("css=" + TimeSelectorUIControls.sFlexRelTimeVal, String.valueOf(relTimeVal));

		driver.waitForElementPresent("css=" + TimeSelectorUIControls.sFlexRelTimeOpt);
		driver.click("css=" + TimeSelectorUIControls.sFlexRelTimeOpt);
		String optionLocator = getOptionsLocator(driver, relTimeUnit.getTimeUnit());
		driver.click("css=" + optionLocator);
		
		try {
			clickApplyButton(driver);
		}
		catch (Exception e) {
			driver.getLogger().info(e.getLocalizedMessage());
		}
		String returnTimeRange = driver.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");

		if (returnTimeRange.startsWith("Last") && returnTimeRange.indexOf(":") > -1) {
			return dateConvert(driver, returnTimeRange, TimeRange.Custom, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a", index);
		}
		else {
			String returnStartDate = driver.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sStartDateInput_XPATH + "@value")
					+ " "
					+ driver.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sStartTimeInput_XPATH + "@value");
			String returnEndDate = driver.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sEndDateInput_XPATH + "@value")
					+ " "
					+ driver.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + index + "]" 
					+ TimeSelectorUIControls.sEndTimeInput_XPATH + "@value");
			returnStartDate = timeFormatChange(driver, returnStartDate, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a");
			returnEndDate = timeFormatChange(driver, returnEndDate, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a");

			String returnDate = returnTimeRange + ": " + returnStartDate + " - " + returnEndDate;

			return dateConvert(driver, returnDate, TimeRange.Custom, "MM/dd/yyyy hh:mm a", "MMM d, yyyy hh:mm a", index);
		}	
	}

	@Override
	public String setFlexibleRelativeTimeRange(WebDriver webd, int relTimeVal, TimeUnit relTimeUnit)
	{
		return setFlexibleRelativeTimeRange(webd, 1, relTimeVal, relTimeUnit);
	}

	@Override
	public String setFlexibleRelativeTimeRangeWithDateOnly(WebDriver webd, int index, int relTimeVal, TimeUnit relTimeUnit)
	{
		webd.getLogger().info("Start to setFlexibleRelativeTimeRangeWithDateOnly...");

		Validator.fromValidValues("relTimeUnit", relTimeUnit, TimeUnit.Day, TimeUnit.Week, TimeUnit.Month, TimeUnit.Year);

		// open time selector
		clickTimePicker(webd, index);

		// click "Custom" option to open panel
		webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Custom);
		webd.click("css=" + TimeSelectorUIControls.sTimeRange_Custom);

		webd.waitForElementPresent("css=" + TimeSelectorUIControls.sLastRadio);
		webd.click("css=" + TimeSelectorUIControls.sLastRadio);

		webd.waitForElementPresent("css=" + TimeSelectorUIControls.sFlexRelTimeVal);
		webd.getElement("css=" + TimeSelectorUIControls.sFlexRelTimeVal).clear();
		webd.click("css=" + TimeSelectorUIControls.sFlexRelTimeVal);
		webd.sendKeys("css=" + TimeSelectorUIControls.sFlexRelTimeVal, String.valueOf(relTimeVal));

		webd.waitForElementPresent("css=" + TimeSelectorUIControls.sFlexRelTimeOpt);
		webd.click("css=" + TimeSelectorUIControls.sFlexRelTimeOpt);
		String optionLocator = getOptionsLocator(webd, relTimeUnit.getTimeUnit());
		webd.click("css=" + optionLocator);
		
		try {
			clickApplyButton(webd);
		}
		catch (Exception e) {
			webd.getLogger().info(e.getLocalizedMessage());
		}
		String returnTimeRange = webd.getText("xpath=(" + TimeSelectorUIControls.sTimeRangeBtn_XPATH + ")[" + index + "]");

		if (returnTimeRange.startsWith("Last") && returnTimeRange.indexOf(":") > -1) {
			return dateConvert(webd, returnTimeRange, TimeRange.Custom, "MM/dd/yyyy", "MMM d, yyyy", index);
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
			returnStartDate = timeFormatChange(webd, returnStartDate, "MM/dd/yyyy", "MMM d, yyyy");
			returnEndDate = timeFormatChange(webd, returnEndDate, "MM/dd/yyyy", "MMM d, yyyy");

			String returnDate = returnTimeRange + ": " + returnStartDate + " - " + returnEndDate;

			return dateConvert(webd, returnDate, TimeRange.Custom, "MM/dd/yyyy", "MMM d, yyyy", index);
		}
	}

	@Override
	public String setFlexibleRelativeTimeRangeWithDateOnly(WebDriver webd, int relTimeVal, TimeUnit relTimeUnit)
	{
		return setFlexibleRelativeTimeRangeWithDateOnly(webd, 1, relTimeVal, relTimeUnit);
	}

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
			case Latest:
				webd.isElementPresent("css=" + TimeSelectorUIControls.sTimeRange_Latest);
				webd.click("css=" + TimeSelectorUIControls.sTimeRange_Latest);

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
			String returnStartDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" 
					+ TimeSelectorUIControls.sStartDateInput_XPATH + "@value")
					+ " "
					+ webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" 
					+ TimeSelectorUIControls.sStartTimeInput_XPATH + "@value");
			String returnEndDate = webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" 
					+ TimeSelectorUIControls.sEndDateInput_XPATH + "@value")
					+ " "
					+ webd.getAttribute("xpath=(" + TimeSelectorUIControls.sDateTimePick_XPATH + ")[" + Index + "]" 
					+ TimeSelectorUIControls.sEndTimeInput_XPATH + "@value");

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
	
	protected String getOptionsLocator(WebDriver driver, String option)
	{		
		int i=1;
		int listCount = driver.getElementCount("css=" + TimeSelectorUIControls.sFlexRelTimeOptList);
		for(i=1; i<=listCount; i++) {
			if(option.equals(driver.getAttribute("xpath=("+ TimeSelectorUIControls.sFlexRelTimeOptList_XPATH + ")[" + i + "]@value").trim())) break;
		}
		return TimeSelectorUIControls.sFlexRelTimeOptStart + (i) + TimeSelectorUIControls.sFlexRelTimeOptEnd;
	}
}
