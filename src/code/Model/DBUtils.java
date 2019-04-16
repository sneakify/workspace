package code.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private String url;
    private String user;
    private String password;
    private Connection con = null;

    public DBUtils(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<Song> existingSongs() {

        // TODO - for this to work, need to set up the mysql-connector dependency in Project Structure
        // for Reference: https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea

        List<Song> mylist = new ArrayList<>();

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery("SELECT * FROM song");

            while (rs.next()) {
                // TODO - THIS IS WHAT TO LOOK AT, figure out how to retun this shit
                String spotify_id = rs.getString("spotify_id");
                String title = rs.getString("title");
                String artist_id = rs.getString("artist_id");
                String album_id = rs.getString("album_id");
                String song_value = rs.getString("song_value");


                // String spotifyID, String title, String artistID, int rank, String albumID)

                Song newSong = new Song(
                        rs.getString("spotify_id"),
                        rs.getString("title"),
                        rs.getString("artist_id"),
                        rs.getInt("song_value"), // rank
                        rs.getString("song_value"));

                // add stuff
//                mylist.add(name)

                // print stuff
                System.out.println("adding: " + newSong.toString());

            }

            rs.close();
            st.close();
            conn.close();

        } catch(SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // could maybe do finally to close connection? idk
        return mylist;
    }

    public Connection getConnection()
    {
        if (con == null) {
            try {
                con = DriverManager.getConnection(url, user, password);
                return con;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        return con;
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Closed that connedtion yo. Or it Was Null already");
        }
    }

    public int insertOneRecord(String insertSQL) {
        System.out.println("INSERT STATEMENT: "+insertSQL);
        int key = -1;
        try {

            // get connection and initialize statement
            Connection con = getConnection();
            Statement stmt = con.createStatement();

            stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

            // extract auto-incremented ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) key = rs.getInt(1);

            // Cleanup
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("ERROR: Could not insert record: "+insertSQL);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return key;
    }

    /**
     * For a table of terms consisting of an id and string value pair, get the id of the term
     * adding a new term if it does not yet exist in the table
     * @param table The table of terms
     * @param term The term value
     * @return The id of the term
     */
    public int getOrInsertTerm(String table, String keyColumn, String valueColumn, String term) {
        int key = -1;

        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            String sqlGet = "SELECT "+keyColumn+" FROM "+table+" WHERE "+valueColumn+" = '"+term.toUpperCase()+"'";
            ResultSet rs = stmt.executeQuery(sqlGet);
            if (rs.next())
                key = rs.getInt(1);
            else {
                String sqlInsert = "INSERT INTO "+table+" ("+valueColumn+") VALUES ('"+term.toUpperCase()+"')";
                stmt.executeUpdate(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if (rs.next()) key = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return key;
    }
    
 // inserts purchases from users into database
  public void buy_shares(User u, Song s, int n) {
    String sql = "INSERT INTO buy (user_id, spotify_id, price, n_shares, purchase_time) VALUES"
        + "('" + u.getUserID() + "," + s.getSpotifyID() + ","
        + "SELECT song_value FROM song WHERE song_id =" + s.getSpotifyID() + ","
        + "CURRENT_TIMESTAMP)";
    try {

      // get connection and initialize statement
      Connection con = getConnection();
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

// inserts sales from users into database
  public void sell_shares(User u, Song s, int n) {
    String sql = "INSERT INTO sell (user_id, spotify_id, price, n_shares, sale_time) VALUES" + "('"
        + u.getUserID() + "," + s.getSpotifyID() + ","
        + ", SELECT song_value FROM song WHERE song_id =" + s.getSpotifyID() + ","
        + "CURRENT_TIMESTAMP)";
    try {

      // get connection and initialize statement
      Connection con = getConnection();
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

// returns album name of given song (how do i return the output of sql query? aka rs)
  public String song_album(Song s) {

    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT album_name FROM song JOIN album Using(album_id) WHERE song_id ="
          + s.getSpotifyID();
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();

    }
  }

//returns artist name of given song (how do i return the output of sql query? aka rs)
  public String song_artist(Song s) {

    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT artist_name FROM song JOIN artist Using(artist_id) WHERE song_id ="
          + s.getSpotifyID();
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }
  
// returns the past 7 days of the given song's history (void for now not sure how to return this info)
  public void song7(Song s) {
    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT date, day_value FROM song_history WHERE song_id =" + s.getSpotifyID()+
          "ORDER BY date DESC LIMIT 7";
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }
  
//returns the past 7 days of the given user's portfolio (void for now not sure how to return this info)
 public void user7(User u) {
   try {
     Connection con = getConnection();
     Statement stmt = con.createStatement();
     String sqlGet = "SELECT date, portfolio_value FROM user_history WHERE user_id =" + u.getUserID()+
         "ORDER BY date DESC LIMIT 7";
     ResultSet rs = stmt.executeQuery(sqlGet);

     rs.close();
     stmt.close();

   } catch (SQLException e) {
     System.err.println(e.getMessage());
     e.printStackTrace();
   }
 }
}
