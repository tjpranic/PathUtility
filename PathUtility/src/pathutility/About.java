package pathutility;

import java.awt.Component;

import javax.swing.JDialog;

public class About extends JDialog {
	
	private static final long serialVersionUID = 6434876696397741862L;

	public About( Component parent ) {
		setTitle( "About" );
		setSize( 275, 225 );
		setLocationRelativeTo( parent );
		setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		setResizable( false );
		setModal( true );
	}
}