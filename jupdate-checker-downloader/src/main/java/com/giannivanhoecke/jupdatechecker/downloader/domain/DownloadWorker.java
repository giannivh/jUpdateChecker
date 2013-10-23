/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.downloader.domain;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class DownloadWorker implements Runnable {

    public static final int BYTES = 1024;

    private String urlString;
    private String prefix;
    private String suffix;

    private void loadConfig()
            throws IOException {

        //Download file
        URL url = new URL( Controller.INSTANCE.getConfigLink() );

        BufferedReader reader = new BufferedReader(
                new InputStreamReader( url.openConnection().getInputStream() ) );

        urlString = reader.readLine();

        reader.close();

        prefix = "jUpdateChecker";
        suffix = urlString.substring( urlString.lastIndexOf( "." ), urlString.length() ); //extension
    }

    @SuppressWarnings({"UnusedAssignment", "ConstantConditions"})
    @Override
    public void run() {

        BufferedInputStream in = null;
        FileOutputStream fout  = null;
        HttpURLConnection conn = null;

        try {

            loadConfig();

            File tempFile = File.createTempFile( prefix, suffix );
            URL url       = new URL( urlString );
            in            = new BufferedInputStream( url.openStream() );
            fout          = new FileOutputStream( tempFile );
            conn          = (HttpURLConnection) url.openConnection();

            //
            // Get file size
            //
            try {

                conn.setRequestMethod( "HEAD" );
                conn.getInputStream();
            }
            catch ( Exception e ) {

                //Sometimes getInputStream fails using HEAD (FileNotFoundException)
                //So let's get an estimate using the GET
                conn = (HttpURLConnection) url.openConnection();
            }
            int totalSize = conn.getContentLength();
            Controller.INSTANCE.setFileSize( totalSize );

            //
            // Download...
            //
            byte data[] = new byte[ BYTES ];
            int count;
            double byteCounter = 0;

            while( ( count = in.read( data, 0, BYTES ) ) != -1 ) {

                fout.write( data, 0, count );

                double progress = 0.0;
                byteCounter += count;
                Controller.INSTANCE.setAlreadyDownloaded( (int)byteCounter );

                if( totalSize > 1)
                    progress = ( byteCounter / (double)totalSize ) * 100.0;

                Controller.INSTANCE.update(
                        ( totalSize == -1 ) ?
                                String.format( "Downloading... (%s)",
                                        ByteUtils.getPrettyDisplay( (int)byteCounter ) ) :
                                String.format( "Downloading... (%s / %s)",
                                        ByteUtils.getPrettyDisplay( (int)byteCounter ),
                                        ByteUtils.getPrettyDisplay( totalSize ) ),
                        (int) progress,
                        totalSize == -1 );
            }

            //
            // Close streams...
            //
            in.close();
            fout.close();

            //
            // Notify
            //
            Controller.INSTANCE.setTempDownloadedFile( tempFile );
            Controller.INSTANCE.completed( "The update is ready to be installed." );
        }
        catch ( Exception e ) {

            Controller.INSTANCE.errorOccurred( String.format( "Cannot update (%s)", e.getMessage() ) );
        }
        finally {

            try {

                if (in != null)
                    in.close();
                if (fout != null)
                    fout.close();

            }
            catch ( Exception e ) {

                /*Ignore*/
            }
            finally {

                in = null;
                fout = null;
            }
        }
    }
}
