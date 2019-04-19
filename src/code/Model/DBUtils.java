package code.Model;

import java.sql.*;

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

    public int insertOneRecord(String insertSQL)
    {
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
    public int getOrInsertTerm(String table, String keyColumn, String valueColumn, String term)
    {

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
    
//inserts new user into database
  public void new_user(String name, String username, String email, String password) {
    String sql = "INSERT INTO user Value ("+name+"," +username+"," +email+"," +password+", CURDATE(), 5000)";
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
      System.err.println("ERROR: Coult not complete sale:" + sql);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

// returns album name of given song 
  public String song_album(Song s) {

    String value = null;
    
    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT album_name FROM song JOIN album Using(album_id) WHERE song_id ="
          + s.getSpotifyID();
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();
      
      while (rs.next()) {
        value = rs.getString("album_name");
     }

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
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT artist_name FROM song JOIN artist Using(artist_id) WHERE song_id ="
          + s.getSpotifyID();
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();
      
      while (rs.next()) {
        value = rs.getString("artist_name");
      }

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
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT date, day_value FROM song_history WHERE song_id =" + s.getSpotifyID()
          + "ORDER BY date DESC LIMIT 7";
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();

      while (rs.next()) {
        String d = rs.getString("date");
        int v = rs.getInt("day_value");
        h.put(d, v);
      }

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
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT date, portfolio_value FROM user_history WHERE user_id ="
          + u.getUserID() + "ORDER BY date DESC LIMIT 7";
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();

      while (rs.next()) {
        String d = rs.getString("date");
        int v = rs.getInt("portfolio_value");
        h.put(d, v);
  

      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    return h;
  }

  // returns given user's stock portfolio
  public HashMap<Integer, Integer> user_port(User u) {

    HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();
    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT spotify_id, user_bought - COALESCE(user_sold,0) AS shares_owned"
          + "FROM (SELECT user_id, spotify_id, SUM(n_shares) AS user_bought"
          + "  FROM buy GROUP BY user_id, spotify_id) AS total_bought"
          + "LEFT JOIN (SELECT user_id, spotify_id, SUM(n_shares) AS user_sold"
          + "  FROM sell GROUP BY user_id, spotify_id) AS total_sold"
          + "USING (user_id, spotify_id)WHERE user_id = " + u.getUserID();
      ResultSet rs = stmt.executeQuery(sqlGet);

      rs.close();
      stmt.close();

      while (rs.next()) {
        int s = rs.getInt("spotify_id");
        int n = rs.getInt("shares_owned");
        h.put(s, n);

      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    return h;
  }

//returns the value of the given user's portfolio
 public int port_value(User u) {

   Integer n =  null;
   
   try {
     Connection con = getConnection();
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

     rs.close();
     stmt.close();

     while (rs.next()) {
       n = rs.getInt("portfolio_value");

     }
   } catch (SQLException e) {
     System.err.println(e.getMessage());
     e.printStackTrace();
   }
   return n;
 }
}
