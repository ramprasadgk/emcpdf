/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.ws;

import javax.ws.rs.core.Response.Status;

import oracle.sysman.emaas.platform.dashboards.core.DashboardErrorConstants;
import oracle.sysman.emaas.platform.dashboards.core.exception.DashboardException;

/**
 * @author guobaochen
 */
public class ErrorEntity
{
	private Integer errorCode;
	private String errorMessage;

	public ErrorEntity()
	{

	}

	public ErrorEntity(DashboardException de)
	{
		if (de != null) {
			errorCode = de.getErrorCode();
			errorMessage = de.getMessage();
		}
	}

	public Integer getErrorCode()
	{
		return errorCode;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public int getStatusCode()
	{
		if (errorCode != null) {
			if (errorCode <= DashboardErrorConstants.DASHBOARD_UI_MAX_ERROR_CODE) {
				return Status.BAD_REQUEST.getStatusCode();
			}
			if (errorCode <= DashboardErrorConstants.DASHBOARD_RESOURCE_MAX_ERROR_CODE) {
				return Status.NOT_FOUND.getStatusCode();
			}
			if (errorCode <= DashboardErrorConstants.DASHBOARD_SECURITY_MAX_ERROR_CODE) {
				return Status.FORBIDDEN.getStatusCode();
			}
		}

		return Status.BAD_REQUEST.getStatusCode();
	}

	public void setErrorCode(Integer errorCode)
	{
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
