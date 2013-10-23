/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker;

import com.giannivanhoecke.jupdatechecker.domain.FetchResult;
import com.giannivanhoecke.jupdatechecker.domain.Version;
import com.giannivanhoecke.jupdatechecker.exception.InvalidVersionException;
import com.giannivanhoecke.jupdatechecker.io.UpdateInfoFetcher;
import com.giannivanhoecke.jupdatechecker.ui.UpdatePanelContainer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 *
 * <p>Class used to check for an update.</p>
 */
public class JUpdateChecker {

    /**
     * The initial dimension used to display the frame.
     */
    public static final Dimension GUI_DIMENSION = new Dimension( 700, 500 );

    private String  title           = "Software Update";
    private String  upToDateMessage = "Your software is up-to-date!";

    private static final String DEFAULT_IMAGE_PATH = "/updateicon.png";

    private String  iconImagePath;
    private String  appName;
    private Version currentVersion;
    private String  newVersionURL;
    private String  releaseNotesURL;
    private String  downloadPageURL;
    private String  autoUpdaterURL;

    /**
     * Intantiates a new object.
     *
     * @param appName the name of your application.
     * @param currentVersion the current (working) version of your application.
     * @param newVersionURL the URL of the textfile containing the new version of your application.
     * @param releaseNotesURL the URL of the textfile containing the release notes. Use an HTML file for markup.
     * @param downloadPageURL the URL of the page where the new version can be downloaded from, if any.
     */
    public JUpdateChecker( String appName, Version currentVersion, String newVersionURL, String releaseNotesURL,
                           String downloadPageURL ) {

        this( appName, currentVersion, newVersionURL, releaseNotesURL, downloadPageURL, null );
    }

    /**
     * Intantiates a new object.
     *
     * @param appName the name of your application.
     * @param currentVersion the current (working) version of your application.
     * @param newVersionURL the URL of the textfile containing the new version of your application.
     * @param releaseNotesURL the URL of the textfile containing the release notes. Use an HTML file for markup.
     * @param downloadPageURL the URL of the page where the new version can be downloaded, if any.
     * @param iconImagePath the path to the image to display when using the GUI. Null for default image.
     */
    public JUpdateChecker( String appName, Version currentVersion, String newVersionURL, String releaseNotesURL,
                           String downloadPageURL, String iconImagePath ) {

        this( appName, currentVersion, newVersionURL, releaseNotesURL, downloadPageURL, iconImagePath, null );
    }

    /**
     * Intantiates a new object.
     *
     * @param appName the name of your application.
     * @param currentVersion the current (working) version of your application.
     * @param newVersionURL the URL of the textfile containing the new version of your application.
     * @param releaseNotesURL the URL of the textfile containing the release notes. Use an HTML file for markup.
     * @param downloadPageURL the URL of the page where the new version can be downloaded, if any.
     * @param iconImagePath the path to the image to display when using the GUI. Null for default image.
     * @param autoUpdaterURL specify a link to a file containing the URL of the latest version of your app to use the
     *                       auto updater feature. Set to null (default) to disable this feature and link to the site
     *                       instead.
     */
    public JUpdateChecker( String appName, Version currentVersion, String newVersionURL, String releaseNotesURL,
                           String downloadPageURL, String iconImagePath, String autoUpdaterURL ) {

        this.appName = appName;
        this.currentVersion = currentVersion;
        this.newVersionURL = newVersionURL;
        this.releaseNotesURL = releaseNotesURL;
        this.downloadPageURL = downloadPageURL;
        this.iconImagePath = iconImagePath == null ? DEFAULT_IMAGE_PATH : iconImagePath;
        this.autoUpdaterURL = autoUpdaterURL;
    }

    /**
     * Starts the update checker and notifies the user.
     *
     * @param silentOnNoUpdate Set to true if nothing should be done when the version is up-to-date.
     * @throws IOException when there isn't a connection available.
     * @throws InvalidVersionException when the downloaded version file has invalid content.
     */
    public void checkForUpdates( boolean silentOnNoUpdate )
            throws IOException, InvalidVersionException {

        //Get all data...
        UpdateInfoFetcher.fetch( this.newVersionURL );

        //Check if new version is available...
        if( FetchResult.INSTANCE.getVersion().equals( this.currentVersion ) ) {

            if( !silentOnNoUpdate ) {

                //No update available
                JOptionPane.showMessageDialog( null, this.upToDateMessage, this.title,
                        JOptionPane.INFORMATION_MESSAGE );
            }
        }
        else {

            //Show the output using a graphical user interface
            SwingUtilities.invokeLater( new Runnable() {

                public void run() {

                    UpdatePanelContainer updatePanelContainer =
                            new UpdatePanelContainer( title, appName, currentVersion, releaseNotesURL,
                                    downloadPageURL, autoUpdaterURL );
                    updatePanelContainer.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

                    updatePanelContainer.setImage( iconImagePath );

                    updatePanelContainer.pack();

                    updatePanelContainer.setMinimumSize( GUI_DIMENSION );
                    updatePanelContainer.setSize( GUI_DIMENSION );
                    updatePanelContainer.setLocationRelativeTo( null );
                    updatePanelContainer.setVisible( true );
                }
            });
        }
    }

    //
    // GETTERS AND SETTER
    //

    /**
     * @return the name of your application.
     */
    public String getAppName() {

        return appName;
    }

    /**
     * @param appName the name of your application.
     */
    public void setAppName( String appName ) {

        this.appName = appName;
    }

    /**
     * @return the current (working) version of your application.
     */
    public Version getCurrentVersion() {

        return currentVersion;
    }

    /**
     * @param currentVersion the current (working) version of your application.
     */
    public void setCurrentVersion( Version currentVersion ) {

        this.currentVersion = currentVersion;
    }

    /**
     * @return the URL of the textfile containing the new version of your application.
     */
    public String getNewVersionURL() {

        return newVersionURL;
    }

    /**
     * @param newVersionURL the URL of the textfile containing the new version of your application.
     */
    public void setNewVersionURL( String newVersionURL ) {

        this.newVersionURL = newVersionURL;
    }

    /**
     * @return the URL of the textfile containing the release notes. Use an HTML file for markup.
     */
    public String getReleaseNotesURL() {

        return releaseNotesURL;
    }

    /**
     * @param releaseNotesURL the URL of the textfile containing the release notes. Use an HTML file for markup.
     */
    public void setReleaseNotesURL( String releaseNotesURL ) {

        this.releaseNotesURL = releaseNotesURL;
    }

    /**
     * @return the URL of the page where the new version can be downloaded, if any.
     */
    public String getDownloadPageURL() {

        return downloadPageURL;
    }

    /**
     * @param downloadPageURL the URL of the page where the new version can be downloaded, if any.
     */
    public void setDownloadPageURL( String downloadPageURL ) {

        this.downloadPageURL = downloadPageURL;
    }

    /**
     * @return the path to the image to display when using the GUI. Null for default image.
     */
    public String getIconImagePath() {

        return iconImagePath;
    }

    /**
     * @param iconImagePath the path to the image to display when using the GUI. Null for default image.
     */
    public void setIconImagePath( String iconImagePath ) {

        this.iconImagePath = iconImagePath;
    }

    /**
     * @return The title of the updater. Printed inside the terminal or shown as the title of the GUI frame.
     */
    public String getTitle() {

        return title;
    }

    /**
     * @param title The title of the updater. Printed inside the terminal or shown as the title of the GUI frame.
     */
    public void setTitle( String title ) {

        this.title = title;
    }

    /**
     * @return the up-to-date message.
     */
    public String getUpToDateMessage() {

        return upToDateMessage;
    }

    /**
     * @param upToDateMessage the up-to-date message.
     */
    public void setUpToDateMessage( String upToDateMessage ) {

        this.upToDateMessage = upToDateMessage;
    }
}
