package framework.logging;

import java.io.File;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.extras.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.exception.ExceptionCodes;
import framework.exception.ServerException;
import framework.utils.TimeSpan;

public class Log4jLogManager implements ILogManager
{
	// dependency
	private final boolean isVerboseMode;
	
	// variants
	private Logger logger; // will be initialized when we successfully configure log4j
	
	public Log4jLogManager(boolean isVerboseMode)
	{
		this.isVerboseMode = isVerboseMode;
	}
	
	public Log4jLogManager()
	{
		this.isVerboseMode = false;
	}
	
	@Override
	public void configure(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName)
	{
		doConfigure(defaultConfigFile, defaultLogDirectory, defaultLogFileName, false, null);
	}
	
	@Override
	public void configureAndWatch(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName)
	{
		doConfigure(defaultConfigFile, defaultLogDirectory, defaultLogFileName, true, DEFAULT_WATCH_PERIOD);
	}

	@Override
	public void configureAndWatch(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName, TimeSpan watchPeriod)
	{
		if (watchPeriod == null)
		{
			watchPeriod = DEFAULT_WATCH_PERIOD;
		}
		
		doConfigure(defaultConfigFile, defaultLogDirectory, defaultLogFileName, true, watchPeriod);
	}

	@Override
	public void shutdown()
	{
		if (this.isVerboseMode)
		{
			System.out.println("shutdown request is sent to log4j.");
		}
		
		LogManager.shutdown();
		
		if (this.isVerboseMode)
		{
			System.out.println("log4j is successfully shutdown.");
		}
	}

	private void doConfigure(String defaultConfigFile, String defaultLogDirectory, String defaultLogFileName, boolean shouldWatch, TimeSpan watchPeriod)
	{
		try
		{
			updateEnvironmetVariables(defaultLogDirectory, defaultLogFileName);
			
			String configUrlStr = System.getProperty("log4j.configuration");
			
			if (configUrlStr == null)
			{
				if (this.isVerboseMode)
				{
					System.out.println("log4j.configuration system property not found! Looking log4j.xml under current directory.");
				}
				
				// check configuration file existence, since log4j handles FileNotFoundExceptions gracefully.
				File configFile = new File(defaultConfigFile);
				
				if (!configFile.exists())
				{
					throw new ServerException(ExceptionCodes.LOGGING_CANNOT_FIND_LOGGING_CONFIGURATION_FILE, configFile.getAbsolutePath());
				}
				
				// load from current directory
				if (shouldWatch)
				{
					DOMConfigurator.configureAndWatch(defaultConfigFile, watchPeriod.toMilliSeconds());
				}
				else
				{
					DOMConfigurator.configure(defaultConfigFile);
				}
				
				// get logger at log successful configuration
				this.logger = LoggerFactory.getLogger(Log4jLogManager.class);
				
				this.logger.info("log4j configuration is successfully loaded from current directory.");
			}
			else
			{
				if (this.isVerboseMode)
				{
					System.out.println("log4j.configuration system property found! Looking log4j.xml at " + configUrlStr);
				}
				
				// check configuration file existence, since log4j handles FileNotFoundExceptions gracefully.
				URL configUrl = new URL(configUrlStr);
				File configFile = new File(configUrl.toURI());
				
				if (!configFile.exists())
				{
					throw new ServerException(ExceptionCodes.LOGGING_CANNOT_FIND_LOGGING_CONFIGURATION_FILE, configUrlStr);
				}
				
				// load from the given URL
				if (shouldWatch)
				{
					DOMConfigurator.configureAndWatch(configFile.getAbsolutePath(), watchPeriod.toMilliSeconds());
				}
				else
				{
					DOMConfigurator.configure(configUrl);
				}
				
				// get logger at log successful configuration
				this.logger = LoggerFactory.getLogger(Log4jLogManager.class);
				
				this.logger.info("log4j configuration is successfully loaded from {}.", configUrlStr);
			}
		}
		catch (Exception e)
		{
			throw new ServerException(e, ExceptionCodes.LOGGING_CANNOT_CONFIGURE_LOGGING_FRAMEWORK);
		}
	}

	private void updateEnvironmetVariables(String defaultLogDirectory, String defaultLogFileName)
	{
		if (System.getProperty("log.dir") == null)
		{
			System.setProperty("log.dir", defaultLogDirectory);
		}
		
		if (System.getProperty("log.filename") == null)
		{
			System.setProperty("log.filename", defaultLogFileName);
		}
	}
}
