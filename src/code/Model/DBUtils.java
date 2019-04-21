package code.Model;

import java.sql.*;

/**
 * This class is a utility class which we use mainly to deal with connecting to the MySQL database.
 * We have a Constructor, a method ConnectedHuh that was mostly useful for debugging purposes, ways to insert one record
 * and also open and close the connection.
 *
 * NOTE - DEPENDENCY - Need to have set up the mysql-connector dependency in
 *     Project Structure
 *       for Reference:
 *       https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea
 */
public class DBUtils {

private String url;
  private String user;
  private String password;
  private Connection con = null;

  /**
   * Constructor
   * @param url String, a url we are connecting to
   * @param user String, the MySQL username we are using
   * @param password String, the MySQL user's password
   */
  public DBUtils(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  /**
   * Returns a String representing whether we are connected. Should always be a Yes if the Constructor is set up properly.
   * @return a string, representing 'yea' or 'naw'
   */
  public String connectedHuh() {
    if (this.user == null || this.password == null || this.url == null) {
      return "Something's null, so not connected";
    } else {
      return "It's connected";
    }
  }

  /**
   * Gets a connection!
   * @return the connection
   */
  public Connection getConnection() {
    if (con == null) {
      try {
        con = DriverManager.getConnection(url, user, password);
        return con;
      } catch (SQLException e) {
        System.err.println("getConnection:" + e.getMessage());
        System.exit(1);
      }
    }
    return con;
  }

  /**
   * Closes the Connection!
   */
  public void closeConnection() {
    try {
      if (con != null) {
        con.close();
      }
    } catch (SQLException e) {
      System.err.println("closeConnection" + e.getMessage());
      e.printStackTrace();
    } finally {
      System.out.println("Closed that connection yo");
    }
  }

  /**
   * Inserts one Record, given an insert query-String
   * @param insertSQL the insert query-String
   * @return the status (as int)
   */
  public int insertOneRecord(String insertSQL) {
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
      System.err.println("GetOrInsertTerm: " + e.getMessage());
      e.printStackTrace();
    }

    return key;
  }
   
 }
