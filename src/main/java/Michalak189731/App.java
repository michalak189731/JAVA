package Michalak189731;

import DBConnect.DBConnector;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
    	
    	DBConnector connect = new DBConnector();
    	connect.getData();
        System.out.println( "Hello World!" );
        

    }
    public void SayHello(){
    	System.out.println("Hi");
    	System.out.print("Hello");
    	System.out.println("Derp");
    }
}
