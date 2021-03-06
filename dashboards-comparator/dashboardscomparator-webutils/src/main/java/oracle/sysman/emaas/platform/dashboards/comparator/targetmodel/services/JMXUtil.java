/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.dashboards.comparator.targetmodel.services;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JMXUtil
{

	private static final Logger _logger = LogManager.getLogger(JMXUtil.class);
	private static volatile JMXUtil instance = null;

	public static final String DASHBOARDCOMPARATOR_STATUS = "oracle.sysman.emaas.platform.dashboards:Name=DashboardUIStatus,Type=oracle.sysman.emaas.platform.dashboards.ui.targetmodel.services.DashboardUIStatus";

	public static JMXUtil getInstance()
	{
		if (instance == null) {
			synchronized (JMXUtil.class) {
				if (instance == null) {
					instance = new JMXUtil();
				}
			}
		}

		return instance;
	}

	private MBeanServer server = null;

	private JMXUtil()
	{
	}

	public void registerMBeans() throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException,
			NotCompliantMBeanException
	{
		server = ManagementFactory.getPlatformMBeanServer();
		ObjectName statusObjectName = new ObjectName(DASHBOARDCOMPARATOR_STATUS);
		if (!server.isRegistered(statusObjectName)) {
			DashboardComparatorStatus dashboardComparatorStatus = new DashboardComparatorStatus();
			server.registerMBean(dashboardComparatorStatus, statusObjectName);
		}

		_logger.info("start Dashboard Comparator MBeans!");
	}

	public void unregisterMBeans() throws MalformedObjectNameException, MBeanRegistrationException, InstanceNotFoundException
	{
		ObjectName statusObjectName = new ObjectName(DASHBOARDCOMPARATOR_STATUS);
		if (server.isRegistered(statusObjectName)) {
			server.unregisterMBean(statusObjectName);
		}
		_logger.info("stop Dashboard Comparator MBeans!");
	}

}
