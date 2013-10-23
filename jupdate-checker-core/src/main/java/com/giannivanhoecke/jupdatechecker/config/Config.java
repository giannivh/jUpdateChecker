/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.config;

import java.util.ResourceBundle;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class Config {

    public static String getApplicationVersion() {

        return getConfig().getString( "jupdatechecker.version" );
    }

    public static String getDownloaderJar() {

        return getConfig().getString( "jupdatechecker.downloader.name" );
    }

    private static ResourceBundle getConfig() {

        return ResourceBundle.getBundle( "jupdatechecker" );
    }
}
