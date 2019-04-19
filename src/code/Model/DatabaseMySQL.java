package code.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseMySQL implements DatabaseAPI {
    DBUtils dbu;
    private HashMap<Song, Integer> allTheSongs;

    public DatabaseMySQL() {
        // Authenticate your access to the server.
        this.authenticate(Login.usr, Login.pword);

        this.allTheSongs = new HashMap<>();

        // what's today's date?!?!?
        java.sql.Date today = this.todayDateSQL();

        // get all them songs
        List<Song> all = this.existingSongs(today);

        System.out.println("list o all songs" + all.toString()); // TODO - delete this

        // put all them songs in a hashmap
        for (Song s: all) {
            allTheSongs.putIfAbsent(s, s.getSongValue());
        }
    }

    ////////////////////

    public void authenticate(String user, String password) {
        System.out.println(user + "::" + password);
        dbu = new DBUtils("jdbc:mysql://localhost:3306/spootify?serverTimezone=EST5EDT", user, password);
        System.out.println(dbu.connectedHuh());
    }


    public java.sql.Date todayDateSQL() {
        // get today's date in the correct format yo
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
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
     * Find existing Songs on the given date
     * @param date Required date.  Better would be to accept null as "latest"
     * @return A list of Songs, those stored in the chart on the given date
     */
    public List<Song> existingSongs(java.sql.Date date) {

        // TODO - for this to work, need to set up the mysql-connector dependency in Project Structure
        // for Reference: https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea

        List<Song> mylist = new ArrayList<>();

        try {
            Connection conn = dbu.getConnection();
            Statement st = conn.createStatement();
            String sqlGet = "SELECT * FROM song";

            ResultSet rs = st.executeQuery(sqlGet);

            while (rs.next()) {
                String spotify_id = rs.getString("spotify_id");
                String title = rs.getString("title");
                String artist_id = rs.getString("artist_id");
                String album_id = rs.getString("album_id");
                String song_value = rs.getString("song_value");

                Song newSong = new Song(
                        rs.getString("spotify_id"),
                        rs.getString("title"),
                        rs.getString("artist_id"),
                        rs.getInt("song_value"), // rank
                        rs.getString("song_value"));

                // add stuff
                mylist.add(newSong);
            }

            rs.close();
            st.close();

        } catch(SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return mylist;
    }

    /**
     * Insert one user into the database
     * @param u the user to be inserted
     * @return an int representing status of the insert
     */
    public int insertUser(User u) {

        int status;
        try {
            Connection conn = dbu.getConnection();
            Statement st = conn.createStatement();
            String sqlInsert = "INSERT INTO user VALUES" +
                    "(" +
                    u.getUserID() +
                    "," + "'" + u.getFullName() + "'" +
                    "," + "'" + u.getUserName() + "'" +
                    "," + "'" + u.getEmail() + "'" +
                    "," + "'" + u.getPassword() + "'" +
                    "," + "'" + u.getCreatedDate() + "'" +
                    "," + u.getPurchasing_power() + ");";
            System.out.println(sqlInsert);

            status = st.executeUpdate(sqlInsert);

            st.close();

        } catch(SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            status = 1;
        }
        return status;
    }

    /**
     * Register a new day of charts
     * @param date The date of the chart to be input
     * @return The newly created chart ID or -1 if chart already exists at that date
     */
    public int registerNewDayOfCharts(String date) {
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


    ///////////////////////////////////////////////////////////////////////


    /**
     * Close the connection when application finishes
     */
    public void closeConnection() { dbu.closeConnection(); }


    public void buy_shares(User u, Song s, int n) {
        String sql = "INSERT INTO buy (user_id, spotify_id, price, n_shares, purchase_time) VALUES" +
                "('"+ u.getUserID()+","+ s.getSpotifyID()+ ","
                + ", SELECT song_value FROM song WHERE song_id =" +
                s.getSpotifyID()+"," +"CURRENT_TIMESTAMP)";
    }

    public void sell_shares(User u, Song s, int n) {
        String sql = "INSERT INTO sell (user_id, spotify_id, price, n_shares, sale_time) VALUES" +
                "('"+ u.getUserID()+","+ s.getSpotifyID()+ ","
                + ", SELECT song_value FROM song WHERE song_id =" +
                s.getSpotifyID()+"," +"CURRENT_TIMESTAMP)";
    }

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
//

}
