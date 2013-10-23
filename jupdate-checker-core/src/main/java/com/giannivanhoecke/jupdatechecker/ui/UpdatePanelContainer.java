/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.ui;

import com.giannivanhoecke.jupdatechecker.domain.Version;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class UpdatePanelContainer extends JFrame {

    private static final Dimension IMAGE_SIZE = new Dimension( 100, 64 );

    private String appName;
    private Version currentVersion;
    private String releaseNotesURL;
    private String downloadPageURL;
    private String autoUpdaterURL;

    private JLabel lblImageIcon;

    public UpdatePanelContainer( String title, String appName, Version currentVersion,
                                 String releaseNotesURL, String downloadPageURL, String autoUpdaterURL ) {

        super( title );

        this.appName = appName;
        this.currentVersion = currentVersion;
        this.releaseNotesURL = releaseNotesURL;
        this.downloadPageURL = downloadPageURL;
        this.autoUpdaterURL = autoUpdaterURL;

        this.initLayout();
    }

    private void initLayout() {

        this.setLayout( new BorderLayout( 0, 0 ) );

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout( new BorderLayout( 0, 0 ) );
        panelContainer.setBorder( BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder( 20, 20, 20, 20 ), null ) );

        this.add( panelContainer, BorderLayout.CENTER );

        //Image icon
        lblImageIcon = new JLabel();
        try {

            lblImageIcon.setIcon( new ImageIcon( ImageIO.read( getClass().getResource( "/updateicon.png" ) ) ) );
        }
        catch( IOException e ) {

            //No icon
            e.printStackTrace();
        }
        lblImageIcon.setMaximumSize( IMAGE_SIZE );
        lblImageIcon.setMinimumSize( IMAGE_SIZE );
        lblImageIcon.setPreferredSize( IMAGE_SIZE );
        lblImageIcon.setText( "" );
        lblImageIcon.setVerticalAlignment( 1 );
        lblImageIcon.setVerticalTextPosition( 1 );
        panelContainer.add( lblImageIcon, BorderLayout.WEST );

        //Text panel
        TextPanel textPanel = new TextPanel( this, this.appName, this.currentVersion, this.releaseNotesURL,
                this.downloadPageURL, this.autoUpdaterURL );

        panelContainer.add( textPanel, BorderLayout.CENTER );
    }

    public void setImage( String iconImagePath ) {

        try {

            this.setIconImage( new ImageIcon( ImageIO.read( getClass().getResource( iconImagePath ) ) ).getImage() );
            this.lblImageIcon.setIcon( new ImageIcon( ImageIO.read( getClass().getResource( iconImagePath ) ) ) );
        }
        catch( IOException e ) {

            //No icon
            e.printStackTrace();
        }

        this.validate();
    }
}
