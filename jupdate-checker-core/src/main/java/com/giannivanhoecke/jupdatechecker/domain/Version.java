/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.domain;

import com.giannivanhoecke.jupdatechecker.exception.InvalidVersionException;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class Version implements Comparable<Version> {

    private int major;
    private int minor;
    private int patch;

    /**
     * Creates a new version.
     *
     * @param major The major version indicator.
     * @param minor The minor version indicator.
     * @param patch The patch version indicator.
     */
    public Version( int major, int minor, int patch ) {

        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Creates a new version.
     *
     * @param major The major version indicator.
     * @param minor The minor version indicator.
     */
    public Version( int major, int minor ) {

        this.major = major;
        this.minor = minor;
        this.patch = -1;
    }

    /**
     * @return The human readable representation of the version.
     */
    @Override
    public String toString() {

        if (-1 == patch)
            return String.format( "%d.%d", major, minor );

        return String.format( "%d.%d-p%d", major, minor, patch );
    }

    /**
     * Creates a Version object of a String representation.
     *
     * @param versionString The version as string. Pattern: [major].[minor]-p[patch]
     * @return The Version object
     * @throws InvalidVersionException when parsing the string fails
     */
    public static Version getVersion( String versionString )
            throws InvalidVersionException {

        int major;
        int minor;
        int patch;

        if ( !versionString.contains( "." ) ) {

            throw new InvalidVersionException(
                    String.format( "Illegal version \"%s\". Pattern should be [major].[minor]{-p[patch]}" ,
                            versionString ) );
        }

        try {

            if ( versionString.contains("-p") ) {

                minor = Integer.parseInt( versionString.substring( versionString.indexOf( "." ) + 1,
                        versionString.indexOf( "-p" ) ) );
                patch = Integer.parseInt( versionString.substring( versionString.indexOf( "-p" ) + 2 ) );

            } else {

                minor = Integer.parseInt( versionString.substring( versionString.indexOf( "." ) + 1 ) );
                patch = -1;

            }

            major = Integer.parseInt( versionString.substring( 0, versionString.indexOf( "." ) ) );

            return new Version( major, minor, patch );
        }
        catch( NumberFormatException e ) {

            throw new InvalidVersionException(
                    String.format( "Illegal version \"%s\". Pattern should be [major].[minor]{-p[patch]}" ,
                            versionString ) );
        }
    }

    @Override
    public boolean equals( Object obj ) {

        if ( obj == this )
            return true;

        if ( !( obj instanceof Version ) )
            return false;

        Version version = (Version) obj;
        return this.compareTo( version ) == 0;
    }

    public int compareTo( Version v ) {

        if (major < v.major)
            return -1;
        if (major > v.major)
            return 1;

        if (minor < v.minor)
            return -1;
        if (minor > v.minor)
            return 1;

        if (patch < v.patch)
            return -1;
        if (patch > v.patch)
            return 1;

        return 0;
    }
}