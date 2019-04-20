package code.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model implements DatabaseAPI {
    public DBUtils dbu;
    public User myUser;
    private HashMap<Song, Integer> allTheSongs;

    public Model(int userID) {
        // Authenticate your access to the server.
        this.authenticate(Login.usr, Login.pword);

        this.myUser = this.getUser(userID);
        this.allTheSongs = new HashMap<>();
        this.dbu = new DBUtils(Login.url, Login.usr, Login.pword);

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

    private User getUser(int userID) {
        User u = null;
        try {
            Connection conn = dbu.getConnection();
            Statement st = conn.createStatement();
            String sqlGet = "Select * from user where user_id =" + userID + ";";

            ResultSet rs = st.executeQuery(sqlGet);

            while (rs.next()) {

                u = new User(rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("created"),
                        rs.getInt("purchasing_power"));
            }

            rs.close();
            st.close();
        } catch(SQLException e) {
            System.err.println("GetUser:" + e.getMessage());
            System.exit(1);
        }
        return u;
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

//    /**
//     * Register a new day of charts
//     * @param date The date of the chart to be input
//     * @return The newly created chart ID or -1 if chart already exists at that date
//     */
//    public int registerNewDayOfCharts(String date) {
//        // TODO stuff pls stub
//        return 1;
//
//    }


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



    //inserts new user into database
    public void new_user(String name, String username, String email, String password) {
        String sql = "INSERT INTO user Value (" + name + "," + username + "," + email + "," + password + ", CURDATE(), 5000)";
        try {

            // get connection and initialize statement
            Connection con = dbu.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);

            // cleanup
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Coult not complete purchase:" + sql);
            System.err.println("New User: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // inserts purchases from users into database
    @SuppressWarnings("Duplicates")
    public void buy_shares(User u, Song s, int n) {
        System.out.println("This is the thing" + u.toString() + s.toString() + String.valueOf(n));


        String sql = "INSERT INTO buy (user_id, spotify_id, price, n_shares, purchase_time) VALUES"
                + "('" + u.getUserID() + "," + s.getSpotifyID() + ","
                + "SELECT song_value FROM song WHERE spotify_id =" + s.getSpotifyID() + ","
                + "CURRENT_TIMESTAMP)";
        try {

            // get connection and initialize statement
            Connection con = dbu.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);

            // cleanup
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Coult not complete purchase:" + sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // inserts sales from users into database
    public void sell_shares(User u, Song s, int n) {
        System.out.println("This is the thing" + u.toString() + s.toString() + String.valueOf(n));

        String sql = "INSERT INTO sell (user_id, spotify_id, price, n_shares, sale_time) VALUES" + "('"
                + u.getUserID() + "," + s.getSpotifyID() + ","
                + ", SELECT song_value FROM song WHERE spotify_id =" + s.getSpotifyID() + ","
                + "CURRENT_TIMESTAMP)";

        try {

            // get connection and initialize statement
            Connection con = dbu.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);

            // cleanup
            stmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR: Coult not complete sale:" + sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // returns album name of given song
    public String song_album(Song s) {

        String value = null;

        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "SELECT album_name FROM song JOIN album Using(album_id) WHERE spotify_id ="
                    + s.getSpotifyID();
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                value = rs.getString("album_name");
            }

            rs.close();
            stmt.close();


        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        }
        return value;
    }

    //returns artist name of given song
    public String song_artist(Song s) {

        String value = null;
        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "SELECT artist_name FROM song JOIN artist Using(artist_id) WHERE spotify_id ="
                    + s.getSpotifyID();
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                value = rs.getString("artist_name");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return value;
    }

    // returns the past 7 days of the given song's history
    public HashMap<String, Integer> song7(Song s) {

        HashMap<String, Integer> h = new HashMap<String, Integer>();
        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "SELECT date, day_value FROM song_history WHERE spotify_id =" + s.getSpotifyID()
                    + "ORDER BY date DESC LIMIT 7";
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                String d = rs.getString("date");
                int v = rs.getInt("day_value");
                h.put(d, v);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return h;
    }

    //returns the past 7 days of the given user's portfolio
    public HashMap<String, Integer> user7(User u) {

        HashMap<String, Integer> h = new HashMap<String, Integer>();
        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "SELECT date, portfolio_value FROM user_history WHERE user_id ="
                    + u.getUserID() + "ORDER BY date DESC LIMIT 7";
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                String d = rs.getString("date");
                int v = rs.getInt("portfolio_value");
                h.put(d, v);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return h;
    }

    // returns given user's current portfolio
    public HashMap<Song, Integer> user_port(User u) {

        HashMap<Song, Integer> h = new HashMap<Song, Integer>();

        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "Select spotify_id, title, artist_id, album_id, song_value, user_bought - coalesce(user_sold,0) as shares_owned" +
                    "    From " +
                    "      (Select user_id, spotify_id, sum(n_shares) as user_bought" +
                    "      From buy" +
                    "      Group by user_id, spotify_id) as total_bought" +
                    "    Left Join " +
                    "      (Select user_id, spotify_id, sum(n_shares) as user_sold" +
                    "      From sell" +
                    "      Group by user_id, spotify_id) as total_sold" +
                    "    Using (user_id, spotify_id)" +
                    "    Join song Using (spotify_id)" +
                    "Where user_id = " + u.getUserID();
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                String spotify_id = rs.getString("spotify_id");
                String title = rs.getString("title");
                String artist_id = rs.getString("artist_id");
                String album_id = rs.getString("album_id");
                int song_value = rs.getInt("song_value");
                int owned = rs.getInt("shares_owned");
                Song temp = new Song(spotify_id, title, artist_id, song_value, album_id);

                h.put(temp, owned);

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return h;
    }

    //returns the value of the given user's portfolio
    public int port_value(User u) {

        Integer n = null;

        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "Select sum(song_total) + purchasing_power as portfolio_value" +
                    "  From" +
                    "    (Select user_id, title, shares_owned * song_value as song_total" +
                    "    From" +
                    "      (Select user_id, spotify_id, user_bought - coalesce(user_sold,0) as shares_owned\r\n" +
                    "      From" +
                    "        (Select user_id, spotify_id, sum(n_shares) as user_bought\r\n" +
                    "        From buy" +
                    "        Group by user_id, spotify_id) as total_bought" +
                    "      Left Join" +
                    "        (Select user_id, spotify_id, sum(n_shares) as user_sold" +
                    "        From sell" +
                    "        Group by user_id, spotify_id) as total_sold" +
                    "        Using (user_id, spotify_id)) as user_total" +
                    "    Join song Using (spotify_id)) as all_songs" +
                    "  Join user Using (user_id)" +
                    "Where user_id =" + u.getUserID();
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                n = rs.getInt("portfolio_value");

            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Port-Value Error" + e.getMessage());
            e.printStackTrace();
        }
        return n;
    }

    //returns list of all songs
    public ArrayList<Song> all_songs() {
        ArrayList<Song> mylist = new ArrayList<Song>();

        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "Select * From song";
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                String spotify_id = rs.getString("spotify_id");
                String title = rs.getString("title");
                String artist_id = rs.getString("artist_id");
                String album_id = rs.getString("album_id");
                int song_value = rs.getInt("song_value");
                Song temp = new Song(spotify_id, title, artist_id, song_value, album_id);

                mylist.add(temp);

            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("All songs" + e.getMessage());
            e.printStackTrace();
        }
        return mylist;
    }

    //returns the genre of the given song
    public String song_genre(Song s) {

        String value = null;
        try {
            Connection con = dbu.getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "Select genre_name " +
                    "From song " +
                    "Join artist USing(artist_id) " +
                    "Join genre Using(genre_id) " +
                    "Where spotify_id = " + s.getSpotifyID() + ";";
            ResultSet rs = stmt.executeQuery(sqlGet);

            while (rs.next()) {
                value = rs.getString("genre_name");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("song_genre" + e.getMessage());
            e.printStackTrace();
        }
        return value;
    }


    // updates the email and password of given user
    public void update_user(User u, String email, String password) {
        String sql = "Update user " +
                "Set email = "+ email+", password = "+password +
                " Where user_id = "+ u.getUserID()+";";
        try {

            // get connection and initialize statement
            Connection con = dbu.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);

            // cleanup
            stmt.close();
        }

        catch (SQLException e) {
            System.err.println("ERROR: Coult not complete purchase:" + sql);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
