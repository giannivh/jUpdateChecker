/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.downloader.domain;

import com.giannivanhoecke.jupdatechecker.downloader.ui.UI;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public enum Controller {

    INSTANCE;

    private UI ui;

    private String configLink;
    private String appFileName;

    private File tempDownloadedFile;
    private int fileSize;
    private int alreadyDownloaded;

    Controller() {

        ui = null;
        tempDownloadedFile = null;
        fileSize = 0;
        alreadyDownloaded = 0;
    }

    //
    // Methods
    //

    public void addObserver( UI ui ) {

        this.ui = ui;
    }

    public void start() {

        if( this.ui == null ) {

            System.err.println( "Runtime error." );
            System.exit( 2 );
        }

        new Thread( new DownloadWorker() ).start();
    }

    public void install() {

        //Check if all is writable...
        if( new File( getAppFileName() ).canWrite() ) {

            //Delete the old version.
            if( new File( getAppFileName() ).delete() ) {

                //Move the file to the location of the app.
                if( tempDownloadedFile.renameTo( new File( getAppFileName() ) ) ) {

                    //OK, relaunch app.
                    if( Desktop.isDesktopSupported() ) {

                        try {

                            File fileToOpen;

                            if( getAppFileName().contains( ".app/" ) ) {

                                //Mac
                                fileToOpen = new File(
                                        getAppFileName().substring( 0, getAppFileName().indexOf( ".app/" ) + 4 ) );
                            } else {

                                //Other OS
                                fileToOpen = new File( getAppFileName() );
                            }

                            Desktop.getDesktop().open( fileToOpen );
                            System.exit( 0 );

                        } catch (IOException e) {

                            this.ui.sendMessage( "Can't restart the application, please start it manually.", true );
                        }
                    } else {

                        this.ui.sendMessage( "Can't restart the application, please start it manually.", true );
                    }
                } else {

                    this.ui.sendMessage( "Can't update the application. The updater has insufficient rights.", true );
                }
            } else {

                this.ui.sendMessage( "Can't update the application. The updater has insufficient rights.", true );
            }
        } else {

            this.ui.sendMessage( "Can't update the application. The updater has insufficient rights.", true );
        }
    }

    public void update( String message, int progress, boolean fileSizeIsUnknown ) {

        this.ui.setStatus( message, progress, fileSizeIsUnknown );
    }

    public void completed( String message ) {

        this.ui.completed( message );
    }

    public void errorOccurred( String message ) {

        this.ui.errorOccurred( message );
    }

    //
    // Getters & setters
    //

    public String getConfigLink() {

        return configLink;
    }

    public void setConfigLink( String configLink ) {

        this.configLink = configLink;
    }

    public File getTempDownloadedFile() {

        return tempDownloadedFile;
    }

    public void setTempDownloadedFile( File tempDownloadedFile ) {

        this.tempDownloadedFile = tempDownloadedFile;
    }

    public String getAppFileName() {

        return appFileName;
    }

    public void setAppFileName( String appFileName ) {

        this.appFileName = appFileName;
    }

    public int getFileSize() {

        return fileSize;
    }

    public void setFileSize( int fileSize ) {

        this.fileSize = fileSize;
    }

    public int getAlreadyDownloaded() {

        return alreadyDownloaded;
    }

    public void setAlreadyDownloaded( int alreadyDownloaded ) {

        this.alreadyDownloaded = alreadyDownloaded;
    }
}
