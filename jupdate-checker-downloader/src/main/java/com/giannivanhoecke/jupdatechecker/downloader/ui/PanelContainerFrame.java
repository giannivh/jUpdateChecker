/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.downloader.ui;

import com.giannivanhoecke.jupdatechecker.downloader.domain.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class PanelContainerFrame extends JFrame implements UI {

    private JLabel lblStatus;
    private JProgressBar progressBar;
    private JButton cmdInstallAndRestart;

    public PanelContainerFrame() {

        super( "Software Update..." );

        Dimension dimension = new Dimension( 300, 100 );

        setSize( dimension );
        setMinimumSize( dimension);
        setPreferredSize( dimension );
        setMaximumSize( dimension );
        setResizable( false );
        setLocationRelativeTo( null );
        setVisible( true );
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

        setupUI();
    }

    private void setupUI() {

        setLayout( new BorderLayout() );

        int eb = 5;
        ((JPanel) getContentPane()).setBorder( BorderFactory.createEmptyBorder( eb, eb, eb, eb ) );

        lblStatus = new JLabel();
        lblStatus.setText( "Preparing..." );
        lblStatus.setToolTipText( "Preparing..." );
        add( lblStatus, BorderLayout.NORTH );

        progressBar = new JProgressBar();
        progressBar.setIndeterminate( true );
        add(progressBar, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new BorderLayout() );
        add( buttonPanel, BorderLayout.SOUTH );

        cmdInstallAndRestart = new JButton();
        cmdInstallAndRestart.setText("Restart");
        cmdInstallAndRestart.setEnabled( false );
        cmdInstallAndRestart.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent e ) {

                buttonClicked();
            }
        });

        buttonPanel.add( cmdInstallAndRestart, BorderLayout.EAST );

        pack();
    }

    private void buttonClicked() {

        if( cmdInstallAndRestart.getText().equals( "Exit" ) ) {

            System.exit( 0 );
        }
        else {

            Controller.INSTANCE.install();
        }
    }

    @Override
    public void setStatus( final String status, final int progress, final boolean fileSizeIsUnknown ) {

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {

                lblStatus.setText( status );
                lblStatus.setToolTipText( status );
                progressBar.setIndeterminate( fileSizeIsUnknown );
                progressBar.setValue( progress );
                if( fileSizeIsUnknown )
                    progressBar.setToolTipText( null );
                else
                    progressBar.setToolTipText(String.format("%d %%", progress));
            }
        } );
    }

    @Override
    public void completed( final String message ) {

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {

                lblStatus.setText( message );
                lblStatus.setToolTipText( message );
                progressBar.setIndeterminate( false );
                progressBar.setValue( 100 );
                cmdInstallAndRestart.setEnabled( true );
            }
        } );
    }

    @Override
    public void errorOccurred( final String message ) {

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {

                lblStatus.setText( message );
                lblStatus.setToolTipText( message );
                progressBar.setIndeterminate( false );
                progressBar.setValue(0);
                progressBar.setEnabled( false );
                cmdInstallAndRestart.setText( "Exit" );
                cmdInstallAndRestart.setEnabled( true );
            }
        } );
    }

    @Override
    public void sendMessage( final String message, final boolean isError ) {

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {

                JOptionPane.showMessageDialog( null, message, getTitle(), JOptionPane.ERROR_MESSAGE );
            }
        } );
    }
}
