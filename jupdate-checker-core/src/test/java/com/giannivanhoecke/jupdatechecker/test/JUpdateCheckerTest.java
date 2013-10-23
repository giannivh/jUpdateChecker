/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.test;

import com.giannivanhoecke.jupdatechecker.JUpdateChecker;
import com.giannivanhoecke.jupdatechecker.domain.Version;
import com.giannivanhoecke.jupdatechecker.exception.InvalidVersionException;

import java.io.IOException;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class JUpdateCheckerTest {

    public static void main( String[] args ) {

        //Create object...
        String  appName         = "Test App";
        Version currentVersion  = new Version( 0, 1 );
        String  newVersionURL   = "http://giannivanhoecke.com/jUpdateChecker_version.txt";
        String  releaseNotesURL = "http://sfap.sourceforge.net/releasenotes.html";
        String  downloadPageURL = "http://giannivanhoecke.com/";

        JUpdateChecker jUpdateChecker =
                new JUpdateChecker( appName, currentVersion, newVersionURL, releaseNotesURL, downloadPageURL, null,
                        "http://giannivanhoecke.com/dev/BelgacomMeter_file.txt" );

        try {

            //Show gui...
            jUpdateChecker.checkForUpdates( false );
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        catch (InvalidVersionException e) {

            e.printStackTrace();
        }
    }
}
