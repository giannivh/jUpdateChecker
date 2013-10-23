/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.downloader.domain;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class ByteUtils {

    public static String getPrettyDisplay( long bytes ) {

        int unit = 1024;

        if( bytes < unit )
            return String.format( "%d B", bytes );

        int exp = (int) ( Math.log( bytes ) / Math.log( unit ) );

        String pre = ( "KMGTPE" ).charAt( exp - 1 ) + ( "i" );

        return String.format( "%.2f %sB", bytes / Math.pow( unit, exp ), pre );
    }
}
