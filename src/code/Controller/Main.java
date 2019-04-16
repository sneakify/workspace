package code.Controller;

import code.Model.*;
import code.View.MainFrame;

import java.sql.Date;
import java.util.Calendar;
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

  private static DatabaseAPI api = new DatabaseMySQL();


  public static void main(String[] args) {
    System.out.println("The Program Started Running"); // (i.e. show the on button worked)

    // Authenticate your access to the server.
    api.authenticate("murach", "grendel");
    System.out.println("\n\nNo Error Yet? Congratulations, you connected to the database:");

    // TODO - uncomment and then run
    // one way to get the current date on the machine running the Java
//    long ms = System.currentTimeMillis();
//    Date date = new Date(ms);
//    List<Song> songList = api.existingSongs(Date.valueOf(date.toString()));
//
//    for (Song s : songList) {
//      System.out.println(s.toString());
//    }

    //Song(1, "Old Town Road - Remix", 1, 1, 293),

    //        new Song("")

    Song mySong = new Song("Old Town Road - Remix");

    System.out.println(api.song_album(mySong));
    System.out.println(api.song_artist(mySong));


    // TODO instantiate java.Model and make it relate to view somehow, pass into view probably
    ModelSQLInterface mod = new MyModel(api);



    // Bradley - I'M NOT GONNA MESS WITH VIEW FILES, but should pass in the 'mod' MODELSQLINTERFACE
    //           object into the View, which really should be 'View mf = new MainFrame(mod)'
    // Start the java.View
    MainFrame mf = new MainFrame();
    // TODO ==== View mf = new MainFrame(mod);

    // Close connection with the MySQL Database
    api.closeConnection();
  }
}

///// EXAMPLES FROM DOCTORPATIENT
/*
		System.out.println("\n\nAvailable oncologists");
		List<Doctor> doctors = api.acceptingNewPatients("oncology");
		for (Doctor d : doctors)
			System.out.println(d.toString());

		*/

// Insert a test patient
// Exception thrown if patient already exists

//SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//System.out.println("\n\nRegistering a patient");
//Patient p = new Patient("Gates", "Bill", 'M', sdf.parse("10-28-1955"));
//api.registerPatientMethod1(p);
//int pid = api.registerPatientMethod2(p);
//int pid = api.registerPatient(p);

//System.out.println("Patient registered.");
//System.out.println("Newly inserted patient: "+pid);




// Test adding specialities
// Note the repeats!
//System.out.println("Adding specialties");
//int sid1 = api.getOrInsertSpecialty("oncology");
//int sid2 = api.getOrInsertSpecialty("cardiology");
//int sid3 = api.getOrInsertSpecialty("pediatrics");
//int sid4 = api.getOrInsertSpecialty("ent");
//int sid5 = api.getOrInsertSpecialty("cardiology");
//System.out.println("Specialty IDs: "+sid1+" "+sid2+" "+sid3+" "+sid4+" "+sid5);

// add some doctors to a list collection and then add them all to the database
/*
		List<Doctor> drlist = new ArrayList<Doctor>();
		drlist.add(new Doctor("House", "Gregory", true, "diagnostics"));
		drlist.add(new Doctor("McCoy", "Leonard", true, "surgeon"));
		drlist.add(new Doctor("Crusher", "Beverly", false, "oncology"));
		drlist.add(new Doctor("Smith", "Joe", true, "oncology"));
		drlist.add(new Doctor("Johnson", "Liz", true, "oncology"));

		api.insertDoctors(drlist);

		System.out.println("\n\nAvailable oncologists");
		List<Doctor> doctors = api.acceptingNewPatients("oncology");
		for (Doctor d : doctors)
			System.out.println(d.toString());
*/