/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.core.cache.screenshot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emaas.platform.dashboards.core.util.StringUtil;

/**
 * @author guochen
 */
public class ScreenshotPathGenerator
{
	private static final Logger logger = LogManager.getLogger(ScreenshotPathGenerator.class);
	private static String DEFAULT_SCREENSHOT_EXT = ".png";
	private static String SERVICE_VERSION_PROPERTY = "serviceVersion";

	private static ScreenshotPathGenerator instance = new ScreenshotPathGenerator();

	public static ScreenshotPathGenerator getInstance()
	{
		return instance;
	}

	private String versionString = null;

	private ScreenshotPathGenerator()
	{
		initialize();
	}

	public String generateFileName(Long dashboardId, Date creation, Date modification)
	{
		if (dashboardId == null) {
			logger.error(
					"Unexpected null dashboard id to generate screenshot file name: dashboard id={}, creation={}, modification={}",
					dashboardId, creation, modification);
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (modification != null) {
			sb.append(modification.getTime());
		}
		else if (creation != null) {
			sb.append(creation.getTime());
		}
		else {
			logger.error("Unexpected dashboard with null values for both creation and modification date");
			return null;
		}
		sb.append("_");
		sb.append(dashboardId);
		sb.append(DEFAULT_SCREENSHOT_EXT);
		return sb.toString();
	}

	public String generateScreenshotUrl(String baseUrl, Long dashboardId, Date creation, Date modification)
	{
		if (StringUtil.isEmpty(baseUrl)) {
			logger.error("Unexpected null/empty base url to generate screenshot URL");
			return null;
		}
		if (StringUtil.isEmpty(versionString)) {
			logger.error("Unexpected null/empty versionString to generate screenshot URL");
			return null;
		}
		String fileName = generateFileName(dashboardId, creation, modification);
		if (fileName == null) {
			return null;
		}
		return baseUrl + (baseUrl.endsWith("/") ? "" : "/") + dashboardId + "/screenshot/" + versionString + "/images/"
				+ fileName;
	}

	private void initialize()
	{
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = ScreenshotPathGenerator.class.getResourceAsStream("/dashboard-api.properties");
			properties.load(is);
			versionString = (String) properties.get(SERVICE_VERSION_PROPERTY);
			logger.debug("Initialize the screenshot path version string to {}", versionString);
		}
		catch (IOException e) {
			logger.error(e);
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
}
