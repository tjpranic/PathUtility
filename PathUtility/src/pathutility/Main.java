package pathutility;

import java.awt.EventQueue;

import elevator.Elevator;

public class Main {
    
    public static void main( String[] args ) {
        //NOTE: Elevator re-runs the program if super user is
        //not detected so checking for super user is necessary
        if( Elevator.isSuperUser( ) ) {
            run( args );
        }
        else {
            Elevator.elevate( args );
        }
    }
    public static void run( String[] args ) {
        EventQueue.invokeLater( new Runnable( ) {
            @Override
            public void run( ) {
                PathUtility pu = new PathUtility( );
                pu.setVisible( true );
            }
        } );
    }
}