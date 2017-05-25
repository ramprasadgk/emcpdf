Rem
Rem cleanup_oob.sql
Rem
Rem Copyright (c) 2013, 2014, 2015, 2016, 2017 Oracle and/or its affiliates.
Rem All rights reserved.
Rem
Rem    NAME
Rem      update_oob_time.sql
Rem
Rem    DESCRIPTION
Rem      update oob dashboards creation time and last modification time to keep the sort as before
Rem
Rem    NOTES
Rem      None
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    REX      05/05/25 - 
Rem

SET FEEDBACK ON
SET SERVEROUTPUT ON
DECLARE
    CONST_TENANT             CONSTANT      NUMBER:=-11;
    V_TIMESTAMP_STR                        VARCHAR2(64 BYTE);
    V_CREATION_DATE                        TIMESTAMP(6);
    V_LAST_MODIFICATION_DATE               TIMESTAMP(6);
    V_DASHBOARD_ID                         NUMBER(38,0);
    V_DASHBOARD_SET_ID                     NUMBER(38,0);
    V_SUB_DASHBOARD_ID                     NUMBER(38,0);

BEGIN
  -- Timeseries - Line Basic
  V_DASHBOARD_ID              := 41;
  V_TIMESTAMP_STR             := '2017-02-08T22:20:00.000Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Timeseries - Line Advanced
  V_DASHBOARD_ID              := 42;
  V_TIMESTAMP_STR             := '2017-02-08T22:20:00.001Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Timeseries - Area
  V_DASHBOARD_ID              := 43;
  V_TIMESTAMP_STR             := '2017-02-08T22:20:00.002Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Categorical - Basic
  V_DASHBOARD_ID              := 44;
  V_TIMESTAMP_STR             := '2017-02-08T22:20:00.003Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Categorical - Advanced
  V_DASHBOARD_ID              := 45;
  V_TIMESTAMP_STR             := '2017-02-08T22:20:00.004Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Trend and Forecasting
  V_DASHBOARD_ID              := 46;
  V_TIMESTAMP_STR             := '2017-02-08T22:20:00.005Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Performance Analytics: Database
  V_DASHBOARD_ID              := 2;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.002Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Resource Analytics: Database
  V_DASHBOARD_ID              := 3;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.003Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Performance Analytics Application Server
  V_DASHBOARD_ID              := 4;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.004Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Application Performance Monitoring
  V_DASHBOARD_ID              := 14;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.014Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Database Operations
  V_DASHBOARD_ID              := 15;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.015Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Host Operations
  V_DASHBOARD_ID              := 16;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.016Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Middleware Operations
  V_DASHBOARD_ID              := 17;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.017Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Resource Analytics: Middleware
  V_DASHBOARD_ID              := 18;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.018Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Resource Analytics: Host
  V_DASHBOARD_ID              := 19;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.019Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Application Performance Analytics
  V_DASHBOARD_ID              := 20;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.020Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Availability Analytics
  V_DASHBOARD_ID              := 21;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.021Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- UI Gallery
  V_DASHBOARD_ID              := 24;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.024Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Others
  V_DASHBOARD_ID              := 27;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.027Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Exadata Health
  V_DASHBOARD_ID              := 28;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.028Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Overview
  V_DASHBOARD_ID              := 29;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.029Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Performance
  V_DASHBOARD_ID              := 30;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.030Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Enterprise Health
  V_DASHBOARD_ID              := 31;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.031Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Summary
  V_DASHBOARD_ID              := 32;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.032Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Hosts
  V_DASHBOARD_ID              := 33;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.033Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Databases
  V_DASHBOARD_ID              := 34;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.034Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Application Servers
  V_DASHBOARD_ID              := 35;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.035Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Entities
  V_DASHBOARD_ID              := 36;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.036Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Orchestration Workflows
  V_DASHBOARD_ID              := 37;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.037Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- DNS
  V_DASHBOARD_ID              := 40;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.040Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Firewall
  V_DASHBOARD_ID              := 47;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.047Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  -- Oracle Database Security
  V_DASHBOARD_ID              := 48;
  V_TIMESTAMP_STR             := '2015-01-01T01:01:01.048Z';
  V_CREATION_DATE             := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  V_LAST_MODIFICATION_DATE    := TO_TIMESTAMP(V_TIMESTAMP_STR,'YYYY-MM-DD"T"HH24:MI:SS.ff3"Z"');
  UPDATE EMS_DASHBOARD SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  UPDATE EMS_DASHBOARD_TILE SET CREATION_DATE = V_CREATION_DATE,LAST_MODIFICATION_DATE = V_LAST_MODIFICATION_DATE WHERE DASHBOARD_ID = V_DASHBOARD_ID and TENANT_ID = CONST_TENANT;
  
  DBMS_OUTPUT.PUT_LINE('Success to update OOB dashboards time ' || SQLERRM);
  COMMIT;
  EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Failed to update OOB dashboards time ' || SQLERRM);
    RAISE;
END;
/
