/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.downloader;

import com.giannivanhoecke.jupdatechecker.downloader.domain.Controller;
import com.giannivanhoecke.jupdatechecker.downloader.ui.PanelContainerFrame;
import com.giannivanhoecke.jupdatechecker.downloader.ui.UI;

import javax.swing.*;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class JUpdateCheckerDownloader {

    public static void main( String[] args ) {

        if( args.length != 2 ) {

            System.err.println( "Invalid application call." );
            System.exit( 1 );
        }

        String configLink  = args[0];
        String appFileName = args[1];

        Controller.INSTANCE.setConfigLink( configLink );
        Controller.INSTANCE.setAppFileName( appFileName );

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {

                UI ui = new PanelContainerFrame();

                Controller.INSTANCE.addObserver( ui );
                Controller.INSTANCE.start();
            }
        } );
    }
}
