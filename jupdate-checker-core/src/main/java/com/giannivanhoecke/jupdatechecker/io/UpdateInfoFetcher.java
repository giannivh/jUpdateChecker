/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.io;

import com.giannivanhoecke.jupdatechecker.domain.FetchResult;
import com.giannivanhoecke.jupdatechecker.domain.Version;
import com.giannivanhoecke.jupdatechecker.exception.InvalidVersionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class UpdateInfoFetcher {

    public static void fetch( String newVersionURL )
            throws IOException, InvalidVersionException {

        String versionString = getTextFromURL( newVersionURL );

        FetchResult.INSTANCE.setVersion( Version.getVersion( versionString ) );
    }

    public static String getTextFromURL( String urlString )
            throws IOException, InvalidVersionException {

        URL url = new URL( urlString );

        BufferedReader reader = new BufferedReader(
                new InputStreamReader( url.openConnection().getInputStream() ) );

        String input;
        String text = "";

        while( ( input = reader.readLine() ) != null ) {

            text += input + System.getProperty( "line.separator" );
        }

        return replaceLast( text, System.getProperty( "line.separator" ), "" );
    }

    public static String replaceLast( String string, String toReplace, String replacement ) {

        int pos = string.lastIndexOf( toReplace );

        if ( pos > -1 ) {

            return string.substring( 0, pos )
                    + replacement
                    + string.substring( pos + toReplace.length(), string.length() );

        } else {

            return string;
        }
    }
}
