/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.ui;

import com.giannivanhoecke.jupdatechecker.io.AutoUpdater;
import com.giannivanhoecke.jupdatechecker.io.OpenBrowser;
import com.giannivanhoecke.jupdatechecker.util.AppUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class ButtonsPanel extends JPanel {

    private static final Dimension BUTTON_SIZE = new Dimension( 140, 35 );

    public ButtonsPanel( final JFrame frame, final String downloadPageURL, final String autoUpdaterURL ) {

        this.initLayout( frame, downloadPageURL, autoUpdaterURL );
    }

    private void initLayout( final JFrame frame, final String downloadPageURL, final String autoUpdaterURL ) {

        this.setLayout( new FlowLayout( FlowLayout.RIGHT, 5, 5 ) );
        this.setMaximumSize( new Dimension( 280, 28 ) );

        JButton cmdRemindMeLater = new JButton();
        cmdRemindMeLater.setMaximumSize( BUTTON_SIZE );
        cmdRemindMeLater.setMinimumSize( BUTTON_SIZE );
        cmdRemindMeLater.setPreferredSize( BUTTON_SIZE );
        cmdRemindMeLater.setText( "Remind Me Later" );
        this.add( cmdRemindMeLater );
        cmdRemindMeLater.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                frame.dispose();
            }
        });

        JButton cmdDownloadUpdate = new JButton();
        cmdDownloadUpdate.setMaximumSize( BUTTON_SIZE );
        cmdDownloadUpdate.setMinimumSize( BUTTON_SIZE );
        cmdDownloadUpdate.setPreferredSize( BUTTON_SIZE );
        cmdDownloadUpdate.setText( "Download Update" );
        this.add( cmdDownloadUpdate );
        cmdDownloadUpdate.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent actionEvent ) {

                if( autoUpdaterURL == null ) {

                    OpenBrowser.openURL( downloadPageURL );
                    frame.dispose();
                }
                else {

                    AutoUpdater.start( autoUpdaterURL, AppUtils.getApplicationFile() );
                }
            }
        });
    }
}
