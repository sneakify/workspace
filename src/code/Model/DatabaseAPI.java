package code.Model;

import java.util.List;

public interface DatabaseAPI {

    /**
     * Insert The Songs for This Day
     * @param loSongYo Song
     */
    public void insertTodaySongs(List<Song> loSongYo);

    /**
     * Find existing Songs on the given date
     * @param date Required date.  Better would be to accept null as "latest"
     * @return A list of Songs, those stored in the chart on the given date
     */
    public List<Song> existingSongs(java.sql.Date date);

    /**
     * Register a new day of charts
     * @param date The date of the chart to be input
     * @return The newly created chart ID or -1 if chart already exists at that date
     */
    public int registerNewDayOfCharts(String date);

    /**
     * Get or insert on album
     * @param albumName The albumName
     * @return ID of a new or existing album.
     */
    public int getOrInsertAlbumID(String albumName);

    /**
     * Insert one user
     * @param u
     * @return
     */
    public int insertUser(User u);

    /////////////////////////////////////////////////////

    /**
     * Set connection settings
     * @param user
     * @param password
     */
    public void authenticate(String user, String password);

    /**
     * Close the connection when application finishes
     */
    public void closeConnection();


    /**
     * The Basic ToString method
     * @return String
     */
    String toString();

    // purchase

    // sell

    // update settings (password, email)

    // fill data for today

}

//    /**
//     * Find doctors accepting new patients for a given patient
//     * @param specialty Required specialty.  Better would be to accept null as "any"
//     * @return A list of doctors
//     */
//    public List<Doctor> acceptingNewPatients(String specialty);
//
//
//    /**
//     * Register a new patient
//     * @param p The patient
//     * @return The newly created patient ID or -1 if patient already exists
//     */
//    public void registerPatientMethod1(Patient p);
//    public int registerPatientMethod2(Patient p);
//    public int registerPatient(Patient p) ;
//
//    /**
//     * Insert one doctor
//     * @param d
//     * @return
//     */
//    public int insertDoctor(Doctor d);
//
//    /**
//     * Insert many doctors.  Demonstrates using prepared statements
//     * @param drlist A List of Doctor objects
//     */
//    public void insertDoctors(List<Doctor> drlist);
//
//    /**
//     * Get or insert on specialty term
//     * @param specialty The specialty
//     * @return ID of a new or existing specialty.
//     */
//    public int getOrInsertSpecialty(String specialty);