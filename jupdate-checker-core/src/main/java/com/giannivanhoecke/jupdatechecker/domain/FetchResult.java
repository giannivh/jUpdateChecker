/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.domain;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public enum FetchResult {

    INSTANCE;

    private Version version;

    FetchResult() {

        version = null;
    }

    public Version getVersion() {

        return version;
    }

    public void setVersion( Version version ) {

        this.version = version;
    }
}
