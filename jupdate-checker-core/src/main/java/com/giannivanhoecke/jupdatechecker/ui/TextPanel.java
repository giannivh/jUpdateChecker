/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.ui;

import com.giannivanhoecke.jupdatechecker.domain.FetchResult;
import com.giannivanhoecke.jupdatechecker.domain.Version;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class TextPanel extends JPanel {

    private String appName;
    private Version currentVersion;
    private String releaseNotesURL;
    private String downloadPageURL;
    private String autoUpdaterURL;

    private JLabel lblHeader;
    private JScrollPane scrollPane;
    private JTextPane txtReleaseNotes;

    public TextPanel( JFrame frame, String appName, Version currentVersion, String releaseNotesURL,
                      String downloadPageURL, String autoUpdaterURL ) {

        this.appName = appName;
        this.currentVersion = currentVersion;
        this.releaseNotesURL = releaseNotesURL;
        this.downloadPageURL = downloadPageURL;
        this.autoUpdaterURL = autoUpdaterURL;

        this.initLayout( frame );
        this.generateHeader();
        this.setReleaseNotes();
    }

    private void initLayout( JFrame frame ) {

        this.setLayout( new BorderLayout( 0, 0 ) );

        lblHeader = new JLabel();
        lblHeader.setText( "A new version of xxx is available" );
        this.add( lblHeader, BorderLayout.NORTH );

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        this.add( scrollPane, BorderLayout.CENTER );

        txtReleaseNotes = new JTextPane();
        txtReleaseNotes.setDragEnabled( false );
        txtReleaseNotes.setEditable( false );
        txtReleaseNotes.setText( "RELEASE NOTES HERE" );

        scrollPane.setViewportView( txtReleaseNotes );

        //Buttons
        ButtonsPanel buttonsPanel = new ButtonsPanel( frame, this.downloadPageURL, this.autoUpdaterURL );
        this.add( buttonsPanel, BorderLayout.SOUTH );
    }

    private void generateHeader() {

        String info = "<html>";
        info += "<p><strong>A new version of " + appName + " is available!</strong></p>";
        info += "<br />";
        info += "<p><font size=\"-2\">";
        info += appName + " " + FetchResult.INSTANCE.getVersion().toString() +
                " is now available&mdash;you have ";
        info += currentVersion + ". Would you like to download it now?";
        info += "</font></p>";
        info += "<br /><strong>Release notes:</strong><br /><br />";
        info += "</html>";

        lblHeader.setText( info );
    }

    private void setReleaseNotes() {

        try {

            txtReleaseNotes.setContentType( "text/html" );
            txtReleaseNotes.setPage( releaseNotesURL );
        }
        catch( IOException e ) {

            txtReleaseNotes.setText( "Could not download release notes..." );
        }

        txtReleaseNotes.setCaretPosition( 0 );
    }

}
