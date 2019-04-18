package code.Model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseMySQL implements DatabaseAPI {


    public void authenticate(String user, String password) {

        dbu = new DBUtils("jdbc:mysql://localhost:3306/spootify?serverTimezone=EST5EDT", user, password);
    }


    public java.sql.Date todayDateSQL() {
        // get today's date in the correct format yo
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }

    public DatabaseMySQL() {

        this.allTheSongs = new HashMap<>();

        // Authenticate your access to the server.
        this.authenticate("murach", "grendel");

        // what's today's date?!?!?
        java.sql.Date today = this.todayDateSQL();

        // get all them songs
        List<Song> all = this.existingSongs(today);

        // put all them songs in a hashmap
        for (Song s: all) {
            allTheSongs.putIfAbsent(s, s.getSongValue());
        }
    }

    public Integer whatIsCurrentPrice(Song s) {

        this.getOrInsertSong(s);

        return this.getCurrentPrice(s.getSpotifyID());
    }

    /////////////////////////////////// ////////////////////////////////
    /////////////////////////////////// ////////////////////////////////

    // For demonstration purposes. Better would be a constructor that takes a file path
    // and loads parameters dynamically.
    DBUtils dbu;

    // hashmap of song objects to integers (representing number of shares
    private HashMap<Song, Integer> allTheSongs;

    /**
     * Find existing Songs on the given date
     * @param date Required date.  Better would be to accept null as "latest"
     * @return A list of Songs, those stored in the chart on the given date
     */
    public List<Song> existingSongs(Date date) {

        // TODO - for this to work, need to set up the mysql-connector dependency in Project Structure
        // for Reference: https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea

        List<Song> mylist = new ArrayList<>();

        try {
            Connection conn = dbu.getConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM song");

            while (rs.next()) {
                // TODO - THIS IS WHAT TO LOOK AT, figure out how to retun this shit
                String spotify_id = rs.getString("spotify_id");
                String title = rs.getString("title");
                String artist_id = rs.getString("artist_id");
                String album_id = rs.getString("album_id");
                String song_value = rs.getString("song_value");

                // String spotifyID, String title, String artistID,
                //
                //
                //
                //
                // int rank, String albumID)

                Song newSong = new Song(
                        rs.getString("spotify_id"),
                        rs.getString("title"),
                        rs.getString("artist_id"),
                        rs.getInt("song_value"), // rank
                        rs.getString("song_value"));
                System.out.println("hello");

                // add stuff
                mylist.add(newSong);

                // print stuff
                System.out.println("adding: " + newSong.toString());
            }

            rs.close();
            st.close();

        } catch(SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // could maybe do finally to close connection? idk
        return mylist;
    }

    public void getConnection() {
        // stub TODO stuff stub

    }
    /**
     * Insert The Songs for This Day
     * @param loSongYo Song
     */
    public void insertTodaySongs(List<Song> loSongYo) {
        // TODO stuff pls stub
        System.out.println("Oops tried to do a dummy method");
    }

    /**
     * Insert one user
     * @param u
     * @return
     */
    public int insertUser(User u) {
        // TODO stuff pls stub
        return dbu.getOrInsertTerm(
                "user",
                "user_id",
                "purchasing_power",
                String.valueOf(u.getPurchasing_power()));
    }

    /**
     * Register a new day of charts
     * @param date The date of the chart to be input
     * @return The newly created chart ID or -1 if chart already exists at that date
     */
    public int registerNewDayOfCharts(String date) {

        // TODO could do something like below line
        // dbu.getOrInsertTerm("")

        // TODO stuff pls stub
        return 1;

    }


    /**
     * Get or insert on album
     * @param albumName The albumName
     * @return ID of a new or existing album.
     */
    public int getOrInsertAlbumID(String albumName) {
        // TODO stuff pls stub
        return dbu.getOrInsertTerm("album", "album_id", "album_name", albumName);
    }


    public void refreshSongs(List<Song> listSongs, java.sql.Date today) {
        // not most efficient when inserting many records
        // for Doctor d: drlist)
        //      insertDoctor(d);

        String sql = "Insert into song " +
                "(spotify_id, title, artist_id, album_id, song_value)" +
                "Values (?,?,?,?,?)";


        try {
            // db.getConnection?

            // TODO change to authenticate stuff?
            this.existingSongs(today);
            this.clearSongData();

            DBUtils utl = new DBUtils("stuff", "morestuff", "pword");


            Connection con = utl.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);

//            for (Song s : listSongs) {
//                pstmt.setString(1, s.getLastName());
//                pstmt.setString(2, s.getFirstName());
//                pstmt.setBoolean(3, s.isAcceptingNewPatients());
//                pstmt.setInt(4, getOrInsertSpecialty(s.getSpecialty()));
//                pstmt.execute();
//            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String toString() {
        return allTheSongs.toString();
    }


    @Override
    public int getOrInsertSong(Song s) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql =  "INSERT INTO song (spotify_id,title,artist_id,album_id, song_value) VALUES" +
                "('" + s.getSpotifyID() + "','" + s.getTitle() + "','" + s.getArtistID() +
                "','" + s.getAlbumID() +"','" + s.getSongValue() + "')";

        return dbu.insertOneRecord(sql);
    }


//
//     /**
//     //     * Register a patient - fetch patient_id that was created
//     //     * @param p The Patient
//     //     * @return patient_id
//     //     */
//    public int registerPatientMethod2(Patient p)
//    {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String sql = "INSERT INTO patient (lastname,firstname,sex,dob) VALUES" +
//                "('"+p.getLastName()+"','"+p.getFirstName()+"','"+p.getSex()+"','"+sdf.format(p.getDob())+"')";
//        int key = -1;
//        try {
//
//            // get connection and initialize statement
//            Connection con = dbu.getConnection();
//            Statement stmt = con.createStatement();
//
//            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
//
//            // extract auto-incremented ID
//            ResultSet rs = stmt.getGeneratedKeys();
//            if (rs.next()) key = rs.getInt(1);
//
//            // Cleanup
//            rs.close();
//            stmt.close();
//
//        } catch (SQLException e) {
//            System.err.println("ERROR: Could not insert record: "+sql);
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return key;
//    }
//
//
//    /**
//     * Register a new patient
//     * @param p The patient
//     * @return The newly created patient ID or -1 if patient already exists
//     */
//    public int registerPatient(Patient p)
//    {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String sql = "INSERT INTO patient (lastname,firstname,sex,dob) VALUES" +
//                    "('"+p.getLastName()+"','"+p.getFirstName()+"','"+p.getSex()+"','"+sdf.format(p.getDob())+"')";
//        return dbu.insertOneRecord(sql);
//    }

    @Override
    public int getCurrentPrice(String spotifyID) {
        return 0;
    }

    @Override
    public int insertSong(Integer rank) {
        return 0;
    }

    @Override
    public void clearSongData() {

    }

    ///////////////////////////////////////////////////////////////////////

    /**
     * Close the connection when application finishes
     */
    public void closeConnection() { dbu.closeConnection(); }

    @Override
    public String song_album(Song s) {
        return null;
    }

    @Override
    public String song_artist(Song s) {
        return null;
    }

//    /**
//     * Register a patient - no recovery of newly created patient_id
//     * @param p The patient
//     */
//    public void registerPatientMethod1(Patient p)
//    {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String sql = "INSERT INTO patient (lastname,firstname,sex,dob) VALUES" +
//                "('"+p.getLastName()+"','"+p.getFirstName()+"','"+p.getSex()+"','"+sdf.format(p.getDob())+"')";
//        int key = -1;
//        try {
//
//            // get connection and initialize statement
//            Connection con = dbu.getConnection();
//            Statement stmt = con.createStatement();
//            stmt.executeUpdate(sql);
//
//            // Cleanup
//            stmt.close();
//
//        } catch (SQLException e) {
//            System.err.println("ERROR: Could not insert record: "+sql);
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }
//

//
//
//    /**
//     * Get or insert on specialty term
//     * @param specialty The specialty
//     * @return ID of a new or existing specialty.
//     */
//    public int getOrInsertSpecialty(String specialty)
//    {
//      return dbu.getOrInsertTerm("specialty", "specialty_id", "specialty", specialty);
//    }
//
//
//    /**
//     * Insert one doctor
//     * @param d
//     * @return
//     */
//    public int insertDoctor(Doctor d)
//    {
//
//        // Lookup specialty
//        int sid = getOrInsertSpecialty(d.getSpecialty());
//
//        // build and run query
//        String sql = "INSERT INTO doctor (lastname,firstname,new_patients,specialty_id) VALUES" +
//                    "('"+d.getLastName()+"','"+d.getFirstName()+"',"+d.isAcceptingNewPatients()+","+sid+")";
//
//            // Return new doctor ID
//        return dbu.insertOneRecord(sql);
//
//    }
//
//
//    /**
//     * Insert many doctors.  Demonstrates using prepared statements
//     * @param drlist A List of Doctor objects
//     */
//    public void insertDoctors(List<Doctor> drlist)
//    {
//        // This seems simplest but is not really the most efficient when inserting many records
//
//        // for (Doctor d : drlist)
//        //    insertDoctor(d);
//
//
//        String sql = "INSERT INTO doctor (lastname,firstname,new_patients,specialty_id) VALUES (?,?,?,?)";
//        try {
//            Connection con = dbu.getConnection();
//            PreparedStatement pstmt = con.prepareStatement(sql);
//
//            for (Doctor d : drlist) {
//                pstmt.setString(1, d.getLastName());
//                pstmt.setString(2, d.getFirstName());
//                pstmt.setBoolean(3, d.isAcceptingNewPatients());
//                pstmt.setInt(4, getOrInsertSpecialty(d.getSpecialty()));
//                pstmt.execute();
//            }
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * Find doctors accepting new patients for a given patient
//     * @param specialty Required specialty.  Better would be to accept null as "any"
//     * @return A list of doctors
//     */
//    public List<Doctor> acceptingNewPatients(String specialty)
//    {
//        String sql = "select doctor_id, lastname, firstname, new_patients, specialty "+
//                     "from doctor join specialty using (specialty_id) "+
//                     "where new_patients = 1 "+
//                     "and specialty like '"+specialty.toUpperCase()+"'";
//        List<Doctor> doctors = new ArrayList<Doctor>();
//
//        try {
//            // get connection and initialize statement
//            Connection con = dbu.getConnection();
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next() != false)
//                doctors.add(new Doctor(rs.getInt("doctor_id"), rs.getString("lastname"), rs.getString("firstname"), rs.getBoolean("new_patients"), rs.getString("specialty")));
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//        return doctors;
//    }
}
