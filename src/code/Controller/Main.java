package code.Controller;

import code.Model.*;
import code.View.BuyPanel;
import code.View.MainFrame;
import java.util.List;


/**
 * This program exercises the DPDatabaseAPI (MySQL implementation).
 * Notice that nothing other than the instantiation of the API shows us that
 * the underlying database is Relational, or MySQL.

 */
public class Main {

  /*
	There is much to do:
	- implement javascript capabilities of api calling, doing http requests and going through responses
	- make functions that insert and do the things we want getting done
	- Make it so a daily event in mysql can reach for the top charts in this java program which is running somewhere.
	- Make it so this gets only the data we need to store, but does it in clever ways
	- Make sure we avoid stuff like the fan trap for database design
	 */

  // this is our driver, our main, the one which runs

  public static void main(String[] args) {

    // Let's the log-lookers know that "yes, we have liftoff"
    System.out.println("The Program Started Running");


    // TODO - make the functions that pull the user from the database info and such?
    //User user = new User(33,"Bradley Fargo", "breadfergy","me@gmail.com", "totallyrad", "1997-01-02", 100);



    // instantiate DatabaseAPI object (sort of like a bootleg model),
    // which in its constructor calls authenticate
    Model api = new Model(1);
    // TODO - NOTE, this calls the constructor of this class, which runs the "authenticate" function,

    // this exists in our database
    Song s = new Song("1", "Old Town Road - Remix", "1", 293, "1");

    // Start the java.View
    MainFrame mf = new MainFrame(api);
    BuyPanel bp = new BuyPanel(mf, s);

    // Close connection with the MySQL Database
    //api.closeConnection();
  }
}