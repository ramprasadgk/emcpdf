/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.ws.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;
import oracle.sysman.emaas.platform.dashboards.core.exception.security.CommonSecurityException;
import oracle.sysman.emaas.platform.dashboards.core.model.Dashboard;
import oracle.sysman.emaas.platform.dashboards.core.util.JsonUtil;
import oracle.sysman.emaas.platform.dashboards.core.util.MessageUtils;
import oracle.sysman.emaas.platform.dashboards.core.util.TenantContext;
import oracle.sysman.emaas.platform.dashboards.core.util.UserContext;
import oracle.sysman.emaas.platform.dashboards.ws.ErrorEntity;
import oracle.sysman.emaas.platform.dashboards.ws.rest.util.DashboardAPIUtil;

/**
 * @author wenjzhu
 */
public class APIBase
{
	@Context
	protected UriInfo uriInfo;
	private final JsonUtil jsonUtil;

	public APIBase()
	{
		super();
		jsonUtil = JsonUtil.buildNonNullMapper();
		jsonUtil.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}

	public Response buildErrorResponse(ErrorEntity error)
	{
		if (error == null) {
			return null;
		}
		return Response.status(error.getStatusCode()).entity(getJsonUtil().toJson(error)).build();
	}

	public JsonUtil getJsonUtil()
	{
		return jsonUtil;
	}

	public Long getTenantId(String tenantId) throws CommonSecurityException, BasicServiceMalfunctionException
	{
		if (tenantId == null || "".equals(tenantId)) {
			throw new CommonSecurityException(
					MessageUtils.getDefaultBundleString(CommonSecurityException.X_USER_IDENTITY_DOMAIN_REQUIRED));
		}
		try {
			long internalTenantId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(tenantId);
			return internalTenantId;
		}
		catch (BasicServiceMalfunctionException e) {
			throw e;
		}
		catch (Exception e) {
			throw new CommonSecurityException(MessageUtils.getDefaultBundleString(
					CommonSecurityException.TENANT_NAME_NOT_RECOGNIZED, tenantId), e);
		}

	}

	public void initializeUserContext(String userTenant) throws CommonSecurityException
	{
		if (userTenant == null || "".equals(userTenant)) {
			throw new CommonSecurityException(
					MessageUtils.getDefaultBundleString(CommonSecurityException.VALID_X_REMOTE_USER_REQUIRED));
		}
		int idx = userTenant.indexOf(".");
		if (idx <= 0) {
			throw new CommonSecurityException(
					MessageUtils.getDefaultBundleString(CommonSecurityException.VALID_X_REMOTE_USER_REQUIRED));
		}
		String userName = userTenant.substring(idx + 1, userTenant.length());
		if (userName == null || "".equals(userName)) {
			throw new CommonSecurityException(
					MessageUtils.getDefaultBundleString(CommonSecurityException.VALID_X_REMOTE_USER_REQUIRED));
		}
		UserContext.setCurrentUser(userName);
		String tenantName = userTenant.substring(0, idx);
		if (tenantName == null || "".equals(tenantName)) {
			throw new CommonSecurityException(
					MessageUtils.getDefaultBundleString(CommonSecurityException.VALID_X_REMOTE_USER_REQUIRED));
		}
		TenantContext.setCurrentTenant(tenantName);
	}

	/*
	 * Updates the specified dashboard by generating all href fields
	 */
	protected Dashboard updateDashboardHref(Dashboard dbd)
	{
		if (dbd == null) {
			return null;
		}
		String externalBase = DashboardAPIUtil.getExternalAPIBase();
		String href = externalBase + "dashboards/" + dbd.getDashboardId();
		//		String href = uriInfo.getBaseUri() + "v1/dashboards/" + dbd.getDashboardId();
		dbd.setHref(href);
		return dbd;
	}
}
