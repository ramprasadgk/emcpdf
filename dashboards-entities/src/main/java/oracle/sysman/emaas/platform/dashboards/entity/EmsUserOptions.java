package oracle.sysman.emaas.platform.dashboards.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * @author jishshi
 * @since 2/1/2016
 */

@Entity
@NamedQueries({ @NamedQuery(name = "EmsUserOptions.removeAll", query = "delete from EmsUserOptions o where o.dashboardId = :dashboardId") })
@Table(name = "EMS_DASHBOARD_USER_OPTIONS")
@IdClass(EmsUserOptionsPK.class)
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant.id", length = 32, primaryKey = true)
public class EmsUserOptions implements Serializable
{
	private static final long serialVersionUID = 8723513639667559582L;

	@Id
	@Column(name = "DASHBOARD_ID", nullable = false, length = 256)
	Long dashboardId;
	@Id
	@Column(name = "USER_NAME", nullable = false, length = 128)
	String userName;

	@Column(name = "AUTO_REFRESH_INTERVAL", nullable = false, length = 256)
	Long autoRefreshInterval;
	@Column(name = "IS_FAVORITE", nullable = false)
	private Integer isFavorite;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACCESS_DATE")
	private Date accessDate;

	public EmsUserOptions()
	{

	}

	/**
	 * @return the accessDate
	 */
	public Date getAccessDate()
	{
		return accessDate;
	}

	public Long getAutoRefreshInterval()
	{
		return autoRefreshInterval;
	}

	public Long getDashboardId()
	{
		return dashboardId;
	}

	/**
	 * @return the isFavorite
	 */
	public Integer getIsFavorite()
	{
		return isFavorite;
	}

	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param accessDate
	 *            the accessDate to set
	 */
	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}

	public void setAutoRefreshInterval(Long autoRefreshInterval)
	{
		this.autoRefreshInterval = autoRefreshInterval;
	}

	public void setDashboardId(Long dashboardId)
	{
		this.dashboardId = dashboardId;
	}

	/**
	 * @param isFavorite
	 *            the isFavorite to set
	 */
	public void setIsFavorite(Integer isFavorite)
	{
		this.isFavorite = isFavorite;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
}
