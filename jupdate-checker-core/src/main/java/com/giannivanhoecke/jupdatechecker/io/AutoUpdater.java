/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.io;

import com.giannivanhoecke.jupdatechecker.config.Config;

import java.io.*;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class AutoUpdater {

    /**
     * Opens the auto updater.
     *
     * @param autoUpdaterURL the update url.
     * @param currentApplicationFile the app this checker runs in.
     */
    public static void start( String autoUpdaterURL, String currentApplicationFile ) {

        //Copy the updater to temp dir
        BufferedInputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            //Source
            inputStream = new BufferedInputStream(
                    AutoUpdater.class.getResourceAsStream( "/" + Config.getDownloaderJar() ) );

            //Destination
            File updaterFile = File.createTempFile( "updater", ".jar" );
            outputStream = new FileOutputStream( updaterFile );

            int read = 0;
            byte[] bytes = new byte[1024];

            while( ( read = inputStream.read( bytes ) ) != -1 ) {

                outputStream.write( bytes, 0, read );
            }

            //Open the updater
            System.out.println( String.format( "java -jar %s %s %s",
                    updaterFile.getAbsolutePath(),
                    autoUpdaterURL,
                    currentApplicationFile ) );
            Runtime.getRuntime().exec( String.format( "java -jar %s %s %s",
                    updaterFile.getAbsolutePath(),
                    autoUpdaterURL,
                    currentApplicationFile ) );
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {

            try {

                if( outputStream != null )
                    outputStream.close();

                if( inputStream != null )
                    inputStream.close();

            }
            catch ( Exception e ) {

                /* Ignore */
            }
        }

        //Exit the app this is running in
        System.exit( 0 );
    }
}
