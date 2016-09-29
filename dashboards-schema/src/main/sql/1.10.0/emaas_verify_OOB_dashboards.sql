Rem
Rem
Rem emaas_verify_OOB_dashboards.sql
Rem
Rem
Rem    NAME
Rem      emaas_verify_OOB_dashboards.sql
Rem
Rem    DESCRIPTION
Rem      verify the OOB dashboard of tenant
Rem		 For JIRA : EMCPDF-2228
Rem
Rem    NOTES
Rem      if one want to verify a specific tenant, input the parameter which is tenant id,if want to verify all tenants, input -1
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    chehao   9/14/2016  Created
DEFINE TENANT_ID = '&tenant_id'
DEFINE EMSAAS_SQL_ROOT ='&log_root'
SET FEEDBACK ON
SET SERVEROUTPUT ON
spool '&EMSAAS_SQL_ROOT/oob_dashboards_verify.log';
DECLARE
  V_DASHBOARD_COUNT     NUMBER;
  V_TENANT_ID NUMBER(38,0);
  V_TID       NUMBER(38,0) := '&TENANT_ID';
  V_FLAG NUMBER(38,0);
  
  V_DASHBOARD_ID NUMBER(38,0);
  V_DASHBOARD_NAME VARCHAR2(320);
  V_DASHBOARD_DESC VARCHAR2(1024);
  V_DASHBOARD_WIDGET_COUNT NUMBER;
  
  C_DASHBOARD_COUNT NUMBER;
  C_DASHBOARD_ID NUMBER(38,0);
  C_DASHBOARD_NAME VARCHAR2(320);
  C_DASHBOARD_DESC VARCHAR2(1024);
  C_DASHBOARD_WIDGET_COUNT NUMBER;
  CURSOR TENANT_CURSOR IS
    SELECT DISTINCT TENANT_ID FROM EMS_DASHBOARD ORDER BY TENANT_ID;
	
	--function CHECK_DASHBOARD()
	FUNCTION CHECK_DASHBOARD(
	C_DASHBOARD_ID IN NUMBER,
	C_DASHBOARD_NAME IN VARCHAR2,
	C_DASHBOARD_DESC IN VARCHAR2,
	V_DASHBOARD_ID IN OUT NUMBER,
	V_DASHBOARD_NAME IN OUT VARCHAR2,
	V_DASHBOARD_DESC IN OUT VARCHAR2,
	V_TENANT_ID IN NUMBER
	)RETURN NUMBER IS V_FLAG NUMBER;
BEGIN
	SELECT DASHBOARD_ID,NAME,DESCRIPTION INTO V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC FROM EMS_DASHBOARD WHERE TENANT_ID=V_TENANT_ID AND IS_SYSTEM=1 AND DASHBOARD_ID=C_DASHBOARD_ID;
	if(C_DASHBOARD_ID = V_DASHBOARD_ID)	THEN	
		DBMS_OUTPUT.PUT_LINE('OOB dashboard id is correct for tenant id:'||V_TENANT_ID||',dashboard id:'||V_DASHBOARD_ID);
	ELSE
		DBMS_OUTPUT.PUT_LINE('[*Error*]:OOB dashboard id is not correct for tenant id:'||V_TENANT_ID||',dashboard id:'||V_DASHBOARD_ID||'. Expected:['||C_DASHBOARD_ID||'] Actual:['||V_DASHBOARD_ID||']');
		return -1;
	END IF;	
	--check dashboard name
	if(C_DASHBOARD_NAME = V_DASHBOARD_NAME)	THEN	
		DBMS_OUTPUT.PUT_LINE('OOB dashboard name is correct for tenant id:'||V_TENANT_ID||',dashboard id:'||V_DASHBOARD_ID);
	ELSE
		DBMS_OUTPUT.PUT_LINE('[*Error*]:OOB dashboard name is not correct for tenant id:'||V_TENANT_ID||',dashboard id:'||V_DASHBOARD_ID||'. Expected:['||C_DASHBOARD_NAME||'] Actual:['||V_DASHBOARD_NAME||']');
		return -1;
	END IF;	
	--check dashboard desc
	if(C_DASHBOARD_DESC = V_DASHBOARD_DESC OR (C_DASHBOARD_DESC IS NULL AND V_DASHBOARD_DESC IS NULL))	THEN	
		DBMS_OUTPUT.PUT_LINE('OOB dashboard description is correct for tenant id:'||V_TENANT_ID||',dashboard id:'||V_DASHBOARD_ID);
	ELSE
		DBMS_OUTPUT.PUT_LINE('[*Error*]:OOB dashboard description is not correct for tenant id:'||V_TENANT_ID||',dashboard id:'||V_DASHBOARD_ID||'. Expected:['||C_DASHBOARD_DESC||'] Actual:['||V_DASHBOARD_DESC||']');
		return -1;
	END IF;		
	RETURN 1;
END CHECK_DASHBOARD;
	--function CHECK_WIDGET_COUNT()
	FUNCTION CHECK_WIDGET_COUNT(
	C_DASHBOARD_ID IN NUMBER,
	C_DASHBOARD_WIDGET_COUNT IN NUMBER,
	V_DASHBOARD_WIDGET_COUNT IN OUT NUMBER,
	V_TENANT_ID IN NUMBER
	)RETURN NUMBER IS V_FLAG NUMBER;
BEGIN
	SELECT COUNT(1) INTO V_DASHBOARD_WIDGET_COUNT FROM EMS_DASHBOARD_TILE WHERE TENANT_ID=V_TENANT_ID AND DASHBOARD_ID=C_DASHBOARD_ID;
	IF(V_DASHBOARD_WIDGET_COUNT<>C_DASHBOARD_WIDGET_COUNT) THEN
		DBMS_OUTPUT.PUT_LINE('[*Error*]:OOB dashboard widgets number is not correct for tenant id:'||V_TENANT_ID||', dashboard id:'||C_DASHBOARD_ID||'. Expected:['||C_DASHBOARD_WIDGET_COUNT||'] Actual:['||V_DASHBOARD_WIDGET_COUNT||']');
		RETURN -1;
	ELSE
		DBMS_OUTPUT.PUT_LINE('OOB dashboard widgets number is correct for tenant id:'||V_TENANT_ID||',dashboard id:'||C_DASHBOARD_ID);
	END IF;	
		RETURN 1;
    
END CHECK_WIDGET_COUNT;

BEGIN
  OPEN TENANT_CURSOR;
  LOOP
    IF (V_TID<>-1) THEN
      V_TENANT_ID:=V_TID;
    ELSE
      FETCH TENANT_CURSOR INTO V_TENANT_ID;
      EXIT WHEN TENANT_CURSOR%NOTFOUND;     
    END IF;  
	--verify OOB dashboard count
	C_DASHBOARD_COUNT :=26;
    SELECT COUNT(1) INTO V_DASHBOARD_COUNT FROM EMS_DASHBOARD WHERE TENANT_ID=V_TENANT_ID AND IS_SYSTEM=1;
    IF (V_DASHBOARD_COUNT<>C_DASHBOARD_COUNT) THEN
      DBMS_OUTPUT.PUT_LINE('[*Error*]:OOB dashboard count is not correct for tenant id:'||V_TENANT_ID||'. Expected:['||C_DASHBOARD_COUNT||'] Actual:['||V_DASHBOARD_COUNT||']');
    ELSE
      DBMS_OUTPUT.PUT_LINE('OOB dashboard count is correct for tenant id:'||V_TENANT_ID);
    END IF;
	
	--verify Dashboard id, name, desc,widget count
	--1.
	C_DASHBOARD_ID:=2;
	C_DASHBOARD_NAME:='Performance Analytics: Database';
	C_DASHBOARD_DESC:='Database Performance Analytics';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	--2.
	C_DASHBOARD_ID:=3;
	C_DASHBOARD_NAME:='Resource Analytics: Database';
	C_DASHBOARD_DESC:='Database Resource Planning';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--3.
	C_DASHBOARD_ID:=4;
	C_DASHBOARD_NAME:='Performance Analytics Application Server';
	C_DASHBOARD_DESC:='Analyze overhead of Garbage Collection and its impact of WebLogic performance';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--4.
	C_DASHBOARD_ID:=14;
	C_DASHBOARD_NAME:='Application Performance Monitoring';
	C_DASHBOARD_DESC:='Monitor performance of your applications, view response times, invocation counts across all pages of your application.';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--5.
	C_DASHBOARD_ID:=15;
	C_DASHBOARD_NAME:='Database Operations';
	C_DASHBOARD_DESC:='Displays the current health of your database ecosystem';
	C_DASHBOARD_WIDGET_COUNT:=4;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--6.
	C_DASHBOARD_ID:=16;
	C_DASHBOARD_NAME:='Host Operations';
	C_DASHBOARD_DESC:='Displays the current health of your monitored hosts';
	C_DASHBOARD_WIDGET_COUNT:=4;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--7.
	C_DASHBOARD_ID:=17;
	C_DASHBOARD_NAME:='Middleware Operations';
	C_DASHBOARD_DESC:='Displays the current health of your Oracle middleware ecosystem';
	C_DASHBOARD_WIDGET_COUNT:=4;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--8.
	C_DASHBOARD_ID:=18;
	C_DASHBOARD_NAME:='Resource Analytics: Middleware';
	C_DASHBOARD_DESC:='Perform Resource Analytics for Oracle Fusion Middleware based on Heap, CPU Usage etc.';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--9.
	C_DASHBOARD_ID:=19;
	C_DASHBOARD_NAME:='Resource Analytics: Host';
	C_DASHBOARD_DESC:='Resource Analytics: Host';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--10.
	C_DASHBOARD_ID:=20;
	C_DASHBOARD_NAME:='Application Performance Analytics';
	C_DASHBOARD_DESC:='Application Performance Analytics';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--11.
	C_DASHBOARD_ID:=21;
	C_DASHBOARD_NAME:='Availability Analytics';
	C_DASHBOARD_DESC:='RAvailability Analytics';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--12.
	C_DASHBOARD_ID:=22;
	C_DASHBOARD_NAME:='Workflow Submissions';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=4;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--13
	C_DASHBOARD_ID:=23;
	C_DASHBOARD_NAME:='Orchestration';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=0;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--14.
	C_DASHBOARD_ID:=24;
	C_DASHBOARD_NAME:='UI Gallery';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=0;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--15.
	C_DASHBOARD_ID:=25;
	C_DASHBOARD_NAME:='Timeseries';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=12;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--16.
	C_DASHBOARD_ID:=26;
	C_DASHBOARD_NAME:='Categorical';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=10;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--17.
	C_DASHBOARD_ID:=27;
	C_DASHBOARD_NAME:='Others';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=6;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--18.
	C_DASHBOARD_ID:=28;
	C_DASHBOARD_NAME:='Exadata Health';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=0;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--19.
	C_DASHBOARD_ID:=29;
	C_DASHBOARD_NAME:='Overview';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=4;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--20.
	C_DASHBOARD_ID:=30;
	C_DASHBOARD_NAME:='Performance';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=7;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--21.
	C_DASHBOARD_ID:=31;
	C_DASHBOARD_NAME:='Enterprise Health';
	C_DASHBOARD_DESC:='svw';
	C_DASHBOARD_WIDGET_COUNT:=0;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--22.
	C_DASHBOARD_ID:=32;
	C_DASHBOARD_NAME:='Summary';
	C_DASHBOARD_DESC:='Summary of all monitored entities. svw';
	C_DASHBOARD_WIDGET_COUNT:=13;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--23.
	C_DASHBOARD_ID:=33;
	C_DASHBOARD_NAME:='Hosts';
	C_DASHBOARD_DESC:='All monitored hosts in this entperise. svw';
	C_DASHBOARD_WIDGET_COUNT:=10;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--24
	C_DASHBOARD_ID:=34;
	C_DASHBOARD_NAME:='Databases';
	C_DASHBOARD_DESC:='All monitored databases in this enterprise. svw';
	C_DASHBOARD_WIDGET_COUNT:=9;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--25
	C_DASHBOARD_ID:=35;
	C_DASHBOARD_NAME:='Application Servers';
	C_DASHBOARD_DESC:=null;
	C_DASHBOARD_WIDGET_COUNT:=10;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);
	
	--26
	C_DASHBOARD_ID:=36;
	C_DASHBOARD_NAME:='Entities';
	C_DASHBOARD_DESC:='All monitored entities in this enterprise. svw';
	C_DASHBOARD_WIDGET_COUNT:=1;
	V_FLAG:=CHECK_DASHBOARD(C_DASHBOARD_ID,C_DASHBOARD_NAME,C_DASHBOARD_DESC,V_DASHBOARD_ID,V_DASHBOARD_NAME,V_DASHBOARD_DESC,V_TENANT_ID);
	V_FLAG:=CHECK_WIDGET_COUNT(C_DASHBOARD_ID,C_DASHBOARD_WIDGET_COUNT,V_DASHBOARD_WIDGET_COUNT,V_TENANT_ID);

    IF (V_TID<>-1) THEN
      EXIT;
    END IF;
  END LOOP;
  CLOSE TENANT_CURSOR;
  
  EXCEPTION
	WHEN OTHERS THEN
	ROLLBACK;
	DBMS_OUTPUT.PUT_LINE('Fail to check OOB dashboards due to '||SQLERRM);
 RAISE;

END;
/
spool off;