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

  public List<Song> existingSongs() {

    // TODO - for this to work, need to set up the mysql-connector dependency in
    // Project Structure
    // for Reference:
    // https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea

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

        Song newSong = new Song(rs.getInt("spotify_id"), rs.getString("title"),
            rs.getInt("artist_id"), rs.getInt("song_value"), // rank
            rs.getInt("album_id"));

        // add stuff
//                mylist.add(name)

        // print stuff
        System.out.println("adding: " + newSong.toString());

      }

      rs.close();
      st.close();
      conn.close();

    } catch (SQLException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
    // could maybe do finally to close connection? idk
    return mylist;
  }

  public Connection getConnection() {
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
    System.out.println("INSERT STATEMENT: " + insertSQL);
    int key = -1;
    try {

      // get connection and initialize statement
      Connection con = getConnection();
      Statement stmt = con.createStatement();

      stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);

      // extract auto-incremented ID
      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next())
        key = rs.getInt(1);

      // Cleanup
      rs.close();
      stmt.close();

    } catch (SQLException e) {
      System.err.println("ERROR: Could not insert record: " + insertSQL);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    return key;
  }

  /**
   * For a table of terms consisting of an id and string value pair, get the id of
   * the term adding a new term if it does not yet exist in the table
   * 
   * @param table The table of terms
   * @param term  The term value
   * @return The id of the term
   */
  public int getOrInsertTerm(String table, String keyColumn, String valueColumn, String term) {
    int key = -1;

    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      String sqlGet = "SELECT " + keyColumn + " FROM " + table + " WHERE " + valueColumn + " = '"
          + term.toUpperCase() + "'";
      ResultSet rs = stmt.executeQuery(sqlGet);
      if (rs.next())
        key = rs.getInt(1);
      else {
        String sqlInsert = "INSERT INTO " + table + " (" + valueColumn + ") VALUES ('"
            + term.toUpperCase() + "')";
        stmt.executeUpdate(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        rs = stmt.getGeneratedKeys();
        if (rs.next())
          key = rs.getInt(1);
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

  // returns given user's current portfolio
  public HashMap<Song, Integer> user_port(User u) {

    HashMap<Song, Integer> h = new HashMap<Song, Integer>();
    
    try {
      Connection con = getConnection();
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

      rs.close();
      stmt.close();

      while (rs.next()) {
        int spotify_id = rs.getInt("spotify_id");
        String title = rs.getString("title");
        int artist_id = rs.getInt("artist_id");
        int album_id = rs.getInt("album_id");
        int song_value = rs.getInt("song_value");
        int owned = rs.getInt("shares_owned");
        Song temp = new Song(spotify_id, title, artist_id, song_value, album_id);
        
        h.put(temp, owned);

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
 
 //returns list of all songs
 public ArrayList<Song> all_songs(){
   ArrayList<Song> mylist = new ArrayList<Song>();
   
   try {
     Connection con = getConnection();
     Statement stmt = con.createStatement();
     String sqlGet = "Select * From song";
     ResultSet rs = stmt.executeQuery(sqlGet);

     rs.close();
     stmt.close();
   
     while (rs.next()) {
       int spotify_id = rs.getInt("spotify_id");
       String title = rs.getString("title");
       int artist_id = rs.getInt("artist_id");
       int album_id = rs.getInt("album_id");
       int song_value = rs.getInt("song_value");
       Song temp = new Song(spotify_id, title, artist_id, song_value, album_id);
       
       mylist.add(temp);

     }
   } catch (SQLException e) {
     System.err.println(e.getMessage());
     e.printStackTrace();
   }
   return mylist;
 }
   
 }
