/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.downloader.ui;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public interface UI {

    public void setStatus( String status, int progress, boolean fileSizeIsUnknown );
    public void completed( String message );
    public void errorOccurred( String message );
    public void sendMessage( String message, boolean isError );
}
