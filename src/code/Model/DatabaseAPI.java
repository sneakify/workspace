package code.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DatabaseAPI {

    /**
     * Find existing Songs on the given date
     * @param date Required date.  Better would be to accept null as "latest"
     * @return A list of Songs, those stored in the chart on the given date
     */
    public List<Song> existingSongs(java.sql.Date date);

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


    /**
     * The Basic ToString method
     * @return String
     */
    String toString();

    /**
     * Instantiates a new DBUtils object and stores it. Basically that establishes connection to MySQL database.
     * Prints to console whether you're connected.
     * @param user the String representing the MySQL Username
     * @param password The String representing the MySQL password
     */
    public void authenticate(String user, String password);

    /**
     * Close the connection when application finishes
     */
    public void closeConnection();

    /**
     * returns the genre of the given song
     * @param s, the given song object
     * @return the genre of the song as a String
     */
    String song_genre(Song s);

    /**
     *  updates the email and password of given user
     * @param u, given user
     * @param email, the new email the user would like to use
     * @param password, the new password the user wants
     */
    void update_user(User u, String email, String password);

    /**
     * returns list of all songs
     * @return an ArrayList that contains all songs
     */
    ArrayList<Song> all_songs();

    /**
     * returns the value of the given user's portfolio
     * @param u, the given user
     * @return the value of the given user's portfolio as an int
     */
    int port_value(User u);

    /**
     * Returns the portfolio of the given User in the form of a HashMap of Songs to Integer, The Integer represents the
     * Number of shares for that Song (for that user).
     * @param u the given user, active-buyer-person
     * @return The Hashmap of Song to number of shares
     */
    HashMap<Song, Integer> user_port(User u);

    /**
     * Returns the portfolio value history of the given User in the form of a HashMap of String(date) to Integer, The Integer represents the
     * portfolio value for that date (for that user).
     * @param u the given user, active-buyer-person
     * @return The Hashmap of date to portfolio value
     */
    HashMap<String, Integer> user7(User u);

    /**
     * Returns the song value history of the given Song in the form of a HashMap of String(date) to Integer, The Integer represents the
     * song value for that date (for that user).
     * @param s the given song
     * @return The Hashmap of date to portfolio value
     */
    HashMap<String, Integer> song7(Song s);

    /**
     *  returns artist name of given song
     * @param s, the given song
     * @return the artist of the song as a String
     */
    String song_artist(Song s);

    /**
     *  returns album name of given song
     * @param s, the given song
     * @return the album of the song as a String
     */
    String song_album(Song s);

    /**
     * inserts sales from users into database
     * @param u, given user
     * @param s, given song
     * @param n, number of shares
     * inserts into the sell table a new row that logs the info about the sale
     */
    void sell_shares(User u, Song s, int n);

    /**
     * inserts purchases from users into database
     * @param u, given user
     * @param s, given song
     * @param n, number of shares
     * inserts into the buy table a new row that logs the info about the purchase made
     */
    void buy_shares(User u, Song s, int n);

    /**
     * inserts new user into database
     * @param name
     * @param username
     * @param email
     * @param password
     * inserts a new row into the user table based off of the new user created
     */
    void new_user(String name, String username, String email, String password);

    }