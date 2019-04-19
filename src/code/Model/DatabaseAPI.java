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




    /**
     * The Basic ToString method
     * @return String
     */
    String toString();

    // TODO - Purchase?

    // TODO - Sell?




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
}