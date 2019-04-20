package code.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

  /**
   * Temp fn, want to know if connected
   * @return
   */
  public String connectedHuh() {
    if (this.user == null || this.password == null || this.url == null) {
      return "Something's null, so not connected";
    } else {
      return "It's connected";
    }
  }

    // TODO - for this to work, need to set up the mysql-connector dependency in
    // Project Structure
    // for Reference:
    // https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea

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

    System.out.println("GetConnectionGettingGot::" + con.toString());
    return con;
  }

  public void closeConnection() {
    try {
      if (con != null) {
        con.close();
      }
    } catch (SQLException e) {
      System.err.println("closeConnection" + e.getMessage());
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
      System.err.println("GetOrInsertTerm: " + e.getMessage());
      e.printStackTrace();
    }

    return key;
  }
   
 }
