package pathutility;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

//TODO:
//add load from text file
//add create backup
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -3236441883355684879L;

	private JMenuBar  menuBar;
	private JMenu     pathMenu;
	private JMenu     optionMenu;
	private JMenu     helpMenu;
	private JMenuItem addMenuItem;
	private JMenuItem removeMenuItem;
	private JMenuItem modifyMenuItem;
	private JMenuItem showMenuItem;
	private JMenuItem loadMenuItem;
	private JMenuItem backupMenuItem;
	private JMenuItem quitMenuItem;
	private JMenuItem aboutMenuItem;
	
	private JPanel 			  	     listPanel;
	private JList<String> 			 envList;
	private DefaultListModel<String> envListModel;
	
	public MainFrame( ) {
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
				openMenuItemClick( event );
			}
		} );
		
		pathMenu.add( addMenuItem );
		pathMenu.add( removeMenuItem );
		pathMenu.add( modifyMenuItem );
		pathMenu.addSeparator( );
		pathMenu.add( showMenuItem );

		optionMenu = new JMenu( "Options" );
		menuBar.add( optionMenu );
		
		loadMenuItem = new JMenuItem( "Load from file" );
		loadMenuItem.addActionListener( new ActionListener( ) {
			@Override
			public void actionPerformed( ActionEvent event ) {
				loadMenuItemClick( event );
			}
		} );
		
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
		
		optionMenu.add( loadMenuItem );
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
		
		envListModel = new DefaultListModel<>( );
		String pathEnvVar = Advapi32Util.registryGetStringValue(
			WinReg.HKEY_LOCAL_MACHINE,
			"SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
			"Path"
		);
        String[] envVars = pathEnvVar.split( ";" );
        
        for( int i = 0; i < envVars.length; ++i ) {
        	envListModel.addElement( envVars[i] );
        }
		
		envList = new JList<String>( envListModel );
		envList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		scrollPane.setViewportView( envList );
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
	
	private void loadMenuItemClick( ActionEvent event ) {
		JFileChooser fc = new JFileChooser( "C:\\" );
        int choice = fc.showOpenDialog( this );
        
        if( choice == JFileChooser.APPROVE_OPTION ) {
            //
        }
	}
	private void backupMenuItemClick( ActionEvent event ) {
		
	}
	private void quitMenuItemClick( ActionEvent event ) {
        System.exit( 0 );
    }
	private void addMenuItemClick( ActionEvent event ) {
        JFileChooser fc = new JFileChooser( "C:\\" );
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
    private void openMenuItemClick( ActionEvent event ) {
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
            	About a = new About( MainFrame.this );
        		a.setVisible( true );
            }
        } );
	}
	
	private void updatePathVariable( ) {
		String newPathVar = "";
		
		for( int i = 0; i < envListModel.size( ); ++i ) {
			newPathVar += envListModel.get( i ) + ";";
        }
		
		try {
			Advapi32Util.registrySetStringValue(
				WinReg.HKEY_LOCAL_MACHINE,
				"SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment",
				"Path",
				newPathVar
			);
		}
		catch( Exception e ) {
			JOptionPane.showMessageDialog( this, "Error\n" + e.getMessage( ) );
			e.printStackTrace( );
		}
	}
}