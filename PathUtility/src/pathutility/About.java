package pathutility;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class About extends JDialog {
    
    private static final long serialVersionUID = 6434876696397741862L;

    public About( Component parent ) {
        setTitle( "About" );
        setSize( 250, 130 );
        setLocationRelativeTo( parent );
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        setResizable( false );
        setModal( true );
        
        getContentPane( ).setLayout( null );
        
        JLabel pathLabel      = new JLabel( "Path Utility" );
        JLabel authorLabel    = new JLabel( "Coded by Thomas Pranic" );
        JLabel libLabel       = new JLabel( "Using the Java Native Access libraries" );
        JLabel copyrightLabel = new JLabel( "Copyright 2015" );
        
        pathLabel.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        pathLabel.setHorizontalAlignment( SwingConstants.CENTER );
        pathLabel.setBounds( 0, 20, 245, 15 );
        
        authorLabel.setHorizontalAlignment( SwingConstants.CENTER );
        authorLabel.setBounds( 0, 35, 245, 15 );
        
        libLabel.setHorizontalAlignment( SwingConstants.CENTER );
        libLabel.setBounds( 0, 50, 245, 15 );
        
        copyrightLabel.setHorizontalAlignment( SwingConstants.CENTER );
        copyrightLabel.setBounds( 0, 65, 245, 15 );
        
        getContentPane( ).add( pathLabel );
        getContentPane( ).add( authorLabel );
        getContentPane( ).add( libLabel );
        getContentPane( ).add( copyrightLabel );
    }
}