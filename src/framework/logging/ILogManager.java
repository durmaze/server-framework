package framework.logging;

import framework.utils.TimeSpan;

public interface ILogManager
{
	// constants
	public static final TimeSpan DEFAULT_WATCH_PERIOD = TimeSpan.fromSeconds(60);

	/**
	 * Configures underlying logging framework.
	 * 
	 * Configuration file for the logging framework, log directories and log filenames are usually supplied via VM arguments by System administrators. 
	 * Underlying logging frameworks should honour these values. 
	 * 
	 * If no VM arguments are supplied, default parameters are set as System properties, which underlying logging framework can refer to.
	 * 
	 * @param defaultConfigFile default config file if no config file is supplied as VM arguments
	 * @param defaultLogDirectory default log directory if no log directory is supplied as VM arguments
	 * @param defaultLogFileName default log filename if no log filename is supplied as VM arguments
	 */
	public void configure(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName);

	/**
	 * Dynamically configures underlying logging framework.
	 * 
	 * Configuration file for the logging framework, log directories and log filenames are usually supplied via VM arguments by System administrators. 
	 * Underlying logging frameworks should honour these values. 
	 * 
	 * If no VM arguments are supplied, default parameters are set as System properties, which underlying logging framework can refer to.
	 * 
	 * @param defaultConfigFile default config file if no config file is supplied as VM arguments
	 * @param defaultLogDirectory default log directory if no log directory is supplied as VM arguments
	 * @param defaultLogFileName default log filename if no log filename is supplied as VM arguments
	 */
	public void configureAndWatch(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName);
	
	/**
	 * Dynamically configures underlying logging framework. Configuration is checked at defined watch periods.
	 * 
	 * Configuration file for the logging framework, log directories and log filenames are usually supplied via VM arguments by System administrators. 
	 * Underlying logging frameworks should honour these values. 
	 * 
	 * If no VM arguments are supplied, default parameters are set as System properties, which underlying logging framework can refer to.
	 * 
	 * @param defaultConfigFile default config file if no config file is supplied as VM arguments
	 * @param defaultLogDirectory default log directory if no log directory is supplied as VM arguments
	 * @param defaultLogFileName default log filename if no log filename is supplied as VM arguments
	 * @param watchPeriod defines watch period
	 */
	public void configureAndWatch(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName, TimeSpan watchPeriod);
	
	/**
	 * Closes any resources used by the underlying logging framework
	 */
	public void shutdown();
}
