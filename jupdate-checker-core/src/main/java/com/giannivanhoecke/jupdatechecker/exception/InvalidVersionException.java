/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.exception;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class InvalidVersionException extends Exception {

    public InvalidVersionException() {

        super();
    }

    public InvalidVersionException( String s ) {

        super( s );
    }

    public InvalidVersionException( String s, Throwable throwable ) {

        super( s, throwable );
    }

    public InvalidVersionException( Throwable throwable ) {

        super( throwable );
    }
}
