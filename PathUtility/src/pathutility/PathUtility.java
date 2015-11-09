package pathutility;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingUtilities;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PathUtility extends JFrame {

    private static final long serialVersionUID = -3236441883355684879L;

    private JMenuBar  menuBar;
    private JMenu     pathMenu;
    private JMenu     optionMenu;
    private JMenu     helpMenu;
    private JMenuItem addMenuItem;
    private JMenuItem removeMenuItem;
    private JMenuItem modifyMenuItem;
    private JMenuItem showMenuItem;
    private JMenuItem backupMenuItem;
    private JMenuItem quitMenuItem;
    private JMenuItem aboutMenuItem;
    
    private JPanel                        listPanel;
    private JList<String>              envList;
    private DefaultListModel<String> envListModel;
    
    private JPopupMenu listPopupMenu;
    private JMenuItem  modifyPopupMenuItem;
    private JMenuItem  removePopupMenuItem;
    private JMenuItem  showPopupMenuItem;
    
    public PathUtility( ) {
        setTitle( "PathVar Tool" );
        setSize( 450, 300 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        initalizeMenu( );
        initializeList( );
        initializeLayout( );
    }
    
    private void initalizeMenu( ) {
        menuBar = new JMenuBar( );
                
        pathMenu = new JMenu( "Path" );
        menuBar.add( pathMenu );

        addMenuItem = new JMenuItem( "Add" );
        addMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                addMenuItemClick( event );
            }
        } );
        
        removeMenuItem = new JMenuItem( "Remove" );
        removeMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                removeMenuItemClick( event );
            }
        } );
        
        modifyMenuItem = new JMenuItem( "Modify" );
        modifyMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                modifyMenuItemClick( event );
            }
        } );
        
        showMenuItem = new JMenuItem( "Show in Explorer" );
        showMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                showMenuItemClick( event );
            }
        } );
        
        pathMenu.add( addMenuItem );
        pathMenu.add( removeMenuItem );
        pathMenu.add( modifyMenuItem );
        pathMenu.addSeparator( );
        pathMenu.add( showMenuItem );

        optionMenu = new JMenu( "Options" );
        menuBar.add( optionMenu );
        
        backupMenuItem = new JMenuItem( "Create Backup" );
        backupMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                backupMenuItemClick( event );
            }
        } );
        
        quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                quitMenuItemClick( event );
            }
        } );
        
        optionMenu.add( backupMenuItem );
        optionMenu.addSeparator( );
        optionMenu.add( quitMenuItem );
        
        helpMenu = new JMenu( "Help" );
        menuBar.add( helpMenu );
        
        aboutMenuItem = new JMenuItem( "About" );
        aboutMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                aboutMenuItemClick( event );
            }
        } );
        
        helpMenu.add( aboutMenuItem );
        
        setJMenuBar( menuBar );
    }
    private void initializeList( ) {
        listPanel = new JPanel( );
        listPanel.setLayout( new BorderLayout( ) );

        JScrollPane scrollPane = new JScrollPane( );
        scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        listPanel.add( scrollPane, BorderLayout.CENTER );
        
        listPopupMenu = new JPopupMenu( );
        
        modifyPopupMenuItem = new JMenuItem( "Modify" );
        modifyPopupMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                modifyMenuItemClick( event );
            }
        } );
        
        removePopupMenuItem = new JMenuItem( "Remove" );
        removePopupMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                removeMenuItemClick( event );
            }
        } );
        
        showPopupMenuItem = new JMenuItem( "Show in explorer" );
        showPopupMenuItem.addActionListener( new ActionListener( ) {
            @Override
            public void actionPerformed( ActionEvent event ) {
                showMenuItemClick( event );
            }
        } );
        
        listPopupMenu.add( modifyPopupMenuItem );
        listPopupMenu.add( removePopupMenuItem );
        listPopupMenu.addSeparator( );
        listPopupMenu.add( showPopupMenuItem );
        
        envListModel = new DefaultListModel<>( );
        String pathVar = Advapi32Util.registryGetStringValue(
            WinReg.HKEY_LOCAL_MACHINE,
            "SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
            "Path"
        );
        
        String[] pathVars = pathVar.split( ";" );
        for( int i = 0; i < pathVars.length; ++i ) {
            envListModel.addElement( pathVars[i] );
        }
        
        envList = new JList<String>( envListModel );
        envList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        scrollPane.setViewportView( envList );
        
        envList.addMouseListener( new MouseListener( ) {
            @Override
            public void mouseClicked( MouseEvent event ) {
                if( SwingUtilities.isRightMouseButton( event ) ) {
                    envList.setSelectedIndex( envList.locationToIndex( event.getPoint( ) ) );
                    listPopupMenu.show( event.getComponent( ), event.getX( ), event.getY( ) );
                }
            }
            
            public void mouseEntered  ( MouseEvent event ) { }
            public void mouseExited   ( MouseEvent event ) { }
            public void mousePressed  ( MouseEvent event ) { }
            public void mouseReleased ( MouseEvent event ) { }
        } );
    }
    private void initializeLayout( ) {
        GroupLayout listPanelGL = new GroupLayout( getContentPane( ) );
        listPanelGL.setHorizontalGroup(
            listPanelGL.createParallelGroup( Alignment.LEADING )
                .addGroup( Alignment.TRAILING, listPanelGL.createSequentialGroup( )
                    .addContainerGap( )
                    .addComponent( listPanel, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE )
                    .addGap( 10 ) )
        );
        listPanelGL.setVerticalGroup(
            listPanelGL.createParallelGroup( Alignment.TRAILING )
                .addGroup( Alignment.LEADING, listPanelGL.createSequentialGroup( )
                    .addGap( 10 )
                    .addComponent( listPanel, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE )
                    .addGap( 10 ) )
        );
        getContentPane( ).setLayout( listPanelGL );
    }
    
    private void backupMenuItemClick( ActionEvent event ) {
        JFileChooser fc = new JFileChooser( "C:\\" );
        fc.setFileFilter( new FileNameExtensionFilter( "Text file (.txt)", "txt" ) );
        
        int choice = fc.showSaveDialog( this );
        if( choice == JFileChooser.APPROVE_OPTION ) {
            String filename = "";
            try {
                filename = fc.getSelectedFile( ).getCanonicalPath( );
                if( !filename.endsWith( ".txt" ) ) {
                    filename += "." + ( ( FileNameExtensionFilter )fc.getFileFilter( ) ).getExtensions( )[0];
                }
            }
            catch( Exception e ) {
                JOptionPane.showMessageDialog( this, "Error saving file:\n" + e.getMessage( ) );
            }
            
            try( FileWriter fw = new FileWriter( filename ) ) {
                String newPaths = "";
                for( int i = 0; i < envListModel.size( ); ++i ) {
                    newPaths += envListModel.get( i ) + ";";
                }
                
                fw.write( newPaths.toString( ) );
            }
            catch( Exception e ) {
                JOptionPane.showMessageDialog( this, "Unable to create backup:\n" + e.getMessage( ) );
            }
        }
    }
    private void quitMenuItemClick( ActionEvent event ) {
        System.exit( 0 );
    }
    private void addMenuItemClick( ActionEvent event ) {
        JFileChooser fc = new JFileChooser( "C:\\" );
        fc.setApproveButtonText( "Select" );
        fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        
        int choice = fc.showOpenDialog( this );
        if( choice == JFileChooser.APPROVE_OPTION ) {
            File file = fc.getSelectedFile( );
            envListModel.addElement( file.getPath( ) );
            updatePathVariable( );
        }
    }
    private void removeMenuItemClick( ActionEvent event ) {
        int index = envList.getSelectedIndex( );
        if( index != -1 ) {
            int choice =
                JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to remove: \"" + envList.getSelectedValue( ) + "\"?",
                    "Remove",
                    JOptionPane.YES_NO_OPTION
                );
        
            if( choice == JOptionPane.YES_OPTION ) {
                envListModel.remove( index );
                updatePathVariable( );
            }
        }
        else {
            JOptionPane.showMessageDialog( this, "No path selected" );
        }
    }
    private void modifyMenuItemClick( ActionEvent event ) {
        int index = envList.getSelectedIndex( );
        if( index != -1 ) {
            String newPath = JOptionPane.showInputDialog( this, "Modify path", envList.getSelectedValue( ) );
            if( newPath != null ) {
                envListModel.remove( index );
                envListModel.insertElementAt( newPath, index );
                updatePathVariable( );
            }
        }
        else {
            JOptionPane.showMessageDialog( this, "No path selected" );
        }
    }
    private void showMenuItemClick( ActionEvent event ) {
        int index = envList.getSelectedIndex( );
        if( index != -1 ) {
            try {
                Desktop.getDesktop( ).open( new File( envList.getSelectedValue( ) ) );
            }
            catch( Exception e ) {
                JOptionPane.showMessageDialog( this, "Unable to open file path\n" + e.getMessage( ) );
            }
        }
        else {
            JOptionPane.showMessageDialog( this, "No path selected" );
        }
    }
    private void aboutMenuItemClick( ActionEvent event ) {
        EventQueue.invokeLater( new Runnable( ) {
            @Override
            public void run( ) {
                About a = new About( PathUtility.this );
                a.setVisible( true );
            }
        } );
    }
    
    private void updatePathVariable( ) {
        String newPaths = "";
        for( int i = 0; i < envListModel.size( ); ++i ) {
            newPaths += envListModel.get( i ) + ";";
        }
        
        try {
            Advapi32Util.registrySetStringValue(
                WinReg.HKEY_LOCAL_MACHINE,
                "SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
                "Path",
                newPaths
            );
        }
        catch( Exception e ) {
            JOptionPane.showMessageDialog( this, "Error:\n" + e.getMessage( ) );
        }
    }
}