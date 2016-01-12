/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.ui.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import oracle.sysman.emaas.platform.dashboards.ui.webutils.util.RegistryLookupUtil;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * If users have added a dashboard as home page, then after they log in, the specific dashboard page should be displayed instead
 * of the welcome page
 *
 * @author pingwu
 */
public class HomePageFilter implements Filter
{
	private final Logger logger = LogManager.getLogger(HomePageFilter.class);
	private static final String OAM_REMOTE_USER_HEADER = "OAM_REMOTE_USER";
	private static final String USER_IDENTITY_DOMAIN_NAME = "X-USER-IDENTITY-DOMAIN-NAME";
	private static final String REMOTE_USER = "X-REMOTE-USER";
	private static final String AUTHORIZATION = "Authorization";
	private static final String REFERER = "Referer";
	private static final String HOME_PAGE_PREFERENCE_KEY = "Dashboards.homeDashboardId";
	private static final String AUTH_CRED_PATH = "oam/server/auth_cred_submit";
	private static final String SERVICE_NAME = "Dashboard-API";
	private static final String VERSION = "0.1";
	private static final String PATH = "static/dashboards.preferences";
	private static final String PATH_DASHBOARD_API = "static/dashboards.service";

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		String referer = httpReq.getHeader(REFERER);
		logger.info("The Referer is: \"{}\"", referer);
		if (referer != null) {
			if (referer.toLowerCase().contains(AUTH_CRED_PATH)) {
				String userTenant = httpReq.getHeader(OAM_REMOTE_USER_HEADER);
				if (userTenant != null && !userTenant.equals("")) {
					String domainName = userTenant.substring(0, userTenant.indexOf("."));
					String authorization = new String(LookupManager.getInstance().getAuthorizationToken());
					String preference = getPreference(domainName, authorization, userTenant);
					if (preference != null && !preference.equals("")) {
						int flag = preference.indexOf("value");
						if (flag > 0) {
							String value = preference.substring(flag + 8, preference.length() - 2);
							if (!value.equals("")) {
								StringBuffer homeUrl = httpReq.getRequestURL();
								int position = homeUrl.indexOf("/emcpdfui");
								String url = homeUrl.substring(0, position + 10);
								String redirectUrl = url + "builder.html?dashboardId=" + value;
								if (!isHomeDashboardExists(domainName, authorization, userTenant, value)) {
									redirectUrl = url + "error.html?msg=DBS_ERROR_HOME_PAGE_NOT_FOUND_MSG";
									removeDashboardAsHomePreference(domainName, authorization, userTenant);
								}

								httpRes.sendRedirect(redirectUrl);
								return;
							}
						}
					}

				}

			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		// TODO Auto-generated method stub

	}

	private String getPreference(String domainName, String authorization, String remoteUser)
	{
		String value = "";
		CloseableHttpClient client = HttpClients.createDefault();
		Link link = RegistryLookupUtil.getServiceInternalLink(SERVICE_NAME, VERSION, PATH, domainName);
		if (link != null) {
			String href = link.getHref();
			String url = href + "/" + HOME_PAGE_PREFERENCE_KEY;
			HttpGet get = new HttpGet(url);
			get.addHeader(USER_IDENTITY_DOMAIN_NAME, domainName);
			get.addHeader(AUTHORIZATION, authorization);
			get.addHeader(REMOTE_USER, remoteUser);
			CloseableHttpResponse response = null;
			try {
				response = client.execute(get);
			}
			catch (ClientProtocolException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			InputStream instream = null;
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					try {
						instream = entity.getContent();
						value = getStrFromInputSteam(instream);
					}
					catch (IllegalStateException e) {
						logger.error(e.getLocalizedMessage(), e);
					}
					catch (IOException e) {
						logger.error(e.getLocalizedMessage(), e);
					}
				}
			}
			finally {
				try {
					instream.close();
					response.close();
				}
				catch (IOException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}
		return value;
	}

	private String getStrFromInputSteam(InputStream in)
	{
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = bf.readLine()) != null) {
				buffer.append(line);
			}
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return buffer.toString();
	}

	private boolean isHomeDashboardExists(String domainName, String authorization, String remoteUser, String dashboardId)
	{
		boolean isExisted = false;
		if (dashboardId != null && !"".equals(dashboardId)) {

		}
		CloseableHttpClient client = HttpClients.createDefault();
		Link link = RegistryLookupUtil.getServiceInternalLink(SERVICE_NAME, VERSION, PATH_DASHBOARD_API, domainName);
		if (link != null) {
			String href = link.getHref();
			String url = href + "/" + dashboardId;
			HttpGet get = new HttpGet(url);
			get.addHeader(USER_IDENTITY_DOMAIN_NAME, domainName);
			get.addHeader(AUTHORIZATION, authorization);
			get.addHeader(REMOTE_USER, remoteUser);
			CloseableHttpResponse response = null;
			try {
				response = client.execute(get);
				if (200 == response.getStatusLine().getStatusCode()) {
					isExisted = true;
				}
			}
			catch (ClientProtocolException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			finally {
				try {
					if (response != null) {
						response.close();
					}
				}
				catch (IOException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}
		return isExisted;
	}

	private void removeDashboardAsHomePreference(String domainName, String authorization, String remoteUser)
	{
		CloseableHttpClient client = HttpClients.createDefault();
		Link link = RegistryLookupUtil.getServiceInternalLink(SERVICE_NAME, VERSION, PATH, domainName);
		if (link != null) {
			String href = link.getHref();
			String url = href + "/" + HOME_PAGE_PREFERENCE_KEY;
			HttpDelete delete = new HttpDelete(url);
			delete.addHeader(USER_IDENTITY_DOMAIN_NAME, domainName);
			delete.addHeader(AUTHORIZATION, authorization);
			delete.addHeader(REMOTE_USER, remoteUser);
			CloseableHttpResponse response = null;
			try {
				response = client.execute(delete);
			}
			catch (ClientProtocolException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			catch (IOException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
			finally {
				try {
					if (response != null) {
						response.close();
					}
				}
				catch (IOException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}

}