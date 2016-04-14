import org.apache.commons.io.FileUtils;
import org.omg.CORBA.portable.ApplicationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

/**
 * It contains actual Updating logic
 */
public class Updater
{
	final public static String configFile = "update.properties";
	final private File config;

	public Updater( final File inputFile )
	{
		config = inputFile;
	}

	public void parseAndUpdate()
	{
		Properties properties = new Properties(  );
		try
		{
			properties.load( new FileReader( config ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		String originFileName = properties.getProperty( "origin" );
		File origin = new File( originFileName );
//		System.out.println( origin.exists() );
//		System.out.println( new Date(origin.lastModified()) );
//
		String installedFileName = properties.getProperty( "installed" );
		File installed = new File( installedFileName );
//		System.out.println( installed.exists() );
//		System.out.println( new Date(installed.lastModified()) );
//
//		System.out.println( properties.getProperty( "workspace" ) );

		if( origin.lastModified() > installed.lastModified() )
		{
            System.out.println( "New update is detected." );
			try
			{
				FileUtils.copyDirectory( origin.getParentFile(), new File(System.getProperty("user.dir")) );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}

		run( installedFileName );

        Runtime.getRuntime().exit(0);
	}

	static public void run(String... commands)
	{
		try
		{
			Runtime.getRuntime().exec( commands );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	public static void main( final String[] args )
	{
		final File config = new File( System.getProperty("user.dir") + "/" + configFile );

		if( config.exists() )
		{
			new Updater( config ).parseAndUpdate();
		}
		else
		{
			throw new RuntimeException( "No config file is provided." );
		}
	}
}
