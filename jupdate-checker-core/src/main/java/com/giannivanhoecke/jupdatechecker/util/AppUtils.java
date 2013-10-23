/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class AppUtils {

    /**
     * @return the absolute path to the application where this update checker runs in.
     */
    public static String getApplicationFile() {

        String decodedPath = null;

        try {

            String path = AppUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            decodedPath = URLDecoder.decode( path, "UTF-8" );

        } catch ( UnsupportedEncodingException e ) {

            //Should not occur.
        }

        return decodedPath;
    }
}
