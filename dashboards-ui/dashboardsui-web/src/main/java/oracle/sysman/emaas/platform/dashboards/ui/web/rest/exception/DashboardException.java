package oracle.sysman.emaas.platform.dashboards.ui.web.rest.exception;

/**
 * Abstract class used as base exception for dashbaord business layer. For different categories of dashbaord exceptions, better
 * please implement 'common' exception (e.g. CommonSecurityException) and exceptions for specific error type (e.g.
 * DeleteSystemDashboardException)
 *
 * @author guobaochen
 */
abstract public class DashboardException extends Exception
{
	private static final long serialVersionUID = -3841748006797396784L;
	private final Integer errorCode;

	public DashboardException(Integer errorCode, String message)
	{
		super(message);
		this.errorCode = errorCode;
	}

	public DashboardException(Integer errorCode, String message, Throwable t)
	{
		super(message, t);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode()
	{
		return errorCode;
	}
}
